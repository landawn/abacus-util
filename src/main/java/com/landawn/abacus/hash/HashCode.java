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

import java.io.Serializable;

import com.landawn.abacus.hash.Util.UnsignedInts;
import com.landawn.abacus.util.N;

/**
 * Note: It's copied from Google Guava under Apache License 2.0
 * 
 * An immutable hash code of arbitrary bit length.
 *
 * @author Dimitris Andreou
 * @author Kurt Alfred Kluever
 * @since 11.0
 */
public abstract class HashCode {

    /**
     * Instantiates a new hash code.
     */
    HashCode() {
    }

    /**
     * Returns the number of bits in this hash code; a positive multiple of 8.
     *
     * @return
     */
    public abstract int bits();

    /**
     * Returns the first four bytes of {@linkplain #asBytes() this hashcode's bytes}, converted to an
     * {@code int} value in little-endian order.
     *
     * @return
     * @throws IllegalStateException if {@code bits() < 32}
     */
    public abstract int asInt();

    /**
     * Returns the first eight bytes of {@linkplain #asBytes() this hashcode's bytes}, converted to a
     * {@code long} value in little-endian order.
     *
     * @return
     * @throws IllegalStateException if {@code bits() < 64}
     */
    public abstract long asLong();

    /**
     * If this hashcode has enough bits, returns {@code asLong()}, otherwise returns a {@code long}
     * value with {@code asBytes()} as the least-significant bytes and {@code 0x00} as the remaining
     * most-significant bytes.
     *
     * @return
     * @since 14.0 (since 11.0 as {@code Hashing.padToLong(HashCode)})
     */
    public abstract long padToLong();

    /**
     * Returns the value of this hash code as a byte array. The caller may modify the byte array;
     * changes to it will <i>not</i> be reflected in this {@code HashCode} object or any other arrays
     * returned by this method.
     *
     * @return
     */
    // TODO(user): consider ByteString here, when that is available
    public abstract byte[] asBytes();

    /**
     * Copies bytes from this hash code into {@code dest}.
     *
     * @param dest the byte array into which the hash code will be written
     * @param offset the start offset in the data
     * @param maxLength the maximum number of bytes to write
     * @return
     * @throws IndexOutOfBoundsException if there is not enough room in {@code dest}
     */

    public int writeBytesTo(byte[] dest, int offset, int maxLength) {
        maxLength = N.min(maxLength, bits() / 8);
        Util.checkPositionIndexes(offset, offset + maxLength, dest.length);
        writeBytesToImpl(dest, offset, maxLength);
        return maxLength;
    }

    /**
     * Write bytes to impl.
     *
     * @param dest
     * @param offset
     * @param maxLength
     */
    abstract void writeBytesToImpl(byte[] dest, int offset, int maxLength);

    /**
     * Returns a mutable view of the underlying bytes for the given {@code HashCode} if it is a
     * byte-based hashcode. Otherwise it returns {@link HashCode#asBytes}. Do <i>not</i> mutate this
     * array or else you will break the immutability contract of {@code HashCode}.
     *
     * @return
     */
    byte[] getBytesInternal() {
        return asBytes();
    }

    /**
     * Returns whether this {@code HashCode} and that {@code HashCode} have the same value, given that
     * they have the same number of bits.
     *
     * @param that
     * @return true, if successful
     */
    abstract boolean equalsSameBits(HashCode that);

    /**
     * Creates a 32-bit {@code HashCode} representation of the given int value. The underlying bytes
     * are interpreted in little endian order.
     *
     * @param hash
     * @return
     * @since 15.0 (since 12.0 in HashCodes)
     */
    public static HashCode fromInt(int hash) {
        return new IntHashCode(hash);
    }

    /**
     * The Class IntHashCode.
     */
    private static final class IntHashCode extends HashCode implements Serializable {

        /** The hash. */
        final int hash;

        /**
         * Instantiates a new int hash code.
         *
         * @param hash
         */
        IntHashCode(int hash) {
            this.hash = hash;
        }

        /**
         *
         * @return
         */
        @Override
        public int bits() {
            return 32;
        }

        /**
         *
         * @return
         */
        @Override
        public byte[] asBytes() {
            return new byte[] { (byte) hash, (byte) (hash >> 8), (byte) (hash >> 16), (byte) (hash >> 24) };
        }

        /**
         *
         * @return
         */
        @Override
        public int asInt() {
            return hash;
        }

        /**
         *
         * @return
         */
        @Override
        public long asLong() {
            throw new IllegalStateException("this HashCode only has 32 bits; cannot create a long");
        }

        /**
         * Pad to long.
         *
         * @return
         */
        @Override
        public long padToLong() {
            return UnsignedInts.toLong(hash);
        }

        /**
         * Write bytes to impl.
         *
         * @param dest
         * @param offset
         * @param maxLength
         */
        @Override
        void writeBytesToImpl(byte[] dest, int offset, int maxLength) {
            for (int i = 0; i < maxLength; i++) {
                dest[offset + i] = (byte) (hash >> (i * 8));
            }
        }

        /**
         * Equals same bits.
         *
         * @param that
         * @return true, if successful
         */
        @Override
        boolean equalsSameBits(HashCode that) {
            return hash == that.asInt();
        }

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 0;
    }

    /**
     * Creates a 64-bit {@code HashCode} representation of the given long value. The underlying bytes
     * are interpreted in little endian order.
     *
     * @param hash
     * @return
     * @since 15.0 (since 12.0 in HashCodes)
     */
    public static HashCode fromLong(long hash) {
        return new LongHashCode(hash);
    }

    /**
     * The Class LongHashCode.
     */
    private static final class LongHashCode extends HashCode implements Serializable {

        /** The hash. */
        final long hash;

        /**
         * Instantiates a new long hash code.
         *
         * @param hash
         */
        LongHashCode(long hash) {
            this.hash = hash;
        }

        /**
         *
         * @return
         */
        @Override
        public int bits() {
            return 64;
        }

        /**
         *
         * @return
         */
        @Override
        public byte[] asBytes() {
            return new byte[] { (byte) hash, (byte) (hash >> 8), (byte) (hash >> 16), (byte) (hash >> 24), (byte) (hash >> 32), (byte) (hash >> 40),
                    (byte) (hash >> 48), (byte) (hash >> 56) };
        }

        /**
         *
         * @return
         */
        @Override
        public int asInt() {
            return (int) hash;
        }

        /**
         *
         * @return
         */
        @Override
        public long asLong() {
            return hash;
        }

        /**
         * Pad to long.
         *
         * @return
         */
        @Override
        public long padToLong() {
            return hash;
        }

        /**
         * Write bytes to impl.
         *
         * @param dest
         * @param offset
         * @param maxLength
         */
        @Override
        void writeBytesToImpl(byte[] dest, int offset, int maxLength) {
            for (int i = 0; i < maxLength; i++) {
                dest[offset + i] = (byte) (hash >> (i * 8));
            }
        }

        /**
         * Equals same bits.
         *
         * @param that
         * @return true, if successful
         */
        @Override
        boolean equalsSameBits(HashCode that) {
            return hash == that.asLong();
        }

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 0;
    }

    /**
     * Creates a {@code HashCode} from a byte array. The array is defensively copied to preserve the
     * immutability contract of {@code HashCode}. The array cannot be empty.
     *
     * @param bytes
     * @return
     * @since 15.0 (since 12.0 in HashCodes)
     */
    public static HashCode fromBytes(byte[] bytes) {
        N.checkArgument(bytes.length >= 1, "A HashCode must contain at least 1 byte.");
        return fromBytesNoCopy(bytes.clone());
    }

    /**
     * Creates a {@code HashCode} from a byte array. The array is <i>not</i> copied defensively, so it
     * must be handed-off so as to preserve the immutability contract of {@code HashCode}.
     *
     * @param bytes
     * @return
     */
    static HashCode fromBytesNoCopy(byte[] bytes) {
        return new BytesHashCode(bytes);
    }

    /**
     * The Class BytesHashCode.
     */
    private static final class BytesHashCode extends HashCode implements Serializable {

        /** The bytes. */
        final byte[] bytes;

        /**
         * Instantiates a new bytes hash code.
         *
         * @param bytes
         */
        BytesHashCode(byte[] bytes) {
            this.bytes = N.checkArgNotNull(bytes);
        }

        /**
         *
         * @return
         */
        @Override
        public int bits() {
            return bytes.length * 8;
        }

        /**
         *
         * @return
         */
        @Override
        public byte[] asBytes() {
            return bytes.clone();
        }

        /**
         *
         * @return
         */
        @Override
        public int asInt() {
            N.checkState(bytes.length >= 4, "HashCode#asInt() requires >= 4 bytes (it only has %s bytes).", bytes.length);
            return (bytes[0] & 0xFF) | ((bytes[1] & 0xFF) << 8) | ((bytes[2] & 0xFF) << 16) | ((bytes[3] & 0xFF) << 24);
        }

        /**
         *
         * @return
         */
        @Override
        public long asLong() {
            N.checkState(bytes.length >= 8, "HashCode#asLong() requires >= 8 bytes (it only has %s bytes).", bytes.length);
            return padToLong();
        }

        /**
         * Pad to long.
         *
         * @return
         */
        @Override
        public long padToLong() {
            long retVal = (bytes[0] & 0xFF);
            for (int i = 1; i < Math.min(bytes.length, 8); i++) {
                retVal |= (bytes[i] & 0xFFL) << (i * 8);
            }
            return retVal;
        }

        /**
         * Write bytes to impl.
         *
         * @param dest
         * @param offset
         * @param maxLength
         */
        @Override
        void writeBytesToImpl(byte[] dest, int offset, int maxLength) {
            System.arraycopy(bytes, 0, dest, offset, maxLength);
        }

        /**
         * Gets the bytes internal.
         *
         * @return
         */
        @Override
        byte[] getBytesInternal() {
            return bytes;
        }

        /**
         * Equals same bits.
         *
         * @param that
         * @return true, if successful
         */
        @Override
        boolean equalsSameBits(HashCode that) {
            // We don't use MessageDigest.isEqual() here because its contract does not guarantee
            // constant-time evaluation (no short-circuiting).
            if (this.bytes.length != that.getBytesInternal().length) {
                return false;
            }

            boolean areEqual = true;
            for (int i = 0; i < this.bytes.length; i++) {
                areEqual &= (this.bytes[i] == that.getBytesInternal()[i]);
            }
            return areEqual;
        }

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 0;
    }

    /**
     * Creates a {@code HashCode} from a hexadecimal ({@code base 16}) encoded string. The string must
     * be at least 2 characters long, and contain only valid, lower-cased hexadecimal characters.
     * 
     * <p>This method accepts the exact format generated by {@link #toString}. If you require more
     * lenient {@code base 16} decoding, please use {@link com.google.common.io.BaseEncoding#decode}
     * (and pass the result to {@link #fromBytes}).
     *
     * @param string
     * @return
     * @since 15.0
     */
    public static HashCode fromString(String string) {
        N.checkArgument(string.length() >= 2, "input string (%s) must have at least 2 characters", string);
        N.checkArgument(string.length() % 2 == 0, "input string (%s) must have an even number of characters", string);

        byte[] bytes = new byte[string.length() / 2];
        for (int i = 0; i < string.length(); i += 2) {
            int ch1 = decode(string.charAt(i)) << 4;
            int ch2 = decode(string.charAt(i + 1));
            bytes[i / 2] = (byte) (ch1 + ch2);
        }
        return fromBytesNoCopy(bytes);
    }

    /**
     *
     * @param ch
     * @return
     */
    private static int decode(char ch) {
        if (ch >= '0' && ch <= '9') {
            return ch - '0';
        }
        if (ch >= 'a' && ch <= 'f') {
            return ch - 'a' + 10;
        }
        throw new IllegalArgumentException("Illegal hexadecimal character: " + ch);
    }

    /**
     * Returns {@code true} if {@code object} is a {@link HashCode} instance with the identical byte
     * representation to this hash code.
     * 
     * <p><b>Security note:</b> this method uses a constant-time (not short-circuiting) implementation
     * to protect against <a href="http://en.wikipedia.org/wiki/Timing_attack">timing attacks</a>.
     *
     * @param object
     * @return true, if successful
     */
    @Override
    public final boolean equals(Object object) {
        if (object instanceof HashCode) {
            HashCode that = (HashCode) object;
            return bits() == that.bits() && equalsSameBits(that);
        }
        return false;
    }

    /**
     * Returns a "Java hash code" for this {@code HashCode} instance; this is well-defined (so, for
     * example, you can safely put {@code HashCode} instances into a {@code
     * HashSet}) but is otherwise probably not what you want to use.
     *
     * @return
     */
    @Override
    public final int hashCode() {
        // If we have at least 4 bytes (32 bits), just take the first 4 bytes. Since this is
        // already a (presumably) high-quality hash code, any four bytes of it will do.
        if (bits() >= 32) {
            return asInt();
        }
        // If we have less than 4 bytes, use them all.
        byte[] bytes = getBytesInternal();
        int val = (bytes[0] & 0xFF);
        for (int i = 1; i < bytes.length; i++) {
            val |= ((bytes[i] & 0xFF) << (i * 8));
        }
        return val;
    }

    /**
     * Returns a string containing each byte of {@link #asBytes}, in order, as a two-digit unsigned
     * hexadecimal number in lower case.
     * 
     * <p>Note that if the output is considered to be a single hexadecimal number, this hash code's
     * bytes are the <i>big-endian</i> representation of that number. This may be surprising since
     * everything else in the hashing API uniformly treats multibyte values as little-endian. But this
     * format conveniently matches that of utilities such as the UNIX {@code md5sum} command.
     * 
     * <p>To create a {@code HashCode} from its string representation, see {@link #fromString}.
     *
     * @return
     */
    @Override
    public final String toString() {
        byte[] bytes = getBytesInternal();
        StringBuilder sb = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            sb.append(hexDigits[(b >> 4) & 0xf]).append(hexDigits[b & 0xf]);
        }
        return sb.toString();
    }

    /** The Constant hexDigits. */
    private static final char[] hexDigits = "0123456789abcdef".toCharArray();
}
