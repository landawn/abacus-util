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

package com.landawn.abacus.http;

import java.io.Closeable;
import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.landawn.abacus.exception.UncheckedIOException;
import com.landawn.abacus.util.AsyncExecutor;
import com.landawn.abacus.util.ContinuableFuture;
import com.landawn.abacus.util.IOUtil;
import com.landawn.abacus.util.N;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractHttpClient.
 *
 * @author Haiyang Li
 * @since 0.8
 */
public abstract class AbstractHttpClient implements Closeable {

    /** The Constant DEFAULT_MAX_CONNECTION. */
    // ...
    public static final int DEFAULT_MAX_CONNECTION = 16;

    /** Unit is milliseconds. */
    public static final int DEFAULT_CONNECTION_TIMEOUT = 8000;

    /** The Constant DEFAULT_READ_TIMEOUT. */
    public static final int DEFAULT_READ_TIMEOUT = 16000;

    /** The Constant asyncExecutor. */
    // for static asynchronization operation.
    protected static final AsyncExecutor asyncExecutor = new AsyncExecutor(Math.min(8, IOUtil.CPU_CORES), 256, 180L, TimeUnit.SECONDS);

    /** The url. */
    // ...
    protected final String _url;

    /** The max connection. */
    protected final int _maxConnection;

    /** The conn timeout. */
    protected final long _connTimeout;

    /** The read timeout. */
    protected final long _readTimeout;

    /** The settings. */
    protected final HttpSettings _settings;

    /** The async executor. */
    protected final AsyncExecutor _asyncExecutor;

    /**
     * Instantiates a new abstract http client.
     *
     * @param url
     */
    protected AbstractHttpClient(String url) {
        this(url, DEFAULT_MAX_CONNECTION, DEFAULT_CONNECTION_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * Instantiates a new abstract http client.
     *
     * @param url
     * @param maxConnection
     */
    protected AbstractHttpClient(String url, int maxConnection) {
        this(url, maxConnection, DEFAULT_CONNECTION_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * Instantiates a new abstract http client.
     *
     * @param url
     * @param maxConnection
     * @param connTimeout
     * @param readTimeout
     */
    protected AbstractHttpClient(String url, int maxConnection, long connTimeout, long readTimeout) {
        this(url, maxConnection, connTimeout, readTimeout, null);
    }

    /**
     * Instantiates a new abstract http client.
     *
     * @param url
     * @param maxConnection
     * @param connTimeout
     * @param readTimeout
     * @param settings
     * @throws UncheckedIOException the unchecked IO exception
     */
    protected AbstractHttpClient(String url, int maxConnection, long connTimeout, long readTimeout, HttpSettings settings) throws UncheckedIOException {
        if (N.isNullOrEmpty(url)) {
            throw new IllegalArgumentException("url can't be null or empty");
        }

        if ((maxConnection < 0) || (connTimeout < 0) || (readTimeout < 0)) {
            throw new IllegalArgumentException(
                    "maxConnection, connTimeout or readTimeout can't be less than 0: " + maxConnection + ", " + connTimeout + ", " + readTimeout);
        }

        this._url = url;
        this._maxConnection = (maxConnection == 0) ? DEFAULT_MAX_CONNECTION : maxConnection;
        this._connTimeout = (connTimeout == 0) ? DEFAULT_CONNECTION_TIMEOUT : connTimeout;
        this._readTimeout = (readTimeout == 0) ? DEFAULT_READ_TIMEOUT : readTimeout;
        this._settings = settings == null ? HttpSettings.create() : settings;

        _asyncExecutor = new AsyncExecutor(Math.min(8, this._maxConnection), this._maxConnection, 300L, TimeUnit.SECONDS);
    }

    /**
     * Url.
     *
     * @return
     */
    public String url() {
        return _url;
    }

    /**
     * Gets the.
     *
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String get() throws UncheckedIOException {
        return get(String.class);
    }

    /**
     * Gets the.
     *
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String get(final HttpSettings settings) throws UncheckedIOException {
        return get(String.class, settings);
    }

    /**
     * Gets the.
     *
     * @param queryParameters
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String get(final Object queryParameters) throws UncheckedIOException {
        return get(String.class, queryParameters);
    }

    /**
     * Gets the.
     *
     * @param queryParameters
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String get(final Object queryParameters, final HttpSettings settings) throws UncheckedIOException {
        return get(String.class, queryParameters, settings);
    }

    /**
     * Gets the.
     *
     * @param <T>
     * @param resultClass
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public <T> T get(final Class<T> resultClass) throws UncheckedIOException {
        return get(resultClass, null, _settings);
    }

    /**
     * Gets the.
     *
     * @param <T>
     * @param resultClass
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public <T> T get(final Class<T> resultClass, final HttpSettings settings) throws UncheckedIOException {
        return get(resultClass, null, settings);
    }

    /**
     * Gets the.
     *
     * @param <T>
     * @param resultClass
     * @param queryParameters
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public <T> T get(final Class<T> resultClass, final Object queryParameters) throws UncheckedIOException {
        return get(resultClass, queryParameters, _settings);
    }

    /**
     * Gets the.
     *
     * @param <T>
     * @param resultClass
     * @param queryParameters
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public <T> T get(final Class<T> resultClass, final Object queryParameters, final HttpSettings settings) throws UncheckedIOException {
        return execute(resultClass, HttpMethod.GET, queryParameters, settings);
    }

    /**
     * Async get.
     *
     * @return
     */
    public ContinuableFuture<String> asyncGet() {
        return asyncGet(String.class);
    }

    /**
     * Async get.
     *
     * @param settings
     * @return
     */
    public ContinuableFuture<String> asyncGet(final HttpSettings settings) {
        return asyncGet(String.class, settings);
    }

    /**
     * Async get.
     *
     * @param queryParameters
     * @return
     */
    public ContinuableFuture<String> asyncGet(final Object queryParameters) {
        return asyncGet(String.class, queryParameters);
    }

    /**
     * Async get.
     *
     * @param queryParameters
     * @param settings
     * @return
     */
    public ContinuableFuture<String> asyncGet(final Object queryParameters, final HttpSettings settings) {
        return asyncGet(String.class, queryParameters, settings);
    }

    /**
     * Async get.
     *
     * @param <T>
     * @param resultClass
     * @return
     */
    public <T> ContinuableFuture<T> asyncGet(final Class<T> resultClass) {
        return asyncGet(resultClass, null, _settings);
    }

    /**
     * Async get.
     *
     * @param <T>
     * @param resultClass
     * @param settings
     * @return
     */
    public <T> ContinuableFuture<T> asyncGet(final Class<T> resultClass, final HttpSettings settings) {
        return asyncGet(resultClass, null, settings);
    }

    /**
     * Async get.
     *
     * @param <T>
     * @param resultClass
     * @param queryParameters
     * @return
     */
    public <T> ContinuableFuture<T> asyncGet(final Class<T> resultClass, final Object queryParameters) {
        return asyncGet(resultClass, queryParameters, _settings);
    }

    /**
     * Async get.
     *
     * @param <T>
     * @param resultClass
     * @param queryParameters
     * @param settings
     * @return
     */
    public <T> ContinuableFuture<T> asyncGet(final Class<T> resultClass, final Object queryParameters, final HttpSettings settings) {
        return asyncExecute(resultClass, HttpMethod.GET, queryParameters, settings);
    }

    /**
     * Delete.
     *
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String delete() throws UncheckedIOException {
        return delete(String.class);
    }

    /**
     * Delete.
     *
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String delete(final HttpSettings settings) throws UncheckedIOException {
        return delete(String.class, settings);
    }

    /**
     * Delete.
     *
     * @param queryParameters
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String delete(final Object queryParameters) throws UncheckedIOException {
        return delete(String.class, queryParameters);
    }

    /**
     * Delete.
     *
     * @param queryParameters
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String delete(final Object queryParameters, final HttpSettings settings) throws UncheckedIOException {
        return delete(String.class, queryParameters, settings);
    }

    /**
     * Delete.
     *
     * @param <T>
     * @param resultClass
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public <T> T delete(final Class<T> resultClass) throws UncheckedIOException {
        return delete(resultClass, null, _settings);
    }

    /**
     * Delete.
     *
     * @param <T>
     * @param resultClass
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public <T> T delete(final Class<T> resultClass, final HttpSettings settings) throws UncheckedIOException {
        return delete(resultClass, null, settings);
    }

    /**
     * Delete.
     *
     * @param <T>
     * @param resultClass
     * @param queryParameters
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public <T> T delete(final Class<T> resultClass, final Object queryParameters) throws UncheckedIOException {
        return delete(resultClass, queryParameters, _settings);
    }

    /**
     * Delete.
     *
     * @param <T>
     * @param resultClass
     * @param queryParameters
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public <T> T delete(final Class<T> resultClass, final Object queryParameters, final HttpSettings settings) throws UncheckedIOException {
        return execute(resultClass, HttpMethod.DELETE, queryParameters, settings);
    }

    /**
     * Async delete.
     *
     * @return
     */
    public ContinuableFuture<String> asyncDelete() {
        return asyncDelete(String.class);
    }

    /**
     * Async delete.
     *
     * @param settings
     * @return
     */
    public ContinuableFuture<String> asyncDelete(final HttpSettings settings) {
        return asyncDelete(String.class, settings);
    }

    /**
     * Async delete.
     *
     * @param queryParameters
     * @return
     */
    public ContinuableFuture<String> asyncDelete(final Object queryParameters) {
        return asyncDelete(String.class, queryParameters);
    }

    /**
     * Async delete.
     *
     * @param queryParameters
     * @param settings
     * @return
     */
    public ContinuableFuture<String> asyncDelete(final Object queryParameters, final HttpSettings settings) {
        return asyncDelete(String.class, queryParameters, settings);
    }

    /**
     * Async delete.
     *
     * @param <T>
     * @param resultClass
     * @return
     */
    public <T> ContinuableFuture<T> asyncDelete(final Class<T> resultClass) {
        return asyncDelete(resultClass, null, _settings);
    }

    /**
     * Async delete.
     *
     * @param <T>
     * @param resultClass
     * @param settings
     * @return
     */
    public <T> ContinuableFuture<T> asyncDelete(final Class<T> resultClass, final HttpSettings settings) {
        return asyncDelete(resultClass, null, settings);
    }

    /**
     * Async delete.
     *
     * @param <T>
     * @param resultClass
     * @param queryParameters
     * @return
     */
    public <T> ContinuableFuture<T> asyncDelete(final Class<T> resultClass, final Object queryParameters) {
        return asyncDelete(resultClass, queryParameters, _settings);
    }

    /**
     * Async delete.
     *
     * @param <T>
     * @param resultClass
     * @param queryParameters
     * @param settings
     * @return
     */
    public <T> ContinuableFuture<T> asyncDelete(final Class<T> resultClass, final Object queryParameters, final HttpSettings settings) {
        return asyncExecute(resultClass, HttpMethod.DELETE, queryParameters, settings);
    }

    /**
     * Post.
     *
     * @param request
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String post(final Object request) throws UncheckedIOException {
        return post(String.class, request);
    }

    /**
     * Post.
     *
     * @param request
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String post(final Object request, final HttpSettings settings) throws UncheckedIOException {
        return post(String.class, request, settings);
    }

    /**
     * Post.
     *
     * @param <T>
     * @param resultClass
     * @param request
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public <T> T post(final Class<T> resultClass, final Object request) throws UncheckedIOException {
        return post(resultClass, request, _settings);
    }

    /**
     * Post.
     *
     * @param <T>
     * @param resultClass
     * @param request
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public <T> T post(final Class<T> resultClass, final Object request, final HttpSettings settings) throws UncheckedIOException {
        return execute(resultClass, HttpMethod.POST, request, settings);
    }

    /**
     * Async post.
     *
     * @param request
     * @return
     */
    public ContinuableFuture<String> asyncPost(final Object request) {
        return asyncPost(String.class, request);
    }

    /**
     * Async post.
     *
     * @param request
     * @param settings
     * @return
     */
    public ContinuableFuture<String> asyncPost(final Object request, final HttpSettings settings) {
        return asyncPost(String.class, request, settings);
    }

    /**
     * Async post.
     *
     * @param <T>
     * @param resultClass
     * @param request
     * @return
     */
    public <T> ContinuableFuture<T> asyncPost(final Class<T> resultClass, final Object request) {
        return asyncPost(resultClass, request, _settings);
    }

    /**
     * Async post.
     *
     * @param <T>
     * @param resultClass
     * @param request
     * @param settings
     * @return
     */
    public <T> ContinuableFuture<T> asyncPost(final Class<T> resultClass, final Object request, final HttpSettings settings) {
        return asyncExecute(resultClass, HttpMethod.POST, request, settings);
    }

    /**
     * Put.
     *
     * @param request
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String put(final Object request) throws UncheckedIOException {
        return put(String.class, request);
    }

    /**
     * Put.
     *
     * @param request
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String put(final Object request, final HttpSettings settings) throws UncheckedIOException {
        return put(String.class, request, settings);
    }

    /**
     * Put.
     *
     * @param <T>
     * @param resultClass
     * @param request
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public <T> T put(final Class<T> resultClass, final Object request) throws UncheckedIOException {
        return put(resultClass, request, _settings);
    }

    /**
     * Put.
     *
     * @param <T>
     * @param resultClass
     * @param request
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public <T> T put(final Class<T> resultClass, final Object request, final HttpSettings settings) throws UncheckedIOException {
        return execute(resultClass, HttpMethod.PUT, request, settings);
    }

    /**
     * Async put.
     *
     * @param request
     * @return
     */
    public ContinuableFuture<String> asyncPut(final Object request) {
        return asyncPut(String.class, request);
    }

    /**
     * Async put.
     *
     * @param request
     * @param settings
     * @return
     */
    public ContinuableFuture<String> asyncPut(final Object request, final HttpSettings settings) {
        return asyncPut(String.class, request, settings);
    }

    /**
     * Async put.
     *
     * @param <T>
     * @param resultClass
     * @param request
     * @return
     */
    public <T> ContinuableFuture<T> asyncPut(final Class<T> resultClass, final Object request) {
        return asyncPut(resultClass, request, _settings);
    }

    /**
     * Async put.
     *
     * @param <T>
     * @param resultClass
     * @param request
     * @param settings
     * @return
     */
    public <T> ContinuableFuture<T> asyncPut(final Class<T> resultClass, final Object request, final HttpSettings settings) {
        return asyncExecute(resultClass, HttpMethod.PUT, request, settings);
    }

    /**
     * Execute.
     *
     * @param httpMethod
     * @param request
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String execute(final HttpMethod httpMethod, final Object request) throws UncheckedIOException {
        return execute(String.class, httpMethod, request);
    }

    /**
     * Execute.
     *
     * @param httpMethod
     * @param request
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public String execute(final HttpMethod httpMethod, final Object request, final HttpSettings settings) throws UncheckedIOException {
        return execute(String.class, httpMethod, request, settings);
    }

    /**
     * Execute.
     *
     * @param <T>
     * @param resultClass
     * @param httpMethod
     * @param request
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public <T> T execute(final Class<T> resultClass, final HttpMethod httpMethod, final Object request) throws UncheckedIOException {
        return execute(resultClass, httpMethod, request, _settings);
    }

    /**
     * Write the specified <code>request</code> to request body.
     *
     * @param <T>
     * @param resultClass
     * @param httpMethod
     * @param request can be String/Map/Entity/InputStream/Reader...
     * @param settings
     * @return
     * @throws UncheckedIOException the unchecked IO exception
     */
    public abstract <T> T execute(final Class<T> resultClass, final HttpMethod httpMethod, final Object request, final HttpSettings settings)
            throws UncheckedIOException;

    /**
     * Execute.
     *
     * @param output write the InputStream in the response to this specified File.
     * @param httpMethod
     * @param request
     * @param settings
     * @throws UncheckedIOException the unchecked IO exception
     */
    public abstract void execute(final File output, final HttpMethod httpMethod, final Object request, final HttpSettings settings) throws UncheckedIOException;

    /**
     * Execute.
     *
     * @param output write the InputStream in the response to this specified OutputStream.
     * @param httpMethod
     * @param request
     * @param settings
     * @throws UncheckedIOException the unchecked IO exception
     */
    public abstract void execute(final OutputStream output, final HttpMethod httpMethod, final Object request, final HttpSettings settings)
            throws UncheckedIOException;

    /**
     * Execute.
     *
     * @param output write the InputStream in the response to this specified Writer.
     * @param httpMethod
     * @param request
     * @param settings
     * @throws UncheckedIOException the unchecked IO exception
     */
    public abstract void execute(final Writer output, final HttpMethod httpMethod, final Object request, final HttpSettings settings)
            throws UncheckedIOException;

    /**
     * Async execute.
     *
     * @param httpMethod
     * @param request
     * @return
     */
    public ContinuableFuture<String> asyncExecute(final HttpMethod httpMethod, final Object request) {
        return asyncExecute(String.class, httpMethod, request);
    }

    /**
     * Async execute.
     *
     * @param httpMethod
     * @param request
     * @param settings
     * @return
     */
    public ContinuableFuture<String> asyncExecute(final HttpMethod httpMethod, final Object request, final HttpSettings settings) {
        return asyncExecute(String.class, httpMethod, request, settings);
    }

    /**
     * Async execute.
     *
     * @param <T>
     * @param resultClass
     * @param httpMethod
     * @param request
     * @return
     */
    public <T> ContinuableFuture<T> asyncExecute(final Class<T> resultClass, final HttpMethod httpMethod, final Object request) {
        return asyncExecute(resultClass, httpMethod, request, _settings);
    }

    /**
     * Async execute.
     *
     * @param <T>
     * @param resultClass
     * @param httpMethod
     * @param request
     * @param settings
     * @return
     */
    public <T> ContinuableFuture<T> asyncExecute(final Class<T> resultClass, final HttpMethod httpMethod, final Object request, final HttpSettings settings) {
        final Callable<T> cmd = new Callable<T>() {
            @Override
            public T call() throws Exception {
                return execute(resultClass, httpMethod, request, settings);
            }
        };

        return _asyncExecutor.execute(cmd);
    }

    /**
     * Async execute.
     *
     * @param output
     * @param httpMethod
     * @param request
     * @param settings
     * @return
     */
    public ContinuableFuture<Void> asyncExecute(final File output, final HttpMethod httpMethod, final Object request, final HttpSettings settings) {
        final Callable<Void> cmd = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                execute(output, httpMethod, request, settings);

                return null;
            }
        };

        return _asyncExecutor.execute(cmd);
    }

    /**
     * Async execute.
     *
     * @param output
     * @param httpMethod
     * @param request
     * @param settings
     * @return
     */
    public ContinuableFuture<Void> asyncExecute(final OutputStream output, final HttpMethod httpMethod, final Object request, final HttpSettings settings) {
        final Callable<Void> cmd = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                execute(output, httpMethod, request, settings);

                return null;
            }
        };

        return _asyncExecutor.execute(cmd);
    }

    /**
     * Async execute.
     *
     * @param output
     * @param httpMethod
     * @param request
     * @param settings
     * @return
     */
    public ContinuableFuture<Void> asyncExecute(final Writer output, final HttpMethod httpMethod, final Object request, final HttpSettings settings) {
        final Callable<Void> cmd = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                execute(output, httpMethod, request, settings);

                return null;
            }
        };

        return _asyncExecutor.execute(cmd);
    }

    /**
     * Close.
     */
    @Override
    public void close() {
        // do nothing.
    }

    /**
     * Checks if is one way request.
     *
     * @param settings
     * @return true, if is one way request
     */
    protected boolean isOneWayRequest(HttpSettings settings) {
        return _settings.isOneWayRequest() || ((settings != null) && settings.isOneWayRequest());
    }

    /**
     * Gets the content format.
     *
     * @param settings
     * @return
     */
    protected ContentFormat getContentFormat(HttpSettings settings) {
        ContentFormat contentFormat = null;

        if (settings != null) {
            contentFormat = settings.getContentFormat();

            if (contentFormat == null) {
                String contentType = (String) settings.headers().get(HttpHeaders.Names.CONTENT_TYPE);
                String contentEncoding = (String) settings.headers().get(HttpHeaders.Names.CONTENT_ENCODING);

                contentFormat = HTTP.getContentFormat(contentType, contentEncoding);
            }
        }

        if (contentFormat == null) {
            contentFormat = _settings.getContentFormat();

            if (contentFormat == null) {
                String contentType = (String) _settings.headers().get(HttpHeaders.Names.CONTENT_TYPE);
                String contentEncoding = (String) _settings.headers().get(HttpHeaders.Names.CONTENT_ENCODING);

                contentFormat = HTTP.getContentFormat(contentType, contentEncoding);
            }
        }

        return contentFormat;
    }

    /**
     * Gets the content type.
     *
     * @param settings
     * @return
     */
    protected String getContentType(HttpSettings settings) {
        String contentType = null;

        if (settings != null) {
            if (settings.getContentFormat() != null) {
                contentType = HTTP.getContentType(settings.getContentFormat());
            } else {
                contentType = (String) settings.headers().get(HttpHeaders.Names.CONTENT_TYPE);
            }
        }

        if (N.isNullOrEmpty(contentType)) {
            if (_settings.getContentFormat() != null) {
                contentType = HTTP.getContentType(_settings.getContentFormat());
            } else {
                contentType = (String) _settings.headers().get(HttpHeaders.Names.CONTENT_TYPE);
            }
        }

        return contentType;
    }

    /**
     * Gets the content encoding.
     *
     * @param settings
     * @return
     */
    protected String getContentEncoding(HttpSettings settings) {
        String contentEncoding = null;

        if (settings != null) {
            if (settings.getContentFormat() != null) {
                contentEncoding = HTTP.getContentEncoding(settings.getContentFormat());
            } else {
                contentEncoding = (String) settings.headers().get(HttpHeaders.Names.CONTENT_ENCODING);
            }
        }

        if (N.isNullOrEmpty(contentEncoding)) {
            if (_settings.getContentFormat() != null) {
                contentEncoding = HTTP.getContentEncoding(_settings.getContentFormat());
            } else {
                contentEncoding = (String) _settings.headers().get(HttpHeaders.Names.CONTENT_ENCODING);
            }
        }

        return contentEncoding;
    }
}
