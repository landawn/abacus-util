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
package com.landawn.abacus.util;

import com.landawn.abacus.annotation.Internal;

/**
 * The Class Internals.
 */
@Internal
final class Internals {

    /** The Constant POOL_SIZE. */
    static final int POOL_SIZE;

    static {
        int multi = (int) (Runtime.getRuntime().maxMemory() / ((1024 * 1024) * 256));

        POOL_SIZE = Math.max(1000, Math.min(1000 * multi, 8192));
    }

    /**
     * Instantiates a new internals.
     */
    private Internals() {
        // singleton.
    }
}