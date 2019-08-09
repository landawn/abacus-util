/*
 * Copyright (C) 2017 HaiYang Li
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

import java.util.Collection;
import java.util.Map;

import com.landawn.abacus.annotation.Beta;
import com.landawn.abacus.util.function.Supplier;

// TODO: Auto-generated Javadoc
/**
 * This class is mainly designed for functional programming. 
 * Generally the traditional "{@code if-else}" or ternary operator: "{@code ? : }" is preferred over this class.
 * 
 * @author HaiYang Li
 *
 */
@Beta
public final class If {

    /** The Constant TRUE. */
    private static final If TRUE = new If(true);

    /** The Constant FALSE. */
    private static final If FALSE = new If(false);

    /** The b. */
    private final boolean b;

    /**
     * Instantiates a new if.
     *
     * @param b the b
     */
    If(boolean b) {
        this.b = b;
    }

    /**
     * Checks if is.
     *
     * @param b the b
     * @return the if
     */
    public static If is(final boolean b) {
        return b ? TRUE : FALSE;
    }

    /**
     * Not.
     *
     * @param b the b
     * @return the if
     */
    public static If not(final boolean b) {
        return b ? FALSE : TRUE;
    }

    /**
     * {@code true} for {@code index >= 0}, {@code false} for {@code index < 0}.
     *
     * @param index the index
     * @return the if
     */
    public static If exists(final int index) {
        return index >= 0 ? TRUE : FALSE;
    }

    /**
     * Checks if is null or empty.
     *
     * @param s the s
     * @return the if
     */
    public static If isNullOrEmpty(final CharSequence s) {
        return is(N.isNullOrEmpty(s));
    }

    /**
     * Checks if is null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If isNullOrEmpty(final boolean[] a) {
        return is(N.isNullOrEmpty(a));
    }

    /**
     * Checks if is null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If isNullOrEmpty(final char[] a) {
        return is(N.isNullOrEmpty(a));
    }

    /**
     * Checks if is null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If isNullOrEmpty(final byte[] a) {
        return is(N.isNullOrEmpty(a));
    }

    /**
     * Checks if is null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If isNullOrEmpty(final short[] a) {
        return is(N.isNullOrEmpty(a));
    }

    /**
     * Checks if is null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If isNullOrEmpty(final int[] a) {
        return is(N.isNullOrEmpty(a));
    }

    /**
     * Checks if is null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If isNullOrEmpty(final long[] a) {
        return is(N.isNullOrEmpty(a));
    }

    /**
     * Checks if is null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If isNullOrEmpty(final float[] a) {
        return is(N.isNullOrEmpty(a));
    }

    /**
     * Checks if is null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If isNullOrEmpty(final double[] a) {
        return is(N.isNullOrEmpty(a));
    }

    /**
     * Checks if is null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If isNullOrEmpty(final Object[] a) {
        return is(N.isNullOrEmpty(a));
    }

    /**
     * Checks if is null or empty.
     *
     * @param c the c
     * @return the if
     */
    public static If isNullOrEmpty(final Collection<?> c) {
        return is(N.isNullOrEmpty(c));
    }

    /**
     * Checks if is null or empty.
     *
     * @param m the m
     * @return the if
     */
    public static If isNullOrEmpty(final Map<?, ?> m) {
        return is(N.isNullOrEmpty(m));
    }

    /**
     * Checks if is null or empty.
     *
     * @param list the list
     * @return the if
     */
    @SuppressWarnings("rawtypes")
    public static If isNullOrEmpty(final PrimitiveList list) {
        return is(N.isNullOrEmpty(list));
    }

    /**
     * Checks if is null or empty.
     *
     * @param s the s
     * @return the if
     */
    public static If isNullOrEmpty(final Multiset<?> s) {
        return is(N.isNullOrEmpty(s));
    }

    /**
     * Checks if is null or empty.
     *
     * @param s the s
     * @return the if
     */
    public static If isNullOrEmpty(final LongMultiset<?> s) {
        return is(N.isNullOrEmpty(s));
    }

    /**
     * Checks if is null or empty.
     *
     * @param m the m
     * @return the if
     */
    public static If isNullOrEmpty(final Multimap<?, ?, ?> m) {
        return is(N.isNullOrEmpty(m));
    }

    /**
     * Checks if is null or empty or blank.
     *
     * @param s the s
     * @return the if
     */
    // DON'T change 'OrEmptyOrBlank' to 'OrBlank' because of the occurring order in the auto-completed context menu. 
    public static If isNullOrEmptyOrBlank(final CharSequence s) {
        return is(N.isNullOrEmptyOrBlank(s));
    }

    /**
     * Not null or empty.
     *
     * @param s the s
     * @return the if
     */
    public static If notNullOrEmpty(final CharSequence s) {
        return is(N.notNullOrEmpty(s));
    }

    /**
     * Not null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If notNullOrEmpty(final boolean[] a) {
        return is(N.notNullOrEmpty(a));
    }

    /**
     * Not null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If notNullOrEmpty(final char[] a) {
        return is(N.notNullOrEmpty(a));
    }

    /**
     * Not null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If notNullOrEmpty(final byte[] a) {
        return is(N.notNullOrEmpty(a));
    }

    /**
     * Not null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If notNullOrEmpty(final short[] a) {
        return is(N.notNullOrEmpty(a));
    }

    /**
     * Not null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If notNullOrEmpty(final int[] a) {
        return is(N.notNullOrEmpty(a));
    }

    /**
     * Not null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If notNullOrEmpty(final long[] a) {
        return is(N.notNullOrEmpty(a));
    }

    /**
     * Not null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If notNullOrEmpty(final float[] a) {
        return is(N.notNullOrEmpty(a));
    }

    /**
     * Not null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If notNullOrEmpty(final double[] a) {
        return is(N.notNullOrEmpty(a));
    }

    /**
     * Not null or empty.
     *
     * @param a the a
     * @return the if
     */
    public static If notNullOrEmpty(final Object[] a) {
        return is(N.notNullOrEmpty(a));
    }

    /**
     * Not null or empty.
     *
     * @param c the c
     * @return the if
     */
    public static If notNullOrEmpty(final Collection<?> c) {
        return is(N.notNullOrEmpty(c));
    }

    /**
     * Not null or empty.
     *
     * @param m the m
     * @return the if
     */
    public static If notNullOrEmpty(final Map<?, ?> m) {
        return is(N.notNullOrEmpty(m));
    }

    /**
     * Not null or empty.
     *
     * @param list the list
     * @return the if
     */
    @SuppressWarnings("rawtypes")
    public static If notNullOrEmpty(final PrimitiveList list) {
        return is(N.notNullOrEmpty(list));
    }

    /**
     * Not null or empty.
     *
     * @param s the s
     * @return the if
     */
    public static If notNullOrEmpty(final Multiset<?> s) {
        return is(N.notNullOrEmpty(s));
    }

    /**
     * Not null or empty.
     *
     * @param s the s
     * @return the if
     */
    public static If notNullOrEmpty(final LongMultiset<?> s) {
        return is(N.notNullOrEmpty(s));
    }

    /**
     * Not null or empty.
     *
     * @param m the m
     * @return the if
     */
    public static If notNullOrEmpty(final Multimap<?, ?, ?> m) {
        return is(N.notNullOrEmpty(m));
    }

    /**
     * Not null or empty or blank.
     *
     * @param s the s
     * @return the if
     */
    // DON'T change 'OrEmptyOrBlank' to 'OrBlank' because of the occurring order in the auto-completed context menu. 
    public static If notNullOrEmptyOrBlank(final CharSequence s) {
        return is(N.notNullOrEmptyOrBlank(s));
    }

    //    public <E extends Exception> void thenRun(final Try.Runnable<E> cmd) throws E {
    //        if (b) {
    //            cmd.run();
    //        }
    //    }
    //
    //    public <T, E extends Exception> void thenRun(final T init, final Try.Consumer<? super T, E> action) throws E {
    //        if (b) {
    //            action.accept(init);
    //        }
    //    }
    //
    //    public <T, E extends Exception> Nullable<T> thenCall(final Try.Callable<? extends T, E> callable) throws E {
    //        return b ? Nullable.of(callable.call()) : Nullable.<T> empty();
    //    }
    //
    //    public <T, R, E extends Exception> Nullable<R> thenCall(final T init, final Try.Function<? super T, R, E> func) throws E {
    //        return b ? Nullable.of(func.apply(init)) : Nullable.<R> empty();
    //    }

    /**
     * Then do nothing.
     *
     * @return the or
     */
    public Or thenDoNothing() {
        return Or.of(b);
    }

    /**
     * Then.
     *
     * @param <E> the element type
     * @param cmd the cmd
     * @return the or
     * @throws E the e
     */
    public <E extends Exception> Or then(final Try.Runnable<E> cmd) throws E {
        N.checkArgNotNull(cmd);

        if (b) {
            cmd.run();
        }

        return Or.of(b);
    }

    /**
     * Then.
     *
     * @param <U> the generic type
     * @param <E> the element type
     * @param init the init
     * @param action the action
     * @return the or
     * @throws E the e
     */
    public <U, E extends Exception> Or then(final U init, final Try.Consumer<? super U, E> action) throws E {
        N.checkArgNotNull(action);

        if (b) {
            action.accept(init);
        }

        return Or.of(b);
    }

    /**
     * Then throw.
     *
     * @param <E> the element type
     * @param exceptionSupplier the exception supplier
     * @return the or
     * @throws E the e
     */
    public <E extends Exception> Or thenThrow(final Supplier<? extends E> exceptionSupplier) throws E {
        N.checkArgNotNull(exceptionSupplier);

        if (b) {
            throw exceptionSupplier.get();
        }

        return Or.of(b);
    }

    //    public <T, E extends Exception> Nullable<T> then(final Try.Callable<T, E> callable) throws E {
    //        return b ? Nullable.of(callable.call()) : Nullable.<T> empty();
    //    }
    //
    //    public <T, R, E extends Exception> Nullable<R> then(final T init, final Try.Function<? super T, R, E> func) throws E {
    //        return b ? Nullable.of(func.apply(init)) : Nullable.<R> empty();
    //    }

    /**
     * The Class Or.
     */
    public static final class Or {

        /** The Constant TRUE. */
        private static final Or TRUE = new Or(true);

        /** The Constant FALSE. */
        private static final Or FALSE = new Or(false);

        /** The b. */
        private final boolean b;

        /**
         * Instantiates a new or.
         *
         * @param b the b
         */
        Or(final boolean b) {
            this.b = b;
        }

        /**
         * Of.
         *
         * @param b the b
         * @return the or
         */
        static Or of(boolean b) {
            return b ? TRUE : FALSE;
        }

        /**
         * Or else do nothing.
         */
        void orElseDoNothing() {
            // Do nothing.
        }

        /**
         * Or else.
         *
         * @param <E> the element type
         * @param cmd the cmd
         * @throws E the e
         */
        public <E extends Exception> void orElse(final Try.Runnable<E> cmd) throws E {
            N.checkArgNotNull(cmd);

            if (!b) {
                cmd.run();
            }
        }

        /**
         * Or else.
         *
         * @param <U> the generic type
         * @param <E> the element type
         * @param init the init
         * @param action the action
         * @throws E the e
         */
        public <U, E extends Exception> void orElse(final U init, final Try.Consumer<? super U, E> action) throws E {
            N.checkArgNotNull(action);

            if (!b) {
                action.accept(init);
            }
        }

        /**
         * Or else throw.
         *
         * @param <E> the element type
         * @param exceptionSupplier the exception supplier
         * @throws E the e
         */
        public <E extends Exception> void orElseThrow(final Supplier<? extends E> exceptionSupplier) throws E {
            N.checkArgNotNull(exceptionSupplier);

            if (!b) {
                throw exceptionSupplier.get();
            }
        }
    }

    //    /**
    //     * This class is mainly designed for functional programming. 
    //     * Generally the traditional "{@code if-else}" or ternary operator: "{@code ? : }" is preferred over this class.
    //     * 
    //     * @author HaiYang Li
    //     *
    //     */
    //    @Beta
    //    @Deprecated
    //    public static final class IF {
    //        private static final IF TRUE = new IF(true);
    //        private static final IF FALSE = new IF(false);
    //
    //        @SuppressWarnings("rawtypes")
    //        private static final Or FALSE_OR = new FalseOr();
    //
    //        private final boolean b;
    //
    //        IF(boolean b) {
    //            this.b = b;
    //        }
    //
    //        public static IF is(final boolean b) {
    //            return b ? TRUE : FALSE;
    //        }
    //
    //        public static IF not(final boolean b) {
    //            return b ? FALSE : TRUE;
    //        }
    //
    //        /**
    //         * {@code true} for {@code index >= 0}, {@code false} for {@code index < 0}.
    //         * 
    //         * @param index
    //         * @return
    //         */
    //        public static IF exists(final int index) {
    //            return index >= 0 ? TRUE : FALSE;
    //        }
    //
    //        public static IF isNullOrEmpty(final CharSequence s) {
    //            return is(N.isNullOrEmpty(s));
    //        }
    //
    //        public static IF isNullOrEmpty(final boolean[] a) {
    //            return is(N.isNullOrEmpty(a));
    //        }
    //
    //        public static IF isNullOrEmpty(final char[] a) {
    //            return is(N.isNullOrEmpty(a));
    //        }
    //
    //        public static IF isNullOrEmpty(final byte[] a) {
    //            return is(N.isNullOrEmpty(a));
    //        }
    //
    //        public static IF isNullOrEmpty(final short[] a) {
    //            return is(N.isNullOrEmpty(a));
    //        }
    //
    //        public static IF isNullOrEmpty(final int[] a) {
    //            return is(N.isNullOrEmpty(a));
    //        }
    //
    //        public static IF isNullOrEmpty(final long[] a) {
    //            return is(N.isNullOrEmpty(a));
    //        }
    //
    //        public static IF isNullOrEmpty(final float[] a) {
    //            return is(N.isNullOrEmpty(a));
    //        }
    //
    //        public static IF isNullOrEmpty(final double[] a) {
    //            return is(N.isNullOrEmpty(a));
    //        }
    //
    //        public static IF isNullOrEmpty(final Object[] a) {
    //            return is(N.isNullOrEmpty(a));
    //        }
    //
    //        public static IF isNullOrEmpty(final Collection<?> c) {
    //            return is(N.isNullOrEmpty(c));
    //        }
    //
    //        public static IF isNullOrEmpty(final Map<?, ?> m) {
    //            return is(N.isNullOrEmpty(m));
    //        }
    //
    //        @SuppressWarnings("rawtypes")
    //        public static IF isNullOrEmpty(final PrimitiveList list) {
    //            return is(N.isNullOrEmpty(list));
    //        }
    //
    //        public static IF isNullOrEmpty(final Multiset<?> s) {
    //            return is(N.isNullOrEmpty(s));
    //        }
    //
    //        public static IF isNullOrEmpty(final LongMultiset<?> s) {
    //            return is(N.isNullOrEmpty(s));
    //        }
    //
    //        public static IF isNullOrEmpty(final Multimap<?, ?, ?> m) {
    //            return is(N.isNullOrEmpty(m));
    //        }
    //
    //        // DON'T change 'OrEmptyOrBlank' to 'OrBlank' because of the occurring order in the auto-completed context menu. 
    //        public static IF isNullOrEmptyOrBlank(final CharSequence s) {
    //            return is(N.isNullOrEmptyOrBlank(s));
    //        }
    //
    //        public static IF notNullOrEmpty(final CharSequence s) {
    //            return is(N.notNullOrEmpty(s));
    //        }
    //
    //        public static IF notNullOrEmpty(final boolean[] a) {
    //            return is(N.notNullOrEmpty(a));
    //        }
    //
    //        public static IF notNullOrEmpty(final char[] a) {
    //            return is(N.notNullOrEmpty(a));
    //        }
    //
    //        public static IF notNullOrEmpty(final byte[] a) {
    //            return is(N.notNullOrEmpty(a));
    //        }
    //
    //        public static IF notNullOrEmpty(final short[] a) {
    //            return is(N.notNullOrEmpty(a));
    //        }
    //
    //        public static IF notNullOrEmpty(final int[] a) {
    //            return is(N.notNullOrEmpty(a));
    //        }
    //
    //        public static IF notNullOrEmpty(final long[] a) {
    //            return is(N.notNullOrEmpty(a));
    //        }
    //
    //        public static IF notNullOrEmpty(final float[] a) {
    //            return is(N.notNullOrEmpty(a));
    //        }
    //
    //        public static IF notNullOrEmpty(final double[] a) {
    //            return is(N.notNullOrEmpty(a));
    //        }
    //
    //        public static IF notNullOrEmpty(final Object[] a) {
    //            return is(N.notNullOrEmpty(a));
    //        }
    //
    //        public static IF notNullOrEmpty(final Collection<?> c) {
    //            return is(N.notNullOrEmpty(c));
    //        }
    //
    //        public static IF notNullOrEmpty(final Map<?, ?> m) {
    //            return is(N.notNullOrEmpty(m));
    //        }
    //
    //        @SuppressWarnings("rawtypes")
    //        public static IF notNullOrEmpty(final PrimitiveList list) {
    //            return is(N.notNullOrEmpty(list));
    //        }
    //
    //        public static IF notNullOrEmpty(final Multiset<?> s) {
    //            return is(N.notNullOrEmpty(s));
    //        }
    //
    //        public static IF notNullOrEmpty(final LongMultiset<?> s) {
    //            return is(N.notNullOrEmpty(s));
    //        }
    //
    //        public static IF notNullOrEmpty(final Multimap<?, ?, ?> m) {
    //            return is(N.notNullOrEmpty(m));
    //        }
    //
    //        // DON'T change 'OrEmptyOrBlank' to 'OrBlank' because of the occurring order in the auto-completed context menu. 
    //        public static IF notNullOrEmptyOrBlank(final CharSequence s) {
    //            return is(N.notNullOrEmptyOrBlank(s));
    //        }
    //
    //        public <T, E extends Exception> Nullable<T> thenGet(Try.Supplier<T, E> supplier) throws E {
    //            return b ? Nullable.of(supplier.get()) : Nullable.<T> empty();
    //        }
    //
    //        public <T, U, E extends Exception> Nullable<T> thenApply(final U init, final Try.Function<? super U, T, E> func) throws E {
    //            return b ? Nullable.of(func.apply(init)) : Nullable.<T> empty();
    //        }
    //
    //        public <T, E extends Exception> Or<T> then(final Try.Callable<T, E> callable) throws E {
    //            N.checkArgNotNull(callable);
    //
    //            return b ? new TrueOr<>(callable.call()) : FALSE_OR;
    //        }
    //
    //        public <T, U, E extends Exception> Or<T> then(final U init, final Try.Function<? super U, T, E> func) throws E {
    //            N.checkArgNotNull(func);
    //
    //            return b ? new TrueOr<>(func.apply(init)) : FALSE_OR;
    //        }
    //
    //        public static abstract class Or<T> {
    //            Or() {
    //            }
    //
    //            public abstract <E extends Exception> T orElse(final Try.Callable<T, E> callable) throws E;
    //
    //            public abstract <U, E extends Exception> T orElse(final U init, final Try.Function<? super U, T, E> func) throws E;
    //
    //            public abstract <E extends Exception> T orElseThrow(final Supplier<? extends E> exceptionSupplier) throws E;
    //        }
    //
    //        static final class TrueOr<T> extends Or<T> {
    //            private final T result;
    //
    //            TrueOr(final T result) {
    //                this.result = result;
    //            }
    //
    //            @Override
    //            public <E extends Exception> T orElse(final Try.Callable<T, E> callable) throws E {
    //                N.checkArgNotNull(callable);
    //
    //                return result;
    //            }
    //
    //            @Override
    //            public <U, E extends Exception> T orElse(final U init, final Try.Function<? super U, T, E> func) throws E {
    //                N.checkArgNotNull(func);
    //
    //                return result;
    //            }
    //
    //            @Override
    //            public <E extends Exception> T orElseThrow(final Supplier<? extends E> exceptionSupplier) throws E {
    //                N.checkArgNotNull(exceptionSupplier);
    //
    //                return result;
    //            }
    //        }
    //
    //        static final class FalseOr<T> extends Or<T> {
    //            FalseOr() {
    //            }
    //
    //            @Override
    //            public <E extends Exception> T orElse(final Try.Callable<T, E> callable) throws E {
    //                return callable.call();
    //            }
    //
    //            @Override
    //            public <U, E extends Exception> T orElse(final U init, final Try.Function<? super U, T, E> func) throws E {
    //                return func.apply(init);
    //            }
    //
    //            @Override
    //            public <E extends Exception> T orElseThrow(final Supplier<? extends E> exceptionSupplier) throws E {
    //                throw exceptionSupplier.get();
    //            }
    //        }
    //    }
}
