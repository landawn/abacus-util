/*
 * Copyright (C) 2011 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.landawn.abacus.hash;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Arrays;

import com.landawn.abacus.annotation.SuppressFBWarnings;
import com.landawn.abacus.hash.BloomFilterStrategies.BitArray;
import com.landawn.abacus.hash.Util.SignedBytes;
import com.landawn.abacus.hash.Util.UnsignedBytes;
import com.landawn.abacus.util.N;
import com.landawn.abacus.util.function.BiConsumer;
import com.landawn.abacus.util.function.Predicate;

/**
 * Note: It's copied from Google Guava under Apache License 2.0
 * 
 * A Bloom filter for instances of {@code T}. A Bloom filter offers an approximate containment test
 * with one-sided error: if it claims that an element is contained in it, this might be in error,
 * but if it claims that an element is <i>not</i> contained in it, then this is definitely true.
 * 
 * <p>If you are unfamiliar with Bloom filters, this nice
 * <a href="http://llimllib.github.com/bloomfilter-tutorial/">tutorial</a> may help you understand
 * how they work.
 * 
 * <p>The false positive probability ({@code FPP}) of a bloom filter is defined as the probability
 * that {@linkplain #mightContain(Object)} will erroneously return {@code true} for an object that
 * has not actually been put in the {@code BloomFilter}.
 * 
 * <p>Bloom filters are serializable. They also support a more compact serial representation via the
 * {@link #writeTo} and {@link #readFrom} methods. Both serialized forms will continue to be
 * supported by future versions of this library. However, serial forms generated by newer versions
 * of the code may not be readable by older versions of the code (e.g., a serialized bloom filter
 * generated today may <i>not</i> be readable by a binary that was compiled 6 months ago).
 *
 * @author Dimitris Andreou
 * @author Kevin Bourrillion
 * @param <T> the type of instances that the {@code BloomFilter} accepts
 * @since 11.0
 */
@SuppressFBWarnings("SE_BAD_FIELD")
public final class BloomFilter<T> implements Predicate<T>, Serializable {

    private static final long serialVersionUID = 8191335744261522602L;

    /**
     * A strategy to translate T instances, to {@code numHashFunctions} bit indexes.
     *
     * <p>Implementations should be collections of pure functions (i.e. stateless).
     */
    interface Strategy extends java.io.Serializable {

        /**
         * Sets {@code numHashFunctions} bits of the given bit array, by hashing a user element.
         * 
         * <p>Returns whether any bits changed as a result of this operation.
         *
         * @param <T>
         * @param object
         * @param funnel
         * @param numHashFunctions
         * @param bits
         * @return true, if successful
         */
        <T> boolean put(T object, BiConsumer<? super T, ? super Hasher> funnel, int numHashFunctions, BitArray bits);

        /**
         * Queries {@code numHashFunctions} bits of the given bit array, by hashing a user element;
         * returns {@code true} if and only if all selected bits are set.
         *
         * @param <T>
         * @param object
         * @param funnel
         * @param numHashFunctions
         * @param bits
         * @return true, if successful
         */
        <T> boolean mightContain(T object, BiConsumer<? super T, ? super Hasher> funnel, int numHashFunctions, BitArray bits);

        /**
         * Identifier used to encode this strategy, when marshalled as part of a BloomFilter. Only
         * values in the [-128, 127] range are valid for the compact serial form. Non-negative values
         * are reserved for enums defined in BloomFilterStrategies; negative values are reserved for any
         * custom, stateful strategy we may define (e.g. any kind of strategy that would depend on user
         * input).
         *
         * @return
         */
        int ordinal();
    }

    /**  The bit set of the BloomFilter (not necessarily power of 2!). */
    private final BitArray bits;

    /**  Number of hashes per element. */
    private final int numHashFunctions;

    /**  The funnel to translate Ts to bytes. */
    private final BiConsumer<? super T, ? super Hasher> funnel;

    /**
     * The strategy we employ to map an element T to {@code numHashFunctions} bit indexes.
     */
    private final Strategy strategy;

    /**
     * Creates a BloomFilter.
     *
     * @param bits
     * @param numHashFunctions
     * @param funnel
     * @param strategy
     */
    private BloomFilter(BitArray bits, int numHashFunctions, BiConsumer<? super T, ? super Hasher> funnel, Strategy strategy) {
        N.checkArgument(numHashFunctions > 0, "numHashFunctions (%s) must be > 0", numHashFunctions);
        N.checkArgument(numHashFunctions <= 255, "numHashFunctions (%s) must be <= 255", numHashFunctions);
        this.bits = N.checkArgNotNull(bits);
        this.numHashFunctions = numHashFunctions;
        this.funnel = N.checkArgNotNull(funnel);
        this.strategy = N.checkArgNotNull(strategy);
    }

    /**
     * Creates a new {@code BloomFilter} that's a copy of this instance. The new instance is equal to
     * this instance but shares no mutable state.
     *
     * @return
     * @since 12.0
     */
    public BloomFilter<T> copy() {
        return new BloomFilter<T>(bits.copy(), numHashFunctions, funnel, strategy);
    }

    /**
     * Returns {@code true} if the element <i>might</i> have been put in this Bloom filter,
     * {@code false} if this is <i>definitely</i> not the case.
     *
     * @param object
     * @return true, if successful
     */
    public boolean mightContain(T object) {
        return strategy.mightContain(object, funnel, numHashFunctions, bits);
    }

    /**
     *
     * @param input
     * @return true, if successful
     * @deprecated Provided only to satisfy the {@link Predicate} interface; use {@link #mightContain}
     *     instead.
     */
    @Deprecated
    @Override
    public boolean test(T input) {
        return mightContain(input);
    }

    /**
     * Puts an element into this {@code BloomFilter}. Ensures that subsequent invocations of
     * {@link #mightContain(Object)} with the same element will always return {@code true}.
     *
     * @param object
     * @return true if the bloom filter's bits changed as a result of this operation. If the bits
     *     changed, this is <i>definitely</i> the first time {@code object} has been added to the
     *     filter. If the bits haven't changed, this <i>might</i> be the first time {@code object} has
     *     been added to the filter. Note that {@code put(t)} always returns the <i>opposite</i>
     *     result to what {@code mightContain(t)} would have returned at the time it is called."
     * @since 12.0 (present in 11.0 with {@code void} return type})
     */

    public boolean put(T object) {
        return strategy.put(object, funnel, numHashFunctions, bits);
    }

    /**
     * Returns the probability that {@linkplain #mightContain(Object)} will erroneously return
     * {@code true} for an object that has not actually been put in the {@code BloomFilter}.
     * 
     * <p>Ideally, this number should be close to the {@code fpp} parameter passed in
     * {@linkplain #create(BiConsumer, int, double)}, or smaller. If it is significantly higher, it is
     * usually the case that too many elements (more than expected) have been put in the
     * {@code BloomFilter}, degenerating it.
     *
     * @return
     * @since 14.0 (since 11.0 as expectedFalsePositiveProbability())
     */
    public double expectedFpp() {
        // You down with FPP? (Yeah you know me!) Who's down with FPP? (Every last homie!)
        return Math.pow((double) bits.bitCount() / bitSize(), numHashFunctions);
    }

    /**
     * Returns the number of bits in the underlying bit array.
     *
     * @return
     */
    long bitSize() {
        return bits.bitSize();
    }

    /**
     * Determines whether a given bloom filter is compatible with this bloom filter. For two bloom
     * filters to be compatible, they must:
     * 
     * <ul>
     * <li>not be the same instance
     * <li>have the same number of hash functions
     * <li>have the same bit size
     * <li>have the same strategy
     * <li>have equal funnels
     * <ul>
     *
     * @param that The bloom filter to check for compatibility.
     * @return true, if is compatible
     * @since 15.0
     */
    public boolean isCompatible(BloomFilter<T> that) {
        N.checkArgNotNull(that);
        return (this != that) && (this.numHashFunctions == that.numHashFunctions) && (this.bitSize() == that.bitSize()) && (this.strategy.equals(that.strategy))
                && (this.funnel.equals(that.funnel));
    }

    /**
     * Combines this bloom filter with another bloom filter by performing a bitwise OR of the
     * underlying data. The mutations happen to <b>this</b> instance. Callers must ensure the bloom
     * filters are appropriately sized to avoid saturating them.
     *
     * @param that The bloom filter to combine this bloom filter with. It is not mutated.
     * @throws IllegalArgumentException if {@code isCompatible(that) == false}
     *
     * @since 15.0
     */
    public void putAll(BloomFilter<T> that) {
        N.checkArgNotNull(that);
        N.checkArgument(this != that, "Cannot combine a BloomFilter with itself.");
        N.checkArgument(this.numHashFunctions == that.numHashFunctions, "BloomFilters must have the same number of hash functions (%s != %s)",
                this.numHashFunctions, that.numHashFunctions);
        N.checkArgument(this.bitSize() == that.bitSize(), "BloomFilters must have the same size underlying bit arrays (%s != %s)", this.bitSize(),
                that.bitSize());
        N.checkArgument(this.strategy.equals(that.strategy), "BloomFilters must have equal strategies (%s != %s)", this.strategy, that.strategy);
        N.checkArgument(this.funnel.equals(that.funnel), "BloomFilters must have equal funnels (%s != %s)", this.funnel, that.funnel);
        this.bits.putAll(that.bits);
    }

    /**
     *
     * @param object
     * @return true, if successful
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof BloomFilter) {
            BloomFilter<?> that = (BloomFilter<?>) object;
            return this.numHashFunctions == that.numHashFunctions && this.funnel.equals(that.funnel) && this.bits.equals(that.bits)
                    && this.strategy.equals(that.strategy);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(N.asArray(numHashFunctions, funnel, strategy, bits));
    }

    /**
     * Creates a {@link BloomFilter BloomFilter<T>} with the expected number of insertions and
     * expected false positive probability.
     * 
     * <p>Note that overflowing a {@code BloomFilter} with significantly more elements than specified,
     * will result in its saturation, and a sharp deterioration of its false positive probability.
     * 
     * <p>The constructed {@code BloomFilter<T>} will be serializable if the provided
     * {@code BiConsumer<? super T, ? super Hasher>} is.
     * 
     * <p>It is recommended that the funnel be implemented as a Java enum. This has the benefit of
     * ensuring proper serialization and deserialization, which is important since {@link #equals}
     * also relies on object identity of funnels.
     *
     * @param <T>
     * @param funnel the funnel of T's that the constructed {@code BloomFilter<T>} will use
     * @param expectedInsertions the number of expected insertions to the constructed
     *     {@code BloomFilter<T>}; must be positive
     * @param fpp the desired false positive probability (must be positive and less than 1.0)
     * @return a {@code BloomFilter}
     */
    public static <T> BloomFilter<T> create(BiConsumer<? super T, ? super Hasher> funnel, int expectedInsertions, double fpp) {
        return create(funnel, (long) expectedInsertions, fpp);
    }

    /**
     * Creates a {@link BloomFilter BloomFilter<T>} with the expected number of insertions and
     * expected false positive probability.
     * 
     * <p>Note that overflowing a {@code BloomFilter} with significantly more elements than specified,
     * will result in its saturation, and a sharp deterioration of its false positive probability.
     * 
     * <p>The constructed {@code BloomFilter<T>} will be serializable if the provided
     * {@code BiConsumer<? super T, ? super Hasher>} is.
     * 
     * <p>It is recommended that the funnel be implemented as a Java enum. This has the benefit of
     * ensuring proper serialization and deserialization, which is important since {@link #equals}
     * also relies on object identity of funnels.
     *
     * @param <T>
     * @param funnel the funnel of T's that the constructed {@code BloomFilter<T>} will use
     * @param expectedInsertions the number of expected insertions to the constructed
     *     {@code BloomFilter<T>}; must be positive
     * @param fpp the desired false positive probability (must be positive and less than 1.0)
     * @return a {@code BloomFilter}
     * @since 19.0
     */
    public static <T> BloomFilter<T> create(BiConsumer<? super T, ? super Hasher> funnel, long expectedInsertions, double fpp) {
        return create(funnel, expectedInsertions, fpp, BloomFilterStrategies.MURMUR128_MITZ_64);
    }

    /**
     *
     * @param <T>
     * @param funnel
     * @param expectedInsertions
     * @param fpp
     * @param strategy
     * @return
     */
    static <T> BloomFilter<T> create(BiConsumer<? super T, ? super Hasher> funnel, long expectedInsertions, double fpp, Strategy strategy) {
        N.checkArgNotNull(funnel);
        N.checkArgument(expectedInsertions >= 0, "Expected insertions (%s) must be >= 0", expectedInsertions);
        N.checkArgument(fpp > 0.0, "False positive probability (%s) must be > 0.0", fpp);
        N.checkArgument(fpp < 1.0, "False positive probability (%s) must be < 1.0", fpp);
        N.checkArgNotNull(strategy);

        if (expectedInsertions == 0) {
            expectedInsertions = 1;
        }
        /*
         * TODO(user): Put a warning in the javadoc about tiny fpp values, since the resulting size
         * is proportional to -log(p), but there is not much of a point after all, e.g.
         * optimalM(1000, 0.0000000000000001) = 76680 which is less than 10kb. Who cares!
         */
        long numBits = optimalNumOfBits(expectedInsertions, fpp);
        int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);
        try {
            return new BloomFilter<T>(new BitArray(numBits), numHashFunctions, funnel, strategy);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Could not create BloomFilter of " + numBits + " bits", e);
        }
    }

    /**
     * Creates a {@link BloomFilter BloomFilter<T>} with the expected number of insertions and a
     * default expected false positive probability of 3%.
     * 
     * <p>Note that overflowing a {@code BloomFilter} with significantly more elements than specified,
     * will result in its saturation, and a sharp deterioration of its false positive probability.
     * 
     * <p>The constructed {@code BloomFilter<T>} will be serializable if the provided
     * {@code BiConsumer<? super T, ? super Hasher>} is.
     * 
     * <p>It is recommended that the funnel be implemented as a Java enum. This has the benefit of
     * ensuring proper serialization and deserialization, which is important since {@link #equals}
     * also relies on object identity of funnels.
     *
     * @param <T>
     * @param funnel the funnel of T's that the constructed {@code BloomFilter<T>} will use
     * @param expectedInsertions the number of expected insertions to the constructed
     *     {@code BloomFilter<T>}; must be positive
     * @return a {@code BloomFilter}
     */
    public static <T> BloomFilter<T> create(BiConsumer<? super T, ? super Hasher> funnel, int expectedInsertions) {
        return create(funnel, (long) expectedInsertions);
    }

    /**
     * Creates a {@link BloomFilter BloomFilter<T>} with the expected number of insertions and a
     * default expected false positive probability of 3%.
     * 
     * <p>Note that overflowing a {@code BloomFilter} with significantly more elements than specified,
     * will result in its saturation, and a sharp deterioration of its false positive probability.
     * 
     * <p>The constructed {@code BloomFilter<T>} will be serializable if the provided
     * {@code BiConsumer<? super T, ? super Hasher>} is.
     * 
     * <p>It is recommended that the funnel be implemented as a Java enum. This has the benefit of
     * ensuring proper serialization and deserialization, which is important since {@link #equals}
     * also relies on object identity of funnels.
     *
     * @param <T>
     * @param funnel the funnel of T's that the constructed {@code BloomFilter<T>} will use
     * @param expectedInsertions the number of expected insertions to the constructed
     *     {@code BloomFilter<T>}; must be positive
     * @return a {@code BloomFilter}
     * @since 19.0
     */
    public static <T> BloomFilter<T> create(BiConsumer<? super T, ? super Hasher> funnel, long expectedInsertions) {
        return create(funnel, expectedInsertions, 0.03); // FYI, for 3%, we always get 5 hash functions
    }

    // Cheat sheet:
    //
    // m: total bits
    // n: expected insertions
    // b: m/n, bits per insertion
    // p: expected false positive probability
    //
    // 1) Optimal k = b * ln2
    // 2) p = (1 - e ^ (-kn/m))^k
    // 3) For optimal k: p = 2 ^ (-k) ~= 0.6185^b
    // 4) For optimal k: m = -nlnp / ((ln2) ^ 2)

    /**
     * Computes the optimal k (number of hashes per element inserted in Bloom filter), given the
     * expected insertions and total number of bits in the Bloom filter.
     * 
     * See http://en.wikipedia.org/wiki/File:Bloom_filter_fp_probability.svg for the formula.
     *
     * @param n expected insertions (must be positive)
     * @param m total number of bits in Bloom filter (must be positive)
     * @return
     */
    static int optimalNumOfHashFunctions(long n, long m) {
        // (m / n) * log(2), but avoid truncation due to division!
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }

    /**
     * Computes m (total bits of Bloom filter) which is expected to achieve, for the specified
     * expected insertions, the required false positive probability.
     * 
     * See http://en.wikipedia.org/wiki/Bloom_filter#Probability_of_false_positives for the formula.
     *
     * @param n expected insertions (must be positive)
     * @param p false positive rate (must be 0 < p < 1)
     * @return
     */
    static long optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

    private Object writeReplace() {
        return new SerialForm<T>(this);
    }

    /**
     * The Class SerialForm.
     *
     * @param <T>
     */
    private static class SerialForm<T> implements Serializable {

        /** The data. */
        final long[] data;

        /** The num hash functions. */
        final int numHashFunctions;

        /** The funnel. */
        final BiConsumer<? super T, ? super Hasher> funnel;

        /** The strategy. */
        final Strategy strategy;

        /**
         * Instantiates a new serial form.
         *
         * @param bf
         */
        SerialForm(BloomFilter<T> bf) {
            this.data = bf.bits.data;
            this.numHashFunctions = bf.numHashFunctions;
            this.funnel = bf.funnel;
            this.strategy = bf.strategy;
        }

        /**
         *
         * @return
         */
        Object readResolve() {
            return new BloomFilter<T>(new BitArray(data), numHashFunctions, funnel, strategy);
        }

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1;
    }

    /**
     * Writes this {@code BloomFilter} to an output stream, with a custom format (not Java
     * serialization). This has been measured to save at least 400 bytes compared to regular
     * serialization.
     * 
     * <p>Use {@linkplain #readFrom(InputStream, BiConsumer)} to reconstruct the written BloomFilter.
     *
     * @param out
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void writeTo(OutputStream out) throws IOException {
        // Serial form:
        // 1 signed byte for the strategy
        // 1 unsigned byte for the number of hash functions
        // 1 big endian int, the number of longs in our bitset
        // N big endian longs of our bitset
        DataOutputStream dout = new DataOutputStream(out);
        dout.writeByte(SignedBytes.checkedCast(strategy.ordinal()));
        dout.writeByte(UnsignedBytes.checkedCast(numHashFunctions)); // note: checked at the c'tor
        dout.writeInt(bits.data.length);
        for (long value : bits.data) {
            dout.writeLong(value);
        }
    }

    /**
     * Reads a byte stream, which was written by {@linkplain #writeTo(OutputStream)}, into a
     * {@code BloomFilter<T>}.
     * 
     * The {@code BiConsumer} to be used is not encoded in the stream, so it must be provided here.
     * <b>Warning:</b> the funnel provided <b>must</b> behave identically to the one used to populate
     * the original Bloom filter!
     *
     * @param <T>
     * @param in
     * @param funnel
     * @return
     * @throws IOException if the InputStream throws an {@code IOException}, or if its data does not
     *     appear to be a BloomFilter serialized using the {@linkplain #writeTo(OutputStream)} method.
     */
    public static <T> BloomFilter<T> readFrom(InputStream in, BiConsumer<? super T, ? super Hasher> funnel) throws IOException {
        N.checkArgNotNull(in, "InputStream");
        N.checkArgNotNull(funnel, "BiConsumer");
        int strategyOrdinal = -1;
        int numHashFunctions = -1;
        int dataLength = -1;
        try {
            DataInputStream din = new DataInputStream(in);
            // currently this assumes there is no negative ordinal; will have to be updated if we
            // add non-stateless strategies (for which we've reserved negative ordinals; see
            // Strategy.ordinal()).
            strategyOrdinal = din.readByte();
            numHashFunctions = UnsignedBytes.toInt(din.readByte());
            dataLength = din.readInt();

            Strategy strategy = BloomFilterStrategies.values()[strategyOrdinal];
            long[] data = new long[dataLength];
            for (int i = 0; i < data.length; i++) {
                data[i] = din.readLong();
            }
            return new BloomFilter<T>(new BitArray(data), numHashFunctions, funnel, strategy);
        } catch (RuntimeException e) {
            String message = "Unable to deserialize BloomFilter from InputStream." + " strategyOrdinal: " + strategyOrdinal + " numHashFunctions: "
                    + numHashFunctions + " dataLength: " + dataLength;
            throw new IOException(message, e);
        }
    }
}
