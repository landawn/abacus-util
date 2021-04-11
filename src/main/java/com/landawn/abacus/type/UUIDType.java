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

package com.landawn.abacus.type;

import java.util.UUID;

import com.landawn.abacus.util.N;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public class UUIDType extends AbstractType<UUID> {

    public static final String UUID = "UUID";

    UUIDType() {
        super(UUID);
    }

    @Override
    public Class<UUID> clazz() {
        return UUID.class;
    }

    /**
     *
     * @param x
     * @return
     */
    @Override
    public String stringOf(UUID x) {
        return x == null ? null : x.toString();
    }

    /**
     *
     * @param str
     * @return
     */
    @Override
    public UUID valueOf(String str) {
        return N.isNullOrEmpty(str) ? null : java.util.UUID.fromString(str);
    }
}
