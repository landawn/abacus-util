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

import com.landawn.abacus.util.N;

/**
 *
 * @author Haiyang Li
 * @param <T>
 * @since 0.8
 */
public final class EntityType<T> extends AbstractType<T> {

    private final Class<T> typeClass;

    EntityType(Class<T> cls) {
        super(TypeFactory.getClassName(cls));
        this.typeClass = cls;
    }

    @Override
    public Class<T> clazz() {
        return typeClass;
    }

    /**
     * Checks if is entity.
     *
     * @return true, if is entity
     */
    @Override
    public boolean isEntity() {
        return true;
    }

    /**
     * Checks if is serializable.
     *
     * @return true, if is serializable
     */
    @Override
    public boolean isSerializable() {
        return false;
    }

    /**
     * Gets the serialization type.
     *
     * @return
     */
    @Override
    public SerializationType getSerializationType() {
        return SerializationType.ENTITY;
    }

    /**
     *
     * @param x
     * @return
     */
    @Override
    public String stringOf(T x) {
        return (x == null) ? null : Utils.jsonParser.serialize(x, Utils.jsc);
    }

    /**
     *
     * @param st
     * @return
     */
    @Override
    public T valueOf(String st) {
        return (N.isNullOrEmpty(st)) ? null : (T) Utils.jsonParser.deserialize(typeClass, st);
    }
}
