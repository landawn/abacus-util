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

package com.landawn.abacus.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class InvalidTransactionIdException.
 *
 * @author Haiyang Li
 * @since 0.8
 */
public class InvalidTransactionIdException extends AbacusException {
    /**
     * Field serialVersionUID. (value is -8260899582499449660L)
     */
    private static final long serialVersionUID = -8260899582499449660L;

    /**
     * Constructor for InvalidTransactionIdException.
     */
    public InvalidTransactionIdException() {
        super();
    }

    /**
     * Constructor for InvalidTransactionIdException.
     *
     * @param message
     */
    public InvalidTransactionIdException(String message) {
        super(message);
    }

    /**
     * Constructor for InvalidTransactionIdException.
     *
     * @param message
     * @param cause
     */
    public InvalidTransactionIdException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for InvalidTransactionIdException.
     *
     * @param cause
     */
    public InvalidTransactionIdException(Throwable cause) {
        super(cause);
    }
}
