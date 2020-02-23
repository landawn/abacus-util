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

package com.landawn.abacus.logging;

import java.util.HashMap;
import java.util.Map;

/**
 * A factory for creating Logger objects.
 *
 * @author Haiyang Li
 * @since 0.8
 */
public final class LoggerFactory {

    /** The Constant JAVA_VENDOR. */
    private static final String JAVA_VENDOR = System.getProperty("java.vendor");

    /** The Constant JAVA_VM_VENDOR. */
    private static final String JAVA_VM_VENDOR = System.getProperty("java.vm.vendor");

    /** The Constant IS_ANDROID_PLATFORM. */
    private static final boolean IS_ANDROID_PLATFORM = JAVA_VENDOR.toUpperCase().contains("ANDROID") || JAVA_VM_VENDOR.contains("ANDROID");

    /** The Constant jdkLogger. */
    private static final Logger jdkLogger = new JDKLogger(LoggerFactory.class.getName());

    /** The Constant namedLoggers. */
    private static final Map<String, Logger> namedLoggers = new HashMap<String, Logger>();

    /** The log type. */
    private static volatile int logType = 0;

    /** The initialized. */
    private static volatile boolean initialized = false;

    /**
     * Gets the logger.
     *
     * @param clazz
     * @return
     */
    public static synchronized Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    /**
     * Gets the logger.
     *
     * @param name
     * @return
     */
    public static synchronized Logger getLogger(String name) {
        Logger logger = namedLoggers.get(name);

        if (logger == null) {
            switch (logType) {
                case 0:
                    if (IS_ANDROID_PLATFORM) {
                        try {
                            logger = new AndroidLogger(name);

                            if (initialized == false) {
                                jdkLogger.info("Initialized with Android Logger");
                            }

                            logType = 0;
                            initialized = true;

                            break;
                        } catch (Throwable e) {
                            // ignore
                        }
                    }

                case 1:
                    if (logger == null) {
                        try {
                            logger = new SLF4JLogger(name);

                            if (initialized == false) {
                                jdkLogger.info("Initialized with SLF4J Logger");
                            }

                            logType = 1;
                            initialized = true;

                            break;
                        } catch (Throwable e) {
                            // ignore
                        }
                    }

                case 2:
                    if (logger == null) {
                        try {
                            logger = new Log4Jv2Logger(name);

                            if (initialized == false) {
                                jdkLogger.info("Initialized with Log4j v2 Logger");
                            }

                            logType = 2;
                            initialized = true;

                            break;
                        } catch (Throwable e) {
                            // ignore
                        }
                    }

                case 3:
                    if (logger == null) {
                        logger = new JDKLogger(name);

                        if (initialized == false) {
                            jdkLogger.info("Initialized with JDK Logger");
                        }

                        logType = 3;
                        initialized = true;

                        break;
                    }
            }

            namedLoggers.put(name, logger);
        }

        return logger;
    }
}
