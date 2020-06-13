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

package com.landawn.abacus.util;

/**
 * The Enum DayOfWeek.
 *
 * @author Haiyang Li
 * @since 0.8
 */
public enum DayOfWeek {

    /** The sunday. */
    SUNDAY(0),
    /** The monday. */
    MONDAY(1),
    /** The tuesday. */
    TUESDAY(2),
    /** The wednesday. */
    WEDNESDAY(3),
    /** The thursday. */
    THURSDAY(4),
    /** The friday. */
    FRIDAY(5),
    /** The saturday. */
    SATURDAY(6);

    /** The int value. */
    private int intValue;

    /**
     * Instantiates a new day of week.
     *
     * @param intValue
     */
    DayOfWeek(int intValue) {
        this.intValue = intValue;
    }

    /**
     *
     * @return
     */
    public int intValue() {
        return intValue;
    }

    /**
     *
     * @param intValue
     * @return
     */
    public static DayOfWeek valueOf(int intValue) {
        switch (intValue) {
            case 0:
                return SUNDAY;

            case 1:
                return MONDAY;

            case 2:
                return TUESDAY;

            case 3:
                return WEDNESDAY;

            case 4:
                return THURSDAY;

            case 5:
                return FRIDAY;

            case 6:
                return SATURDAY;

            default:
                throw new IllegalArgumentException("No mapping instance found by int value: " + intValue);
        }
    }
}
