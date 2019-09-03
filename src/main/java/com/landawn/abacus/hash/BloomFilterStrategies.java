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

import java.math.RoundingMode;
import java.util.Arrays;

import com.landawn.abacus.hash.Util.Ints;
import com.landawn.abacus.hash.Util.LongMath;
import com.landawn.abacus.hash.Util.Longs;
import com.landawn.abacus.util.N;
import com.landawn.abacus.util.function.BiConsumer;

// TODO: Auto-generated Javadoc
/**
 * Note: It's copied from Google Guava under Apache License 2.0
 * 
 * Collections of strategies of generating the k * log(M) bits required for an element to be mapped
 * to a BloomFilter of M bits and k hash functions. These strategies are part of the serialized form
 * of the Bloom filters that use them, thus they must be preserved as is (no updates allowed, only
 * introduction of new versions).
 *
 * Important: the order of the constants cannot change, and they cannot be deleted - we depend on
 * their ordinal for BloomFilter serialization.
 *
 * @author Dimitris Andreou
 * @author Kurt Alfred Kluever
 */
enum BloomFilterStrategies implements BloomFilter.Strategy {
    /**
     * See "Less Hashing, Same Performance: Building a Better Bloom Filter" by Adam Kirsch and Michael
     * Mitzenmacher. The paper argues that this trick doesn't significantly deteriorate the
     * performance of a Bloom filter (yet only needs two 32bit hash functions).
     */
    MURMUR128_MITZ_32() {
        @Override
        public <T> boolean put(T object, BiConsumer<? super T, ? super Hasher> funnel, int numHashFunctions, BitArray bits) {
            long bitSize = bits.bitSize();
            long hash64 = Hashing.murmur3_128().hash(object, funnel).asLong();
            int hash1 = (int) hash64;
            int hash2 = (int) (hash64 >>> 32);

            boolean bitsChanged = false;
            for (int i = 1; i <= numHashFunctions; i++) {
                int combinedHash = hash1 + (i * hash2);
                // Flip all the bits if it's negative (guaranteed positive number)
                if (combinedHash < 0) {
                    combinedHash = ~combinedHash;
                }
                bitsChanged |= bits.set(combinedHash % bitSize);
            }
            return bitsChanged;
        }

        @Override
        public <T> boolean mightContain(T object, BiConsumer<? super T, ? super Hasher> funnel, int numHashFunctions, BitArray bits) {
            long bitSize = bits.bitSize();
            long hash64 = Hashing.murmur3_128().hash(object, funnel).asLong();
            int hash1 = (int) hash64;
            int hash2 = (int) (hash64 >>> 32);

            for (int i = 1; i <= numHashFunctions; i++) {
                int combinedHash = hash1 + (i * hash2);
                // Flip all the bits if it's negative (guaranteed positive number)
                if (combinedHash < 0) {
                    combinedHash = ~combinedHash;
                }
                if (!bits.get(combinedHash % bitSize)) {
                    return false;
                }
            }
            return true;
        }
    },
    /**
     * This strategy uses all 128 bits of {@link Hashing#murmur3_128} when hashing. It looks different
     * than the implementation in MURMUR128_MITZ_32 because we're avoiding the multiplication in the
     * loop and doing a (much simpler) += hash2. We're also changing the index to a positive number by
     * AND'ing with Long.MAX_VALUE instead of flipping the bits.
     */
    MURMUR128_MITZ_64() {
        @Override
        public <T> boolean put(T object, BiConsumer<? super T, ? super Hasher> funnel, int numHashFunctions, BitArray bits) {
            long bitSize = bits.bitSize();
            byte[] bytes = Hashing.murmur3_128().hash(object, funnel).getBytesInternal();
            long hash1 = lowerEight(bytes);
            long hash2 = upperEight(bytes);

            boolean bitsChanged = false;
            long combinedHash = hash1;
            for (int i = 0; i < numHashFunctions; i++) {
                // Make the combined hash positive and indexable
                bitsChanged |= bits.set((combinedHash & Long.MAX_VALUE) % bitSize);
                combinedHash += hash2;
            }
            return bitsChanged;
        }

        @Override
        public <T> boolean mightContain(T object, BiConsumer<? super T, ? super Hasher> funnel, int numHashFunctions, BitArray bits) {
            long bitSize = bits.bitSize();
            byte[] bytes = Hashing.murmur3_128().hash(object, funnel).getBytesInternal();
            long hash1 = lowerEight(bytes);
            long hash2 = upperEight(bytes);

            long combinedHash = hash1;
            for (int i = 0; i < numHashFunctions; i++) {
                // Make the combined hash positive and indexable
                if (!bits.get((combinedHash & Long.MAX_VALUE) % bitSize)) {
                    return false;
                }
                combinedHash += hash2;
            }
            return true;
        }

        private /* static */ long lowerEight(byte[] bytes) {
            return Longs.fromBytes(bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]);
        }

        private /* static */ long upperEight(byte[] bytes) {
            return Longs.fromBytes(bytes[15], bytes[14], bytes[13], bytes[12], bytes[11], bytes[10], bytes[9], bytes[8]);
        }
    };

    /**
     * The Class BitArray.
     */
    // Note: We use this instead of java.util.BitSet because we need access to the long[] data field
    static final class BitArray {

        /** The data. */
        final long[] data;

        /** The bit count. */
        long bitCount;

        /**
         * Instantiates a new bit array.
         *
         * @param bits
         */
        BitArray(long bits) {
            this(new long[Ints.checkedCast(LongMath.divide(bits, 64, RoundingMode.CEILING))]);
        }

        /**
         * Instantiates a new bit array.
         *
         * @param data
         */
        // Used by serialization
        BitArray(long[] data) {
            N.checkArgument(data.length > 0, "data length is zero!");
            this.data = data;
            long bitCount = 0;
            for (long value : data) {
                bitCount += Long.bitCount(value);
            }
            this.bitCount = bitCount;
        }

        /**
         *  Returns true if the bit changed value.
         *
         * @param index
         * @return true, if successful
         */
        boolean set(long index) {
            if (!get(index)) {
                data[(int) (index >>> 6)] |= (1L << index);
                bitCount++;
                return true;
            }
            return false;
        }

        /**
         *
         * @param index
         * @return true, if successful
         */
        boolean get(long index) {
            return (data[(int) (index >>> 6)] & (1L << index)) != 0;
        }

        /**
         *  Number of bits.
         *
         * @return
         */
        long bitSize() {
            return (long) data.length * Long.SIZE;
        }

        /**
         *  Number of set bits (1s).
         *
         * @return
         */
        long bitCount() {
            return bitCount;
        }

        /**
         *
         * @return
         */
        BitArray copy() {
            return new BitArray(data.clone());
        }

        /**
         *  Combines the two BitArrays using bitwise OR.
         *
         * @param array
         */
        void putAll(BitArray array) {
            N.checkArgument(data.length == array.data.length, "BitArrays must be of equal length (%s != %s)", data.length, array.data.length);
            bitCount = 0;
            for (int i = 0; i < data.length; i++) {
                data[i] |= array.data[i];
                bitCount += Long.bitCount(data[i]);
            }
        }

        /**
         *
         * @param o
         * @return true, if successful
         */
        @Override
        public boolean equals(Object o) {
            if (o instanceof BitArray) {
                BitArray bitArray = (BitArray) o;
                return Arrays.equals(data, bitArray.data);
            }
            return false;
        }

        /**
         *
         * @return
         */
        @Override
        public int hashCode() {
            return Arrays.hashCode(data);
        }
    }
}
