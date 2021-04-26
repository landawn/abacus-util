/*
 * Copyright (C) 2019 HaiYang Li
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

package com.landawn.abacus.util;

import java.io.Closeable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.landawn.abacus.parser.ParserUtil;
import com.landawn.abacus.parser.ParserUtil.EntityInfo;
import com.landawn.abacus.util.u.Nullable;
import com.landawn.abacus.util.function.Supplier;
import com.landawn.abacus.util.stream.Stream;

/**
 *
 * @author haiyangl
 * @since 1.3
 */
public final class Joiner implements Closeable {

    public static final String DEFAULT_DELIMITER = N.ELEMENT_SEPARATOR;

    public static final String DEFAULT_KEY_VALUE_DELIMITER = "=";

    private final String prefix;

    private final String delimiter;

    private final String keyValueDelimiter;

    private final String suffix;

    private final boolean isEmptyDelimiter;

    private final boolean isEmptyKeyValueDelimiter;

    private boolean trimBeforeAppend = false;

    private boolean skipNulls = false;

    private boolean useCachedBuffer = false;

    private String nullText = N.NULL_STRING;

    private StringBuilder buffer;

    private String emptyValue;

    Joiner(CharSequence delimiter) {
        this(delimiter, DEFAULT_KEY_VALUE_DELIMITER);
    }

    Joiner(final CharSequence delimiter, CharSequence keyValueDelimiter) {
        this(delimiter, keyValueDelimiter, "", "");
    }

    Joiner(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        this(delimiter, DEFAULT_KEY_VALUE_DELIMITER, prefix, suffix);
    }

    Joiner(CharSequence delimiter, CharSequence keyValueDelimiter, CharSequence prefix, CharSequence suffix) {
        N.checkArgNotNull(prefix, "The prefix must not be null");
        N.checkArgNotNull(delimiter, "The delimiter must not be null");
        N.checkArgNotNull(keyValueDelimiter, "The keyValueDelimiter must not be null");
        N.checkArgNotNull(suffix, "The suffix must not be null");

        // make defensive copies of arguments
        this.prefix = prefix.toString();
        this.delimiter = delimiter.toString();
        this.keyValueDelimiter = keyValueDelimiter.toString();
        this.suffix = suffix.toString();
        this.emptyValue = this.prefix + this.suffix;
        this.isEmptyDelimiter = N.isNullOrEmpty(delimiter);
        this.isEmptyKeyValueDelimiter = N.isNullOrEmpty(keyValueDelimiter);
    }

    public static Joiner defauLt() {
        return with(DEFAULT_DELIMITER, DEFAULT_KEY_VALUE_DELIMITER);
    }

    /**
     *
     * @param delimiter
     * @return
     */
    public static Joiner with(final CharSequence delimiter) {
        return new Joiner(delimiter);
    }

    /**
     *
     * @param delimiter
     * @param keyValueDelimiter
     * @return
     */
    public static Joiner with(final CharSequence delimiter, CharSequence keyValueDelimiter) {
        return new Joiner(delimiter, keyValueDelimiter);
    }

    /**
     *
     * @param delimiter
     * @param prefix
     * @param suffix
     * @return
     */
    public static Joiner with(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        return new Joiner(delimiter, prefix, suffix);
    }

    /**
     *
     * @param delimiter
     * @param keyValueDelimiter
     * @param prefix
     * @param suffix
     * @return
     */
    public static Joiner with(CharSequence delimiter, CharSequence keyValueDelimiter, CharSequence prefix, CharSequence suffix) {
        return new Joiner(delimiter, keyValueDelimiter, prefix, suffix);
    }

    /**
     * Sets the empty value.
     *
     * @param emptyValue
     * @return
     */
    public Joiner setEmptyValue(CharSequence emptyValue) {
        this.emptyValue = N.checkArgNotNull(emptyValue, "The empty value must not be null").toString();

        return this;
    }

    /**
     *
     * @param trim
     * @return
     * @deprecated replaced by {@link #trimBeforeAppend()}
     */
    @Deprecated
    public Joiner trim(boolean trim) {
        this.trimBeforeAppend = trim;

        return this;
    }

    public Joiner trimBeforeAppend() {
        this.trimBeforeAppend = true;

        return this;
    }

    /**
     * Ignore the {@code null} element/value for {@code key/value, Map, Entity} when the specified {@code element} or {@code value} is {@code null} if it's set to {@code true}.
     *
     * @param skipNull
     * @return
     * @deprecated replaced by {@link #skipNulls()}
     */
    @Deprecated
    public Joiner skipNull(boolean skipNull) {
        this.skipNulls = skipNull;

        return this;
    }

    public Joiner skipNulls() {
        this.skipNulls = true;

        return this;
    }

    /**
     * Use for null.
     *
     * @param nullText
     * @return
     */
    public Joiner useForNull(String nullText) {
        this.nullText = nullText == null ? N.NULL_STRING : nullText;

        return this;
    }

    //    /**
    //     * Get the {@code StringBuilder} from object factory to improve performance if it's set to true, and must remember to call {@code toString()/map()/mapIfNotEmpty()/stream()/streamIfNotEmpty()} or {@code close()} to recycle the {@code StringBuilder}.
    //     *
    //     * @param reuseStringBuilder
    //     * @return
    //     * @deprecated replaced by {@code #reuseCachedBuffer(boolean)}
    //     */
    //    @Deprecated
    //    public Joiner reuseStringBuilder(boolean reuseStringBuilder) {
    //        if (buffer != null) {
    //            throw new IllegalStateException("Can't reset because the StringBuilder has been created");
    //        }
    //
    //        this.useCachedBuffer = reuseStringBuilder;
    //
    //        return this;
    //    }

    /**
     * Improving performance by set {@code useCachedBuffer=true}, and must remember to call {@code toString()/map()/mapIfNotEmpty()/stream()/streamIfNotEmpty()} or {@code close()} to recycle the cached buffer.
     *
     * @param useCachedBuffer
     * @return
     * @deprecated replaced by {@link #reuseCachedBuffer()}
     */
    @Deprecated
    public Joiner reuseCachedBuffer(boolean useCachedBuffer) {
        if (buffer != null) {
            throw new IllegalStateException("Can't reset because the buffer has been created");
        }

        this.useCachedBuffer = useCachedBuffer;

        return this;
    }

    public Joiner reuseCachedBuffer() {
        if (buffer != null) {
            throw new IllegalStateException("Can't reset because the buffer has been created");
        }

        this.useCachedBuffer = true;

        return this;
    }

    /**
     *
     * @param element
     * @return
     */
    public Joiner append(boolean element) {
        prepareBuilder().append(element);
        return this;
    }

    /**
     *
     * @param element
     * @return
     */
    public Joiner append(char element) {
        prepareBuilder().append(element);
        return this;
    }

    /**
     *
     * @param element
     * @return
     */
    public Joiner append(int element) {
        prepareBuilder().append(element);
        return this;
    }

    /**
     *
     * @param element
     * @return
     */
    public Joiner append(long element) {
        prepareBuilder().append(element);
        return this;
    }

    /**
     *
     * @param element
     * @return
     */
    public Joiner append(float element) {
        prepareBuilder().append(element);
        return this;
    }

    /**
     *
     * @param element
     * @return
     */
    public Joiner append(double element) {
        prepareBuilder().append(element);
        return this;
    }

    /**
     *
     * @param element
     * @return
     */
    public Joiner append(String element) {
        if (element != null || skipNulls == false) {
            prepareBuilder().append(element == null ? nullText : (trimBeforeAppend ? element.trim() : element));
        }

        return this;
    }

    /**
     *
     * @param element
     * @return
     */
    public Joiner append(CharSequence element) {
        if (element != null || skipNulls == false) {
            prepareBuilder().append(element == null ? nullText : (trimBeforeAppend ? element.toString().trim() : element));
        }

        return this;
    }

    /**
     *
     * @param element
     * @param start
     * @param end
     * @return
     */
    public Joiner append(CharSequence element, final int start, final int end) {
        if (element != null || skipNulls == false) {
            if (element == null) {
                prepareBuilder().append(nullText);
            } else if (trimBeforeAppend) {
                prepareBuilder().append(element.subSequence(start, end).toString().trim());
            } else {
                prepareBuilder().append(element, start, end);
            }
        }

        return this;
    }

    /**
     *
     * @param element
     * @return
     */
    public Joiner append(StringBuffer element) {
        if (element != null || skipNulls == false) {
            if (element == null) {
                prepareBuilder().append(nullText);
            } else {
                prepareBuilder().append(element);
            }
        }

        return this;
    }

    /**
     *
     * @param element
     * @return
     */
    public Joiner append(char[] element) {
        if (element != null || skipNulls == false) {
            if (element == null) {
                prepareBuilder().append(nullText);
            } else {
                prepareBuilder().append(element);
            }
        }

        return this;
    }

    /**
     *
     * @param element
     * @param offset
     * @param len
     * @return
     */
    public Joiner append(char[] element, final int offset, final int len) {
        if (element != null || skipNulls == false) {
            if (element == null) {
                prepareBuilder().append(nullText);
            } else {
                prepareBuilder().append(element, offset, len);
            }
        }

        return this;
    }

    /**
     *
     * @param element
     * @return
     */
    public Joiner append(Object element) {
        if (element != null || skipNulls == false) {
            prepareBuilder().append(toString(element));
        }

        return this;
    }

    /**
     * Append if not null.
     *
     * @param element
     * @return
     */
    public Joiner appendIfNotNull(Object element) {
        if (element != null) {
            prepareBuilder().append(toString(element));
        }

        return this;
    }

    /**
     *
     * @param b
     * @param supplier
     * @return
     */
    public Joiner appendIf(boolean b, Supplier<?> supplier) {
        if (b) {
            append(supplier.get());
        }

        return this;
    }

    /**
     *
     * @param a
     * @return
     */
    public Joiner appendAll(final boolean[] a) {
        if (N.notNullOrEmpty(a)) {
            return appendAll(a, 0, a.length);
        }

        return this;
    }

    /**
     *
     * @param a
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final boolean[] a, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, a == null ? 0 : a.length);

        if (N.isNullOrEmpty(a) || fromIndex == toIndex) {
            return this;
        }

        StringBuilder sb = null;

        for (int i = fromIndex; i < toIndex; i++) {
            if (sb == null) {
                sb = prepareBuilder().append(a[i]);
            } else {
                if (isEmptyDelimiter) {
                    sb.append(a[i]);
                } else {
                    sb.append(delimiter).append(a[i]);
                }
            }
        }

        return this;
    }

    /**
     *
     * @param a
     * @return
     */
    public Joiner appendAll(final char[] a) {
        if (N.notNullOrEmpty(a)) {
            return appendAll(a, 0, a.length);
        }

        return this;
    }

    /**
     *
     * @param a
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final char[] a, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, a == null ? 0 : a.length);

        if (N.isNullOrEmpty(a) || fromIndex == toIndex) {
            return this;
        }

        StringBuilder sb = null;

        for (int i = fromIndex; i < toIndex; i++) {
            if (sb == null) {
                sb = prepareBuilder().append(a[i]);
            } else {
                if (isEmptyDelimiter) {
                    sb.append(a[i]);
                } else {
                    sb.append(delimiter).append(a[i]);
                }
            }
        }

        return this;
    }

    /**
     *
     * @param a
     * @return
     */
    public Joiner appendAll(final byte[] a) {
        if (N.notNullOrEmpty(a)) {
            return appendAll(a, 0, a.length);
        }

        return this;
    }

    /**
     *
     * @param a
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final byte[] a, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, a == null ? 0 : a.length);

        if (N.isNullOrEmpty(a) || fromIndex == toIndex) {
            return this;
        }

        StringBuilder sb = null;

        for (int i = fromIndex; i < toIndex; i++) {
            if (sb == null) {
                sb = prepareBuilder().append(a[i]);
            } else {
                if (isEmptyDelimiter) {
                    sb.append(a[i]);
                } else {
                    sb.append(delimiter).append(a[i]);
                }
            }
        }

        return this;
    }

    /**
     *
     * @param a
     * @return
     */
    public Joiner appendAll(final short[] a) {
        if (N.notNullOrEmpty(a)) {
            return appendAll(a, 0, a.length);
        }

        return this;
    }

    /**
     *
     * @param a
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final short[] a, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, a == null ? 0 : a.length);

        if (N.isNullOrEmpty(a) || fromIndex == toIndex) {
            return this;
        }

        StringBuilder sb = null;

        for (int i = fromIndex; i < toIndex; i++) {
            if (sb == null) {
                sb = prepareBuilder().append(a[i]);
            } else {
                if (isEmptyDelimiter) {
                    sb.append(a[i]);
                } else {
                    sb.append(delimiter).append(a[i]);
                }
            }
        }

        return this;
    }

    /**
     *
     * @param a
     * @return
     */
    public Joiner appendAll(final int[] a) {
        if (N.notNullOrEmpty(a)) {
            return appendAll(a, 0, a.length);
        }

        return this;
    }

    /**
     *
     * @param a
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final int[] a, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, a == null ? 0 : a.length);

        if (N.isNullOrEmpty(a) || fromIndex == toIndex) {
            return this;
        }

        StringBuilder sb = null;

        for (int i = fromIndex; i < toIndex; i++) {
            if (sb == null) {
                sb = prepareBuilder().append(a[i]);
            } else {
                if (isEmptyDelimiter) {
                    sb.append(a[i]);
                } else {
                    sb.append(delimiter).append(a[i]);
                }
            }
        }

        return this;
    }

    /**
     *
     * @param a
     * @return
     */
    public Joiner appendAll(final long[] a) {
        if (N.notNullOrEmpty(a)) {
            return appendAll(a, 0, a.length);
        }

        return this;
    }

    /**
     *
     * @param a
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final long[] a, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, a == null ? 0 : a.length);

        if (N.isNullOrEmpty(a) || fromIndex == toIndex) {
            return this;
        }

        StringBuilder sb = null;

        for (int i = fromIndex; i < toIndex; i++) {
            if (sb == null) {
                sb = prepareBuilder().append(a[i]);
            } else {
                if (isEmptyDelimiter) {
                    sb.append(a[i]);
                } else {
                    sb.append(delimiter).append(a[i]);
                }
            }
        }

        return this;
    }

    /**
     *
     * @param a
     * @return
     */
    public Joiner appendAll(final float[] a) {
        if (N.notNullOrEmpty(a)) {
            return appendAll(a, 0, a.length);
        }

        return this;
    }

    /**
     *
     * @param a
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final float[] a, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, a == null ? 0 : a.length);

        if (N.isNullOrEmpty(a) || fromIndex == toIndex) {
            return this;
        }

        StringBuilder sb = null;

        for (int i = fromIndex; i < toIndex; i++) {
            if (sb == null) {
                sb = prepareBuilder().append(a[i]);
            } else {
                if (isEmptyDelimiter) {
                    sb.append(a[i]);
                } else {
                    sb.append(delimiter).append(a[i]);
                }
            }
        }

        return this;
    }

    /**
     *
     * @param a
     * @return
     */
    public Joiner appendAll(final double[] a) {
        if (N.notNullOrEmpty(a)) {
            return appendAll(a, 0, a.length);
        }

        return this;
    }

    /**
     *
     * @param a
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final double[] a, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, a == null ? 0 : a.length);

        if (N.isNullOrEmpty(a) || fromIndex == toIndex) {
            return this;
        }

        StringBuilder sb = null;

        for (int i = fromIndex; i < toIndex; i++) {
            if (sb == null) {
                sb = prepareBuilder().append(a[i]);
            } else {
                if (isEmptyDelimiter) {
                    sb.append(a[i]);
                } else {
                    sb.append(delimiter).append(a[i]);
                }
            }
        }

        return this;
    }

    /**
     *
     * @param a
     * @return
     */
    public Joiner appendAll(final Object[] a) {
        if (N.notNullOrEmpty(a)) {
            return appendAll(a, 0, a.length);
        }

        return this;
    }

    /**
     *
     * @param a
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final Object[] a, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, a == null ? 0 : a.length);

        if (N.isNullOrEmpty(a) || fromIndex == toIndex) {
            return this;
        }

        StringBuilder sb = null;

        for (int i = fromIndex; i < toIndex; i++) {
            if (a[i] != null || skipNulls == false) {
                if (sb == null) {
                    sb = prepareBuilder().append(toString(a[i]));
                } else {
                    if (isEmptyDelimiter) {
                        sb.append(toString(a[i]));
                    } else {
                        sb.append(delimiter).append(toString(a[i]));
                    }
                }
            }
        }

        return this;
    }

    /**
     *
     * @param c
     * @return
     */
    public Joiner appendAll(final BooleanList c) {
        if (N.notNullOrEmpty(c)) {
            return appendAll(c.array(), 0, c.size());
        }

        return this;
    }

    /**
     *
     * @param c
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final BooleanList c, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, c == null ? 0 : c.size());

        if (N.isNullOrEmpty(c) || fromIndex == toIndex) {
            return this;
        }

        return appendAll(c.array(), fromIndex, toIndex);
    }

    /**
     *
     * @param c
     * @return
     */
    public Joiner appendAll(final CharList c) {
        if (N.notNullOrEmpty(c)) {
            return appendAll(c.array(), 0, c.size());
        }

        return this;
    }

    /**
     *
     * @param c
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final CharList c, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, c == null ? 0 : c.size());

        if (N.isNullOrEmpty(c) || fromIndex == toIndex) {
            return this;
        }

        return appendAll(c.array(), fromIndex, toIndex);
    }

    /**
     *
     * @param c
     * @return
     */
    public Joiner appendAll(final ByteList c) {
        if (N.notNullOrEmpty(c)) {
            return appendAll(c.array(), 0, c.size());
        }

        return this;
    }

    /**
     *
     * @param c
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final ByteList c, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, c == null ? 0 : c.size());

        if (N.isNullOrEmpty(c) || fromIndex == toIndex) {
            return this;
        }

        return appendAll(c.array(), fromIndex, toIndex);
    }

    /**
     *
     * @param c
     * @return
     */
    public Joiner appendAll(final ShortList c) {
        if (N.notNullOrEmpty(c)) {
            return appendAll(c.array(), 0, c.size());
        }

        return this;
    }

    /**
     *
     * @param c
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final ShortList c, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, c == null ? 0 : c.size());

        if (N.isNullOrEmpty(c) || fromIndex == toIndex) {
            return this;
        }

        return appendAll(c.array(), fromIndex, toIndex);
    }

    /**
     *
     * @param c
     * @return
     */
    public Joiner appendAll(final IntList c) {
        if (N.notNullOrEmpty(c)) {
            return appendAll(c.array(), 0, c.size());
        }

        return this;
    }

    /**
     *
     * @param c
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final IntList c, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, c == null ? 0 : c.size());

        if (N.isNullOrEmpty(c) || fromIndex == toIndex) {
            return this;
        }

        return appendAll(c.array(), fromIndex, toIndex);
    }

    /**
     *
     * @param c
     * @return
     */
    public Joiner appendAll(final LongList c) {
        if (N.notNullOrEmpty(c)) {
            return appendAll(c.array(), 0, c.size());
        }

        return this;
    }

    /**
     *
     * @param c
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final LongList c, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, c == null ? 0 : c.size());

        if (N.isNullOrEmpty(c) || fromIndex == toIndex) {
            return this;
        }

        return appendAll(c.array(), fromIndex, toIndex);
    }

    /**
     *
     * @param c
     * @return
     */
    public Joiner appendAll(final FloatList c) {
        if (N.notNullOrEmpty(c)) {
            return appendAll(c.array(), 0, c.size());
        }

        return this;
    }

    /**
     *
     * @param c
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final FloatList c, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, c == null ? 0 : c.size());

        if (N.isNullOrEmpty(c) || fromIndex == toIndex) {
            return this;
        }

        return appendAll(c.array(), fromIndex, toIndex);
    }

    /**
     *
     * @param c
     * @return
     */
    public Joiner appendAll(final DoubleList c) {
        if (N.notNullOrEmpty(c)) {
            return appendAll(c.array(), 0, c.size());
        }

        return this;
    }

    /**
     *
     * @param c
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final DoubleList c, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, c == null ? 0 : c.size());

        if (N.isNullOrEmpty(c) || fromIndex == toIndex) {
            return this;
        }

        return appendAll(c.array(), fromIndex, toIndex);
    }

    /**
     *
     * @param c
     * @return
     */
    public Joiner appendAll(final Collection<?> c) {
        if (N.notNullOrEmpty(c)) {
            return appendAll(c, 0, c.size());
        }

        return this;
    }

    /**
     *
     * @param c
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendAll(final Collection<?> c, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, c == null ? 0 : c.size());

        if (N.isNullOrEmpty(c) || (fromIndex == toIndex && fromIndex < c.size())) {
            return this;
        }

        StringBuilder sb = null;

        int i = 0;
        for (Object e : c) {
            if (i++ < fromIndex) {
                continue;
            }

            if (e != null || skipNulls == false) {
                if (sb == null) {
                    sb = prepareBuilder().append(toString(e));
                } else {
                    if (isEmptyDelimiter) {
                        sb.append(toString(e));
                    } else {
                        sb.append(delimiter).append(toString(e));
                    }
                }
            }

            if (i >= toIndex) {
                break;
            }
        }

        return this;
    }

    public Joiner appendAll(final Iterable<?> iter) {
        if (iter != null) {
            StringBuilder sb = null;

            for (Object e : iter) {
                if (e != null || skipNulls == false) {
                    if (sb == null) {
                        sb = prepareBuilder().append(toString(e));
                    } else {
                        if (isEmptyDelimiter) {
                            sb.append(toString(e));
                        } else {
                            sb.append(delimiter).append(toString(e));
                        }
                    }
                }
            }
        }

        return this;
    }

    public Joiner appendAll(final Iterator<?> iter) {
        if (iter != null) {
            StringBuilder sb = null;
            Object e = null;

            while (iter.hasNext()) {
                e = iter.next();

                if (e != null || skipNulls == false) {
                    if (sb == null) {
                        sb = prepareBuilder().append(toString(e));
                    } else {
                        if (isEmptyDelimiter) {
                            sb.append(toString(e));
                        } else {
                            sb.append(delimiter).append(toString(e));
                        }
                    }
                }
            }
        }

        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public Joiner appendEntry(String key, boolean value) {
        if (isEmptyKeyValueDelimiter) {
            prepareBuilder().append(key).append(value);
        } else {
            prepareBuilder().append(key).append(keyValueDelimiter).append(value);
        }

        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public Joiner appendEntry(String key, char value) {
        if (isEmptyKeyValueDelimiter) {
            prepareBuilder().append(key).append(value);
        } else {
            prepareBuilder().append(key).append(keyValueDelimiter).append(value);
        }

        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public Joiner appendEntry(String key, int value) {
        if (isEmptyKeyValueDelimiter) {
            prepareBuilder().append(key).append(value);
        } else {
            prepareBuilder().append(key).append(keyValueDelimiter).append(value);
        }

        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public Joiner appendEntry(String key, long value) {
        if (isEmptyKeyValueDelimiter) {
            prepareBuilder().append(key).append(value);
        } else {
            prepareBuilder().append(key).append(keyValueDelimiter).append(value);
        }

        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public Joiner appendEntry(String key, float value) {
        if (isEmptyKeyValueDelimiter) {
            prepareBuilder().append(key).append(value);
        } else {
            prepareBuilder().append(key).append(keyValueDelimiter).append(value);
        }

        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public Joiner appendEntry(String key, double value) {
        if (isEmptyKeyValueDelimiter) {
            prepareBuilder().append(key).append(value);
        } else {
            prepareBuilder().append(key).append(keyValueDelimiter).append(value);
        }

        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public Joiner appendEntry(String key, String value) {
        if (value != null || skipNulls == false) {
            if (isEmptyKeyValueDelimiter) {
                prepareBuilder().append(key).append(value == null ? nullText : (trimBeforeAppend ? value.trim() : value));
            } else {
                prepareBuilder().append(key).append(keyValueDelimiter).append(value == null ? nullText : (trimBeforeAppend ? value.trim() : value));
            }
        }

        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public Joiner appendEntry(String key, CharSequence value) {
        if (value != null || skipNulls == false) {
            if (isEmptyKeyValueDelimiter) {
                prepareBuilder().append(key).append(value == null ? nullText : (trimBeforeAppend ? value.toString().trim() : value));
            } else {
                prepareBuilder().append(key).append(keyValueDelimiter).append(value == null ? nullText : (trimBeforeAppend ? value.toString().trim() : value));
            }
        }

        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public Joiner appendEntry(String key, StringBuffer value) {
        if (value != null || skipNulls == false) {
            if (value == null) {
                if (isEmptyKeyValueDelimiter) {
                    prepareBuilder().append(key).append(nullText);
                } else {
                    prepareBuilder().append(key).append(keyValueDelimiter).append(nullText);
                }
            } else {
                if (isEmptyKeyValueDelimiter) {
                    prepareBuilder().append(key).append(value);
                } else {
                    prepareBuilder().append(key).append(keyValueDelimiter).append(value);
                }
            }
        }

        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public Joiner appendEntry(String key, char[] value) {
        if (value != null || skipNulls == false) {
            if (value == null) {
                if (isEmptyKeyValueDelimiter) {
                    prepareBuilder().append(key).append(nullText);
                } else {
                    prepareBuilder().append(key).append(keyValueDelimiter).append(nullText);
                }
            } else {
                if (isEmptyKeyValueDelimiter) {
                    prepareBuilder().append(key).append(value);
                } else {
                    prepareBuilder().append(key).append(keyValueDelimiter).append(value);
                }
            }
        }

        return this;
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public Joiner appendEntry(String key, Object value) {
        if (value != null || skipNulls == false) {
            if (isEmptyKeyValueDelimiter) {
                prepareBuilder().append(key).append(toString(value));
            } else {
                prepareBuilder().append(key).append(keyValueDelimiter).append(toString(value));
            }
        }

        return this;
    }

    /**
     *
     * @param entry
     * @return
     */
    public Joiner appendEntry(Map.Entry<?, ?> entry) {
        if (skipNulls == false || (entry != null && entry.getValue() != null)) {
            if (entry == null) {
                append(nullText);
            } else {
                appendEntry(toString(entry.getKey()), toString(entry.getValue()));
            }
        }

        return this;
    }

    //    public Joiner appendEntryIf(boolean b, String key, Object value) {
    //        if (b) {
    //            appendEntry(key, value);
    //        }
    //
    //        return this;
    //    }
    //
    //    public Joiner appendEntryIf(boolean b, Map.Entry<?, ?> entry) {
    //        if (b) {
    //            appendEntry(entry);
    //        }
    //
    //        return this;
    //    }

    /**
     *
     * @param m
     * @return
     */
    public Joiner appendEntries(final Map<?, ?> m) {
        if (N.notNullOrEmpty(m)) {
            return appendEntries(m, 0, m.size());
        }

        return this;
    }

    /**
     *
     * @param m
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Joiner appendEntries(final Map<?, ?> m, final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, m == null ? 0 : m.size());

        if ((N.isNullOrEmpty(m) && fromIndex == 0 && toIndex == 0) || (fromIndex == toIndex && fromIndex < m.size())) {
            return this;
        }

        StringBuilder sb = null;

        int i = 0;
        for (Map.Entry<?, ?> entry : m.entrySet()) {
            if (i++ < fromIndex) {
                continue;
            }

            if (entry.getValue() != null || skipNulls == false) {
                if (sb == null) {
                    sb = prepareBuilder().append(toString(entry.getKey())).append(keyValueDelimiter).append(toString(entry.getValue()));
                } else {
                    if (isEmptyDelimiter) {
                        sb.append(toString(entry.getKey()));
                    } else {
                        sb.append(delimiter).append(toString(entry.getKey()));
                    }

                    if (isEmptyKeyValueDelimiter) {
                        sb.append(toString(entry.getValue()));
                    } else {
                        sb.append(keyValueDelimiter).append(toString(entry.getValue()));
                    }
                }
            }

            if (i >= toIndex) {
                break;
            }
        }

        return this;
    }

    /**
     *
     * @param entity entity class with getter/setter methods.
     * @return
     */
    public Joiner appendEntries(final Object entity) {
        return appendEntries(entity, null);
    }

    /**
     *
     * @param entity entity class with getter/setter methods.
     * @param propNamesToAppend
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Joiner appendEntries(final Object entity, final Collection<String> propNamesToAppend) {
        if (entity == null) {
            return this;
        } else if (entity instanceof Map) {
            return appendEntries((Map) entity);
        }

        final Class<?> cls = entity.getClass();

        N.checkArgument(ClassUtil.isEntity(cls), "'entity' must be entity class with getter/setter methods");

        final Collection<String> selectPropNames = propNamesToAppend == null ? ClassUtil.getPropNameList(cls) : propNamesToAppend;
        final EntityInfo entityInfo = ParserUtil.getEntityInfo(cls);
        StringBuilder sb = null;
        Object propValue = null;

        for (String propName : selectPropNames) {
            propValue = entityInfo.getPropValue(entity, propName);

            if (propValue != null || skipNulls == false) {
                if (sb == null) {
                    sb = prepareBuilder().append(propName).append(keyValueDelimiter).append(toString(propValue));
                } else {
                    if (isEmptyDelimiter) {
                        sb.append(propName);
                    } else {
                        sb.append(delimiter).append(propName);
                    }

                    if (isEmptyKeyValueDelimiter) {
                        sb.append(toString(propValue));
                    } else {
                        sb.append(keyValueDelimiter).append(toString(propValue));
                    }
                }
            }
        }

        return this;
    }

    /**
     *
     * @param entity entity class with getter/setter methods.
     * @param propNamesToAppend
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Joiner appendEntriesExclusively(final Object entity, final Set<String> propNamesToExeclude) {
        N.checkArgNotNull(propNamesToExeclude, "propNamesToExeclude");

        if (entity == null) {
            return this;
        } else if (entity instanceof Map) {
            return appendEntries((Map) entity);
        }

        final Class<?> cls = entity.getClass();

        N.checkArgument(ClassUtil.isEntity(cls), "'entity' must be entity class with getter/setter methods");

        final EntityInfo entityInfo = ParserUtil.getEntityInfo(cls);
        StringBuilder sb = null;
        Object propValue = null;

        for (String propName : ClassUtil.getPropNameList(cls)) {
            if (propNamesToExeclude.contains(propName)) {
                continue;
            }

            propValue = entityInfo.getPropValue(entity, propName);

            if (propValue != null || skipNulls == false) {
                if (sb == null) {
                    sb = prepareBuilder().append(propName).append(keyValueDelimiter).append(toString(propValue));
                } else {
                    if (isEmptyDelimiter) {
                        sb.append(propName);
                    } else {
                        sb.append(delimiter).append(propName);
                    }

                    if (isEmptyKeyValueDelimiter) {
                        sb.append(toString(propValue));
                    } else {
                        sb.append(keyValueDelimiter).append(toString(propValue));
                    }
                }
            }
        }

        return this;
    }

    /**
     *
     * @param str
     * @param n
     * @return
     */
    public Joiner repeat(final String str, final int n) {
        N.checkArgNotNegative(n, "n");

        final String newString = toString(str);

        if (n < 10) {
            for (int i = 0; i < n; i++) {
                append(newString);
            }
        } else {
            append(StringUtil.repeat(newString, n, delimiter));
        }

        return this;
    }

    /**
     *
     * @param obj
     * @param n
     * @return
     */
    public Joiner repeat(final Object obj, final int n) {
        return repeat(toString(obj), n);
    }

    /**
     * Adds the contents of the given {@code StringJoiner} without prefix and
     * suffix as the next element if it is non-empty. If the given {@code
     * StringJoiner} is empty, the call has no effect.
     *
     * <p>A {@code StringJoiner} is empty if {@link #addAll(CharSequence) add()}
     * has never been called, and if {@code merge()} has never been called
     * with a non-empty {@code StringJoiner} argument.
     *
     * <p>If the other {@code StringJoiner} is using a different delimiter,
     * then elements from the other {@code StringJoiner} are concatenated with
     * that delimiter and the result is appended to this {@code StringJoiner}
     * as a single element.
     * 
     * <p>Remember to close {@code other} Joiner if {@code reuseCachedBuffer} is set to {@code} true.
     *
     * @param other The {@code StringJoiner} whose contents should be merged
     *              into this one
     * @return This {@code StringJoiner}
     * @throws NullPointerException if the other {@code StringJoiner} is null
     */
    public Joiner merge(Joiner other) {
        N.checkArgNotNull(other);

        if (other.buffer != null) {
            final int length = other.buffer.length();
            // lock the length so that we can seize the data to be appended
            // before initiate copying to avoid interference, especially when
            // merge 'this'
            StringBuilder builder = prepareBuilder();
            builder.append(other.buffer, other.prefix.length(), length);
        }

        return this;
    }

    private StringBuilder prepareBuilder() {
        if (buffer != null) {
            if (isEmptyDelimiter == false) {
                buffer.append(delimiter);
            }
        } else {
            buffer = (useCachedBuffer ? Objectory.createStringBuilder() : new StringBuilder()).append(prefix);
        }
        return buffer;
    }

    /**
     *
     * @param obj
     * @return
     */
    private String toString(Object obj) {
        return obj == null ? nullText : (trimBeforeAppend ? N.toString(obj).trim() : N.toString(obj));
    }

    public int length() {
        // Remember that we never actually append the suffix unless we return
        // the full (present) value or some sub-string or length of it, so that
        // we can add on more if we need to.
        return (buffer != null ? buffer.length() + suffix.length() : emptyValue.length());
    }

    /**
     * Returns the current value, consisting of the {@code prefix}, the values
     * added so far separated by the {@code delimiter}, and the {@code suffix},
     * unless no elements have been added in which case, the
     * {@code prefix + suffix} or the {@code emptyValue} characters are returned
     *
     * <pre>
     * The underline {@code StringBuilder} will be recycled after this method is called if {@code resueStringBuilder} is set to {@code true},
     * and should not continue to this instance.
     * </pre>
     *
     * @return
     */
    @Override
    public String toString() {
        if (buffer == null) {
            return emptyValue;
        } else {
            try {
                if (suffix.equals("")) {
                    return buffer.toString();
                } else {
                    int initialLength = buffer.length();
                    String result = buffer.append(suffix).toString();
                    // reset value to pre-append initialLength
                    buffer.setLength(initialLength);
                    return result;
                }
            } finally {
                if (useCachedBuffer) {
                    Objectory.recycle(buffer);
                    buffer = null;
                }
            }
        }
    }

    /**
     * <pre>
     * The underline {@code StringBuilder} will be recycled after this method is called if {@code resueStringBuilder} is set to {@code true},
     * and should not continue to this instance.
     * </pre>
     *
     * @param <T>
     * @param <E>
     * @param mapper
     * @return
     * @throws E the e
     */
    public <T, E extends Exception> T map(Throwables.Function<? super String, T, E> mapper) throws E {
        return mapper.apply(toString());
    }

    /**
     * Call {@code mapper} only if at least one element/object/entry is appended.
     *
     * <pre>
     * The underline {@code StringBuilder} will be recycled after this method is called if {@code resueStringBuilder} is set to {@code true},
     * and should not continue to this instance.
     * </pre>
     *
     * @param <T>
     * @param <E>
     * @param mapper
     * @return
     * @throws E the e
     */
    public <T, E extends Exception> Nullable<T> mapIfNotEmpty(Throwables.Function<? super String, T, E> mapper) throws E {
        N.checkArgNotNull(mapper);

        return buffer == null ? Nullable.<T> empty() : Nullable.of(mapper.apply(toString()));
    }

    /**
     * <pre>
     * The underline {@code StringBuilder} will be recycled after this method is called if {@code resueStringBuilder} is set to {@code true},
     * and should not continue to this instance.
     * </pre>
     *
     * @return
     */
    public Stream<String> stream() {
        return Stream.of(toString());
    }

    /**
     * Returns a stream with the String value generated by {@code toString()} if at least one element/object/entry is appended, otherwise an empty {@code Stream} is returned.
     *
     * <pre>
     * The underline {@code StringBuilder} will be recycled after this method is called if {@code resueStringBuilder} is set to {@code true},
     * and should not continue to this instance.
     * </pre>
     *
     * @return
     */
    public Stream<String> streamIfNotEmpty() {
        return buffer == null ? Stream.<String> empty() : Stream.of(toString());
    }

    /**
     * Close.
     */
    @Override
    public synchronized void close() {
        if (buffer != null && useCachedBuffer) {
            Objectory.recycle(buffer);
            buffer = null;
        }
    }
}
