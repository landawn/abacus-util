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

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import com.landawn.abacus.util.N;
import com.landawn.abacus.util.WD;

/**
 *
 * @author Haiyang Li
 * @param <T>
 * @since 0.8
 */
public abstract class AbstractArrayType<T> extends AbstractType<T> {

    protected AbstractArrayType(String typeName) {
        super(typeName);
    }

    /**
     * Checks if is array.
     *
     * @return true, if is array
     */
    @Override
    public boolean isArray() {
        return true;
    }

    /**
     * Gets the serialization type.
     *
     * @return
     */
    @Override
    public SerializationType getSerializationType() {
        return isSerializable() ? SerializationType.SERIALIZABLE : SerializationType.ARRAY;
    }

    /**
     * Array 2 collection.
     *
     * @param <E>
     * @param collClass
     * @param x
     * @return
     */
    @Override
    public <E> Collection<E> array2Collection(Class<?> collClass, T x) {
        if (x == null) {
            return null;
        }

        Collection<E> c = newCollection(collClass, Array.getLength(x));

        return array2Collection(c, x);
    }

    /**
     *
     * @param <E>
     * @param cls
     * @param len
     * @return
     */
    protected static <E> Collection<E> newCollection(Class<?> cls, int len) {
        Collection<E> c = null;

        if (Modifier.isAbstract(cls.getModifiers())) {
            if (List.class.isAssignableFrom(cls)) {
                c = new ArrayList<>(len);
            } else if (Set.class.isAssignableFrom(cls)) {
                c = N.newHashSet(len);
            } else if (Queue.class.isAssignableFrom(cls)) {
                c = new ArrayDeque<>(len);
            } else {
                c = (Collection<E>) N.newInstance(cls);
            }
        } else {
            c = (Collection<E>) N.newInstance(cls);
        }

        return c;
    }

    /**
     *
     * @param str
     * @return
     */
    protected static String[] split(String str) {
        String[] strs = str.split(ELEMENT_SEPARATOR);

        if (strs.length == 1) {
            if (str.indexOf(WD._COMMA) > 0) {
                strs = str.split(WD.COMMA);
            }
        }

        int len = strs.length;

        if (len > 0) {
            int lastIndex = len - 1;

            if ((strs[0].charAt(0) == WD._BRACKET_L) && (strs[lastIndex].charAt(strs[lastIndex].length() - 1) == WD._BRACKET_R)) {
                strs[0] = strs[0].substring(1);

                strs[lastIndex] = strs[lastIndex].substring(0, strs[lastIndex].length() - 1);
            }
        }

        return strs;
    }
}
