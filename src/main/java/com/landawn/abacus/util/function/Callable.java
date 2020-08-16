/*
 * Copyright (C) 2018 HaiYang Li
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

import com.landawn.abacus.util.Fn;
import com.landawn.abacus.util.Throwables;

/**
 *
 * @since 1.2
 *
 * @author Haiyang Li
 */
public interface Callable<R> extends java.util.concurrent.Callable<R>, Throwables.Callable<R, RuntimeException> {

    @Override
    R call();

    default Runnable toRunnable() {
        return Fn.c2r(this);
    }

    default <E extends Throwable> Throwables.Callable<R, E> toThrowable() {
        return (Throwables.Callable<R, E>) this;
    }
}
