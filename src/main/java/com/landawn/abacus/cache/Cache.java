/*
 * Copyright (C) 2015 HaiYang Li
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

package com.landawn.abacus.cache;

import java.io.Closeable;
import java.util.Set;

import com.landawn.abacus.util.ContinuableFuture;
import com.landawn.abacus.util.Properties;
import com.landawn.abacus.util.u.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Interface Cache.
 *
 * @author Haiyang Li
 * @param <K> the key type
 * @param <V> the value type
 * @since 0.8
 */
public interface Cache<K, V> extends Closeable {

    /** The Constant DEFAULT_LIVE_TIME. */
    public static final long DEFAULT_LIVE_TIME = 3 * 60 * 60 * 1000L;

    /** The Constant DEFAULT_MAX_IDLE_TIME. */
    public static final long DEFAULT_MAX_IDLE_TIME = 30 * 60 * 1000L;

    /**
     * Gets the.
     *
     * @param k the k
     * @return V
     */
    Optional<V> get(final K k);

    /**
     * Gets the t.
     *
     * @param k the k
     * @return V
     */
    V gett(final K k);

    /**
     * Async get.
     *
     * @param k the k
     * @return the continuable future
     */
    ContinuableFuture<Optional<V>> asyncGet(final K k);

    /**
     * Async gett.
     *
     * @param k the k
     * @return the continuable future
     */
    ContinuableFuture<V> asyncGett(final K k);

    /**
     * Put.
     *
     * @param k the k
     * @param v the v
     * @return true, if successful
     */
    boolean put(final K k, final V v);

    /**
     * Async put.
     *
     * @param k the k
     * @param v the v
     * @return the continuable future
     */
    ContinuableFuture<Boolean> asyncPut(final K k, final V v);

    /**
     * Put.
     *
     * @param k the k
     * @param v the v
     * @param liveTime            unit is milliseconds
     * @param maxIdleTime            unit is milliseconds
     * @return true, if successful
     */
    boolean put(final K k, final V v, long liveTime, long maxIdleTime);

    /**
     * Async put.
     *
     * @param k the k
     * @param v the v
     * @param liveTime the live time
     * @param maxIdleTime the max idle time
     * @return the continuable future
     */
    ContinuableFuture<Boolean> asyncPut(final K k, final V v, long liveTime, long maxIdleTime);

    /**
     * Removes the.
     *
     * @param k the k
     */
    void remove(final K k);

    /**
     * Async remove.
     *
     * @param k the k
     * @return the continuable future
     */
    ContinuableFuture<Void> asyncRemove(final K k);

    /**
     * Contains key.
     *
     * @param k the k
     * @return boolean
     */
    boolean containsKey(final K k);

    /**
     * Async contains key.
     *
     * @param k the k
     * @return the continuable future
     */
    ContinuableFuture<Boolean> asyncContainsKey(final K k);

    /**
     * Key set.
     *
     * @return Set<K>
     */
    Set<K> keySet();

    /**
     * Method size.
     *
     * @return the int
     */
    int size();

    /**
     * Remove all cached entities from cache pool.
     */
    void clear();

    /**
     * release the resource token by this cache.
     */
    @Override
    void close();

    /**
     * method isClosed.
     *
     * @return true, if is closed
     */
    boolean isClosed();

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    Properties<String, Object> getProperties();

    /**
     * Gets the property.
     *
     * @param <T> the generic type
     * @param propName the prop name
     * @return the property
     */
    <T> T getProperty(String propName);

    /**
     * Returns the old value associated with the property by the {@code propName}, {@code null} if it doesn't exist.
     *
     * @param <T> the generic type
     * @param propName the prop name
     * @param propValue the prop value
     * @return the t
     */
    <T> T setProperty(String propName, Object propValue);

    /**
     * Returns value of the property which is to be removed, {@code null} if it doesn't exist.
     *
     * @param <T> the generic type
     * @param propName the prop name
     * @return the t
     */
    <T> T removeProperty(String propName);
}
