/*
 * Copyright (C) 2019 HaiYang Li
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

package com.landawn.abacus.type;

/**
 *
 * @author Haiyang Li
 * @since 1.9.13
 */
public class StringBuilderType extends AbstractCharSequenceType<StringBuilder> {

    public static final String STRING_BUILDER = StringBuilder.class.getSimpleName();

    StringBuilderType() {
        super(STRING_BUILDER);
    }

    @Override
    public Class<StringBuilder> clazz() {
        return StringBuilder.class;
    }

    @Override
    public String stringOf(StringBuilder x) {
        return x == null ? null : x.toString();
    }

    @Override
    public StringBuilder valueOf(String str) {
        return str == null ? null : new StringBuilder(str);
    }
}
