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

package com.landawn.abacus;

import java.sql.Connection;

import com.landawn.abacus.util.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Interface DataSource.
 *
 * @author Haiyang Li
 * @since 0.8
 */
public interface DataSource extends javax.sql.DataSource {

    /**
     * Gets the slice selector.
     *
     * @return the slice selector
     */
    SliceSelector getSliceSelector();

    /**
     * Returns the connection for write/read operation.
     *
     * @return the connection
     */
    @Override
    Connection getConnection();

    /**
     * Returns the read-only connection if it's configured in Abacus.xml file, otherwise the same connection as
     * {@code getConnection()} is returned
     *
     * @return the read only connection
     */
    Connection getReadOnlyConnection();

    /**
     * Gets the name.
     *
     * @return the name of the data source if it's configured, otherwise, {@code null} is returned.
     */
    String getName();

    /**
     * Gets the properties.
     *
     * @return the properties used to manage the connection.
     */
    Properties<String, String> getProperties();

    /**
     * Returns the maximum number of active connections that can be allocated from this pool.
     *
     * @return the max active
     */
    int getMaxActive();

    /**
     * Returns the number of allocated collection.
     *
     * @return the current active
     */
    int getCurrentActive();

    /**
     * All the allocated connections will be released/closed.
     */
    void close();

    /**
     * Checks if is closed.
     *
     * @return true, if is closed
     */
    boolean isClosed();
}
