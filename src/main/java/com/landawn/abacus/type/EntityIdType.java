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

import com.landawn.abacus.EntityId;
import com.landawn.abacus.util.N;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public class EntityIdType extends AbstractType<EntityId> {

    public static final String ENTITY_ID = EntityId.class.getSimpleName();

    private final Class<EntityId> typeClass;

    EntityIdType() {
        super(ENTITY_ID);

        this.typeClass = EntityId.class;
    }

    @Override
    public Class<EntityId> clazz() {
        return typeClass;
    }

    /**
     * Checks if is entity id.
     *
     * @return true, if is entity id
     */
    @Override
    public boolean isEntityId() {
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
        return SerializationType.ENTITY_ID;
    }

    /**
     *
     * @param x
     * @return
     */
    @Override
    public String stringOf(EntityId x) {
        return (x == null) ? null : Utils.jsonParser.serialize(x, Utils.jsc);
    }

    /**
     *
     * @param str
     * @return
     */
    @Override
    public EntityId valueOf(String str) {
        return (N.isNullOrEmpty(str)) ? null : (EntityId) Utils.jsonParser.deserialize(typeClass, str);
    }
}
