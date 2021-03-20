/*
 * Copyright (c) 2021, Haiyang Li.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.landawn.abacus.guava.hash;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.landawn.abacus.util.N;

final class GuavaHasher implements Hasher {
    final com.google.common.hash.Hasher gHasher;

    GuavaHasher(com.google.common.hash.Hasher gHasher) {
        this.gHasher = gHasher;
    }

    static GuavaHasher from(final com.google.common.hash.Hasher gHasher) {
        return new GuavaHasher(gHasher);
    }

    @Override
    public Hasher put(final byte b) {
        gHasher.putByte(b);

        return this;
    }

    @Override
    public Hasher put(final byte[] bytes) {
        gHasher.putBytes(bytes);

        return this;
    }

    @Override
    public Hasher put(final byte[] bytes, final int off, final int len) {
        gHasher.putBytes(bytes, off, len);

        return this;
    }

    @Override
    public Hasher put(final ByteBuffer bytes) {
        gHasher.putBytes(bytes);

        return this;
    }

    @Override
    public Hasher put(final short s) {
        gHasher.putShort(s);

        return this;
    }

    @Override
    public Hasher put(final int i) {
        gHasher.putInt(i);

        return this;
    }

    @Override
    public Hasher put(final long l) {
        gHasher.putLong(l);

        return this;
    }

    @Override
    public Hasher put(final float f) {
        gHasher.putFloat(f);

        return this;
    }

    @Override
    public Hasher put(final double d) {
        gHasher.putDouble(d);

        return this;
    }

    @Override
    public Hasher put(final boolean b) {
        gHasher.putBoolean(b);

        return this;
    }

    @Override
    public Hasher put(final char c) {
        gHasher.putChar(c);

        return this;
    }

    @Override
    public Hasher put(final char[] chars) {
        return put(chars, 0, N.len(chars));
    }

    @Override
    public Hasher put(final char[] chars, final int off, final int len) {
        N.checkFromIndexSize(off, len, N.len(chars));

        for (int i = off, to = off + len; i < to; i++) {
            put(chars[i]);
        }

        return this;
    }

    @Override
    public Hasher put(final CharSequence charSequence) {
        gHasher.putUnencodedChars(charSequence);

        return this;
    }

    @Override
    public Hasher put(final CharSequence charSequence, final Charset charset) {
        gHasher.putString(charSequence, charset);

        return this;
    }

    @Override
    public <T> Hasher put(final T instance, final Funnel<? super T> funnel) {
        gHasher.putObject(instance, funnel);

        return this;
    }

    @Override
    public HashCode hash() {
        return gHasher.hash();
    }
}
