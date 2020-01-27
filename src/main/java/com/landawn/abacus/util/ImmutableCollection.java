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

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

import com.landawn.abacus.util.stream.Stream;

// TODO: Auto-generated Javadoc
/**
 * The Class ImmutableCollection.
 *
 * @author Haiyang Li
 * @param <E>
 * @since 0.8
 */
public class ImmutableCollection<E> extends AbstractCollection<E> {

    /** The coll. */
    final Collection<E> coll;

    /**
     * Instantiates a new immutable collection.
     *
     * @param c
     */
    protected ImmutableCollection(Collection<? extends E> c) {
        this.coll = (Collection<E>) c;
    }

    /**
     *
     * @param <E>
     * @param c the elements in this <code>Collection</code> are shared by the returned ImmutableCollection.
     * @return
     */
    public static <E> ImmutableCollection<E> of(final Collection<? extends E> c) {
        if (c == null) {
            return ImmutableList.empty();
        } else if (c instanceof ImmutableCollection) {
            return (ImmutableCollection<E>) c;
        }

        return new ImmutableCollection<>(c);
    }

    /**
     *
     * @param e
     * @return true, if successful
     * @deprecated throws {@code UnsupportedOperationException}
     * @throws UnsupportedOperationException
     */
    @Deprecated
    @Override
    public final boolean add(E e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds the all.
     *
     * @param newElements
     * @return true, if successful
     * @deprecated throws {@code UnsupportedOperationException}
     * @throws UnsupportedOperationException
     */
    @Deprecated
    @Override
    public final boolean addAll(Collection<? extends E> newElements) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param object
     * @return true, if successful
     * @deprecated throws {@code UnsupportedOperationException}
     * @throws UnsupportedOperationException
     */
    @Deprecated
    @Override
    public final boolean remove(Object object) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the if.
     *
     * @param filter
     * @return true, if successful
     * @deprecated throws {@code UnsupportedOperationException}
     * @throws UnsupportedOperationException
     */
    @Deprecated
    @Override
    public boolean removeIf(Predicate<? super E> filter) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the all.
     *
     * @param oldElements
     * @return true, if successful
     * @deprecated throws {@code UnsupportedOperationException}
     * @throws UnsupportedOperationException
     */
    @Deprecated
    @Override
    public final boolean removeAll(Collection<?> oldElements) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param elementsToKeep
     * @return true, if successful
     * @deprecated throws {@code UnsupportedOperationException}
     * @throws UnsupportedOperationException
     */
    @Deprecated
    @Override
    public final boolean retainAll(Collection<?> elementsToKeep) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @deprecated throws {@code UnsupportedOperationException}
     * @throws UnsupportedOperationException
     */
    @Deprecated
    @Override
    public final void clear() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param o
     * @return true, if successful
     */
    @Override
    public boolean contains(Object o) {
        return coll.contains(o);
    }

    /**
     *
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        return coll.iterator();
    }

    /**
     *
     * @return
     */
    @Override
    public int size() {
        return coll.size();
    }

    /**
     *
     * @return
     */
    @Override
    public Object[] toArray() {
        return coll.toArray();
    }

    /**
     *
     * @param <T>
     * @param a
     * @return
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return coll.toArray(a);
    }

    /**
     *
     * @return
     */
    public Stream<E> streamm() {
        return Stream.of(coll);
    }

    /**
     *
     * @param obj
     * @return true, if successful
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof ImmutableCollection && ((ImmutableCollection<E>) obj).coll.equals(coll);
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return coll.hashCode();
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return coll.toString();
    }
}
