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

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import com.landawn.abacus.annotation.Beta;
import com.landawn.abacus.annotation.SequentialOnly;
import com.landawn.abacus.annotation.Stateful;
import com.landawn.abacus.annotation.SuppressFBWarnings;
import com.landawn.abacus.util.function.BiConsumer;
import com.landawn.abacus.util.function.BiFunction;
import com.landawn.abacus.util.function.BooleanConsumer;
import com.landawn.abacus.util.function.ByteConsumer;
import com.landawn.abacus.util.function.CharConsumer;
import com.landawn.abacus.util.function.Consumer;
import com.landawn.abacus.util.function.DoubleConsumer;
import com.landawn.abacus.util.function.FloatConsumer;
import com.landawn.abacus.util.function.Function;
import com.landawn.abacus.util.function.IntConsumer;
import com.landawn.abacus.util.function.IntFunction;
import com.landawn.abacus.util.function.LongConsumer;
import com.landawn.abacus.util.function.ShortConsumer;
import com.landawn.abacus.util.function.TriConsumer;
import com.landawn.abacus.util.function.TriFunction;

/**
 * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
 *
 * @since 1.3
 *
 * @author Haiyang Li
 */
@Beta
@SequentialOnly
@Stateful
public interface NoCachingNoUpdating {

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     *
     * @param <T>
     */
    @Beta
    @SequentialOnly
    @Stateful
    @SuppressFBWarnings("CN_IDIOM_NO_SUPER_CALL")
    public static class DisposableArray<T> implements NoCachingNoUpdating, Cloneable {

        /** The a. */
        private final T[] a;

        /**
         * Instantiates a new disposable array.
         *
         * @param a
         */
        protected DisposableArray(final T[] a) {
            N.checkArgNotNull(a, "a");
            this.a = a;
        }

        /**
         *
         * @param <T>
         * @param a
         * @return
         */
        public static <T> DisposableArray<T> wrap(final T[] a) {
            return new DisposableArray<>(a);
        }

        /**
         *
         * @param index
         * @return
         */
        public T get(final int index) {
            return a[index];
        }

        /**
         *
         * @return
         */
        public int length() {
            return a.length;
        }

        /**
         *
         * @param <A>
         * @param target
         * @return
         */
        public <A> A[] toArray(A[] target) {
            if (target.length < length()) {
                target = N.newArray(target.getClass().getComponentType(), length());
            }

            N.copy(this.a, 0, target, 0, length());

            return target;
        }

        /**
         *
         * @return
         */
        @Override
        public T[] clone() {
            return N.clone(a);
        }

        /**
         *
         * @return
         */
        public List<T> toList() {
            return N.toList(clone());
        }

        /**
         *
         * @param <C>
         * @param supplier
         * @return
         */
        public <C extends Collection<T>> C toCollection(final IntFunction<? extends C> supplier) {
            final C result = supplier.apply(length());
            result.addAll(toList());
            return result;
        }

        /**
         *
         * @param action
         */
        public void forEach(final Consumer<? super T> action) {
            for (T e : a) {
                action.accept(e);
            }
        }

        /**
         *
         * @param <R>
         * @param func
         * @return
         */
        public <R> R apply(final Function<? super T[], R> func) {
            return func.apply(a);
        }

        /**
         *
         * @param action
         */
        public void accept(final Consumer<? super T[]> action) {
            action.accept(a);
        }

        /**
         *
         * @param delimiter
         * @return
         */
        public String join(String delimiter) {
            return StringUtil.join(a, delimiter);
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return N.toString(a);
        }

        /**
         *
         * @return
         */
        protected T[] values() {
            return a;
        }
    }

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     */
    @Beta
    @SequentialOnly
    @Stateful
    public static class DisposableObjArray extends DisposableArray<Object> {

        /**
         * Instantiates a new disposable obj array.
         *
         * @param a
         */
        protected DisposableObjArray(Object[] a) {
            super(a);
        }

        /**
         *
         * @param a
         * @return
         */
        public static DisposableObjArray wrap(final Object[] a) {
            return new DisposableObjArray(a);
        }
    }

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     */
    @Beta
    @SequentialOnly
    @Stateful
    public static class DisposableBooleanArray implements NoCachingNoUpdating, Cloneable {

        /** The a. */
        private final boolean[] a;

        /**
         * Instantiates a new disposable boolean array.
         *
         * @param a
         */
        protected DisposableBooleanArray(final boolean[] a) {
            N.checkArgNotNull(a, "a");
            this.a = a;
        }

        /**
         *
         * @param a
         * @return
         */
        public static DisposableBooleanArray wrap(final boolean[] a) {
            return new DisposableBooleanArray(a);
        }

        /**
         *
         * @param index
         * @return
         */
        public boolean get(final int index) {
            return a[index];
        }

        /**
         *
         * @return
         */
        public int length() {
            return a.length;
        }

        /**
         *
         * @return
         */
        @Override
        public boolean[] clone() {
            return N.clone(a);
        }

        /**
         *
         * @return
         */
        public Boolean[] box() {
            return Array.box(a);
        }

        /**
         *
         * @return
         */
        public BooleanList toList() {
            return BooleanList.of(clone());
        }

        /**
         *
         * @param action
         */
        public void forEach(final BooleanConsumer action) {
            for (boolean e : a) {
                action.accept(e);
            }
        }

        /**
         *
         * @param <R>
         * @param func
         * @return
         */
        public <R> R apply(final Function<? super boolean[], R> func) {
            return func.apply(a);
        }

        /**
         *
         * @param action
         */
        public void accept(final Consumer<? super boolean[]> action) {
            action.accept(a);
        }

        /**
         *
         * @param delimiter
         * @return
         */
        public String join(String delimiter) {
            return StringUtil.join(a, delimiter);
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return N.toString(a);
        }

        /**
         *
         * @return
         */
        protected boolean[] values() {
            return a;
        }
    }

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     */
    @Beta
    @SequentialOnly
    @Stateful
    public static class DisposableCharArray implements NoCachingNoUpdating, Cloneable {

        /** The a. */
        private final char[] a;

        /**
         * Instantiates a new disposable char array.
         *
         * @param a
         */
        protected DisposableCharArray(final char[] a) {
            N.checkArgNotNull(a, "a");
            this.a = a;
        }

        /**
         *
         * @param a
         * @return
         */
        public static DisposableCharArray wrap(final char[] a) {
            return new DisposableCharArray(a);
        }

        /**
         *
         * @param index
         * @return
         */
        public char get(final int index) {
            return a[index];
        }

        /**
         *
         * @return
         */
        public int length() {
            return a.length;
        }

        /**
         *
         * @return
         */
        @Override
        public char[] clone() {
            return N.clone(a);
        }

        /**
         *
         * @return
         */
        public Character[] box() {
            return Array.box(a);
        }

        /**
         *
         * @return
         */
        public CharList toList() {
            return CharList.of(clone());
        }

        /**
         *
         * @return
         */
        public int sum() {
            return N.sum(a);
        }

        /**
         *
         * @return
         */
        public double average() {
            return N.average(a);
        }

        /**
         *
         * @return
         */
        public char min() {
            return N.min(a);
        }

        /**
         *
         * @return
         */
        public char max() {
            return N.max(a);
        }

        /**
         *
         * @param action
         */
        public void forEach(final CharConsumer action) {
            for (char e : a) {
                action.accept(e);
            }
        }

        /**
         *
         * @param <R>
         * @param func
         * @return
         */
        public <R> R apply(final Function<? super char[], R> func) {
            return func.apply(a);
        }

        /**
         *
         * @param action
         */
        public void accept(final Consumer<? super char[]> action) {
            action.accept(a);
        }

        /**
         *
         * @param delimiter
         * @return
         */
        public String join(String delimiter) {
            return StringUtil.join(a, delimiter);
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return N.toString(a);
        }

        /**
         *
         * @return
         */
        protected char[] values() {
            return a;
        }
    }

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     */
    @Beta
    @SequentialOnly
    @Stateful
    public static class DisposableByteArray implements NoCachingNoUpdating, Cloneable {

        /** The a. */
        private final byte[] a;

        /**
         * Instantiates a new disposable byte array.
         *
         * @param a
         */
        protected DisposableByteArray(final byte[] a) {
            N.checkArgNotNull(a, "a");
            this.a = a;
        }

        /**
         *
         * @param a
         * @return
         */
        public static DisposableByteArray wrap(final byte[] a) {
            return new DisposableByteArray(a);
        }

        /**
         *
         * @param index
         * @return
         */
        public byte get(final int index) {
            return a[index];
        }

        /**
         *
         * @return
         */
        public int length() {
            return a.length;
        }

        /**
         *
         * @return
         */
        @Override
        public byte[] clone() {
            return N.clone(a);
        }

        /**
         *
         * @return
         */
        public Byte[] box() {
            return Array.box(a);
        }

        /**
         *
         * @return
         */
        public ByteList toList() {
            return ByteList.of(clone());
        }

        /**
         *
         * @return
         */
        public int sum() {
            return N.sum(a);
        }

        /**
         *
         * @return
         */
        public double average() {
            return N.average(a);
        }

        /**
         *
         * @return
         */
        public byte min() {
            return N.min(a);
        }

        /**
         *
         * @return
         */
        public byte max() {
            return N.max(a);
        }

        /**
         *
         * @param action
         */
        public void forEach(final ByteConsumer action) {
            for (byte e : a) {
                action.accept(e);
            }
        }

        /**
         *
         * @param <R>
         * @param func
         * @return
         */
        public <R> R apply(final Function<? super byte[], R> func) {
            return func.apply(a);
        }

        /**
         *
         * @param action
         */
        public void accept(final Consumer<? super byte[]> action) {
            action.accept(a);
        }

        /**
         *
         * @param delimiter
         * @return
         */
        public String join(String delimiter) {
            return StringUtil.join(a, delimiter);
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return N.toString(a);
        }

        /**
         *
         * @return
         */
        protected byte[] values() {
            return a;
        }
    }

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     */
    @Beta
    @SequentialOnly
    @Stateful
    public static class DisposableShortArray implements NoCachingNoUpdating, Cloneable {

        /** The a. */
        private final short[] a;

        /**
         * Instantiates a new disposable short array.
         *
         * @param a
         */
        protected DisposableShortArray(final short[] a) {
            N.checkArgNotNull(a, "a");
            this.a = a;
        }

        /**
         *
         * @param a
         * @return
         */
        public static DisposableShortArray wrap(final short[] a) {
            return new DisposableShortArray(a);
        }

        /**
         *
         * @param index
         * @return
         */
        public short get(final int index) {
            return a[index];
        }

        /**
         *
         * @return
         */
        public int length() {
            return a.length;
        }

        /**
         *
         * @return
         */
        @Override
        public short[] clone() {
            return N.clone(a);
        }

        /**
         *
         * @return
         */
        public Short[] box() {
            return Array.box(a);
        }

        /**
         *
         * @return
         */
        public ShortList toList() {
            return ShortList.of(clone());
        }

        /**
         *
         * @return
         */
        public int sum() {
            return N.sum(a);
        }

        /**
         *
         * @return
         */
        public double average() {
            return N.average(a);
        }

        /**
         *
         * @return
         */
        public short min() {
            return N.min(a);
        }

        /**
         *
         * @return
         */
        public short max() {
            return N.max(a);
        }

        /**
         *
         * @param action
         */
        public void forEach(final ShortConsumer action) {
            for (short e : a) {
                action.accept(e);
            }
        }

        /**
         *
         * @param <R>
         * @param func
         * @return
         */
        public <R> R apply(final Function<? super short[], R> func) {
            return func.apply(a);
        }

        /**
         *
         * @param action
         */
        public void accept(final Consumer<? super short[]> action) {
            action.accept(a);
        }

        /**
         *
         * @param delimiter
         * @return
         */
        public String join(String delimiter) {
            return StringUtil.join(a, delimiter);
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return N.toString(a);
        }

        /**
         *
         * @return
         */
        protected short[] values() {
            return a;
        }
    }

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     */
    @Beta
    @SequentialOnly
    @Stateful
    public static class DisposableIntArray implements NoCachingNoUpdating, Cloneable {

        /** The a. */
        private final int[] a;

        /**
         * Instantiates a new disposable int array.
         *
         * @param a
         */
        protected DisposableIntArray(final int[] a) {
            N.checkArgNotNull(a, "a");
            this.a = a;
        }

        /**
         *
         * @param a
         * @return
         */
        public static DisposableIntArray wrap(final int[] a) {
            return new DisposableIntArray(a);
        }

        /**
         *
         * @param index
         * @return
         */
        public int get(final int index) {
            return a[index];
        }

        /**
         *
         * @return
         */
        public int length() {
            return a.length;
        }

        /**
         *
         * @return
         */
        @Override
        public int[] clone() {
            return N.clone(a);
        }

        /**
         *
         * @return
         */
        public Integer[] box() {
            return Array.box(a);
        }

        /**
         *
         * @return
         */
        public IntList toList() {
            return IntList.of(clone());
        }

        /**
         *
         * @return
         */
        public int sum() {
            return N.sum(a);
        }

        /**
         *
         * @return
         */
        public double average() {
            return N.average(a);
        }

        /**
         *
         * @return
         */
        public int min() {
            return N.min(a);
        }

        /**
         *
         * @return
         */
        public int max() {
            return N.max(a);
        }

        /**
         *
         * @param action
         */
        public void forEach(final IntConsumer action) {
            for (int e : a) {
                action.accept(e);
            }
        }

        /**
         *
         * @param <R>
         * @param func
         * @return
         */
        public <R> R apply(final Function<? super int[], R> func) {
            return func.apply(a);
        }

        /**
         *
         * @param action
         */
        public void accept(final Consumer<? super int[]> action) {
            action.accept(a);
        }

        /**
         *
         * @param delimiter
         * @return
         */
        public String join(String delimiter) {
            return StringUtil.join(a, delimiter);
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return N.toString(a);
        }

        /**
         *
         * @return
         */
        protected int[] values() {
            return a;
        }
    }

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     */
    @Beta
    @SequentialOnly
    @Stateful
    public static class DisposableLongArray implements NoCachingNoUpdating, Cloneable {

        /** The a. */
        private final long[] a;

        /**
         * Instantiates a new disposable long array.
         *
         * @param a
         */
        protected DisposableLongArray(final long[] a) {
            N.checkArgNotNull(a, "a");
            this.a = a;
        }

        /**
         *
         * @param a
         * @return
         */
        public static DisposableLongArray wrap(final long[] a) {
            return new DisposableLongArray(a);
        }

        /**
         *
         * @param index
         * @return
         */
        public long get(final int index) {
            return a[index];
        }

        /**
         *
         * @return
         */
        public int length() {
            return a.length;
        }

        /**
         *
         * @return
         */
        @Override
        public long[] clone() {
            return N.clone(a);
        }

        /**
         *
         * @return
         */
        public Long[] box() {
            return Array.box(a);
        }

        /**
         *
         * @return
         */
        public LongList toList() {
            return LongList.of(clone());
        }

        /**
         *
         * @return
         */
        public long sum() {
            return N.sum(a);
        }

        /**
         *
         * @return
         */
        public double average() {
            return N.average(a);
        }

        /**
         *
         * @return
         */
        public long min() {
            return N.min(a);
        }

        /**
         *
         * @return
         */
        public long max() {
            return N.max(a);
        }

        /**
         *
         * @param action
         */
        public void forEach(final LongConsumer action) {
            for (long e : a) {
                action.accept(e);
            }
        }

        /**
         *
         * @param <R>
         * @param func
         * @return
         */
        public <R> R apply(final Function<? super long[], R> func) {
            return func.apply(a);
        }

        /**
         *
         * @param action
         */
        public void accept(final Consumer<? super long[]> action) {
            action.accept(a);
        }

        /**
         *
         * @param delimiter
         * @return
         */
        public String join(String delimiter) {
            return StringUtil.join(a, delimiter);
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return N.toString(a);
        }

        /**
         *
         * @return
         */
        protected long[] values() {
            return a;
        }
    }

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     */
    @Beta
    @SequentialOnly
    @Stateful
    public static class DisposableFloatArray implements NoCachingNoUpdating, Cloneable {

        /** The a. */
        private final float[] a;

        /**
         * Instantiates a new disposable float array.
         *
         * @param a
         */
        protected DisposableFloatArray(final float[] a) {
            N.checkArgNotNull(a, "a");
            this.a = a;
        }

        /**
         *
         * @param a
         * @return
         */
        public static DisposableFloatArray wrap(final float[] a) {
            return new DisposableFloatArray(a);
        }

        /**
         *
         * @param index
         * @return
         */
        public float get(final int index) {
            return a[index];
        }

        /**
         *
         * @return
         */
        public int length() {
            return a.length;
        }

        /**
         *
         * @return
         */
        @Override
        public float[] clone() {
            return N.clone(a);
        }

        /**
         *
         * @return
         */
        public Float[] box() {
            return Array.box(a);
        }

        /**
         *
         * @return
         */
        public FloatList toList() {
            return FloatList.of(clone());
        }

        /**
         *
         * @return
         */
        public float sum() {
            return N.sum(a);
        }

        /**
         *
         * @return
         */
        public double average() {
            return N.average(a);
        }

        /**
         *
         * @return
         */
        public float min() {
            return N.min(a);
        }

        /**
         *
         * @return
         */
        public float max() {
            return N.max(a);
        }

        /**
         *
         * @param action
         */
        public void forEach(final FloatConsumer action) {
            for (float e : a) {
                action.accept(e);
            }
        }

        /**
         *
         * @param <R>
         * @param func
         * @return
         */
        public <R> R apply(final Function<? super float[], R> func) {
            return func.apply(a);
        }

        /**
         *
         * @param action
         */
        public void accept(final Consumer<? super float[]> action) {
            action.accept(a);
        }

        /**
         *
         * @param delimiter
         * @return
         */
        public String join(String delimiter) {
            return StringUtil.join(a, delimiter);
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return N.toString(a);
        }

        /**
         *
         * @return
         */
        protected float[] values() {
            return a;
        }
    }

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     */
    @Beta
    @SequentialOnly
    @Stateful
    public static class DisposableDoubleArray implements NoCachingNoUpdating, Cloneable {

        /** The a. */
        private final double[] a;

        /**
         * Instantiates a new disposable double array.
         *
         * @param a
         */
        protected DisposableDoubleArray(final double[] a) {
            N.checkArgNotNull(a, "a");
            this.a = a;
        }

        /**
         *
         * @param a
         * @return
         */
        public static DisposableDoubleArray wrap(final double[] a) {
            return new DisposableDoubleArray(a);
        }

        /**
         *
         * @param index
         * @return
         */
        public double get(final int index) {
            return a[index];
        }

        /**
         *
         * @return
         */
        public int length() {
            return a.length;
        }

        /**
         *
         * @return
         */
        @Override
        public double[] clone() {
            return N.clone(a);
        }

        /**
         *
         * @return
         */
        public Double[] box() {
            return Array.box(a);
        }

        /**
         *
         * @return
         */
        public DoubleList toList() {
            return DoubleList.of(clone());
        }

        /**
         *
         * @return
         */
        public double sum() {
            return N.sum(a);
        }

        /**
         *
         * @return
         */
        public double average() {
            return N.average(a);
        }

        /**
         *
         * @return
         */
        public double min() {
            return N.min(a);
        }

        /**
         *
         * @return
         */
        public double max() {
            return N.max(a);
        }

        /**
         *
         * @param action
         */
        public void forEach(final DoubleConsumer action) {
            for (double e : a) {
                action.accept(e);
            }
        }

        /**
         *
         * @param <R>
         * @param func
         * @return
         */
        public <R> R apply(final Function<? super double[], R> func) {
            return func.apply(a);
        }

        /**
         *
         * @param action
         */
        public void accept(final Consumer<? super double[]> action) {
            action.accept(a);
        }

        /**
         *
         * @param delimiter
         * @return
         */
        public String join(String delimiter) {
            return StringUtil.join(a, delimiter);
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return N.toString(a);
        }

        /**
         *
         * @return
         */
        protected double[] values() {
            return a;
        }
    }

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     *
     * @param <T>
     */
    @Beta
    @SequentialOnly
    @Stateful
    public static class DisposableDeque<T> implements NoCachingNoUpdating {

        /** The deque. */
        private final Deque<T> deque;

        /**
         * Instantiates a new disposable deque.
         *
         * @param deque
         */
        protected DisposableDeque(final Deque<T> deque) {
            N.checkArgNotNull(deque, "deque");
            this.deque = deque;
        }

        /**
         *
         * @param <T>
         * @param deque
         * @return
         */
        public static <T> DisposableDeque<T> wrap(final Deque<T> deque) {
            return new DisposableDeque<>(deque);
        }

        /**
         *
         * @return
         */
        public int size() {
            return deque.size();
        }

        /**
         * Gets the first.
         *
         * @return
         */
        public T getFirst() {
            return deque.getFirst();
        }

        /**
         * Gets the last.
         *
         * @return
         */
        public T getLast() {
            return deque.getLast();
        }

        /**
         *
         * @param <A>
         * @param a
         * @return
         */
        public <A> A[] toArray(A[] a) {
            return deque.toArray(a);
        }

        /**
         *
         * @return
         */
        public List<T> toList() {
            return new ArrayList<>(deque);
        }

        /**
         *
         * @param <C>
         * @param supplier
         * @return
         */
        public <C extends Collection<T>> C toCollection(final IntFunction<? extends C> supplier) {
            final C result = supplier.apply(size());
            result.addAll(deque);
            return result;
        }

        /**
         *
         * @param action
         */
        public void forEach(final Consumer<? super T> action) {
            for (T e : deque) {
                action.accept(e);
            }
        }

        /**
         *
         * @param <R>
         * @param func
         * @return
         */
        public <R> R apply(final Function<? super Deque<T>, R> func) {
            return func.apply(deque);
        }

        /**
         *
         * @param action
         */
        public void accept(final Consumer<? super Deque<T>> action) {
            action.accept(deque);
        }

        /**
         *
         * @param delimiter
         * @return
         */
        public String join(String delimiter) {
            return StringUtil.join(deque, delimiter);
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return N.toString(deque);
        }
    }

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     *
     * @param <K> the key type
     * @param <V> the value type
     */
    @Beta
    @SequentialOnly
    @Stateful
    public static abstract class DisposableEntry<K, V> implements Map.Entry<K, V>, NoCachingNoUpdating {

        /**
         *
         * @param <K> the key type
         * @param <V> the value type
         * @param entry
         * @return
         */
        public static <K, V> DisposableEntry<K, V> wrap(final Map.Entry<K, V> entry) {
            N.checkArgNotNull(entry, "entry");

            return new DisposableEntry<K, V>() {
                private final Map.Entry<K, V> e = entry;

                @Override
                public K getKey() {
                    return e.getKey();
                }

                @Override
                public V getValue() {
                    return e.getValue();
                }
            };
        }

        /**
         * Sets the value.
         *
         * @param value
         * @return
         * @throws UnsupportedOperationException the unsupported operation exception
         * @deprecated UnsupportedOperationException
         */
        @Deprecated
        @Override
        public V setValue(V value) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        public Map.Entry<K, V> copy() {
            return new AbstractMap.SimpleEntry<>(getKey(), getValue());
        }

        public <R> R apply(final Function<? super Map.Entry<K, V>, R> func) {
            return func.apply(this);
        }

        public <R> R apply(final BiFunction<? super K, ? super V, R> func) {
            return func.apply(this.getKey(), this.getValue());
        }

        public void accept(final Consumer<? super Map.Entry<K, V>> action) {
            action.accept(this);
        }

        public void accept(final BiConsumer<? super K, ? super V> action) {
            action.accept(this.getKey(), this.getValue());
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     *
     * @param <L>
     * @param <R>
     */
    @Beta
    @SequentialOnly
    @Stateful
    public static abstract class DisposablePair<L, R> implements NoCachingNoUpdating {

        /**
         *
         * @param <L>
         * @param <R>
         * @param p
         * @return
         */
        public static <L, R> DisposablePair<L, R> wrap(final Pair<L, R> p) {
            N.checkArgNotNull(p, "pair");

            return new DisposablePair<L, R>() {
                private final Pair<L, R> pair = p;

                @Override
                public L left() {
                    return pair.left;
                }

                @Override
                public R right() {
                    return pair.right;
                }
            };
        }

        public abstract L left();

        public abstract R right();

        public Pair<L, R> copy() {
            return Pair.of(left(), right());
        }

        public <U> U apply(final BiFunction<? super L, ? super R, U> func) {
            return func.apply(left(), right());
        }

        public void accept(final BiConsumer<? super L, ? super R> action) {
            action.accept(left(), right());
        }

        @Override
        public String toString() {
            return "[" + N.toString(left()) + ", " + N.toString(right()) + "]";
            // return N.toString(left()) + "=" + N.toString(right());
        }
    }

    /**
     * One-off Object. No caching/saving in memory, No updating. To cache/save/update the Object, call {@code clone()/copy()}.
     *
     * @param <L>
     * @param <M>
     * @param <R>
     */
    @Beta
    @SequentialOnly
    @Stateful
    public static abstract class DisposableTriple<L, M, R> implements NoCachingNoUpdating {

        /**
         *
         * @param <L>
         * @param <M>
         * @param <R>
         * @param p
         * @return
         */
        public static <L, M, R> DisposableTriple<L, M, R> wrap(final Triple<L, M, R> p) {
            N.checkArgNotNull(p, "triple");

            return new DisposableTriple<L, M, R>() {
                private final Triple<L, M, R> triple = p;

                @Override
                public L left() {
                    return triple.left;
                }

                @Override
                public M middle() {
                    return triple.middle;
                }

                @Override
                public R right() {
                    return triple.right;
                }
            };
        }

        public abstract L left();

        public abstract M middle();

        public abstract R right();

        public Triple<L, M, R> copy() {
            return Triple.of(left(), middle(), right());
        }

        public <U> U apply(final TriFunction<? super L, ? super M, ? super R, U> func) {
            return func.apply(left(), middle(), right());
        }

        public void accept(final TriConsumer<? super L, ? super M, ? super R> action) {
            action.accept(left(), middle(), right());
        }

        @Override
        public String toString() {
            return "[" + N.toString(left()) + ", " + N.toString(middle()) + ", " + N.toString(right()) + "]";
        }
    }
}
