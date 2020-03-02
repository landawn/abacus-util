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

/**
 * 
 * @author haiyangl
 *
 */
public enum ContentFormat {
    NONE("", ""), //
    JSON("application/json", ""), //
    JSON_LZ4("application/json", "lz4"), //
    JSON_SNAPPY("application/json", "snappy"), //
    JSON_GZIP("application/json", "gzip"), //
    XML("application/xml", ""), //
    XML_LZ4("application/xml", "lz4"), //
    XML_SNAPPY("application/xml", "snappy"), //
    XML_GZIP("application/xml", "gzip"), //
    FormUrlEncoded("application/x-www-form-urlencoded", ""), //
    KRYO("", "kryo"), //
    LZ4("", "lz4"), //
    SNAPPY("", "snappy"), //
    GZIP("", "gzip");

    private final String contentType;
    private final String contentEncoding;

    ContentFormat(final String contentType, final String contentEncoding) {
        this.contentType = contentType;
        this.contentEncoding = contentEncoding;
    }

    public String contentType() {
        return contentType;
    }

    public String contentEncoding() {
        return contentEncoding;
    }
}
