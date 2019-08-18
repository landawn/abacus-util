/*
 * Copyright (c) 2017, Haiyang Li.
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

package com.landawn.abacus.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.landawn.abacus.annotation.Beta;
import com.landawn.abacus.exception.DuplicatedResultException;
import com.landawn.abacus.util.Fn.BiFunctions;
import com.landawn.abacus.util.Fn.FN;
import com.landawn.abacus.util.Fn.Factory;
import com.landawn.abacus.util.Fn.Suppliers;
import com.landawn.abacus.util.u.Nullable;
import com.landawn.abacus.util.u.Optional;
import com.landawn.abacus.util.u.OptionalDouble;
import com.landawn.abacus.util.u.OptionalInt;
import com.landawn.abacus.util.function.BiConsumer;
import com.landawn.abacus.util.function.BiFunction;
import com.landawn.abacus.util.function.Function;
import com.landawn.abacus.util.function.IntFunction;
import com.landawn.abacus.util.function.Supplier;
import com.landawn.abacus.util.stream.Collector;

// TODO: Auto-generated Javadoc
/**
 * It's an read-only wrapper for <code>Collection</code> to support more daily used/functional methods.
 * All the operations are null safety. And an empty <code>String</code>/<code>Array</code>/<code>Collection</code>/<code>Optional</code>/<code>Nullable</code> will be returned if possible, instead of null.
 * 
 * <br />
 * <code>Seq</code> should not be passed as a parameter or returned as a result because it's a pure utility class for the operations/calculation based on Collection/Array
 *
 * @author Haiyang Li
 * @param <T>
 * @since 0.8
 */
@Beta
public final class Seq<T> extends ImmutableCollection<T> {

    /** The Constant EMPTY. */
    @SuppressWarnings("rawtypes")
    private static final Seq EMPTY = new Seq<>(Collections.EMPTY_LIST);

    /**
     * The returned <code>Seq</code> and the specified <code>Collection</code> are backed by the same data.
     * Any changes to one will appear in the other.
     *
     * @param c
     */
    Seq(final Collection<T> c) {
        super(c == null ? Collections.EMPTY_LIST : c);
    }

    /**
     * Empty.
     *
     * @param <T>
     * @return
     */
    public static <T> Seq<T> empty() {
        return EMPTY;
    }

    /**
     * Just.
     *
     * @param <T>
     * @param t
     * @return
     */
    public static <T> Seq<T> just(T t) {
        return of(t);
    }

    /**
     * Of.
     *
     * @param <T>
     * @param a
     * @return
     */
    @SafeVarargs
    public static <T> Seq<T> of(final T... a) {
        if (N.isNullOrEmpty(a)) {
            return EMPTY;
        }

        return of(Arrays.asList(a));
    }

    /**
     * The returned <code>Seq</code> and the specified <code>Collection</code> are backed by the same data.
     * Any changes to one will appear in the other.
     *
     * @param <T>
     * @param c
     * @return
     */
    public static <T> Seq<T> of(Collection<T> c) {
        if (N.isNullOrEmpty(c)) {
            return EMPTY;
        }

        return new Seq<>(c);
    }

    /**
     * Of.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map
     * @return
     */
    public static <K, V> Seq<Map.Entry<K, V>> of(Map<K, V> map) {
        if (N.isNullOrEmpty(map)) {
            return EMPTY;
        }

        return of(map.entrySet());
    }

    //    /**
    //     * Returns the <code>Collection</code> the <code>Seq</code> is backed with recursively.
    //     * 
    //     * @return
    //     */
    //    public Collection<T> interior() {
    //        if (coll == null) {
    //            return coll;
    //        }
    //
    //        Collection<T> tmp = coll;
    //
    //        if (tmp instanceof Seq) {
    //            while (tmp instanceof Seq) {
    //                tmp = ((Seq<T>) tmp).coll;
    //            }
    //        }
    //
    //        if (tmp instanceof SubCollection) {
    //            while (tmp instanceof SubCollection) {
    //                tmp = ((SubCollection<T>) tmp).c;
    //            }
    //        }
    //
    //        if (tmp instanceof Seq) {
    //            return ((Seq<T>) tmp).interior();
    //        } else {
    //            return tmp;
    //        }
    //    }

    /**
     * Contains.
     *
     * @param e
     * @return true, if successful
     */
    @Override
    public boolean contains(Object e) {
        if (N.isNullOrEmpty(coll)) {
            return false;
        }

        return coll.contains(e);
    }

    /**
     * Contains all.
     *
     * @param c
     * @return true, if successful
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        if (N.isNullOrEmpty(c)) {
            return true;
        } else if (N.isNullOrEmpty(coll)) {
            return false;
        }

        return coll.containsAll(c);
    }

    /**
     * Contains all.
     *
     * @param a
     * @return true, if successful
     */
    public boolean containsAll(Object[] a) {
        if (N.isNullOrEmpty(a)) {
            return true;
        } else if (N.isNullOrEmpty(coll)) {
            return false;
        }

        return containsAll(Arrays.asList(a));
    }

    /**
     * Contains any.
     *
     * @param c
     * @return true, if successful
     */
    public boolean containsAny(Collection<?> c) {
        if (N.isNullOrEmpty(coll) || N.isNullOrEmpty(c)) {
            return false;
        }

        return !disjoint(c);
    }

    /**
     * Contains any.
     *
     * @param a
     * @return true, if successful
     */
    public boolean containsAny(Object[] a) {
        if (N.isNullOrEmpty(coll) || N.isNullOrEmpty(a)) {
            return false;
        }

        return !disjoint(a);
    }

    /**
     * Disjoint.
     *
     * @param c
     * @return true, if successful
     */
    public boolean disjoint(final Collection<?> c) {
        return N.disjoint(this.coll, c);
    }

    /**
     * Disjoint.
     *
     * @param a
     * @return true, if successful
     */
    public boolean disjoint(final Object[] a) {
        if (N.isNullOrEmpty(coll) || N.isNullOrEmpty(a)) {
            return true;
        }

        return disjoint(Arrays.asList(a));
    }

    /**
     * Intersection.
     *
     * @param b
     * @return
     * @see IntList#intersection(IntList)
     */
    public List<T> intersection(Collection<?> b) {
        return N.intersection(coll, b);
    }

    /**
     * Intersection.
     *
     * @param a
     * @return
     */
    public List<T> intersection(final Object[] a) {
        return N.intersection(coll, Array.asList(a));
    }

    /**
     * Difference.
     *
     * @param b
     * @return
     * @see IntList#difference(IntList)
     */
    public List<T> difference(Collection<?> b) {
        return N.difference(coll, b);
    }

    /**
     * Difference.
     *
     * @param a
     * @return
     */
    public List<T> difference(final Object[] a) {
        return N.difference(coll, Array.asList(a));
    }

    /**
     * Symmetric difference.
     *
     * @param b
     * @return this.difference(b).addAll(b.difference(this))
     * @see IntList#symmetricDifference(IntList)
     */
    public List<T> symmetricDifference(Collection<? extends T> b) {
        return N.symmetricDifference(coll, b);
    }

    /**
     * Symmetric difference.
     *
     * @param a
     * @return
     */
    public List<T> symmetricDifference(final T[] a) {
        return N.symmetricDifference(coll, Array.asList(a));
    }

    /**
     * Occurrences of.
     *
     * @param objectToFind
     * @return
     */
    public int occurrencesOf(final Object objectToFind) {
        return N.isNullOrEmpty(coll) ? 0 : N.occurrencesOf(coll, objectToFind);
    }

    /**
     * Min.
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Nullable<T> min() {
        return size() == 0 ? (Nullable<T>) Nullable.empty() : Nullable.of((T) N.min((Collection) coll));
    }

    /**
     * Min.
     *
     * @param cmp
     * @return
     */
    public Nullable<T> min(Comparator<? super T> cmp) {
        return size() == 0 ? (Nullable<T>) Nullable.empty() : Nullable.of(N.min(coll, cmp));
    }

    /**
     * Min by.
     *
     * @param keyMapper
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Nullable<T> minBy(final Function<? super T, ? extends Comparable> keyMapper) {
        return min(Fn.comparingBy(keyMapper));
    }

    /**
     * Max.
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Nullable<T> max() {
        return size() == 0 ? (Nullable<T>) Nullable.empty() : Nullable.of((T) N.max((Collection) coll));
    }

    /**
     * Max.
     *
     * @param cmp
     * @return
     */
    public Nullable<T> max(Comparator<? super T> cmp) {
        return size() == 0 ? (Nullable<T>) Nullable.empty() : Nullable.of(N.max(coll, cmp));
    }

    /**
     * Max by.
     *
     * @param keyMapper
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Nullable<T> maxBy(final Function<? super T, ? extends Comparable> keyMapper) {
        return max(Fn.comparingBy(keyMapper));
    }

    /**
     * Median.
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Nullable<T> median() {
        return size() == 0 ? (Nullable<T>) Nullable.empty() : Nullable.of((T) N.median((Collection) coll));
    }

    /**
     * Median.
     *
     * @param cmp
     * @return
     */
    public Nullable<T> median(Comparator<? super T> cmp) {
        return size() == 0 ? (Nullable<T>) Nullable.empty() : Nullable.of(N.median(coll, cmp));
    }

    /**
     * Kth largest.
     *
     * @param k
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Nullable<T> kthLargest(final int k) {
        N.checkArgPositive(k, "k");

        return size() < k ? (Nullable<T>) Nullable.empty() : Nullable.of((T) N.kthLargest((Collection) coll, k));
    }

    /**
     * Kth largest.
     *
     * @param k
     * @param cmp
     * @return
     */
    public Nullable<T> kthLargest(final int k, Comparator<? super T> cmp) {
        N.checkArgPositive(k, "k");

        return size() < k ? (Nullable<T>) Nullable.empty() : Nullable.of(N.kthLargest(coll, k, cmp));
    }

    /**
     * Sum int.
     *
     * @param <E>
     * @param mapper
     * @return
     * @throws E the e
     */
    public <E extends Exception> int sumInt(final Try.ToIntFunction<? super T, E> mapper) throws E {
        if (N.isNullOrEmpty(coll)) {
            return 0;
        }

        return N.sumInt(coll, mapper);
    }

    /**
     * Sum long.
     *
     * @param <E>
     * @param mapper
     * @return
     * @throws E the e
     */
    public <E extends Exception> long sumLong(final Try.ToLongFunction<? super T, E> mapper) throws E {
        if (N.isNullOrEmpty(coll)) {
            return 0L;
        }

        return N.sumLong(coll, mapper);
    }

    /**
     * Sum double.
     *
     * @param <E>
     * @param mapper
     * @return
     * @throws E the e
     */
    public <E extends Exception> double sumDouble(final Try.ToDoubleFunction<? super T, E> mapper) throws E {
        if (N.isNullOrEmpty(coll)) {
            return 0L;
        }

        return N.sumDouble(coll, mapper);
    }

    /**
     * Average int.
     *
     * @param <E>
     * @param mapper
     * @return
     * @throws E the e
     */
    public <E extends Exception> OptionalDouble averageInt(final Try.ToIntFunction<? super T, E> mapper) throws E {
        return N.averageInt(coll, mapper);
    }

    /**
     * Average long.
     *
     * @param <E>
     * @param mapper
     * @return
     * @throws E the e
     */
    public <E extends Exception> OptionalDouble averageLong(final Try.ToLongFunction<? super T, E> mapper) throws E {
        return N.averageLong(coll, mapper);
    }

    /**
     * Average double.
     *
     * @param <E>
     * @param mapper
     * @return
     * @throws E the e
     */
    public <E extends Exception> OptionalDouble averageDouble(final Try.ToDoubleFunction<? super T, E> mapper) throws E {
        return N.averageDouble(coll, mapper);
    }

    /**
     * Foreach.
     *
     * @param <E>
     * @param action
     * @throws E the e
     */
    public <E extends Exception> void foreach(final Try.Consumer<? super T, E> action) throws E {
        N.forEach(coll, action);
    }

    /**
     * For each.
     *
     * @param <E>
     * @param <E2>
     * @param action
     * @param onComplete
     * @throws E the e
     * @throws E2 the e2
     */
    public <E extends Exception, E2 extends Exception> void forEach(final Try.Consumer<? super T, E> action, Try.Runnable<E2> onComplete) throws E, E2 {
        N.forEach(coll, action);

        onComplete.run();
    }

    //    public <E extends Exception> void forEach(int fromIndex, final int toIndex, final Consumer<? super T> action) throws E {
    //        N.forEach(coll, fromIndex, toIndex, action);
    //    }

    /**
     * For each.
     *
     * @param <E>
     * @param action
     * @throws E the e
     */
    public <E extends Exception> void forEach(final Try.IndexedConsumer<? super T, E> action) throws E {
        N.forEach(coll, action);
    }

    /**
     * For each.
     *
     * @param <U>
     * @param <E>
     * @param <E2>
     * @param flatMapper
     * @param action
     * @throws E the e
     * @throws E2 the e2
     */
    public <U, E extends Exception, E2 extends Exception> void forEach(final Try.Function<? super T, ? extends Collection<U>, E> flatMapper,
            final Try.BiConsumer<? super T, ? super U, E2> action) throws E, E2 {
        N.forEach(coll, flatMapper, action);
    }

    /**
     * For each.
     *
     * @param <T2>
     * @param <T3>
     * @param <E>
     * @param <E2>
     * @param <E3>
     * @param flatMapper
     * @param flatMapper2
     * @param action
     * @throws E the e
     * @throws E2 the e2
     * @throws E3 the e3
     */
    public <T2, T3, E extends Exception, E2 extends Exception, E3 extends Exception> void forEach(
            final Try.Function<? super T, ? extends Collection<T2>, E> flatMapper, final Try.Function<? super T2, ? extends Collection<T3>, E2> flatMapper2,
            final Try.TriConsumer<? super T, ? super T2, ? super T3, E3> action) throws E, E2, E3 {
        N.forEach(coll, flatMapper, flatMapper2, action);
    }

    /**
     * For each non null.
     *
     * @param <E>
     * @param action
     * @throws E the e
     */
    public <E extends Exception> void forEachNonNull(final Try.Consumer<? super T, E> action) throws E {
        N.forEachNonNull(coll, action);
    }

    /**
     * For each non null.
     *
     * @param <U>
     * @param <E>
     * @param <E2>
     * @param flatMapper
     * @param action
     * @throws E the e
     * @throws E2 the e2
     */
    public <U, E extends Exception, E2 extends Exception> void forEachNonNull(final Try.Function<? super T, ? extends Collection<U>, E> flatMapper,
            final Try.BiConsumer<? super T, ? super U, E2> action) throws E, E2 {
        N.forEachNonNull(coll, flatMapper, action);
    }

    /**
     * For each non null.
     *
     * @param <T2>
     * @param <T3>
     * @param <E>
     * @param <E2>
     * @param <E3>
     * @param flatMapper
     * @param flatMapper2
     * @param action
     * @throws E the e
     * @throws E2 the e2
     * @throws E3 the e3
     */
    public <T2, T3, E extends Exception, E2 extends Exception, E3 extends Exception> void forEachNonNull(
            final Try.Function<? super T, ? extends Collection<T2>, E> flatMapper, final Try.Function<? super T2, ? extends Collection<T3>, E2> flatMapper2,
            final Try.TriConsumer<? super T, ? super T2, ? super T3, E3> action) throws E, E2, E3 {
        N.forEachNonNull(coll, flatMapper, flatMapper2, action);
    }

    /**
     * For each pair.
     *
     * @param <E>
     * @param action
     * @throws E the e
     */
    public <E extends Exception> void forEachPair(final Try.BiConsumer<? super T, ? super T, E> action) throws E {
        forEachPair(action, 1);
    }

    /**
     * For each pair.
     *
     * @param <E>
     * @param action
     * @param increment
     * @throws E the e
     */
    public <E extends Exception> void forEachPair(final Try.BiConsumer<? super T, ? super T, E> action, final int increment) throws E {
        N.checkArgNotNull(action);
        final int windowSize = 2;
        N.checkArgument(windowSize > 0 && increment > 0, "windowSize=%s and increment=%s must be bigger than 0", windowSize, increment);

        if (N.isNullOrEmpty(coll)) {
            return;
        }

        final Iterator<T> iter = coll.iterator();
        Iterators.forEachPair(iter, action, increment);
    }

    /**
     * For each triple.
     *
     * @param <E>
     * @param action
     * @throws E the e
     */
    public <E extends Exception> void forEachTriple(final Try.TriConsumer<? super T, ? super T, ? super T, E> action) throws E {
        forEachTriple(action, 1);
    }

    /**
     * For each triple.
     *
     * @param <E>
     * @param action
     * @param increment
     * @throws E the e
     */
    public <E extends Exception> void forEachTriple(final Try.TriConsumer<? super T, ? super T, ? super T, E> action, final int increment) throws E {
        N.checkArgNotNull(action);
        final int windowSize = 3;
        N.checkArgument(windowSize > 0 && increment > 0, "windowSize=%s and increment=%s must be bigger than 0", windowSize, increment);

        if (N.isNullOrEmpty(coll)) {
            return;
        }

        final Iterator<T> iter = coll.iterator();
        Iterators.forEachTriple(iter, action, increment);
    }

    /**
     * First.
     *
     * @return
     */
    public Nullable<T> first() {
        return N.first(coll);
    }

    /**
     * First non null.
     *
     * @return
     */
    public Optional<T> firstNonNull() {
        return N.firstNonNull(coll);
    }

    /**
     * Return at most first <code>n</code> elements.
     *
     * @param n
     * @return
     */
    public List<T> first(final int n) {
        N.checkArgument(n >= 0, "'n' can't be negative: " + n);

        if (N.isNullOrEmpty(coll) || n == 0) {
            return new ArrayList<>();
        } else if (coll.size() <= n) {
            return new ArrayList<>(coll);
        } else if (coll instanceof List) {
            return new ArrayList<>(((List<T>) coll).subList(0, n));
        } else {
            final List<T> result = new ArrayList<>(N.min(n, coll.size()));
            int cnt = 0;

            for (T e : coll) {
                result.add(e);

                if (++cnt == n) {
                    break;
                }
            }

            return result;
        }
    }

    /**
     * Last.
     *
     * @return
     */
    public Nullable<T> last() {
        return N.last(coll);
    }

    /**
     * Last non null.
     *
     * @return
     */
    public Optional<T> lastNonNull() {
        return N.lastNonNull(coll);
    }

    /**
     * Return at most last <code>n</code> elements.
     *
     * @param n
     * @return
     */
    public List<T> last(final int n) {
        N.checkArgument(n >= 0, "'n' can't be negative: " + n);

        if (N.isNullOrEmpty(coll) || n == 0) {
            return new ArrayList<>();
        } else if (coll.size() <= n) {
            return new ArrayList<>(coll);
        } else if (coll instanceof List) {
            return new ArrayList<>(((List<T>) coll).subList(coll.size() - n, coll.size()));
        } else {
            final List<T> result = new ArrayList<>(N.min(n, coll.size()));
            final Iterator<T> iter = coll.iterator();
            int offset = coll.size() - n;

            while (offset-- > 0) {
                iter.next();
            }

            while (iter.hasNext()) {
                result.add(iter.next());
            }

            return result;
        }
    }

    /**
     * Find first.
     *
     * @param <E>
     * @param predicate
     * @return
     * @throws E the e
     */
    public <E extends Exception> Nullable<T> findFirst(Try.Predicate<? super T, E> predicate) throws E {
        return Iterables.findFirst(coll, predicate);
    }

    /**
     * Find last.
     *
     * @param <E>
     * @param predicate
     * @return
     * @throws E the e
     */
    public <E extends Exception> Nullable<T> findLast(Try.Predicate<? super T, E> predicate) throws E {
        return Iterables.findLast(coll, predicate);
    }

    /**
     * Find first index.
     *
     * @param <E>
     * @param predicate
     * @return
     * @throws E the e
     */
    public <E extends Exception> OptionalInt findFirstIndex(Try.Predicate<? super T, E> predicate) throws E {
        return Iterables.findFirstIndex(coll, predicate);
    }

    /**
     * Find last index.
     *
     * @param <E>
     * @param predicate
     * @return
     * @throws E the e
     */
    public <E extends Exception> OptionalInt findLastIndex(Try.Predicate<? super T, E> predicate) throws E {
        return Iterables.findLastIndex(coll, predicate);
    }

    /**
     * Find first or last.
     *
     * @param <E>
     * @param <E2>
     * @param predicateForFirst
     * @param predicateForLast
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <E extends Exception, E2 extends Exception> Nullable<T> findFirstOrLast(final Try.Predicate<? super T, E> predicateForFirst,
            final Try.Predicate<? super T, E2> predicateForLast) throws E, E2 {
        if (N.isNullOrEmpty(coll)) {
            return Nullable.<T> empty();
        }

        final Nullable<T> res = findFirst(predicateForFirst);

        return res.isPresent() ? res : findLast(predicateForLast);
    }

    /**
     * Find first or last index.
     *
     * @param <E>
     * @param <E2>
     * @param predicateForFirst
     * @param predicateForLast
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <E extends Exception, E2 extends Exception> OptionalInt findFirstOrLastIndex(final Try.Predicate<? super T, E> predicateForFirst,
            final Try.Predicate<? super T, E2> predicateForLast) throws E, E2 {
        if (N.isNullOrEmpty(coll)) {
            return OptionalInt.empty();
        }

        final OptionalInt res = findFirstIndex(predicateForFirst);

        return res.isPresent() ? res : findLastIndex(predicateForLast);
    }

    /**
     * Find first and last.
     *
     * @param <E>
     * @param predicate
     * @return
     * @throws E the e
     */
    public <E extends Exception> Pair<Nullable<T>, Nullable<T>> findFirstAndLast(final Try.Predicate<? super T, E> predicate) throws E {
        return findFirstAndLast(predicate, predicate);
    }

    /**
     * Find first and last.
     *
     * @param <E>
     * @param <E2>
     * @param predicateForFirst
     * @param predicateForLast
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <E extends Exception, E2 extends Exception> Pair<Nullable<T>, Nullable<T>> findFirstAndLast(final Try.Predicate<? super T, E> predicateForFirst,
            final Try.Predicate<? super T, E2> predicateForLast) throws E, E2 {
        if (N.isNullOrEmpty(coll)) {
            return Pair.of(Nullable.<T> empty(), Nullable.<T> empty());
        }

        return Pair.of(findFirst(predicateForFirst), findLast(predicateForLast));
    }

    /**
     * Find first and last index.
     *
     * @param <E>
     * @param predicate
     * @return
     * @throws E the e
     */
    public <E extends Exception> Pair<OptionalInt, OptionalInt> findFirstAndLastIndex(final Try.Predicate<? super T, E> predicate) throws E {
        return findFirstAndLastIndex(predicate, predicate);
    }

    /**
     * Find first and last index.
     *
     * @param <E>
     * @param <E2>
     * @param predicateForFirst
     * @param predicateForLast
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <E extends Exception, E2 extends Exception> Pair<OptionalInt, OptionalInt> findFirstAndLastIndex(final Try.Predicate<? super T, E> predicateForFirst,
            final Try.Predicate<? super T, E2> predicateForLast) throws E, E2 {
        if (N.isNullOrEmpty(coll)) {
            return Pair.of(OptionalInt.empty(), OptionalInt.empty());
        }

        return Pair.of(findFirstIndex(predicateForFirst), findLastIndex(predicateForLast));
    }

    /**
     * All match.
     *
     * @param <E>
     * @param filter
     * @return true, if successful
     * @throws E the e
     */
    public <E extends Exception> boolean allMatch(Try.Predicate<? super T, E> filter) throws E {
        if (N.isNullOrEmpty(coll)) {
            return true;
        }

        for (T e : coll) {
            if (filter.test(e) == false) {
                return false;
            }
        }

        return true;
    }

    /**
     * Any match.
     *
     * @param <E>
     * @param filter
     * @return true, if successful
     * @throws E the e
     */
    public <E extends Exception> boolean anyMatch(Try.Predicate<? super T, E> filter) throws E {
        if (N.isNullOrEmpty(coll)) {
            return false;
        }

        for (T e : coll) {
            if (filter.test(e)) {
                return true;
            }
        }

        return false;
    }

    /**
     * None match.
     *
     * @param <E>
     * @param filter
     * @return true, if successful
     * @throws E the e
     */
    public <E extends Exception> boolean noneMatch(Try.Predicate<? super T, E> filter) throws E {
        if (N.isNullOrEmpty(coll)) {
            return true;
        }

        for (T e : coll) {
            if (filter.test(e)) {
                return false;
            }
        }

        return true;
    }

    /**
     * N match.
     *
     * @param <E>
     * @param atLeast
     * @param atMost
     * @param filter
     * @return true, if successful
     * @throws E the e
     */
    public <E extends Exception> boolean nMatch(final int atLeast, final int atMost, final Try.Predicate<? super T, E> filter) throws E {
        N.checkArgNotNegative(atLeast, "atLeast");
        N.checkArgNotNegative(atMost, "atMost");
        N.checkArgument(atLeast <= atMost, "'atLeast' must be <= 'atMost'");

        long cnt = 0;

        for (T e : coll) {
            if (filter.test(e)) {
                if (++cnt > atMost) {
                    return false;
                }
            }
        }

        return cnt >= atLeast && cnt <= atMost;
    }

    /**
     * Checks for duplicates.
     *
     * @return true, if successful
     */
    public boolean hasDuplicates() {
        return N.hasDuplicates(coll, false);
    }

    /**
     * Count.
     *
     * @param <E>
     * @param filter
     * @return
     * @throws E the e
     */
    public <E extends Exception> int count(Try.Predicate<? super T, E> filter) throws E {
        return N.count(coll, filter);
    }

    /**
     * Filter.
     *
     * @param <E>
     * @param filter
     * @return
     * @throws E the e
     */
    public <E extends Exception> List<T> filter(Try.Predicate<? super T, E> filter) throws E {
        return N.filter(coll, filter);
    }

    /**
     * Filter.
     *
     * @param <E>
     * @param filter
     * @param max
     * @return
     * @throws E the e
     */
    public <E extends Exception> List<T> filter(Try.Predicate<? super T, E> filter, final int max) throws E {
        return N.filter(coll, filter, max);
    }

    /**
     * Filter.
     *
     * @param <C>
     * @param <E>
     * @param filter
     * @param supplier
     * @return
     * @throws E the e
     */
    public <C extends Collection<T>, E extends Exception> C filter(Try.Predicate<? super T, E> filter, IntFunction<? extends C> supplier) throws E {
        return N.filter(coll, filter, supplier);
    }

    /**
     * Filter.
     *
     * @param <C>
     * @param <E>
     * @param filter
     * @param max
     * @param supplier
     * @return
     * @throws E the e
     */
    public <C extends Collection<T>, E extends Exception> C filter(Try.Predicate<? super T, E> filter, final int max, IntFunction<? extends C> supplier)
            throws E {
        return N.filter(coll, filter, max, supplier);
    }

    /**
     * Take while.
     *
     * @param <E>
     * @param filter
     * @return
     * @throws E the e
     */
    public <E extends Exception> List<T> takeWhile(Try.Predicate<? super T, E> filter) throws E {
        N.checkArgNotNull(filter);

        final List<T> result = new ArrayList<>(N.min(9, size()));

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            if (filter.test(e)) {
                result.add(e);
            } else {
                break;
            }
        }

        return result;
    }

    /**
     * Take while inclusive.
     *
     * @param <E>
     * @param filter
     * @return
     * @throws E the e
     */
    public <E extends Exception> List<T> takeWhileInclusive(Try.Predicate<? super T, E> filter) throws E {
        N.checkArgNotNull(filter);

        final List<T> result = new ArrayList<>(N.min(9, size()));

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            result.add(e);

            if (filter.test(e) == false) {
                break;
            }
        }

        return result;
    }

    /**
     * Drop while.
     *
     * @param <E>
     * @param filter
     * @return
     * @throws E the e
     */
    public <E extends Exception> List<T> dropWhile(Try.Predicate<? super T, E> filter) throws E {
        N.checkArgNotNull(filter);

        final List<T> result = new ArrayList<>(N.min(9, size()));

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        final Iterator<T> iter = iterator();
        T e = null;

        while (iter.hasNext()) {
            e = iter.next();

            if (filter.test(e) == false) {
                result.add(e);
                break;
            }
        }

        while (iter.hasNext()) {
            result.add(iter.next());
        }

        return result;
    }

    /**
     * Skip until.
     *
     * @param <E>
     * @param filter
     * @return
     * @throws E the e
     */
    public <E extends Exception> List<T> skipUntil(final Try.Predicate<? super T, E> filter) throws E {
        N.checkArgNotNull(filter);

        final List<T> result = new ArrayList<>(N.min(9, size()));

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        final Iterator<T> iter = iterator();
        T e = null;

        while (iter.hasNext()) {
            e = iter.next();

            if (filter.test(e)) {
                result.add(e);
                break;
            }
        }

        while (iter.hasNext()) {
            result.add(iter.next());
        }

        return result;
    }

    /**
     * Map.
     *
     * @param <R>
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <R, E extends Exception> List<R> map(final Try.Function<? super T, ? extends R, E> func) throws E {
        return N.map(coll, func);
    }

    /**
     * Map to boolean.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> BooleanList mapToBoolean(final Try.ToBooleanFunction<? super T, E> func) throws E {
        return N.mapToBoolean(coll, func);
    }

    /**
     * Map to char.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> CharList mapToChar(final Try.ToCharFunction<? super T, E> func) throws E {
        return N.mapToChar(coll, func);
    }

    /**
     * Map to byte.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> ByteList mapToByte(final Try.ToByteFunction<? super T, E> func) throws E {
        return N.mapToByte(coll, func);
    }

    /**
     * Map to short.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> ShortList mapToShort(final Try.ToShortFunction<? super T, E> func) throws E {
        return N.mapToShort(coll, func);
    }

    /**
     * Map to int.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> IntList mapToInt(final Try.ToIntFunction<? super T, E> func) throws E {
        return N.mapToInt(coll, func);
    }

    /**
     * Map to long.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> LongList mapToLong(final Try.ToLongFunction<? super T, E> func) throws E {
        return N.mapToLong(coll, func);
    }

    /**
     * Map to float.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> FloatList mapToFloat(final Try.ToFloatFunction<? super T, E> func) throws E {
        return N.mapToFloat(coll, func);
    }

    /**
     * Map to double.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> DoubleList mapToDouble(final Try.ToDoubleFunction<? super T, E> func) throws E {
        return N.mapToDouble(coll, func);
    }

    /**
     * Flat map.
     *
     * @param <R>
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <R, E extends Exception> List<R> flatMap(final Try.Function<? super T, ? extends Collection<? extends R>, E> func) throws E {
        N.checkArgNotNull(func);

        final List<R> result = new ArrayList<>(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            result.addAll(func.apply(e));
        }

        return result;
    }

    /**
     * Flatt map.
     *
     * @param <R>
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <R, E extends Exception> List<R> flattMap(final Try.Function<? super T, ? extends R[], E> func) throws E {
        N.checkArgNotNull(func);

        final List<R> result = new ArrayList<>(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        R[] a = null;
        for (T e : coll) {
            a = func.apply(e);

            if (N.notNullOrEmpty(a)) {
                if (a.length < 9) {
                    for (R r : a) {
                        result.add(r);
                    }
                } else {
                    result.addAll(Arrays.asList(a));
                }
            }
        }

        return result;
    }

    /**
     * Flat map to boolean.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> BooleanList flatMapToBoolean(final Try.Function<? super T, ? extends Collection<Boolean>, E> func) throws E {
        N.checkArgNotNull(func);

        final BooleanList result = new BooleanList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            for (boolean b : func.apply(e)) {
                result.add(b);
            }
        }

        return result;
    }

    /**
     * Flatt map to boolean.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> BooleanList flattMapToBoolean(final Try.Function<? super T, boolean[], E> func) throws E {
        N.checkArgNotNull(func);

        final BooleanList result = new BooleanList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            result.addAll(func.apply(e));
        }

        return result;
    }

    /**
     * Flat map to char.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> CharList flatMapToChar(final Try.Function<? super T, ? extends Collection<Character>, E> func) throws E {
        N.checkArgNotNull(func);

        final CharList result = new CharList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            for (char b : func.apply(e)) {
                result.add(b);
            }
        }

        return result;
    }

    /**
     * Flatt map to char.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> CharList flattMapToChar(final Try.Function<? super T, char[], E> func) throws E {
        N.checkArgNotNull(func);

        final CharList result = new CharList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            result.addAll(func.apply(e));
        }

        return result;
    }

    /**
     * Flat map to byte.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> ByteList flatMapToByte(final Try.Function<? super T, ? extends Collection<Byte>, E> func) throws E {
        N.checkArgNotNull(func);

        final ByteList result = new ByteList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            for (byte b : func.apply(e)) {
                result.add(b);
            }
        }

        return result;
    }

    /**
     * Flatt map to byte.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> ByteList flattMapToByte(final Try.Function<? super T, byte[], E> func) throws E {
        N.checkArgNotNull(func);

        final ByteList result = new ByteList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            result.addAll(func.apply(e));
        }

        return result;
    }

    /**
     * Flat map to short.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> ShortList flatMapToShort(final Try.Function<? super T, ? extends Collection<Short>, E> func) throws E {
        N.checkArgNotNull(func);

        final ShortList result = new ShortList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            for (short b : func.apply(e)) {
                result.add(b);
            }
        }

        return result;
    }

    /**
     * Flatt map to short.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> ShortList flattMapToShort(final Try.Function<? super T, short[], E> func) throws E {
        N.checkArgNotNull(func);

        final ShortList result = new ShortList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            result.addAll(func.apply(e));
        }

        return result;
    }

    /**
     * Flat map to int.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> IntList flatMapToInt(final Try.Function<? super T, ? extends Collection<Integer>, E> func) throws E {
        N.checkArgNotNull(func);

        final IntList result = new IntList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            for (int b : func.apply(e)) {
                result.add(b);
            }
        }

        return result;
    }

    /**
     * Flatt map to int.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> IntList flattMapToInt(final Try.Function<? super T, int[], E> func) throws E {
        N.checkArgNotNull(func);

        final IntList result = new IntList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            result.addAll(func.apply(e));
        }

        return result;
    }

    /**
     * Flat map to long.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> LongList flatMapToLong(final Try.Function<? super T, ? extends Collection<Long>, E> func) throws E {
        N.checkArgNotNull(func);

        final LongList result = new LongList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            for (long b : func.apply(e)) {
                result.add(b);
            }
        }

        return result;
    }

    /**
     * Flatt map to long.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> LongList flattMapToLong(final Try.Function<? super T, long[], E> func) throws E {
        N.checkArgNotNull(func);

        final LongList result = new LongList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            result.addAll(func.apply(e));
        }

        return result;
    }

    /**
     * Flat map to float.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> FloatList flatMapToFloat(final Try.Function<? super T, ? extends Collection<Float>, E> func) throws E {
        N.checkArgNotNull(func);

        final FloatList result = new FloatList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            for (float b : func.apply(e)) {
                result.add(b);
            }
        }

        return result;
    }

    /**
     * Flatt map to float.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> FloatList flattMapToFloat(final Try.Function<? super T, float[], E> func) throws E {
        N.checkArgNotNull(func);

        final FloatList result = new FloatList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            result.addAll(func.apply(e));
        }

        return result;
    }

    /**
     * Flat map to double.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> DoubleList flatMapToDouble(final Try.Function<? super T, ? extends Collection<Double>, E> func) throws E {
        N.checkArgNotNull(func);

        final DoubleList result = new DoubleList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            for (double b : func.apply(e)) {
                result.add(b);
            }
        }

        return result;
    }

    /**
     * Flatt map to double.
     *
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <E extends Exception> DoubleList flattMapToDouble(final Try.Function<? super T, double[], E> func) throws E {
        N.checkArgNotNull(func);

        final DoubleList result = new DoubleList(size() > N.MAX_ARRAY_SIZE / 2 ? N.MAX_ARRAY_SIZE : size() * 2);

        if (N.isNullOrEmpty(coll)) {
            return result;
        }

        for (T e : coll) {
            result.addAll(func.apply(e));
        }

        return result;
    }

    /**
     * Flat map.
     *
     * @param <U>
     * @param <R>
     * @param <E>
     * @param <E2>
     * @param mapper
     * @param func
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <U, R, E extends Exception, E2 extends Exception> List<R> flatMap(final Try.Function<? super T, ? extends Collection<U>, E> mapper,
            final Try.BiFunction<? super T, ? super U, ? extends R, E2> func) throws E, E2 {
        N.checkArgNotNull(mapper);
        N.checkArgNotNull(func);

        final List<R> result = new ArrayList<R>(N.max(9, coll.size()));

        for (T e : coll) {
            final Collection<U> c = mapper.apply(e);
            if (N.notNullOrEmpty(c)) {
                for (U u : c) {
                    result.add(func.apply(e, u));
                }
            }
        }

        return result;
    }

    /**
     * Flat map.
     *
     * @param <T2>
     * @param <T3>
     * @param <R>
     * @param <E>
     * @param <E2>
     * @param <E3>
     * @param mapper2
     * @param mapper3
     * @param func
     * @return
     * @throws E the e
     * @throws E2 the e2
     * @throws E3 the e3
     */
    public <T2, T3, R, E extends Exception, E2 extends Exception, E3 extends Exception> List<R> flatMap(
            final Try.Function<? super T, ? extends Collection<T2>, E> mapper2, final Try.Function<? super T2, ? extends Collection<T3>, E2> mapper3,
            final Try.TriFunction<? super T, ? super T2, ? super T3, R, E3> func) throws E, E2, E3 {
        N.checkArgNotNull(mapper2);
        N.checkArgNotNull(mapper3);
        N.checkArgNotNull(func);

        if (N.isNullOrEmpty(coll)) {
            return new ArrayList<R>();
        }

        final List<R> result = new ArrayList<R>(N.max(9, coll.size()));

        for (T e : coll) {
            final Collection<T2> c2 = mapper2.apply(e);
            if (N.notNullOrEmpty(c2)) {
                for (T2 t2 : c2) {
                    final Collection<T3> c3 = mapper3.apply(t2);
                    if (N.notNullOrEmpty(c3)) {
                        for (T3 t3 : c3) {
                            result.add(func.apply(e, t2, t3));
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * For better performance, comparing to {@code Stream}.
     *
     * @param <R>
     * @param <E>
     * @param <E2>
     * @param filter
     * @param mapper
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    @Beta
    public <R, E extends Exception, E2 extends Exception> List<R> filterThenMap(final Try.Predicate<? super T, E> filter,
            final Try.Function<? super T, ? extends R, E2> mapper) throws E, E2 {
        N.checkArgNotNull(filter);
        N.checkArgNotNull(mapper);

        final List<R> result = new ArrayList<>();

        for (T e : coll) {
            if (filter.test(e)) {
                result.add(mapper.apply(e));
            }
        }

        return result;
    }

    /**
     * For better performance, comparing to {@code Stream}.
     *
     * @param <R>
     * @param <E>
     * @param <E2>
     * @param filter
     * @param mapper
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    @Beta
    public <R, E extends Exception, E2 extends Exception> List<R> filterThenFlatMap(final Try.Predicate<? super T, E> filter,
            final Try.Function<? super T, ? extends Collection<? extends R>, E2> mapper) throws E, E2 {
        N.checkArgNotNull(filter);
        N.checkArgNotNull(mapper);

        final List<R> result = new ArrayList<>();

        Collection<? extends R> c = null;

        for (T e : coll) {
            if (filter.test(e)) {
                c = mapper.apply(e);

                if (N.notNullOrEmpty(c)) {
                    result.addAll(c);
                }
            }
        }

        return result;
    }

    /**
     * For better performance, comparing to {@code Stream}.
     *
     * @param <R>
     * @param <E>
     * @param <E2>
     * @param mapper
     * @param filter
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    @Beta
    public <R, E extends Exception, E2 extends Exception> List<R> mapThenFilter(final Try.Function<? super T, ? extends R, E> mapper,
            final Try.Predicate<? super R, E2> filter) throws E, E2 {
        N.checkArgNotNull(mapper);
        N.checkArgNotNull(filter);

        final List<R> result = new ArrayList<>();
        R r = null;

        for (T e : coll) {
            r = mapper.apply(e);

            if (filter.test(r)) {
                result.add(r);
            }
        }

        return result;
    }

    /**
     * For better performance, comparing to {@code Stream}.
     *
     * @param <R>
     * @param <E>
     * @param <E2>
     * @param mapper
     * @param filter
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    @Beta
    public <R, E extends Exception, E2 extends Exception> List<R> flatMapThenFilter(final Try.Function<? super T, ? extends Collection<? extends R>, E> mapper,
            final Try.Predicate<? super R, E2> filter) throws E, E2 {
        N.checkArgNotNull(mapper);
        N.checkArgNotNull(filter);

        final List<R> result = new ArrayList<>();
        Collection<? extends R> c = null;

        for (T e : coll) {
            c = mapper.apply(e);

            if (N.notNullOrEmpty(c)) {
                for (R r : c) {
                    if (filter.test(r)) {
                        result.add(r);
                    }
                }
            }
        }

        return result;
    }

    /**
     * For better performance, comparing to {@code Stream}.
     *
     * @param <R>
     * @param <E>
     * @param <E2>
     * @param filter
     * @param action
     * @throws E the e
     * @throws E2 the e2
     */
    @Beta
    public <R, E extends Exception, E2 extends Exception> void filterThenForEach(final Try.Predicate<? super T, E> filter,
            final Try.Consumer<? super T, E2> action) throws E, E2 {
        N.checkArgNotNull(filter);
        N.checkArgNotNull(action);

        for (T e : coll) {
            if (filter.test(e)) {
                action.accept(e);
            }
        }
    }

    /**
     * For better performance, comparing to {@code Stream}.
     *
     * @param <R>
     * @param <E>
     * @param <E2>
     * @param mapper
     * @param action
     * @throws E the e
     * @throws E2 the e2
     */
    @Beta
    public <R, E extends Exception, E2 extends Exception> void mapThenForEach(final Try.Function<? super T, ? extends R, E> mapper,
            final Try.Consumer<? super R, E2> action) throws E, E2 {
        N.checkArgNotNull(mapper);
        N.checkArgNotNull(action);

        for (T e : coll) {
            action.accept(mapper.apply(e));
        }
    }

    /**
     * For better performance, comparing to {@code Stream}.
     *
     * @param <R>
     * @param <E>
     * @param <E2>
     * @param mapper
     * @param action
     * @throws E the e
     * @throws E2 the e2
     */
    @Beta
    public <R, E extends Exception, E2 extends Exception> void flatMapThenForEach(final Try.Function<? super T, ? extends Collection<? extends R>, E> mapper,
            final Try.Consumer<? super R, E2> action) throws E, E2 {
        N.checkArgNotNull(mapper);
        N.checkArgNotNull(action);

        Collection<? extends R> c = null;

        for (T e : coll) {
            c = mapper.apply(e);

            if (N.notNullOrEmpty(c)) {
                for (R r : c) {
                    action.accept(r);
                }
            }
        }
    }

    /**
     * Merge series of adjacent elements which satisfy the given predicate using the merger function.
     * 
     * <p>Example:
     * <pre>
     * <code>
     * Seq.of(new Integer[0]).collapse((a, b) -> a < b, (a, b) -> a + b) => []
     * Seq.of(1).collapse((a, b) -> a < b, (a, b) -> a + b) => [1]
     * Seq.of(1, 2).collapse((a, b) -> a < b, (a, b) -> a + b) => [3]
     * Seq.of(1, 2, 3).collapse((a, b) -> a < b, (a, b) -> a + b) => [6]
     * Seq.of(1, 2, 3, 3, 2, 1).collapse((a, b) -> a < b, (a, b) -> a + b) => [6, 3, 2, 1]
     * </code>
     * </pre>
     *
     * @param <E>
     * @param <E2>
     * @param collapsible
     * @param mergeFunction
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <E extends Exception, E2 extends Exception> List<T> collapse(final Try.BiPredicate<? super T, ? super T, E> collapsible,
            final Try.BiFunction<? super T, ? super T, T, E2> mergeFunction) throws E, E2 {
        N.checkArgNotNull(collapsible);
        N.checkArgNotNull(mergeFunction);

        final List<T> result = new ArrayList<>();
        final Iterator<T> iter = iterator();
        boolean hasNext = false;
        T next = null;

        while (hasNext || iter.hasNext()) {
            T res = hasNext ? next : (next = iter.next());

            while ((hasNext = iter.hasNext())) {
                if (collapsible.test(next, (next = iter.next()))) {
                    res = mergeFunction.apply(res, next);
                } else {
                    break;
                }
            }

            result.add(res);
        }

        return result;
    }

    /**
     * Collapse.
     *
     * @param <U>
     * @param <E>
     * @param <E2>
     * @param collapsible
     * @param init
     * @param op
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <U, E extends Exception, E2 extends Exception> List<U> collapse(final Try.BiPredicate<? super T, ? super T, E> collapsible, final U init,
            final Try.BiFunction<? super U, ? super T, U, E2> op) throws E, E2 {
        N.checkArgNotNull(collapsible);
        N.checkArgNotNull(op);

        final List<U> result = new ArrayList<>();
        final Iterator<T> iter = iterator();
        boolean hasNext = false;
        T next = null;

        while (hasNext || iter.hasNext()) {
            U res = op.apply(init, hasNext ? next : (next = iter.next()));

            while ((hasNext = iter.hasNext())) {
                if (collapsible.test(next, (next = iter.next()))) {
                    res = op.apply(res, next);
                } else {
                    break;
                }
            }

            result.add(res);
        }

        return result;
    }

    /**
     * Collapse.
     *
     * @param <E>
     * @param collapsible
     * @return
     * @throws E the e
     */
    public <E extends Exception> List<List<T>> collapse(final Try.BiPredicate<? super T, ? super T, E> collapsible) throws E {
        return collapse(collapsible, Suppliers.<T> ofList());
    }

    /**
     * Collapse.
     *
     * @param <C>
     * @param <E>
     * @param collapsible
     * @param collectionSupplier
     * @return
     * @throws E the e
     */
    public <C extends Collection<T>, E extends Exception> List<C> collapse(final Try.BiPredicate<? super T, ? super T, E> collapsible,
            final Supplier<? extends C> collectionSupplier) throws E {
        N.checkArgNotNull(collapsible);
        N.checkArgNotNull(collectionSupplier);

        final List<C> result = new ArrayList<>();
        final Iterator<T> iter = iterator();
        boolean hasNext = false;
        T next = null;

        while (hasNext || iter.hasNext()) {
            final C c = collectionSupplier.get();
            c.add(hasNext ? next : (next = iter.next()));

            while ((hasNext = iter.hasNext())) {
                if (collapsible.test(next, (next = iter.next()))) {
                    c.add(next);
                } else {
                    break;
                }
            }

            result.add(c);
        }

        return result;
    }

    /**
     * Merge series of adjacent elements which satisfy the given predicate using the merger function.
     * 
     * <p>Example:
     * <pre>
     * <code>
     * Seq.of(new Integer[0]).collapse((a, b) -> a < b, Collectors.summingInt(Fn.unboxI())) => []
     * Seq.of(1).collapse((a, b) -> a < b, Collectors.summingInt(Fn.unboxI())) => [1]
     * Seq.of(1, 2).collapse((a, b) -> a < b, Collectors.summingInt(Fn.unboxI())) => [3]
     * Seq.of(1, 2, 3).collapse((a, b) -> a < b, Collectors.summingInt(Fn.unboxI())) => [6]
     * Seq.of(1, 2, 3, 3, 2, 1).collapse((a, b) -> a < b, Collectors.summingInt(Fn.unboxI())) => [6, 3, 2, 1]
     * </code>
     * </pre>
     *
     * @param <R>
     * @param <A>
     * @param <E>
     * @param collapsible
     * @param collector
     * @return
     * @throws E the e
     */
    public <R, A, E extends Exception> List<R> collapse(final Try.BiPredicate<? super T, ? super T, E> collapsible, final Collector<? super T, A, R> collector)
            throws E {
        N.checkArgNotNull(collapsible);
        N.checkArgNotNull(collector);

        final List<R> result = new ArrayList<>();
        final Supplier<A> supplier = collector.supplier();
        final BiConsumer<A, ? super T> accumulator = collector.accumulator();
        final Function<A, R> finisher = collector.finisher();
        final Iterator<T> iter = iterator();
        boolean hasNext = false;
        T next = null;

        while (hasNext || iter.hasNext()) {
            final A c = supplier.get();
            accumulator.accept(c, hasNext ? next : (next = iter.next()));

            while ((hasNext = iter.hasNext())) {
                if (collapsible.test(next, (next = iter.next()))) {
                    accumulator.accept(c, next);
                } else {
                    break;
                }
            }

            result.add(finisher.apply(c));
        }

        return result;
    }

    /**
     * Returns a {@code Stream} produced by iterative application of a accumulation function
     * to an initial element {@code identity} and next element of the current stream.
     * Produces a {@code Stream} consisting of {@code identity}, {@code acc(identity, value1)},
     * {@code acc(acc(identity, value1), value2)}, etc.
     * 
     * <p>Example:
     * <pre>
     * <code>
     * Seq.of(new Integer[0]).scan((a, b) -> a + b) => []
     * Seq.of(1).scan((a, b) -> a + b) => [1]
     * Seq.of(1, 2).scan((a, b) -> a + b) => [1, 3]
     * Seq.of(1, 2, 3).scan((a, b) -> a + b) => [1, 3, 6]
     * Seq.of(1, 2, 3, 3, 2, 1).scan((a, b) -> a + b) => [1, 3, 6, 9, 11, 12]
     * </code>
     * </pre>
     *
     * @param <E>
     * @param accumulator the accumulation function
     * @return
     * @throws E the e
     */
    public <E extends Exception> List<T> scan(final Try.BiFunction<? super T, ? super T, T, E> accumulator) throws E {
        N.checkArgNotNull(accumulator);

        final List<T> result = new ArrayList<>();
        final Iterator<T> iter = iterator();
        T next = null;

        if (iter.hasNext()) {
            result.add((next = iter.next()));
        }

        while (iter.hasNext()) {
            result.add((next = accumulator.apply(next, iter.next())));
        }

        return result;
    }

    /**
     * Returns a {@code Stream} produced by iterative application of a accumulation function
     * to an initial element {@code identity} and next element of the current stream.
     * Produces a {@code Stream} consisting of {@code identity}, {@code acc(identity, value1)},
     * {@code acc(acc(identity, value1), value2)}, etc.
     * 
     * <p>Example:
     * <pre>
     * <code>
     * Seq.of(new Integer[0]).scan(10, (a, b) -> a + b) => []
     * Seq.of(1).scan(10, (a, b) -> a + b) => [11]
     * Seq.of(1, 2).scan(10, (a, b) -> a + b) => [11, 13]
     * Seq.of(1, 2, 3).scan(10, (a, b) -> a + b) => [11, 13, 16]
     * Seq.of(1, 2, 3, 3, 2, 1).scan(10, (a, b) -> a + b) => [11, 13, 16, 19, 21, 22]
     * </code>
     * </pre>
     *
     * @param <U>
     * @param <E>
     * @param init the initial value. it's only used once by <code>accumulator</code> to calculate the fist element in the returned stream.
     * It will be ignored if this stream is empty and won't be the first element of the returned stream.
     * @param accumulator the accumulation function
     * @return
     * @throws E the e
     */
    public <U, E extends Exception> List<U> scan(final U init, final Try.BiFunction<? super U, ? super T, U, E> accumulator) throws E {
        return scan(init, accumulator, false);
    }

    /**
     * Scan.
     *
     * @param <U>
     * @param <E>
     * @param init
     * @param accumulator
     * @param initIncluded
     * @return
     * @throws E the e
     */
    public <U, E extends Exception> List<U> scan(final U init, final Try.BiFunction<? super U, ? super T, U, E> accumulator, boolean initIncluded) throws E {
        N.checkArgNotNull(accumulator);

        final List<U> result = new ArrayList<>();

        if (initIncluded) {
            result.add(init);
        }

        final Iterator<T> iter = iterator();
        U next = init;

        while (iter.hasNext()) {
            result.add((next = accumulator.apply(next, iter.next())));
        }

        return result;
    }

    /**
     * This is equivalent to:
     * <pre>
     * <code>
     *    if (isEmpty()) {
     *        return Nullable.empty();
     *    }
     * 
     *    final Iterator<T> iter = iterator();
     *    T result = iter.next();
     * 
     *    while (iter.hasNext()) {
     *        result = accumulator.apply(result, iter.next());
     *    }
     * 
     *    return Nullable.of(result);
     * </code>
     * </pre>
     *
     * @param <E>
     * @param accumulator
     * @return
     * @throws E the e
     */
    public <E extends Exception> Nullable<T> reduce(Try.BinaryOperator<T, E> accumulator) throws E {
        N.checkArgNotNull(accumulator);

        if (isEmpty()) {
            return Nullable.empty();
        }

        final Iterator<T> iter = iterator();
        T result = iter.next();

        while (iter.hasNext()) {
            result = accumulator.apply(result, iter.next());
        }

        return Nullable.of(result);
    }

    /**
     * This is equivalent to:
     * <pre>
     * <code>
     *     if (isEmpty()) {
     *         return identity;
     *     }
     * 
     *     final Iterator<T> iter =  iterator();
     *     U result = identity;
     * 
     *     while (iter.hasNext()) {
     *         result = accumulator.apply(result, iter.next());
     *     }
     * 
     *     return result;
     * </code>
     * </pre>
     *
     * @param <U>
     * @param <E>
     * @param identity
     * @param accumulator
     * @return
     * @throws E the e
     */
    public <U, E extends Exception> U reduce(final U identity, final Try.BiFunction<U, ? super T, U, E> accumulator) throws E {
        N.checkArgNotNull(accumulator);

        if (isEmpty()) {
            return identity;
        }

        final Iterator<T> iter = iterator();
        U result = identity;

        while (iter.hasNext()) {
            result = accumulator.apply(result, iter.next());
        }

        return result;
    }

    /**
     * Collect.
     *
     * @param <R>
     * @param <E>
     * @param supplier
     * @param accumulator
     * @return
     * @throws E the e
     */
    public <R, E extends Exception> R collect(final Supplier<R> supplier, final Try.BiConsumer<? super R, ? super T, E> accumulator) throws E {
        N.checkArgNotNull(supplier);
        N.checkArgNotNull(accumulator);

        final R result = supplier.get();

        for (T e : coll) {
            accumulator.accept(result, e);
        }

        return result;
    }

    /**
     * Collect.
     *
     * @param <A>
     * @param <R>
     * @param <E>
     * @param <E2>
     * @param supplier
     * @param accumulator
     * @param finisher
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <A, R, E extends Exception, E2 extends Exception> R collect(final Supplier<A> supplier, final Try.BiConsumer<? super A, ? super T, E> accumulator,
            final Try.Function<A, R, E2> finisher) throws E, E2 {
        N.checkArgNotNull(supplier);
        N.checkArgNotNull(accumulator);
        N.checkArgNotNull(finisher);

        final A result = supplier.get();

        for (T e : coll) {
            accumulator.accept(result, e);
        }

        return finisher.apply(result);
    }

    /**
     * Collect.
     *
     * @param <A>
     * @param <R>
     * @param collector
     * @return
     */
    public <A, R> R collect(final Collector<? super T, A, R> collector) {
        N.checkArgNotNull(collector);

        final BiConsumer<A, ? super T> accumulator = collector.accumulator();
        final A result = collector.supplier().get();

        for (T e : coll) {
            accumulator.accept(result, e);
        }

        return collector.finisher().apply(result);
    }

    /**
     * Collect.
     *
     * @param <A>
     * @param <R>
     * @param collector
     * @return
     */
    public <A, R> R collect(final java.util.stream.Collector<? super T, A, R> collector) {
        N.checkArgNotNull(collector);

        final java.util.function.BiConsumer<A, ? super T> accumulator = collector.accumulator();
        final A result = collector.supplier().get();

        for (T e : coll) {
            accumulator.accept(result, e);
        }

        return collector.finisher().apply(result);
    }

    /**
     * Collect then apply.
     *
     * @param <A>
     * @param <R>
     * @param <RR>
     * @param <E>
     * @param downstream
     * @param mapper
     * @return
     * @throws E the e
     */
    public <A, R, RR, E extends Exception> RR collectThenApply(final Collector<T, A, R> downstream, final Try.Function<? super R, ? extends RR, E> mapper)
            throws E {
        return mapper.apply(collect(downstream));
    }

    /**
     * Collect then apply.
     *
     * @param <A>
     * @param <R>
     * @param <RR>
     * @param <E>
     * @param downstream
     * @param mapper
     * @return
     * @throws E the e
     */
    public <A, R, RR, E extends Exception> RR collectThenApply(final java.util.stream.Collector<T, A, R> downstream,
            final Try.Function<? super R, ? extends RR, E> mapper) throws E {
        return mapper.apply(collect(downstream));
    }

    /**
     * Collect then accept.
     *
     * @param <A>
     * @param <R>
     * @param <E>
     * @param downstream
     * @param consumer
     * @throws E the e
     */
    public <A, R, E extends Exception> void collectThenAccept(final Collector<T, A, R> downstream, final Try.Consumer<? super R, E> consumer) throws E {
        consumer.accept(collect(downstream));
    }

    /**
     * Collect then accept.
     *
     * @param <A>
     * @param <R>
     * @param <E>
     * @param downstream
     * @param consumer
     * @throws E the e
     */
    public <A, R, E extends Exception> void collectThenAccept(final java.util.stream.Collector<T, A, R> downstream, final Try.Consumer<? super R, E> consumer)
            throws E {
        consumer.accept(collect(downstream));
    }

    /**
     * Append.
     *
     * @param a
     * @return
     */
    @SafeVarargs
    public final List<T> append(T... a) {
        if (N.isNullOrEmpty(a)) {
            return toList();
        }

        return append(Arrays.asList(a));
    }

    /**
     * Append.
     *
     * @param c
     * @return
     */
    public List<T> append(final Collection<? extends T> c) {
        return N.concat(this, c);
    }

    /**
     * Prepend.
     *
     * @param a
     * @return
     */
    @SafeVarargs
    public final List<T> prepend(T... a) {
        if (N.isNullOrEmpty(a)) {
            return toList();
        }

        return prepend(Arrays.asList(a));
    }

    /**
     * Prepend.
     *
     * @param c
     * @return
     */
    public List<T> prepend(final Collection<? extends T> c) {
        return N.concat(c, this);
    }

    /**
     * Merge.
     *
     * @param <E>
     * @param b
     * @param nextSelector
     * @return
     * @throws E the e
     */
    public <E extends Exception> List<T> merge(final Collection<? extends T> b, final Try.BiFunction<? super T, ? super T, Nth, E> nextSelector) throws E {
        return N.merge(this, b, nextSelector);
    }

    /**
     * Zip with.
     *
     * @param <B>
     * @param <R>
     * @param <E>
     * @param b
     * @param zipFunction
     * @return
     * @throws E the e
     */
    public <B, R, E extends Exception> List<R> zipWith(final Collection<B> b, final Try.BiFunction<? super T, ? super B, R, E> zipFunction) throws E {
        return N.zip(this, b, zipFunction);
    }

    /**
     * Zip with.
     *
     * @param <B>
     * @param <R>
     * @param <E>
     * @param b
     * @param valueForNoneA
     * @param valueForNoneB
     * @param zipFunction
     * @return
     * @throws E the e
     */
    public <B, R, E extends Exception> List<R> zipWith(final Collection<B> b, final T valueForNoneA, final B valueForNoneB,
            final Try.BiFunction<? super T, ? super B, R, E> zipFunction) throws E {
        return N.zip(this, b, valueForNoneA, valueForNoneB, zipFunction);
    }

    /**
     * Zip with.
     *
     * @param <B>
     * @param <C>
     * @param <R>
     * @param <E>
     * @param b
     * @param c
     * @param zipFunction
     * @return
     * @throws E the e
     */
    public <B, C, R, E extends Exception> List<R> zipWith(final Collection<B> b, final Collection<C> c,
            final Try.TriFunction<? super T, ? super B, ? super C, R, E> zipFunction) throws E {
        return N.zip(this, b, c, zipFunction);
    }

    /**
     * Zip with.
     *
     * @param <B>
     * @param <C>
     * @param <R>
     * @param <E>
     * @param b
     * @param c
     * @param valueForNoneA
     * @param valueForNoneB
     * @param valueForNoneC
     * @param zipFunction
     * @return
     * @throws E the e
     */
    public <B, C, R, E extends Exception> List<R> zipWith(final Collection<B> b, final Collection<C> c, final T valueForNoneA, final B valueForNoneB,
            final C valueForNoneC, final Try.TriFunction<? super T, ? super B, ? super C, R, E> zipFunction) throws E {
        return N.zip(this, b, c, valueForNoneA, valueForNoneB, valueForNoneC, zipFunction);
    }

    /**
     * Intersperse.
     *
     * @param value
     * @return
     */
    public List<T> intersperse(T value) {
        if (isEmpty()) {
            return new ArrayList<>();
        }

        final int size = size();
        final List<T> result = new ArrayList<>(size * 2 - 1);
        int idx = 0;

        for (T e : coll) {
            result.add(e);

            if (++idx < size) {
                result.add(value);
            }
        }

        return result;
    }

    /**
     * Indexed.
     *
     * @return
     */
    public List<Indexed<T>> indexed() {
        final List<Indexed<T>> result = new ArrayList<>(size());
        int idx = 0;

        for (T e : coll) {
            result.add(Indexed.of(e, idx++));
        }

        return result;
    }

    /**
     * Distinct.
     *
     * @return a new List with distinct elements
     */
    public List<T> distinct() {
        return N.distinct(coll);
    }

    /**
     * Distinct by.
     *
     * @param <E>
     * @param keyMapper don't change value of the input parameter.
     * @return
     * @throws E the e
     */
    public <E extends Exception> List<T> distinctBy(final Try.Function<? super T, ?, E> keyMapper) throws E {
        return N.distinctBy(coll, keyMapper);
    }

    /**
     * Top.
     *
     * @param n
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List<T> top(final int n) {
        return N.top((Collection) coll, n);
    }

    /**
     * Top.
     *
     * @param n
     * @param cmp
     * @return
     */
    public List<T> top(final int n, final Comparator<? super T> cmp) {
        return N.top(coll, n, cmp);
    }

    /**
     * Returns consecutive sub lists of this list, each of the same size (the final list may be smaller),
     * or an empty List if the specified list is null or empty.
     *
     * @param chunkSize the desired size of each sub sequence (the last may be smaller).
     * @return
     */
    public List<List<T>> split(int chunkSize) {
        return N.split(coll, chunkSize);
    }

    /**
     * Split.
     *
     * @param <U>
     * @param <E>
     * @param predicate
     * @return
     * @throws E the e
     */
    public <U, E extends Exception> List<List<T>> split(final Try.Predicate<? super T, E> predicate) throws E {
        return split(predicate, Suppliers.<T> ofList());
    }

    /**
     * Split.
     *
     * @param <U>
     * @param <C>
     * @param <E>
     * @param predicate
     * @param supplier
     * @return
     * @throws E the e
     */
    public <U, C extends Collection<T>, E extends Exception> List<C> split(final Try.Predicate<? super T, E> predicate, final Supplier<? extends C> supplier)
            throws E {
        N.checkArgNotNull(predicate);
        N.checkArgNotNull(supplier);

        final List<C> res = new ArrayList<>();
        final Iterator<T> elements = iterator();
        final T NONE = (T) N.NULL_MASK;
        T next = NONE;
        boolean preCondition = false;

        while (next != NONE || elements.hasNext()) {
            final C piece = supplier.get();

            if (next == NONE) {
                next = elements.next();
            }

            while (next != NONE) {
                if (piece.size() == 0) {
                    piece.add(next);
                    preCondition = predicate.test(next);
                    next = elements.hasNext() ? elements.next() : NONE;
                } else if (predicate.test(next) == preCondition) {
                    piece.add(next);
                    next = elements.hasNext() ? elements.next() : NONE;
                } else {

                    break;
                }
            }

            res.add(piece);
        }

        return res;
    }

    /**
     * <pre>
     * <code>
     * // split the number sequence by window 5.
     * Seq.of(1, 2, 3, 5, 7, 9, 10, 11, 19).split(MutableInt.of(5), (e, b) -> e <= b.intValue(), b -> b.addAndGet(5)).forEach(N::println);
     * </code>
     * </pre>
     *
     * @param <U>
     * @param <E>
     * @param <E2>
     * @param flag
     * @param predicate
     * @param flagUpdate
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <U, E extends Exception, E2 extends Exception> List<List<T>> split(final U flag, final Try.BiPredicate<? super T, ? super U, E> predicate,
            final Try.Consumer<? super U, E2> flagUpdate) throws E, E2 {
        return split(flag, predicate, flagUpdate, Suppliers.<T> ofList());
    }

    /**
     * Split.
     *
     * @param <U>
     * @param <C>
     * @param <E>
     * @param <E2>
     * @param flag
     * @param predicate
     * @param flagUpdate
     * @param supplier
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <U, C extends Collection<T>, E extends Exception, E2 extends Exception> List<C> split(final U flag,
            final Try.BiPredicate<? super T, ? super U, E> predicate, final Try.Consumer<? super U, E2> flagUpdate, final Supplier<? extends C> supplier)
            throws E, E2 {
        N.checkArgNotNull(predicate);
        N.checkArgNotNull(flagUpdate);
        N.checkArgNotNull(supplier);

        final List<C> res = new ArrayList<>();
        final Iterator<T> elements = iterator();
        final T NONE = (T) N.NULL_MASK;
        T next = NONE;
        boolean preCondition = false;

        while (next != NONE || elements.hasNext()) {
            final C piece = supplier.get();

            if (next == NONE) {
                next = elements.next();
            }

            while (next != NONE) {
                if (piece.size() == 0) {
                    piece.add(next);
                    preCondition = predicate.test(next, flag);
                    next = elements.hasNext() ? elements.next() : NONE;
                } else if (predicate.test(next, flag) == preCondition) {
                    piece.add(next);
                    next = elements.hasNext() ? elements.next() : NONE;
                } else {
                    if (flagUpdate != null) {
                        flagUpdate.accept(flag);
                    }

                    break;
                }
            }

            res.add(piece);
        }

        return res;
    }

    /**
     * Split at.
     *
     * @param where
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Pair<List<T>, List<T>> splitAt(final int where) {
        N.checkArgNotNegative(where, "where");

        List<T> left = null;
        List<T> right = null;

        if (N.isNullOrEmpty(coll)) {
            left = new ArrayList<>();
            right = new ArrayList<>();
        } else if (where == 0) {
            left = new ArrayList<>();
            right = new ArrayList<>(coll);
        } else if (where >= coll.size()) {
            left = new ArrayList<>();
            right = new ArrayList<>(coll);
        } else if (coll instanceof List) {
            left = new ArrayList<>(((List) coll).subList(0, where));
            right = new ArrayList<>(((List) coll).subList(where, size()));
        } else {
            left = new ArrayList<>(where);
            right = new ArrayList<>(coll.size() - where);

            final Iterator<T> iter = coll.iterator();
            int cnt = 0;

            while (cnt++ < where) {
                left.add(iter.next());
            }

            while (iter.hasNext()) {
                right.add(iter.next());
            }
        }

        return Pair.of(left, right);
    }

    /**
     * Split by.
     *
     * @param <E>
     * @param predicate
     * @return
     * @throws E the e
     */
    public <E extends Exception> Pair<List<T>, List<T>> splitBy(final Try.Predicate<? super T, E> predicate) throws E {
        N.checkArgNotNull(predicate);

        final List<T> left = new ArrayList<>();
        final List<T> right = new ArrayList<>();

        final Iterator<T> iter = iterator();
        T next = (T) N.NULL_MASK;

        while (iter.hasNext() && predicate.test((next = iter.next()))) {
            left.add(next);

            next = (T) N.NULL_MASK;
        }

        if (next != N.NULL_MASK) {
            right.add(next);
        }

        while (iter.hasNext()) {
            right.add(iter.next());
        }

        return Pair.of(left, right);
    }

    /**
     * Sliding.
     *
     * @param windowSize
     * @return
     */
    public List<List<T>> sliding(final int windowSize) {
        return sliding(windowSize, 1);
    }

    /**
     * Sliding.
     *
     * @param windowSize
     * @param increment
     * @return
     */
    public List<List<T>> sliding(final int windowSize, final int increment) {
        N.checkArgument(windowSize > 0 && increment > 0, "windowSize=%s and increment=%s must be bigger than 0", windowSize, increment);

        final Iterator<T> iter = coll.iterator();
        final List<List<T>> result = new ArrayList<>(coll.size() <= windowSize ? 1 : (1 + (coll.size() - windowSize)) / increment);

        while (iter.hasNext()) {
            if (increment > windowSize && result.size() > 0) {
                int skipNum = increment - windowSize;

                while (skipNum-- > 0 && iter.hasNext()) {
                    iter.next();
                }

                if (iter.hasNext() == false) {
                    break;
                }
            }

            final List<T> window = new ArrayList<>(windowSize);
            int cnt = 0;

            if (increment < windowSize && result.size() > 0) {
                final List<T> prev = result.get(result.size() - 1);
                cnt = windowSize - increment;

                if (cnt <= 8) {
                    for (int i = windowSize - cnt; i < windowSize; i++) {
                        window.add(prev.get(i));
                    }
                } else {
                    window.addAll(prev.subList(windowSize - cnt, windowSize));
                }
            }

            while (cnt++ < windowSize && iter.hasNext()) {
                window.add(iter.next());
            }

            result.add(window);
        }

        return result;
    }

    /**
     * Join.
     *
     * @return
     */
    public String join() {
        return join(N.ELEMENT_SEPARATOR);
    }

    /**
     * Join.
     *
     * @param delimiter
     * @return
     */
    public String join(final char delimiter) {
        return StringUtil.join(coll, delimiter);
    }

    /**
     * Join.
     *
     * @param delimiter
     * @return
     */
    public String join(final String delimiter) {
        return StringUtil.join(coll, delimiter);
    }

    /**
     * Join.
     *
     * @param <E>
     * @param toStringFunc
     * @param delimiter
     * @return
     * @throws E the e
     */
    public <E extends Exception> String join(final Try.Function<? super T, String, E> toStringFunc, final String delimiter) throws E {
        if (N.isNullOrEmpty(coll)) {
            return N.EMPTY_STRING;
        }

        try (final Joiner joiner = Joiner.with(delimiter).reuseCachedBuffer(true)) {
            for (T e : coll) {
                joiner.append(e);
            }

            return joiner.toString();
        }
    }

    /**
     * Only one.
     *
     * @return
     * @throws DuplicatedResultException if there are more than one element in this {@code Seq}.
     */
    public Nullable<T> onlyOne() throws DuplicatedResultException {
        if (isEmpty()) {
            return Nullable.empty();
        } else if (size() == 1) {
            return first();
        } else {
            throw new DuplicatedResultException(N.toString(coll));
        }
    }

    /**
     * Checks if is empty.
     *
     * @return true, if is empty
     */
    @Override
    public boolean isEmpty() {
        return coll == null || coll.isEmpty();
    }

    /**
     * Size.
     *
     * @return
     */
    @Override
    public int size() {
        return coll == null ? 0 : coll.size();
    }

    /**
     * To array.
     *
     * @return
     */
    @Override
    public Object[] toArray() {
        return coll == null ? N.EMPTY_OBJECT_ARRAY : coll.toArray();
    }

    /**
     * To array.
     *
     * @param <A>
     * @param a
     * @return
     */
    @Override
    public <A> A[] toArray(A[] a) {
        return coll == null ? a : coll.toArray(a);
    }

    /**
     * To list.
     *
     * @return
     */
    public List<T> toList() {
        return coll == null ? new ArrayList<T>() : new ArrayList<T>(coll);
    }

    /**
     * To set.
     *
     * @return
     */
    public Set<T> toSet() {
        return coll == null ? new HashSet<T>() : new HashSet<T>(coll);
    }

    /**
     * To collection.
     *
     * @param <C>
     * @param supplier
     * @return
     */
    public <C extends Collection<T>> C toCollection(final IntFunction<? extends C> supplier) {
        final C result = supplier.apply(size());

        if (N.notNullOrEmpty(coll)) {
            result.addAll(coll);
        }

        return result;
    }

    /**
     * To multiset.
     *
     * @return
     */
    public Multiset<T> toMultiset() {
        final Multiset<T> result = new Multiset<>(N.initHashCapacity(size()));

        if (N.notNullOrEmpty(coll)) {
            result.addAll(coll);
        }

        return result;
    }

    /**
     * To multiset.
     *
     * @param supplier
     * @return
     */
    public Multiset<T> toMultiset(final IntFunction<Multiset<T>> supplier) {
        final Multiset<T> result = supplier.apply(size());

        if (N.notNullOrEmpty(coll)) {
            result.addAll(coll);
        }

        return result;
    }

    /**
     * To map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <E>
     * @param <E2>
     * @param keyMapper
     * @param valueMapper
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, E extends Exception, E2 extends Exception> Map<K, V> toMap(Try.Function<? super T, ? extends K, E> keyMapper,
            Try.Function<? super T, ? extends V, E2> valueMapper) throws E, E2 {
        return toMap(keyMapper, valueMapper, Factory.<K, V> ofMap());
    }

    /**
     * To map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <M>
     * @param <E>
     * @param <E2>
     * @param keyMapper
     * @param valueMapper
     * @param mapFactory
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, M extends Map<K, V>, E extends Exception, E2 extends Exception> M toMap(Try.Function<? super T, ? extends K, E> keyMapper,
            Try.Function<? super T, ? extends V, E2> valueMapper, IntFunction<? extends M> mapFactory) throws E, E2 {
        return toMap(keyMapper, valueMapper, Fn.<V> throwingMerger(), mapFactory);
    }

    /**
     * To map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <E>
     * @param <E2>
     * @param <E3>
     * @param keyMapper
     * @param valueMapper
     * @param mergeFunction
     * @return
     * @throws E the e
     * @throws E2 the e2
     * @throws E3 the e3
     */
    public <K, V, E extends Exception, E2 extends Exception, E3 extends Exception> Map<K, V> toMap(Try.Function<? super T, ? extends K, E> keyMapper,
            Try.Function<? super T, ? extends V, E2> valueMapper, Try.BinaryOperator<V, E3> mergeFunction) throws E, E2, E3 {
        return toMap(keyMapper, valueMapper, mergeFunction, Factory.<K, V> ofMap());
    }

    /**
     * To map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <M>
     * @param <E>
     * @param <E2>
     * @param <E3>
     * @param keyMapper
     * @param valueMapper
     * @param mergeFunction
     * @param mapFactory
     * @return
     * @throws E the e
     * @throws E2 the e2
     * @throws E3 the e3
     */
    public <K, V, M extends Map<K, V>, E extends Exception, E2 extends Exception, E3 extends Exception> M toMap(
            Try.Function<? super T, ? extends K, E> keyMapper, Try.Function<? super T, ? extends V, E2> valueMapper, Try.BinaryOperator<V, E3> mergeFunction,
            IntFunction<? extends M> mapFactory) throws E, E2, E3 {
        final M result = mapFactory.apply(size());

        for (T e : coll) {
            Maps.merge(result, keyMapper.apply(e), valueMapper.apply(e), mergeFunction);
        }

        return result;
    }

    /**
     * To map.
     *
     * @param <K> the key type
     * @param <A>
     * @param <D>
     * @param <E>
     * @param keyMapper
     * @param downstream
     * @return
     * @throws E the e
     */
    public <K, A, D, E extends Exception> Map<K, D> toMap(Try.Function<? super T, ? extends K, E> keyMapper, Collector<? super T, A, D> downstream) throws E {
        return toMap(keyMapper, downstream, Factory.<K, D> ofMap());
    }

    /**
     * To map.
     *
     * @param <K> the key type
     * @param <A>
     * @param <D>
     * @param <M>
     * @param <E>
     * @param keyMapper
     * @param downstream
     * @param mapFactory
     * @return
     * @throws E the e
     */
    public <K, A, D, M extends Map<K, D>, E extends Exception> M toMap(final Try.Function<? super T, ? extends K, E> keyMapper,
            final Collector<? super T, A, D> downstream, final IntFunction<? extends M> mapFactory) throws E {
        return toMap(keyMapper, FN.<T, RuntimeException> identity(), downstream, mapFactory);
    }

    /**
     * To map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <A>
     * @param <D>
     * @param <E>
     * @param <E2>
     * @param keyMapper
     * @param valueMapper
     * @param downstream
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, A, D, E extends Exception, E2 extends Exception> Map<K, D> toMap(Try.Function<? super T, ? extends K, E> keyMapper,
            Try.Function<? super T, ? extends V, E2> valueMapper, Collector<? super V, A, D> downstream) throws E, E2 {
        return toMap(keyMapper, valueMapper, downstream, Factory.<K, D> ofMap());
    }

    /**
     * To map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <A>
     * @param <D>
     * @param <M>
     * @param <E>
     * @param <E2>
     * @param keyMapper
     * @param valueMapper
     * @param downstream
     * @param mapFactory
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, A, D, M extends Map<K, D>, E extends Exception, E2 extends Exception> M toMap(final Try.Function<? super T, ? extends K, E> keyMapper,
            Try.Function<? super T, ? extends V, E2> valueMapper, final Collector<? super V, A, D> downstream, final IntFunction<? extends M> mapFactory)
            throws E, E2 {
        final M result = mapFactory.apply(size());
        final Supplier<A> downstreamSupplier = downstream.supplier();
        final BiConsumer<A, ? super V> downstreamAccumulator = downstream.accumulator();
        final Map<K, A> intermediate = (Map<K, A>) result;
        K key = null;
        A v = null;

        for (T e : coll) {
            key = N.checkArgNotNull(keyMapper.apply(e), "element cannot be mapped to a null key");

            if ((v = intermediate.get(key)) == null) {
                if ((v = downstreamSupplier.get()) != null) {
                    intermediate.put(key, v);
                }
            }

            downstreamAccumulator.accept(v, valueMapper.apply(e));
        }

        final BiFunction<? super K, ? super A, ? extends A> function = new BiFunction<K, A, A>() {
            @Override
            public A apply(K k, A v) {
                return (A) downstream.finisher().apply(v);
            }
        };

        Maps.replaceAll(intermediate, function);

        return result;
    }

    /**
     * Flat to map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <E>
     * @param <E2>
     * @param flatKeyMapper
     * @param valueMapper
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, E extends Exception, E2 extends Exception> Map<K, V> flatToMap(Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper,
            Try.BiFunction<? super K, ? super T, ? extends V, E2> valueMapper) throws E, E2 {
        return flatToMap(flatKeyMapper, valueMapper, Factory.<K, V> ofMap());
    }

    /**
     * Flat to map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <M>
     * @param <E>
     * @param <E2>
     * @param flatKeyMapper
     * @param valueMapper
     * @param mapFactory
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, M extends Map<K, V>, E extends Exception, E2 extends Exception> M flatToMap(
            Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper, Try.BiFunction<? super K, ? super T, ? extends V, E2> valueMapper,
            IntFunction<? extends M> mapFactory) throws E, E2 {
        return flatToMap(flatKeyMapper, valueMapper, Fn.<V> throwingMerger(), mapFactory);
    }

    /**
     * Flat to map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <E>
     * @param <E2>
     * @param <E3>
     * @param flatKeyMapper
     * @param valueMapper
     * @param mergeFunction
     * @return
     * @throws E the e
     * @throws E2 the e2
     * @throws E3 the e3
     */
    public <K, V, E extends Exception, E2 extends Exception, E3 extends Exception> Map<K, V> flatToMap(
            Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper, Try.BiFunction<? super K, ? super T, ? extends V, E2> valueMapper,
            Try.BinaryOperator<V, E3> mergeFunction) throws E, E2, E3 {
        return flatToMap(flatKeyMapper, valueMapper, mergeFunction, Factory.<K, V> ofMap());
    }

    /**
     * Flat to map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <M>
     * @param <E>
     * @param <E2>
     * @param <E3>
     * @param flatKeyMapper
     * @param valueMapper
     * @param mergeFunction
     * @param mapFactory
     * @return
     * @throws E the e
     * @throws E2 the e2
     * @throws E3 the e3
     */
    public <K, V, M extends Map<K, V>, E extends Exception, E2 extends Exception, E3 extends Exception> M flatToMap(
            Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper, Try.BiFunction<? super K, ? super T, ? extends V, E2> valueMapper,
            Try.BinaryOperator<V, E3> mergeFunction, IntFunction<? extends M> mapFactory) throws E, E2, E3 {
        final M result = mapFactory.apply(size());
        Collection<? extends K> keys = null;

        for (T e : coll) {
            keys = flatKeyMapper.apply(e);

            if (N.notNullOrEmpty(keys)) {
                for (K k : keys) {
                    Maps.merge(result, k, valueMapper.apply(k, e), mergeFunction);
                }
            }
        }

        return result;
    }

    /**
     * Flat to map.
     *
     * @param <K> the key type
     * @param <A>
     * @param <D>
     * @param <E>
     * @param flatKeyMapper
     * @param downstream
     * @return
     * @throws E the e
     */
    public <K, A, D, E extends Exception> Map<K, D> flatToMap(Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper,
            Collector<? super T, A, D> downstream) throws E {
        return flatToMap(flatKeyMapper, downstream, Factory.<K, D> ofMap());
    }

    /**
     * Flat to map.
     *
     * @param <K> the key type
     * @param <A>
     * @param <D>
     * @param <M>
     * @param <E>
     * @param flatKeyMapper
     * @param downstream
     * @param mapFactory
     * @return
     * @throws E the e
     */
    public <K, A, D, M extends Map<K, D>, E extends Exception> Map<K, D> flatToMap(Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper,
            Collector<? super T, A, D> downstream, final IntFunction<? extends M> mapFactory) throws E {
        return flatToMap(flatKeyMapper, BiFunctions.<K, T> returnSecond(), downstream, mapFactory);
    }

    /**
     * Flat to map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <A>
     * @param <D>
     * @param <E>
     * @param <E2>
     * @param flatKeyMapper
     * @param valueMapper
     * @param downstream
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, A, D, E extends Exception, E2 extends Exception> Map<K, D> flatToMap(
            Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper, Try.BiFunction<? super K, ? super T, ? extends V, E2> valueMapper,
            Collector<? super V, A, D> downstream) throws E, E2 {
        return flatToMap(flatKeyMapper, valueMapper, downstream, Factory.<K, D> ofMap());
    }

    /**
     * Flat to map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <A>
     * @param <D>
     * @param <M>
     * @param <E>
     * @param <E2>
     * @param flatKeyMapper
     * @param valueMapper
     * @param downstream
     * @param mapFactory
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, A, D, M extends Map<K, D>, E extends Exception, E2 extends Exception> M flatToMap(
            Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper, Try.BiFunction<? super K, ? super T, ? extends V, E2> valueMapper,
            final Collector<? super V, A, D> downstream, final IntFunction<? extends M> mapFactory) throws E, E2 {
        final M result = mapFactory.apply(size());
        final Supplier<A> downstreamSupplier = downstream.supplier();
        final BiConsumer<A, ? super V> downstreamAccumulator = downstream.accumulator();
        final Map<K, A> intermediate = (Map<K, A>) result;

        Collection<? extends K> keys = null;
        A v = null;

        for (T e : coll) {
            keys = flatKeyMapper.apply(e);

            if (N.notNullOrEmpty(keys)) {
                for (K k : keys) {
                    N.checkArgNotNull(k, "element cannot be mapped to a null key");

                    if ((v = intermediate.get(k)) == null) {
                        if ((v = downstreamSupplier.get()) != null) {
                            intermediate.put(k, v);
                        }
                    }

                    downstreamAccumulator.accept(v, valueMapper.apply(k, e));
                }
            }
        }

        final BiFunction<? super K, ? super A, ? extends A> function = new BiFunction<K, A, A>() {
            @Override
            public A apply(K k, A v) {
                return (A) downstream.finisher().apply(v);
            }
        };

        Maps.replaceAll(intermediate, function);

        return result;
    }

    /**
     * Group to.
     *
     * @param <K> the key type
     * @param <E>
     * @param keyMapper
     * @return
     * @throws E the e
     */
    public <K, E extends Exception> Map<K, List<T>> groupTo(Try.Function<? super T, ? extends K, E> keyMapper) throws E {
        return groupTo(keyMapper, Factory.<K, List<T>> ofMap());
    }

    /**
     * Group to.
     *
     * @param <K> the key type
     * @param <M>
     * @param <E>
     * @param keyMapper
     * @param mapFactory
     * @return
     * @throws E the e
     */
    public <K, M extends Map<K, List<T>>, E extends Exception> M groupTo(Try.Function<? super T, ? extends K, E> keyMapper, IntFunction<? extends M> mapFactory)
            throws E {
        final M result = mapFactory.apply(size());
        K key = null;
        List<T> values = null;

        for (T e : coll) {
            key = keyMapper.apply(e);
            values = result.get(key);

            if (values == null) {
                values = new ArrayList<>();
                result.put(key, values);
            }

            values.add(e);
        }

        return result;
    }

    /**
     * Group to.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <E>
     * @param <E2>
     * @param keyMapper
     * @param valueMapper
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, E extends Exception, E2 extends Exception> Map<K, List<V>> groupTo(Try.Function<? super T, ? extends K, E> keyMapper,
            Try.Function<? super T, ? extends V, E2> valueMapper) throws E, E2 {
        return groupTo(keyMapper, valueMapper, Factory.<K, List<V>> ofMap());
    }

    /**
     * Group to.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <M>
     * @param <E>
     * @param <E2>
     * @param keyMapper
     * @param valueMapper
     * @param mapFactory
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, M extends Map<K, List<V>>, E extends Exception, E2 extends Exception> M groupTo(Try.Function<? super T, ? extends K, E> keyMapper,
            Try.Function<? super T, ? extends V, E2> valueMapper, IntFunction<? extends M> mapFactory) throws E, E2 {
        final M result = mapFactory.apply(size());
        K key = null;
        List<V> values = null;

        for (T e : coll) {
            key = keyMapper.apply(e);
            values = result.get(key);

            if (values == null) {
                values = new ArrayList<>();
                result.put(key, values);
            }

            values.add(valueMapper.apply(e));
        }

        return result;
    }

    /**
     * Flat group to.
     *
     * @param <K> the key type
     * @param <E>
     * @param flatKeyMapper
     * @return
     * @throws E the e
     */
    public <K, E extends Exception> Map<K, List<T>> flatGroupTo(final Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper) throws E {
        return flatGroupTo(flatKeyMapper, Factory.<K, List<T>> ofMap());
    }

    /**
     * Flat group to.
     *
     * @param <K> the key type
     * @param <M>
     * @param <E>
     * @param flatKeyMapper
     * @param mapFactory
     * @return
     * @throws E the e
     */
    public <K, M extends Map<K, List<T>>, E extends Exception> M flatGroupTo(final Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper,
            final IntFunction<? extends M> mapFactory) throws E {
        final M result = mapFactory.apply(size());
        Collection<? extends K> keys = null;
        List<T> values = null;

        for (T e : coll) {
            keys = flatKeyMapper.apply(e);

            if (N.notNullOrEmpty(keys)) {
                for (K k : keys) {
                    values = result.get(k);

                    if (values == null) {
                        values = new ArrayList<>();
                        result.put(k, values);
                    }

                    values.add(e);
                }
            }
        }

        return result;
    }

    /**
     * Flat group to.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <E>
     * @param <E2>
     * @param flatKeyMapper
     * @param valueMapper
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, E extends Exception, E2 extends Exception> Map<K, List<V>> flatGroupTo(
            final Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper,
            final Try.BiFunction<? super K, ? super T, ? extends V, E2> valueMapper) throws E, E2 {
        return flatGroupTo(flatKeyMapper, valueMapper, Factory.<K, List<V>> ofMap());
    }

    /**
     * Flat group to.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <M>
     * @param <E>
     * @param <E2>
     * @param flatKeyMapper
     * @param valueMapper
     * @param mapFactory
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, M extends Map<K, List<V>>, E extends Exception, E2 extends Exception> M flatGroupTo(
            final Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper,
            final Try.BiFunction<? super K, ? super T, ? extends V, E2> valueMapper, final IntFunction<? extends M> mapFactory) throws E, E2 {
        final M result = mapFactory.apply(size());
        Collection<? extends K> keys = null;
        List<V> values = null;

        for (T e : coll) {
            keys = flatKeyMapper.apply(e);

            if (N.notNullOrEmpty(keys)) {
                for (K k : keys) {
                    values = result.get(k);

                    if (values == null) {
                        values = new ArrayList<>();
                        result.put(k, values);
                    }

                    values.add(valueMapper.apply(k, e));
                }
            }
        }

        return result;
    }

    /**
     * To multimap.
     *
     * @param <K> the key type
     * @param <E>
     * @param keyMapper
     * @return
     * @throws E the e
     */
    public <K, E extends Exception> ListMultimap<K, T> toMultimap(Try.Function<? super T, ? extends K, E> keyMapper) throws E {
        return toMultimap(keyMapper, Factory.<K, T> ofListMultimap());
    }

    /**
     * To multimap.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <M>
     * @param <E>
     * @param keyMapper
     * @param mapFactory
     * @return
     * @throws E the e
     */
    public <K, V extends Collection<T>, M extends Multimap<K, T, V>, E extends Exception> M toMultimap(Try.Function<? super T, ? extends K, E> keyMapper,
            IntFunction<? extends M> mapFactory) throws E {
        final M result = mapFactory.apply(size());

        for (T e : coll) {
            result.put(keyMapper.apply(e), e);
        }

        return result;
    }

    /**
     * To multimap.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <E>
     * @param <E2>
     * @param keyMapper
     * @param valueMapper
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, E extends Exception, E2 extends Exception> ListMultimap<K, V> toMultimap(Try.Function<? super T, ? extends K, E> keyMapper,
            Try.Function<? super T, ? extends V, E2> valueMapper) throws E, E2 {
        return toMultimap(keyMapper, valueMapper, Factory.<K, V> ofListMultimap());
    }

    /**
     * To multimap.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <C>
     * @param <M>
     * @param <E>
     * @param <E2>
     * @param keyMapper
     * @param valueMapper
     * @param mapFactory
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, C extends Collection<V>, M extends Multimap<K, V, C>, E extends Exception, E2 extends Exception> M toMultimap(
            Try.Function<? super T, ? extends K, E> keyMapper, Try.Function<? super T, ? extends V, E2> valueMapper, IntFunction<? extends M> mapFactory)
            throws E, E2 {
        final M result = mapFactory.apply(size());

        for (T e : coll) {
            result.put(keyMapper.apply(e), valueMapper.apply(e));
        }

        return result;
    }

    /**
     * Flat to multimap.
     *
     * @param <K> the key type
     * @param <E>
     * @param flatKeyMapper
     * @return
     * @throws E the e
     */
    public <K, E extends Exception> ListMultimap<K, T> flatToMultimap(Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper) throws E {
        return flatToMultimap(flatKeyMapper, Factory.<K, T> ofListMultimap());
    }

    /**
     * Flat to multimap.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <M>
     * @param <E>
     * @param flatKeyMapper
     * @param mapFactory
     * @return
     * @throws E the e
     */
    public <K, V extends Collection<T>, M extends Multimap<K, T, V>, E extends Exception> M flatToMultimap(
            Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper, IntFunction<? extends M> mapFactory) throws E {
        final M result = mapFactory.apply(size());
        Collection<? extends K> ks = null;

        for (T e : coll) {
            ks = flatKeyMapper.apply(e);

            if (N.notNullOrEmpty(ks)) {
                for (K k : ks) {
                    result.put(k, e);
                }
            }
        }

        return result;
    }

    /**
     * Flat to multimap.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <E>
     * @param <E2>
     * @param flatKeyMapper
     * @param valueMapper
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, E extends Exception, E2 extends Exception> ListMultimap<K, V> flatToMultimap(
            Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper, Try.BiFunction<? super K, ? super T, ? extends V, E2> valueMapper)
            throws E, E2 {
        return flatToMultimap(flatKeyMapper, valueMapper, Factory.<K, V> ofListMultimap());
    }

    /**
     * Flat to multimap.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param <C>
     * @param <M>
     * @param <E>
     * @param <E2>
     * @param flatKeyMapper
     * @param valueMapper
     * @param mapFactory
     * @return
     * @throws E the e
     * @throws E2 the e2
     */
    public <K, V, C extends Collection<V>, M extends Multimap<K, V, C>, E extends Exception, E2 extends Exception> M flatToMultimap(
            Try.Function<? super T, ? extends Collection<? extends K>, E> flatKeyMapper, Try.BiFunction<? super K, ? super T, ? extends V, E2> valueMapper,
            IntFunction<? extends M> mapFactory) throws E, E2 {
        final M result = mapFactory.apply(size());

        Collection<? extends K> ks = null;

        for (T e : coll) {
            ks = flatKeyMapper.apply(e);

            if (N.notNullOrEmpty(ks)) {
                for (K k : ks) {
                    result.put(k, valueMapper.apply(k, e));
                }
            }
        }

        return result;
    }

    /**
     * The time complexity is <i>O(n + m)</i> : <i>n</i> is the size of this <code>Seq</code> and <i>m</i> is the size of specified collection <code>b</code>.
     *
     * @param <U>
     * @param <E>
     * @param <E2>
     * @param b
     * @param leftKeyMapper
     * @param rightKeyMapper
     * @return
     * @throws E the e
     * @throws E2 the e2
     * @see <a href="http://stackoverflow.com/questions/5706437/whats-the-difference-between-inner-join-left-join-right-join-and-full-join">sql join</a>
     */
    public <U, E extends Exception, E2 extends Exception> List<Pair<T, U>> innerJoin(final Collection<U> b, final Try.Function<? super T, ?, E> leftKeyMapper,
            final Try.Function<? super U, ?, E2> rightKeyMapper) throws E, E2 {
        final List<Pair<T, U>> result = new ArrayList<>(N.min(9, size(), N.size(b)));

        if (N.isNullOrEmpty(coll) || N.isNullOrEmpty(b)) {
            return result;
        }

        final ListMultimap<Object, U> rightKeyMap = ListMultimap.from(b, rightKeyMapper);

        for (T left : coll) {
            final List<U> rights = rightKeyMap.get(leftKeyMapper.apply(left));

            if (N.notNullOrEmpty(rights)) {
                for (U right : rights) {
                    result.add(Pair.of(left, right));
                }
            }
        }

        return result;
    }

    /**
     * The time complexity is <i>O(n * m)</i> : <i>n</i> is the size of this <code>Seq</code> and <i>m</i> is the size of specified collection <code>b</code>.
     *
     * @param <U>
     * @param <E>
     * @param b
     * @param predicate
     * @return
     * @throws E the e
     * @see <a href="http://stackoverflow.com/questions/5706437/whats-the-difference-between-inner-join-left-join-right-join-and-ful
     */
    public <U, E extends Exception> List<Pair<T, U>> innerJoin(final Collection<U> b, final Try.BiPredicate<? super T, ? super U, E> predicate) throws E {
        final List<Pair<T, U>> result = new ArrayList<>(N.min(9, size(), N.size(b)));

        if (N.isNullOrEmpty(coll) || N.isNullOrEmpty(b)) {
            return result;
        }

        for (T left : coll) {
            for (U right : b) {
                if (predicate.test(left, right)) {
                    result.add(Pair.of(left, right));
                }
            }
        }

        return result;
    }

    /**
     * The time complexity is <i>O(n + m)</i> : <i>n</i> is the size of this <code>Seq</code> and <i>m</i> is the size of specified collection <code>b</code>.
     *
     * @param <U>
     * @param <E>
     * @param <E2>
     * @param b
     * @param leftKeyMapper
     * @param rightKeyMapper
     * @return
     * @throws E the e
     * @throws E2 the e2
     * @see <a href="http://stackoverflow.com/questions/5706437/whats-the-difference-between-inner-join-left-join-right-join-and-ful
     */
    public <U, E extends Exception, E2 extends Exception> List<Pair<T, U>> fullJoin(final Collection<U> b, final Try.Function<? super T, ?, E> leftKeyMapper,
            final Try.Function<? super U, ?, E2> rightKeyMapper) throws E, E2 {
        final List<Pair<T, U>> result = new ArrayList<>(N.max(9, size(), N.size(b)));

        if (N.isNullOrEmpty(coll)) {
            for (T left : coll) {
                result.add(Pair.of(left, (U) null));
            }
        } else if (N.isNullOrEmpty(b)) {
            for (U right : b) {
                result.add(Pair.of((T) null, right));
            }
        } else {
            final ListMultimap<Object, U> rightKeyMap = ListMultimap.from(b, rightKeyMapper);
            final Map<U, U> joinedRights = new IdentityHashMap<>();

            for (T left : coll) {
                final List<U> rights = rightKeyMap.get(leftKeyMapper.apply(left));

                if (N.notNullOrEmpty(rights)) {
                    for (U right : rights) {
                        result.add(Pair.of(left, right));
                        joinedRights.put(right, right);
                    }
                } else {
                    result.add(Pair.of(left, (U) null));
                }
            }

            for (U right : b) {
                if (joinedRights.containsKey(right) == false) {
                    result.add(Pair.of((T) null, right));
                }
            }
        }

        return result;
    }

    /**
     * The time complexity is <i>O(n * m)</i> : <i>n</i> is the size of this <code>Seq</code> and <i>m</i> is the size of specified collection <code>b</code>.
     *
     * @param <U>
     * @param <E>
     * @param b
     * @param predicate
     * @return
     * @throws E the e
     * @see <a href="http://stackoverflow.com/questions/5706437/whats-the-difference-between-inner-join-left-join-right-join-and-ful
     */
    public <U, E extends Exception> List<Pair<T, U>> fullJoin(final Collection<U> b, final Try.BiPredicate<? super T, ? super U, E> predicate) throws E {
        final List<Pair<T, U>> result = new ArrayList<>(N.max(9, size(), N.size(b)));

        if (N.isNullOrEmpty(coll)) {
            for (T left : coll) {
                result.add(Pair.of(left, (U) null));
            }
        } else if (N.isNullOrEmpty(b)) {
            for (U right : b) {
                result.add(Pair.of((T) null, right));
            }
        } else {
            final Map<U, U> joinedRights = new IdentityHashMap<>();

            for (T left : coll) {
                boolean joined = false;

                for (U right : b) {
                    if (predicate.test(left, right)) {
                        result.add(Pair.of(left, right));
                        joinedRights.put(right, right);
                        joined = true;
                    }
                }

                if (joined == false) {
                    result.add(Pair.of(left, (U) null));
                }
            }

            for (U right : b) {
                if (joinedRights.containsKey(right) == false) {
                    result.add(Pair.of((T) null, right));
                }
            }
        }

        return result;
    }

    /**
     * The time complexity is <i>O(n + m)</i> : <i>n</i> is the size of this <code>Seq</code> and <i>m</i> is the size of specified collection <code>b</code>.
     *
     * @param <U>
     * @param <E>
     * @param <E2>
     * @param b
     * @param leftKeyMapper
     * @param rightKeyMapper
     * @return
     * @throws E the e
     * @throws E2 the e2
     * @see <a href="http://stackoverflow.com/questions/5706437/whats-the-difference-between-inner-join-left-join-right-join-and-ful
     */
    public <U, E extends Exception, E2 extends Exception> List<Pair<T, U>> leftJoin(final Collection<U> b, final Try.Function<? super T, ?, E> leftKeyMapper,
            final Try.Function<? super U, ?, E2> rightKeyMapper) throws E, E2 {
        final List<Pair<T, U>> result = new ArrayList<>(size());

        if (N.isNullOrEmpty(coll)) {
            return result;
        } else if (N.isNullOrEmpty(b)) {
            for (T left : coll) {
                result.add(Pair.of(left, (U) null));
            }
        } else {
            final ListMultimap<Object, U> rightKeyMap = ListMultimap.from(b, rightKeyMapper);

            for (T left : coll) {
                final List<U> rights = rightKeyMap.get(leftKeyMapper.apply(left));

                if (N.notNullOrEmpty(rights)) {
                    for (U right : rights) {
                        result.add(Pair.of(left, right));
                    }
                } else {
                    result.add(Pair.of(left, (U) null));
                }
            }
        }

        return result;
    }

    /**
     * The time complexity is <i>O(n * m)</i> : <i>n</i> is the size of this <code>Seq</code> and <i>m</i> is the size of specified collection <code>b</code>.
     *
     * @param <U>
     * @param <E>
     * @param b
     * @param predicate
     * @return
     * @throws E the e
     * @see <a href="http://stackoverflow.com/questions/5706437/whats-the-difference-between-inner-join-left-join-right-join-and-ful
     */
    public <U, E extends Exception> List<Pair<T, U>> leftJoin(final Collection<U> b, final Try.BiPredicate<? super T, ? super U, E> predicate) throws E {
        final List<Pair<T, U>> result = new ArrayList<>(size());

        if (N.isNullOrEmpty(coll)) {
            return result;
        } else if (N.isNullOrEmpty(b)) {
            for (T left : coll) {
                result.add(Pair.of(left, (U) null));
            }
        } else {
            for (T left : coll) {
                boolean joined = false;

                for (U right : b) {
                    if (predicate.test(left, right)) {
                        result.add(Pair.of(left, right));
                        joined = true;
                    }
                }

                if (joined == false) {
                    result.add(Pair.of(left, (U) null));
                }
            }
        }

        return result;
    }

    /**
     * The time complexity is <i>O(n + m)</i> : <i>n</i> is the size of this <code>Seq</code> and <i>m</i> is the size of specified collection <code>b</code>.
     *
     * @param <U>
     * @param <E>
     * @param <E2>
     * @param b
     * @param leftKeyMapper
     * @param rightKeyMapper
     * @return
     * @throws E the e
     * @throws E2 the e2
     * @see <a href="http://stackoverflow.com/questions/5706437/whats-the-difference-between-inner-join-left-join-right-join-and-ful
     */
    public <U, E extends Exception, E2 extends Exception> List<Pair<T, U>> rightJoin(final Collection<U> b, final Try.Function<? super T, ?, E> leftKeyMapper,
            final Try.Function<? super U, ?, E2> rightKeyMapper) throws E, E2 {
        final List<Pair<T, U>> result = new ArrayList<>(N.size(b));

        if (N.isNullOrEmpty(b)) {
            return result;
        } else if (N.isNullOrEmpty(coll)) {
            for (U right : b) {
                result.add(Pair.of((T) null, right));
            }
        } else {
            final ListMultimap<Object, T> leftKeyMap = ListMultimap.from(coll, leftKeyMapper);

            for (U right : b) {
                final List<T> lefts = leftKeyMap.get(rightKeyMapper.apply(right));

                if (N.notNullOrEmpty(lefts)) {
                    for (T left : lefts) {
                        result.add(Pair.of(left, right));
                    }
                } else {
                    result.add(Pair.of((T) null, right));
                }
            }
        }

        return result;
    }

    /**
     * The time complexity is <i>O(n * m)</i> : <i>n</i> is the size of this <code>Seq</code> and <i>m</i> is the size of specified collection <code>b</code>.
     *
     * @param <U>
     * @param <E>
     * @param b
     * @param predicate
     * @return
     * @throws E the e
     * @see <a href="http://stackoverflow.com/questions/5706437/whats-the-difference-between-inner-join-left-join-right-join-and-ful
     */
    public <U, E extends Exception> List<Pair<T, U>> rightJoin(final Collection<U> b, final Try.BiPredicate<? super T, ? super U, E> predicate) throws E {
        final List<Pair<T, U>> result = new ArrayList<>(N.size(b));

        if (N.isNullOrEmpty(b)) {
            return result;
        } else if (N.isNullOrEmpty(coll)) {
            for (U right : b) {
                result.add(Pair.of((T) null, right));
            }
        } else {
            for (U right : b) {
                boolean joined = false;

                for (T left : coll) {
                    if (predicate.test(left, right)) {
                        result.add(Pair.of(left, right));
                        joined = true;
                    }
                }

                if (joined == false) {
                    result.add(Pair.of((T) null, right));
                }
            }
        }

        return result;
    }

    /**
     * Returns a read-only <code>Seq</code>.
     *
     * @param fromIndex
     * @param toIndex
     * @return
     */
    public Seq<T> slice(final int fromIndex, final int toIndex) {
        N.checkFromToIndex(fromIndex, toIndex, size());

        if (N.isNullOrEmpty(coll)) {
            return this;
        }

        if (coll instanceof List) {
            return new Seq<T>(((List<T>) coll).subList(fromIndex, toIndex));
        }

        return new Seq<T>(new SubCollection<>(coll, fromIndex, toIndex));
    }

    /**
     * Iterator.
     *
     * @return
     */
    @Override
    public ObjIterator<T> iterator() {
        return ObjIterator.of(coll);
    }

    //    public Stream<T> stream() {
    //        return N.isNullOrEmpty(coll) ? Stream.<T> empty() : Stream.of(coll);
    //    }

    /**
     * Apply.
     *
     * @param <R>
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <R, E extends Exception> R apply(Try.Function<? super Seq<T>, R, E> func) throws E {
        return func.apply(this);
    }

    /**
     * Apply if not empty.
     *
     * @param <R>
     * @param <E>
     * @param func
     * @return
     * @throws E the e
     */
    public <R, E extends Exception> Optional<R> applyIfNotEmpty(Try.Function<? super Seq<T>, R, E> func) throws E {
        return isEmpty() ? Optional.<R> empty() : Optional.ofNullable(func.apply(this));
    }

    /**
     * Accept.
     *
     * @param <E>
     * @param action
     * @throws E the e
     */
    public <E extends Exception> void accept(Try.Consumer<? super Seq<T>, E> action) throws E {
        action.accept(this);
    }

    /**
     * Accept if not empty.
     *
     * @param <E>
     * @param action
     * @throws E the e
     */
    public <E extends Exception> void acceptIfNotEmpty(Try.Consumer<? super Seq<T>, E> action) throws E {
        if (size() > 0) {
            action.accept(this);
        }
    }

    /**
     * Println.
     */
    public void println() {
        N.println(toString());
    }

    /**
     * Hash code.
     *
     * @return
     */
    @Override
    public int hashCode() {
        return coll == null ? 0 : coll.hashCode();
    }

    /**
     * Equals.
     *
     * @param obj
     * @return true, if successful
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof Seq) {
            final Seq<T> other = (Seq<T>) obj;

            return N.equals(this.coll, other.coll);
        }

        return false;
    }

    /**
     * To string.
     *
     * @return
     */
    @Override
    public String toString() {
        return coll == null ? N.NULL_STRING : coll.toString();
    }

    /**
     * The Class SubCollection.
     *
     * @param <E>
     */
    private static final class SubCollection<E> implements Collection<E> {

        /** The c. */
        private final Collection<E> c;

        /** The from index. */
        private final int fromIndex;

        /** The to index. */
        private final int toIndex;

        /**
         * Instantiates a new sub collection.
         *
         * @param c
         * @param fromIndex
         * @param toIndex
         */
        SubCollection(Collection<E> c, int fromIndex, int toIndex) {
            this.c = c;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        /**
         * Adds the.
         *
         * @param e
         * @return true, if successful
         */
        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        /**
         * Removes the.
         *
         * @param o
         * @return true, if successful
         */
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        /**
         * Contains.
         *
         * @param o
         * @return true, if successful
         */
        @Override
        public boolean contains(Object o) {
            final Iterator<E> iter = this.iterator();

            while (iter.hasNext()) {
                if (N.equals(iter.next(), o)) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Contains all.
         *
         * @param c
         * @return true, if successful
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object e : c) {
                if (contains(e) == false) {
                    return false;
                }
            }

            return true;
        }

        /**
         * Adds the all.
         *
         * @param c
         * @return true, if successful
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            throw new UnsupportedOperationException();
        }

        /**
         * Removes the all.
         *
         * @param c
         * @return true, if successful
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        /**
         * Retain all.
         *
         * @param c
         * @return true, if successful
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        /**
         * Clear.
         */
        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        /**
         * Checks if is empty.
         *
         * @return true, if is empty
         */
        @Override
        public boolean isEmpty() {
            return size() == 0;
        }

        /**
         * Size.
         *
         * @return
         */
        @Override
        public int size() {
            return toIndex - fromIndex;
        }

        /**
         * Iterator.
         *
         * @return
         */
        @Override
        public Iterator<E> iterator() {
            final Iterator<E> iter = c == null ? ObjIterator.<E> empty() : c.iterator();

            if (fromIndex > 0) {
                int offset = 0;

                while (offset++ < fromIndex) {
                    iter.next();
                }
            }

            return new Iterator<E>() {
                private int cursor = fromIndex;

                @Override
                public boolean hasNext() {
                    return cursor < toIndex;
                }

                @Override
                public E next() {
                    if (cursor >= toIndex) {
                        throw new NoSuchElementException();
                    }

                    cursor++;
                    return iter.next();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        /**
         * To array.
         *
         * @return
         */
        @Override
        public Object[] toArray() {
            final Iterator<E> iter = this.iterator();
            final Object[] a = new Object[size()];

            for (int i = 0, len = a.length; i < len; i++) {
                a[i] = iter.next();
            }

            return a;
        }

        /**
         * To array.
         *
         * @param <A>
         * @param a
         * @return
         */
        @Override
        public <A> A[] toArray(A[] a) {
            if (a.length < size()) {
                a = N.copyOf(a, size());
            }

            final Iterator<E> iter = this.iterator();

            for (int i = 0, len = a.length; i < len; i++) {
                a[i] = (A) iter.next();
            }

            return a;
        }
    }
}
