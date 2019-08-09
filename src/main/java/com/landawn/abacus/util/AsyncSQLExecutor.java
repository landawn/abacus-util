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

package com.landawn.abacus.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.concurrent.Callable;

import com.landawn.abacus.DataSet;
import com.landawn.abacus.logging.Logger;
import com.landawn.abacus.logging.LoggerFactory;
import com.landawn.abacus.util.SQLExecutor.JdbcSettings;
import com.landawn.abacus.util.SQLExecutor.ResultExtractor;
import com.landawn.abacus.util.SQLExecutor.StatementSetter;
import com.landawn.abacus.util.u.Nullable;
import com.landawn.abacus.util.u.Optional;
import com.landawn.abacus.util.u.OptionalBoolean;
import com.landawn.abacus.util.u.OptionalByte;
import com.landawn.abacus.util.u.OptionalChar;
import com.landawn.abacus.util.u.OptionalDouble;
import com.landawn.abacus.util.u.OptionalFloat;
import com.landawn.abacus.util.u.OptionalInt;
import com.landawn.abacus.util.u.OptionalLong;
import com.landawn.abacus.util.u.OptionalShort;
import com.landawn.abacus.util.stream.Stream;

// TODO: Auto-generated Javadoc
/**
 * Asynchronous <code>SQLExecutor</code>.
 *
 * @author Haiyang Li
 * @since 0.8
 */
public final class AsyncSQLExecutor {

    /** The Constant logger. */
    protected static final Logger logger = LoggerFactory.getLogger(AsyncSQLExecutor.class);

    /** The sql executor. */
    private final SQLExecutor sqlExecutor;

    /** The async executor. */
    private final AsyncExecutor asyncExecutor;

    /**
     * Instantiates a new async SQL executor.
     *
     * @param sqlExecutor the sql executor
     * @param asyncExecutor the async executor
     */
    AsyncSQLExecutor(final SQLExecutor sqlExecutor, final AsyncExecutor asyncExecutor) {
        this.sqlExecutor = sqlExecutor;
        this.asyncExecutor = asyncExecutor;
    }

    /**
     * Sync.
     *
     * @return the SQL executor
     */
    public SQLExecutor sync() {
        return sqlExecutor;
    }

    /**
     * Async executor.
     *
     * @return the async executor
     */
    AsyncExecutor asyncExecutor() {
        return asyncExecutor;
    }

    /**
     * Insert.
     *
     * @param <ID> the generic type
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <ID> ContinuableFuture<ID> insert(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<ID>() {
            @Override
            public ID call() throws Exception {
                return sqlExecutor.insert(sql, parameters);
            }
        });
    }

    /**
     * Insert.
     *
     * @param <ID> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <ID> ContinuableFuture<ID> insert(final String sql, final StatementSetter statementSetter, final Object... parameters) {
        return asyncExecutor.execute(new Callable<ID>() {
            @Override
            public ID call() throws Exception {
                return sqlExecutor.insert(sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Insert.
     *
     * @param <ID> the generic type
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <ID> ContinuableFuture<ID> insert(final String sql, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<ID>() {
            @Override
            public ID call() throws Exception {
                return sqlExecutor.insert(sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Insert.
     *
     * @param <ID> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <ID> ContinuableFuture<ID> insert(final String sql, final StatementSetter statementSetter, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<ID>() {
            @Override
            public ID call() throws Exception {
                return sqlExecutor.insert(sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Insert.
     *
     * @param <ID> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param autoGeneratedKeyExtractor the auto generated key extractor
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <ID> ContinuableFuture<ID> insert(final String sql, final StatementSetter statementSetter,
            final JdbcUtil.RowMapper<ID> autoGeneratedKeyExtractor, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<ID>() {
            @Override
            public ID call() throws Exception {
                return sqlExecutor.insert(sql, statementSetter, autoGeneratedKeyExtractor, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Insert.
     *
     * @param <ID> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <ID> ContinuableFuture<ID> insert(final Connection conn, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<ID>() {
            @Override
            public ID call() throws Exception {
                return sqlExecutor.insert(conn, sql, parameters);
            }
        });
    }

    /**
     * Insert.
     *
     * @param <ID> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <ID> ContinuableFuture<ID> insert(final Connection conn, final String sql, final StatementSetter statementSetter, final Object... parameters) {
        return asyncExecutor.execute(new Callable<ID>() {
            @Override
            public ID call() throws Exception {
                return sqlExecutor.insert(conn, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Insert.
     *
     * @param <ID> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <ID> ContinuableFuture<ID> insert(final Connection conn, final String sql, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<ID>() {
            @Override
            public ID call() throws Exception {
                return sqlExecutor.insert(conn, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Insert.
     *
     * @param <ID> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <ID> ContinuableFuture<ID> insert(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<ID>() {
            @Override
            public ID call() throws Exception {
                return sqlExecutor.insert(conn, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Insert.
     *
     * @param <ID> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param autoGeneratedKeyExtractor the auto generated key extractor
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <ID> ContinuableFuture<ID> insert(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcUtil.RowMapper<ID> autoGeneratedKeyExtractor, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<ID>() {
            @Override
            public ID call() throws Exception {
                return sqlExecutor.insert(conn, sql, statementSetter, autoGeneratedKeyExtractor, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Batch insert.
     *
     * @param <ID> the generic type
     * @param sql the sql
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public <ID> ContinuableFuture<List<ID>> batchInsert(final String sql, final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<List<ID>>() {
            @Override
            public List<ID> call() throws Exception {
                return sqlExecutor.batchInsert(sql, parametersList);
            }
        });
    }

    /**
     * Batch insert.
     *
     * @param <ID> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public <ID> ContinuableFuture<List<ID>> batchInsert(final String sql, final StatementSetter statementSetter, final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<List<ID>>() {
            @Override
            public List<ID> call() throws Exception {
                return sqlExecutor.batchInsert(sql, statementSetter, parametersList);
            }
        });
    }

    /**
     * Batch insert.
     *
     * @param <ID> the generic type
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public <ID> ContinuableFuture<List<ID>> batchInsert(final String sql, final JdbcSettings jdbcSettings, final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<List<ID>>() {
            @Override
            public List<ID> call() throws Exception {
                return sqlExecutor.batchInsert(sql, jdbcSettings, parametersList);
            }
        });
    }

    /**
     * Batch insert.
     *
     * @param <ID> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public <ID> ContinuableFuture<List<ID>> batchInsert(final String sql, final StatementSetter statementSetter, final JdbcSettings jdbcSettings,
            final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<List<ID>>() {
            @Override
            public List<ID> call() throws Exception {
                return sqlExecutor.batchInsert(sql, statementSetter, jdbcSettings, parametersList);
            }
        });
    }

    /**
     * Batch insert.
     *
     * @param <ID> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param autoGeneratedKeyExtractor the auto generated key extractor
     * @param jdbcSettings the jdbc settings
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public <ID> ContinuableFuture<List<ID>> batchInsert(final String sql, final StatementSetter statementSetter,
            final JdbcUtil.BiRowMapper<ID> autoGeneratedKeyExtractor, final JdbcSettings jdbcSettings, final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<List<ID>>() {
            @Override
            public List<ID> call() throws Exception {
                return sqlExecutor.batchInsert(sql, statementSetter, autoGeneratedKeyExtractor, jdbcSettings, parametersList);
            }
        });
    }

    /**
     * Batch insert.
     *
     * @param <ID> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public <ID> ContinuableFuture<List<ID>> batchInsert(final Connection conn, final String sql, final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<List<ID>>() {
            @Override
            public List<ID> call() throws Exception {
                return sqlExecutor.batchInsert(conn, sql, parametersList);
            }
        });
    }

    /**
     * Batch insert.
     *
     * @param <ID> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public <ID> ContinuableFuture<List<ID>> batchInsert(final Connection conn, final String sql, final StatementSetter statementSetter,
            final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<List<ID>>() {
            @Override
            public List<ID> call() throws Exception {
                return sqlExecutor.batchInsert(conn, sql, statementSetter, parametersList);
            }
        });
    }

    /**
     * Batch insert.
     *
     * @param <ID> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public <ID> ContinuableFuture<List<ID>> batchInsert(final Connection conn, final String sql, final JdbcSettings jdbcSettings,
            final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<List<ID>>() {
            @Override
            public List<ID> call() throws Exception {
                return sqlExecutor.batchInsert(conn, sql, jdbcSettings, parametersList);
            }
        });
    }

    /**
     * Batch insert.
     *
     * @param <ID> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public <ID> ContinuableFuture<List<ID>> batchInsert(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<List<ID>>() {
            @Override
            public List<ID> call() throws Exception {
                return sqlExecutor.batchInsert(conn, sql, statementSetter, jdbcSettings, parametersList);
            }
        });
    }

    /**
     * Batch insert.
     *
     * @param <ID> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param autoGeneratedKeyExtractor the auto generated key extractor
     * @param jdbcSettings the jdbc settings
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public <ID> ContinuableFuture<List<ID>> batchInsert(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcUtil.BiRowMapper<ID> autoGeneratedKeyExtractor, final JdbcSettings jdbcSettings, final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<List<ID>>() {
            @Override
            public List<ID> call() throws Exception {
                return sqlExecutor.batchInsert(conn, sql, statementSetter, autoGeneratedKeyExtractor, jdbcSettings, parametersList);
            }
        });
    }

    /**
     * Update.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Integer> update(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.update(sql, parameters);
            }
        });
    }

    /**
     * Update.
     *
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Integer> update(final String sql, final StatementSetter statementSetter, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.update(sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Update.
     *
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Integer> update(final String sql, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.update(sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Update.
     *
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Integer> update(final String sql, final StatementSetter statementSetter, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.update(sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Update.
     *
     * @param conn the conn
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Integer> update(final Connection conn, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.update(conn, sql, parameters);
            }
        });
    }

    /**
     * Update.
     *
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Integer> update(final Connection conn, final String sql, final StatementSetter statementSetter, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.update(conn, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Update.
     *
     * @param conn the conn
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Integer> update(final Connection conn, final String sql, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.update(conn, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Update.
     *
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Integer> update(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.update(conn, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Batch update.
     *
     * @param sql the sql
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public ContinuableFuture<Integer> batchUpdate(final String sql, final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.batchUpdate(sql, parametersList);
            }
        });
    }

    /**
     * Batch update.
     *
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public ContinuableFuture<Integer> batchUpdate(final String sql, final StatementSetter statementSetter, final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.batchUpdate(sql, statementSetter, parametersList);
            }
        });
    }

    /**
     * Batch update.
     *
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public ContinuableFuture<Integer> batchUpdate(final String sql, final JdbcSettings jdbcSettings, final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.batchUpdate(sql, jdbcSettings, parametersList);
            }
        });
    }

    /**
     * Batch update.
     *
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public ContinuableFuture<Integer> batchUpdate(final String sql, final StatementSetter statementSetter, final JdbcSettings jdbcSettings,
            final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.batchUpdate(sql, statementSetter, jdbcSettings, parametersList);
            }
        });
    }

    /**
     * Batch update.
     *
     * @param conn the conn
     * @param sql the sql
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public ContinuableFuture<Integer> batchUpdate(final Connection conn, final String sql, final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.batchUpdate(conn, sql, parametersList);
            }
        });
    }

    /**
     * Batch update.
     *
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public ContinuableFuture<Integer> batchUpdate(final Connection conn, final String sql, final StatementSetter statementSetter,
            final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.batchUpdate(conn, sql, statementSetter, parametersList);
            }
        });
    }

    /**
     * Batch update.
     *
     * @param conn the conn
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public ContinuableFuture<Integer> batchUpdate(final Connection conn, final String sql, final JdbcSettings jdbcSettings, final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.batchUpdate(conn, sql, jdbcSettings, parametersList);
            }
        });
    }

    /**
     * Batch update.
     *
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parametersList the parameters list
     * @return the continuable future
     */
    public ContinuableFuture<Integer> batchUpdate(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final List<?> parametersList) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.batchUpdate(conn, sql, statementSetter, jdbcSettings, parametersList);
            }
        });
    }

    /**
     * Exists.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Boolean> exists(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return sqlExecutor.exists(sql, parameters);
            }
        });
    }

    /**
     * Exists.
     *
     * @param conn the conn
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Boolean> exists(final Connection conn, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return sqlExecutor.exists(conn, sql, parameters);
            }
        });
    }

    /**
     * Count.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     * @deprecated may be misused and it's inefficient.
     */
    @Deprecated
    @SafeVarargs
    public final ContinuableFuture<Integer> count(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.count(sql, parameters);
            }
        });
    }

    /**
     * Count.
     *
     * @param conn the conn
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     * @deprecated may be misused and it's inefficient.
     */
    @Deprecated
    @SafeVarargs
    public final ContinuableFuture<Integer> count(final Connection conn, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sqlExecutor.count(conn, sql, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final Class<T> targetClass, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(targetClass, sql, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final Class<T> targetClass, final String sql, final StatementSetter statementSetter,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(targetClass, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final Class<T> targetClass, final String sql, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(targetClass, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final Class<T> targetClass, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(targetClass, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final Class<T> targetClass, final Connection conn, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(targetClass, conn, sql, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final Class<T> targetClass, final Connection conn, final String sql,
            final StatementSetter statementSetter, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(targetClass, conn, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final Class<T> targetClass, final Connection conn, final String sql, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(targetClass, conn, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final Class<T> targetClass, final Connection conn, final String sql,
            final StatementSetter statementSetter, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(targetClass, conn, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final String sql, final JdbcUtil.RowMapper<T> rowMapper, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(sql, rowMapper, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final String sql, final StatementSetter statementSetter, final JdbcUtil.RowMapper<T> rowMapper,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(sql, statementSetter, rowMapper, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final String sql, final JdbcUtil.RowMapper<T> rowMapper, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(sql, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final String sql, final StatementSetter statementSetter, final JdbcUtil.RowMapper<T> rowMapper,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(sql, statementSetter, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final Connection conn, final String sql, final JdbcUtil.RowMapper<T> rowMapper,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(conn, sql, rowMapper, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcUtil.RowMapper<T> rowMapper, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(conn, sql, statementSetter, rowMapper, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final Connection conn, final String sql, final JdbcUtil.RowMapper<T> rowMapper,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(conn, sql, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> get(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcUtil.RowMapper<T> rowMapper, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.get(conn, sql, statementSetter, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final Class<T> targetClass, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(targetClass, sql, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final Class<T> targetClass, final String sql, final StatementSetter statementSetter,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(targetClass, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final Class<T> targetClass, final String sql, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(targetClass, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final Class<T> targetClass, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(targetClass, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final Class<T> targetClass, final Connection conn, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(targetClass, conn, sql, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final Class<T> targetClass, final Connection conn, final String sql, final StatementSetter statementSetter,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(targetClass, conn, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final Class<T> targetClass, final Connection conn, final String sql, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(targetClass, conn, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final Class<T> targetClass, final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(targetClass, conn, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final String sql, final JdbcUtil.RowMapper<T> rowMapper, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(sql, rowMapper, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final String sql, final StatementSetter statementSetter, final JdbcUtil.RowMapper<T> rowMapper,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(sql, statementSetter, rowMapper, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final String sql, final JdbcUtil.RowMapper<T> rowMapper, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(sql, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final String sql, final StatementSetter statementSetter, final JdbcUtil.RowMapper<T> rowMapper,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(sql, statementSetter, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final Connection conn, final String sql, final JdbcUtil.RowMapper<T> rowMapper, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(conn, sql, rowMapper, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcUtil.RowMapper<T> rowMapper, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(conn, sql, statementSetter, rowMapper, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final Connection conn, final String sql, final JdbcUtil.RowMapper<T> rowMapper, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(conn, sql, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Gets the t.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the t
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> gett(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcUtil.RowMapper<T> rowMapper, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.gett(conn, sql, statementSetter, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final Class<T> targetClass, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(targetClass, sql, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final Class<T> targetClass, final String sql, final StatementSetter statementSetter,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(targetClass, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final Class<T> targetClass, final String sql, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(targetClass, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final Class<T> targetClass, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(targetClass, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final Class<T> targetClass, final Connection conn, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(targetClass, conn, sql, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final Class<T> targetClass, final Connection conn, final String sql,
            final StatementSetter statementSetter, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(targetClass, conn, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final Class<T> targetClass, final Connection conn, final String sql,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(targetClass, conn, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final Class<T> targetClass, final Connection conn, final String sql,
            final StatementSetter statementSetter, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(targetClass, conn, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final String sql, final JdbcUtil.RowMapper<T> rowMapper, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(sql, rowMapper, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final String sql, final StatementSetter statementSetter, final JdbcUtil.RowMapper<T> rowMapper,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(sql, statementSetter, rowMapper, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final String sql, final JdbcUtil.RowMapper<T> rowMapper, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(sql, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final String sql, final StatementSetter statementSetter, final JdbcUtil.RowMapper<T> rowMapper,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(sql, statementSetter, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final Connection conn, final String sql, final JdbcUtil.RowMapper<T> rowMapper,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(conn, sql, rowMapper, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcUtil.RowMapper<T> rowMapper, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(conn, sql, statementSetter, rowMapper, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final Connection conn, final String sql, final JdbcUtil.RowMapper<T> rowMapper,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(conn, sql, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Find first.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Optional<T>> findFirst(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcUtil.RowMapper<T> rowMapper, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Optional<T>>() {
            @Override
            public Optional<T> call() throws Exception {
                return sqlExecutor.findFirst(conn, sql, statementSetter, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final Class<T> targetClass, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(targetClass, sql, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final Class<T> targetClass, final String sql, final StatementSetter statementSetter,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(targetClass, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final Class<T> targetClass, final String sql, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(targetClass, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final Class<T> targetClass, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(targetClass, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final Class<T> targetClass, final Connection conn, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(targetClass, conn, sql, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final Class<T> targetClass, final Connection conn, final String sql, final StatementSetter statementSetter,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(targetClass, conn, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final Class<T> targetClass, final Connection conn, final String sql, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(targetClass, conn, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final Class<T> targetClass, final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(targetClass, conn, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final String sql, final JdbcUtil.BiRowMapper<T> rowMapper, final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(sql, rowMapper, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final String sql, final StatementSetter statementSetter, final JdbcUtil.BiRowMapper<T> rowMapper,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(sql, statementSetter, rowMapper, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final String sql, final JdbcUtil.BiRowMapper<T> rowMapper, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(sql, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final String sql, final StatementSetter statementSetter, final JdbcUtil.BiRowMapper<T> rowMapper,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(sql, statementSetter, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final Connection conn, final String sql, final JdbcUtil.BiRowMapper<T> rowMapper,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(conn, sql, rowMapper, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcUtil.BiRowMapper<T> rowMapper, final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(conn, sql, statementSetter, rowMapper, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final Connection conn, final String sql, final JdbcUtil.BiRowMapper<T> rowMapper,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(conn, sql, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> list(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcUtil.BiRowMapper<T> rowMapper, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.list(conn, sql, statementSetter, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List all.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> listAll(final Class<T> targetClass, final String sql, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.listAll(targetClass, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List all.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> listAll(final Class<T> targetClass, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.listAll(targetClass, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List all.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sqls the sqls
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> listAll(final Class<T> targetClass, final List<String> sqls, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.listAll(targetClass, sqls, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List all.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sqls the sqls
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> listAll(final Class<T> targetClass, final List<String> sqls, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.listAll(targetClass, sqls, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List all.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> listAll(final String sql, final JdbcUtil.BiRowMapper<T> rowMapper, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.listAll(sql, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List all.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> listAll(final String sql, final StatementSetter statementSetter, final JdbcUtil.BiRowMapper<T> rowMapper,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.listAll(sql, statementSetter, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List all.
     *
     * @param <T> the generic type
     * @param sqls the sqls
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> listAll(final List<String> sqls, final JdbcUtil.BiRowMapper<T> rowMapper, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.listAll(sqls, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * List all.
     *
     * @param <T> the generic type
     * @param sqls the sqls
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<List<T>> listAll(final List<String> sqls, final StatementSetter statementSetter, final JdbcUtil.BiRowMapper<T> rowMapper,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return sqlExecutor.listAll(sqls, statementSetter, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query for boolean.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<OptionalBoolean> queryForBoolean(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<OptionalBoolean>() {
            @Override
            public OptionalBoolean call() throws Exception {
                return sqlExecutor.queryForBoolean(sql, parameters);
            }
        });
    }

    /**
     * Query for char.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<OptionalChar> queryForChar(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<OptionalChar>() {
            @Override
            public OptionalChar call() throws Exception {
                return sqlExecutor.queryForChar(sql, parameters);
            }
        });
    }

    /**
     * Query for byte.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<OptionalByte> queryForByte(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<OptionalByte>() {
            @Override
            public OptionalByte call() throws Exception {
                return sqlExecutor.queryForByte(sql, parameters);
            }
        });
    }

    /**
     * Query for short.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<OptionalShort> queryForShort(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<OptionalShort>() {
            @Override
            public OptionalShort call() throws Exception {
                return sqlExecutor.queryForShort(sql, parameters);
            }
        });
    }

    /**
     * Query for int.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<OptionalInt> queryForInt(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<OptionalInt>() {
            @Override
            public OptionalInt call() throws Exception {
                return sqlExecutor.queryForInt(sql, parameters);
            }
        });
    }

    /**
     * Query for long.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<OptionalLong> queryForLong(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<OptionalLong>() {
            @Override
            public OptionalLong call() throws Exception {
                return sqlExecutor.queryForLong(sql, parameters);
            }
        });
    }

    /**
     * Query for float.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<OptionalFloat> queryForFloat(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<OptionalFloat>() {
            @Override
            public OptionalFloat call() throws Exception {
                return sqlExecutor.queryForFloat(sql, parameters);
            }
        });
    }

    /**
     * Query for double.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<OptionalDouble> queryForDouble(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<OptionalDouble>() {
            @Override
            public OptionalDouble call() throws Exception {
                return sqlExecutor.queryForDouble(sql, parameters);
            }
        });
    }

    /**
     * Query for big decimal.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Nullable<BigDecimal>> queryForBigDecimal(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<BigDecimal>>() {
            @Override
            public Nullable<BigDecimal> call() throws Exception {
                return sqlExecutor.queryForBigDecimal(sql, parameters);
            }
        });
    }

    /**
     * Query for string.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Nullable<String>> queryForString(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<String>>() {
            @Override
            public Nullable<String> call() throws Exception {
                return sqlExecutor.queryForString(sql, parameters);
            }
        });
    }

    /**
     * Query for date.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Nullable<java.sql.Date>> queryForDate(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<java.sql.Date>>() {
            @Override
            public Nullable<java.sql.Date> call() throws Exception {
                return sqlExecutor.queryForDate(sql, parameters);
            }
        });
    }

    /**
     * Query for time.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Nullable<java.sql.Time>> queryForTime(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<java.sql.Time>>() {
            @Override
            public Nullable<java.sql.Time> call() throws Exception {
                return sqlExecutor.queryForTime(sql, parameters);
            }
        });
    }

    /**
     * Query for timestamp.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Nullable<java.sql.Timestamp>> queryForTimestamp(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<java.sql.Timestamp>>() {
            @Override
            public Nullable<java.sql.Timestamp> call() throws Exception {
                return sqlExecutor.queryForTimestamp(sql, parameters);
            }
        });
    }

    /**
     * Query for single result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForSingleResult(final Class<V> targetClass, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForSingleResult(targetClass, sql, parameters);
            }
        });
    }

    /**
     * Query for single result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForSingleResult(final Class<V> targetClass, final String sql, final StatementSetter statementSetter,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForSingleResult(targetClass, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Query for single result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForSingleResult(final Class<V> targetClass, final String sql, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForSingleResult(targetClass, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query for single result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForSingleResult(final Class<V> targetClass, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForSingleResult(targetClass, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query for single result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForSingleResult(final Class<V> targetClass, final Connection conn, final String sql,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForSingleResult(targetClass, conn, sql, parameters);
            }
        });
    }

    /**
     * Query for single result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForSingleResult(final Class<V> targetClass, final Connection conn, final String sql,
            final StatementSetter statementSetter, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForSingleResult(targetClass, conn, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Query for single result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForSingleResult(final Class<V> targetClass, final Connection conn, final String sql,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForSingleResult(targetClass, conn, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query for single result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForSingleResult(final Class<V> targetClass, final Connection conn, final String sql,
            final StatementSetter statementSetter, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForSingleResult(targetClass, conn, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query for unique result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForUniqueResult(final Class<V> targetClass, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForUniqueResult(targetClass, sql, parameters);
            }
        });
    }

    /**
     * Query for unique result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForUniqueResult(final Class<V> targetClass, final String sql, final StatementSetter statementSetter,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForUniqueResult(targetClass, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Query for unique result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForUniqueResult(final Class<V> targetClass, final String sql, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForUniqueResult(targetClass, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query for unique result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForUniqueResult(final Class<V> targetClass, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForUniqueResult(targetClass, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query for unique result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForUniqueResult(final Class<V> targetClass, final Connection conn, final String sql,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForUniqueResult(targetClass, conn, sql, parameters);
            }
        });
    }

    /**
     * Query for unique result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForUniqueResult(final Class<V> targetClass, final Connection conn, final String sql,
            final StatementSetter statementSetter, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForUniqueResult(targetClass, conn, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Query for unique result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForUniqueResult(final Class<V> targetClass, final Connection conn, final String sql,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForUniqueResult(targetClass, conn, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query for unique result.
     *
     * @param <V> the value type
     * @param targetClass the target class
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <V> ContinuableFuture<Nullable<V>> queryForUniqueResult(final Class<V> targetClass, final Connection conn, final String sql,
            final StatementSetter statementSetter, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Nullable<V>>() {
            @Override
            public Nullable<V> call() throws Exception {
                return sqlExecutor.queryForUniqueResult(targetClass, conn, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<DataSet> query(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<DataSet>() {
            @Override
            public DataSet call() throws Exception {
                return sqlExecutor.query(sql, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<DataSet> query(final String sql, final StatementSetter statementSetter, final Object... parameters) {
        return asyncExecutor.execute(new Callable<DataSet>() {
            @Override
            public DataSet call() throws Exception {
                return sqlExecutor.query(sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<DataSet> query(final String sql, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<DataSet>() {
            @Override
            public DataSet call() throws Exception {
                return sqlExecutor.query(sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<DataSet> query(final String sql, final StatementSetter statementSetter, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<DataSet>() {
            @Override
            public DataSet call() throws Exception {
                return sqlExecutor.query(sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param resultExtractor the result extractor
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> query(final String sql, final ResultExtractor<T> resultExtractor, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.query(sql, resultExtractor, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param resultExtractor the result extractor
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> query(final String sql, final StatementSetter statementSetter, final ResultExtractor<T> resultExtractor,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.query(sql, statementSetter, resultExtractor, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param resultExtractor the result extractor
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> query(final String sql, final ResultExtractor<T> resultExtractor, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.query(sql, resultExtractor, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param resultExtractor the result extractor
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> query(final String sql, final StatementSetter statementSetter, final ResultExtractor<T> resultExtractor,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.query(sql, statementSetter, resultExtractor, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param conn the conn
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<DataSet> query(final Connection conn, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<DataSet>() {
            @Override
            public DataSet call() throws Exception {
                return sqlExecutor.query(conn, sql, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<DataSet> query(final Connection conn, final String sql, final StatementSetter statementSetter, final Object... parameters) {
        return asyncExecutor.execute(new Callable<DataSet>() {
            @Override
            public DataSet call() throws Exception {
                return sqlExecutor.query(conn, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param conn the conn
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<DataSet> query(final Connection conn, final String sql, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<DataSet>() {
            @Override
            public DataSet call() throws Exception {
                return sqlExecutor.query(conn, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<DataSet> query(final Connection conn, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<DataSet>() {
            @Override
            public DataSet call() throws Exception {
                return sqlExecutor.query(conn, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param resultExtractor the result extractor
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> query(final Connection conn, final String sql, final ResultExtractor<T> resultExtractor, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.query(conn, sql, resultExtractor, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param resultExtractor the result extractor
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> query(final Connection conn, final String sql, final StatementSetter statementSetter,
            final ResultExtractor<T> resultExtractor, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.query(conn, sql, statementSetter, resultExtractor, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param resultExtractor the result extractor
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> query(final Connection conn, final String sql, final ResultExtractor<T> resultExtractor,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.query(conn, sql, resultExtractor, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query.
     *
     * @param <T> the generic type
     * @param conn the conn
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param resultExtractor the result extractor
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<T> query(final Connection conn, final String sql, final StatementSetter statementSetter,
            final ResultExtractor<T> resultExtractor, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return sqlExecutor.query(conn, sql, statementSetter, resultExtractor, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query all.
     *
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<DataSet> queryAll(final String sql, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<DataSet>() {
            @Override
            public DataSet call() throws Exception {
                return sqlExecutor.queryAll(sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query all.
     *
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<DataSet> queryAll(final String sql, final StatementSetter statementSetter, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<DataSet>() {
            @Override
            public DataSet call() throws Exception {
                return sqlExecutor.queryAll(sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query all.
     *
     * @param sqls the sqls
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<DataSet> queryAll(final List<String> sqls, final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<DataSet>() {
            @Override
            public DataSet call() throws Exception {
                return sqlExecutor.queryAll(sqls, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Query all.
     *
     * @param sqls the sqls
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<DataSet> queryAll(final List<String> sqls, final StatementSetter statementSetter, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<DataSet>() {
            @Override
            public DataSet call() throws Exception {
                return sqlExecutor.queryAll(sqls, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Stream.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Stream<T>> stream(final Class<T> targetClass, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Stream<T>>() {
            @Override
            public Stream<T> call() throws Exception {
                return sqlExecutor.stream(targetClass, sql, parameters);
            }
        });
    }

    /**
     * Stream.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Stream<T>> stream(final Class<T> targetClass, final String sql, final StatementSetter statementSetter,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Stream<T>>() {
            @Override
            public Stream<T> call() throws Exception {
                return sqlExecutor.stream(targetClass, sql, statementSetter, parameters);
            }
        });
    }

    /**
     * Stream.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Stream<T>> stream(final Class<T> targetClass, final String sql, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Stream<T>>() {
            @Override
            public Stream<T> call() throws Exception {
                return sqlExecutor.stream(targetClass, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Stream.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Stream<T>> stream(final Class<T> targetClass, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Stream<T>>() {
            @Override
            public Stream<T> call() throws Exception {
                return sqlExecutor.stream(targetClass, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Stream.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Stream<T>> stream(final String sql, final JdbcUtil.BiRowMapper<T> rowMapper, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Stream<T>>() {
            @Override
            public Stream<T> call() throws Exception {
                return sqlExecutor.stream(sql, rowMapper, parameters);
            }
        });
    }

    /**
     * Stream.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Stream<T>> stream(final String sql, final StatementSetter statementSetter, final JdbcUtil.BiRowMapper<T> rowMapper,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Stream<T>>() {
            @Override
            public Stream<T> call() throws Exception {
                return sqlExecutor.stream(sql, statementSetter, rowMapper, parameters);
            }
        });
    }

    /**
     * Stream.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Stream<T>> stream(final String sql, final JdbcUtil.BiRowMapper<T> rowMapper, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Stream<T>>() {
            @Override
            public Stream<T> call() throws Exception {
                return sqlExecutor.stream(sql, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Stream.
     *
     * @param <T> the generic type
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param rowMapper the row mapper
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Stream<T>> stream(final String sql, final StatementSetter statementSetter, final JdbcUtil.BiRowMapper<T> rowMapper,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Stream<T>>() {
            @Override
            public Stream<T> call() throws Exception {
                return sqlExecutor.stream(sql, statementSetter, rowMapper, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Stream all.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Stream<T>> streamAll(final Class<T> targetClass, final String sql, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Stream<T>>() {
            @Override
            public Stream<T> call() throws Exception {
                return sqlExecutor.streamAll(targetClass, sql, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Remember to close the returned <code>Stream</code> to close the underlying <code>ResultSet</code> list.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sql the sql
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Stream<T>> streamAll(final Class<T> targetClass, final String sql, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Stream<T>>() {
            @Override
            public Stream<T> call() throws Exception {
                return sqlExecutor.streamAll(targetClass, sql, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Stream all.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sqls the sqls
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Stream<T>> streamAll(final Class<T> targetClass, final List<String> sqls, final JdbcSettings jdbcSettings,
            final Object... parameters) {
        return asyncExecutor.execute(new Callable<Stream<T>>() {
            @Override
            public Stream<T> call() throws Exception {
                return sqlExecutor.streamAll(targetClass, sqls, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Remember to close the returned <code>Stream</code> to close the underlying <code>ResultSet</code> list.
     *
     * @param <T> the generic type
     * @param targetClass the target class
     * @param sqls the sqls
     * @param statementSetter the statement setter
     * @param jdbcSettings the jdbc settings
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final <T> ContinuableFuture<Stream<T>> streamAll(final Class<T> targetClass, final List<String> sqls, final StatementSetter statementSetter,
            final JdbcSettings jdbcSettings, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Stream<T>>() {
            @Override
            public Stream<T> call() throws Exception {
                return sqlExecutor.streamAll(targetClass, sqls, statementSetter, jdbcSettings, parameters);
            }
        });
    }

    /**
     * Execute.
     *
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Void> execute(final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                sqlExecutor.execute(sql, parameters);
                return null;
            }
        });
    }

    /**
     * Execute.
     *
     * @param conn the conn
     * @param sql the sql
     * @param parameters the parameters
     * @return the continuable future
     */
    @SafeVarargs
    public final ContinuableFuture<Void> execute(final Connection conn, final String sql, final Object... parameters) {
        return asyncExecutor.execute(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                sqlExecutor.execute(conn, sql, parameters);
                return null;
            }
        });
    }
}
