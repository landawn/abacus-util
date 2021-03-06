/*
 * Copyright (C) 2016 HaiYang Li
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

package com.landawn.abacus.util.function;

import com.landawn.abacus.util.Throwables;

/**
 * 
 * @since 0.8
 * 
 * @author Haiyang Li
 */
public interface ToCharFunction<T> extends Throwables.ToCharFunction<T, RuntimeException> {

    static final ToCharFunction<Character> UNBOX = new ToCharFunction<Character>() {
        @Override
        public char applyAsChar(Character value) {
            return value == null ? 0 : value.charValue();
        }
    };

    @Override
    char applyAsChar(T value);
}
