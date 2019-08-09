/*
 * Copyright (C) 2018 HaiYang Li
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

import com.landawn.abacus.util.u.Optional;
import com.landawn.abacus.util.stream.LongStream;

// TODO: Auto-generated Javadoc
/**
 * The Class LongTriple.
 *
 * @author Haiyang Li
 * @since 1.2
 */
public final class LongTriple {

    /** The  1. */
    public final long _1;

    /** The  2. */
    public final long _2;

    /** The  3. */
    public final long _3;

    /**
     * Instantiates a new long triple.
     */
    LongTriple() {
        this(0, 0, 0);
    }

    /**
     * Instantiates a new long triple.
     *
     * @param _1 the  1
     * @param _2 the  2
     * @param _3 the  3
     */
    LongTriple(long _1, long _2, long _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    /**
     * Of.
     *
     * @param _1 the  1
     * @param _2 the  2
     * @param _3 the  3
     * @return the long triple
     */
    public static LongTriple of(long _1, long _2, long _3) {
        return new LongTriple(_1, _2, _3);
    }

    /**
     * Min.
     *
     * @return the long
     */
    public long min() {
        return N.min(_1, _2, _3);
    }

    /**
     * Max.
     *
     * @return the long
     */
    public long max() {
        return N.max(_1, _2, _3);
    }

    /**
     * Median.
     *
     * @return the long
     */
    public long median() {
        return N.median(_1, _2, _3);
    }

    /**
     * Sum.
     *
     * @return the long
     */
    public long sum() {
        return _1 + _2 + _3;
    }

    /**
     * Average.
     *
     * @return the double
     */
    public double average() {
        return (0d + _1 + _2 + _3) / 3;
    }

    /**
     * Reversed.
     *
     * @return the long triple
     */
    public LongTriple reversed() {
        return new LongTriple(_3, _2, _1);
    }

    /**
     * To array.
     *
     * @return the long[]
     */
    public long[] toArray() {
        return new long[] { _1, _2, _3 };
    }

    /**
     * To list.
     *
     * @return the long list
     */
    public LongList toList() {
        return LongList.of(_1, _2, _3);
    }

    /**
     * For each.
     *
     * @param <E> the element type
     * @param comsumer the comsumer
     * @throws E the e
     */
    public <E extends Exception> void forEach(Try.LongConsumer<E> comsumer) throws E {
        comsumer.accept(this._1);
        comsumer.accept(this._2);
        comsumer.accept(this._3);
    }

    /**
     * Accept.
     *
     * @param <E> the element type
     * @param action the action
     * @throws E the e
     */
    public <E extends Exception> void accept(Try.Consumer<LongTriple, E> action) throws E {
        action.accept(this);
    }

    /**
     * Map.
     *
     * @param <U> the generic type
     * @param <E> the element type
     * @param mapper the mapper
     * @return the u
     * @throws E the e
     */
    public <U, E extends Exception> U map(Try.Function<LongTriple, U, E> mapper) throws E {
        return mapper.apply(this);
    }

    /**
     * Filter.
     *
     * @param <E> the element type
     * @param predicate the predicate
     * @return the optional
     * @throws E the e
     */
    public <E extends Exception> Optional<LongTriple> filter(final Try.Predicate<LongTriple, E> predicate) throws E {
        return predicate.test(this) ? Optional.of(this) : Optional.<LongTriple> empty();
    }

    /**
     * Stream.
     *
     * @return the long stream
     */
    public LongStream stream() {
        return LongStream.of(_1, _2, _3);
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return (int) ((31 * (31 * _1 + this._2)) + _3);
    }

    /**
     * Equals.
     *
     * @param obj the obj
     * @return true, if successful
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof LongTriple)) {
            return false;
        } else {
            LongTriple other = (LongTriple) obj;
            return this._1 == other._1 && this._2 == other._2 && this._3 == other._3;
        }
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "[" + this._1 + ", " + this._2 + ", " + this._3 + "]";
    }
}
