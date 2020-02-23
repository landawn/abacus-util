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

package com.landawn.abacus.condition;

/**
 * The Class Intersect.
 *
 * @author Haiyang Li
 * @since 0.8
 */
public class Intersect extends Clause {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -6777653082079650004L;

    /**
     * Instantiates a new intersect.
     */
    // For Kryo
    Intersect() {
    }

    /**
     * Constructor for Intersect.
     *
     * @param condition
     */
    public Intersect(SubQuery condition) {
        super(Operator.INTERSECT, condition);
    }
}
