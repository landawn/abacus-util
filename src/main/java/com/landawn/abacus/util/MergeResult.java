/*
 * Copyright (C) 2016 HaiYang Li
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

import java.util.Comparator;

import com.landawn.abacus.annotation.Beta;
import com.landawn.abacus.annotation.SequentialOnly;
import com.landawn.abacus.annotation.Stateful;
import com.landawn.abacus.util.function.BiFunction;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public enum MergeResult {
    TAKE_FIRST, TAKE_SECOND;

    /*, THIRD, FOURTH, FIFTH, SIXTH, SEVENTH*/;

    public static <T extends Comparable<? super T>> MergeResult minFirst(final T a, final T b) {
        return N.compare(a, b) <= 0 ? MergeResult.TAKE_FIRST : MergeResult.TAKE_SECOND;
    }

    /**
     *
     * @param <T>
     * @param a
     * @param b
     * @param cmp
     * @return
     */
    public static <T> MergeResult minFirst(final T a, final T b, final Comparator<? super T> cmp) {
        return cmp.compare(a, b) <= 0 ? MergeResult.TAKE_FIRST : MergeResult.TAKE_SECOND;
    }

    /**
     *
     * @param <T>
     * @param a
     * @param b
     * @return
     */
    public static <T extends Comparable<? super T>> MergeResult maxFirst(final T a, final T b) {
        return N.compare(a, b) >= 0 ? MergeResult.TAKE_FIRST : MergeResult.TAKE_SECOND;
    }

    /**
     *
     * @param <T>
     * @param a
     * @param b
     * @param cmp
     * @return
     */
    public static <T> MergeResult maxFirst(final T a, final T b, final Comparator<? super T> cmp) {
        return cmp.compare(a, b) >= 0 ? MergeResult.TAKE_FIRST : MergeResult.TAKE_SECOND;
    }

    /**
     * Returns a stateful {@code BiFunction}. Don't cache or reuse it.
     *
     * @param <T>
     * @return
     * @deprecated Use {@link Fn#alternated()} instead
     */
    @Deprecated
    @Beta
    @SequentialOnly
    @Stateful
    public static <T> BiFunction<T, T, MergeResult> alternated() {
        return Fn.alternated();
    }
}