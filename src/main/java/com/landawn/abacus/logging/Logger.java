/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.landawn.abacus.logging;

import com.landawn.abacus.util.function.Supplier;

// TODO: Auto-generated Javadoc
/**
 * The org.slf4j.Logger interface is the main user entry point of SLF4J API. It is expected that logging takes place
 * through concrete implementations of this interface.
 * 
 * <h3>Typical usage pattern:</h3>
 * 
 * <pre>
 * import org.slf4j.Logger;
 * import org.slf4j.LoggerFactory;
 * 
 * public class Wombat {
 * 
 *   <span style="color:green">final static Logger logger = LoggerFactory.getLogger(Wombat.class);</span>
 *   Integer t;
 *   Integer oldT;
 * 
 *   public void setTemperature(Integer temperature) {
 *     oldT = t;        
 *     t = temperature;
 *     <span style="color:green">logger.debug("Temperature set to {}. Old temperature was {}.", t, oldT);</span>
 *     if(temperature.intValue() > 50) {
 *       <span style="color:green">logger.info("Temperature has risen above 50 degrees.");</span>
 *     }
 *   }
 * }
 * </pre>
 * 
 * 
 * 
 * @author Ceki G&uuml;lc&uuml;
 */
public interface Logger {

    /**
     * Case insensitive String constant used to retrieve the name of the root logger.
     * 
     * @since 1.3
     */
    final public String ROOT_LOGGER_NAME = "ROOT";

    /**
     * Gets the name.
     *
     * @return
     */
    public String getName();

    /**
     * Is the logger instance enabled for the TRACE level?.
     *
     * @return True if this Logger is enabled for the TRACE level, false otherwise.
     * @since 1.4
     */
    public boolean isTraceEnabled();

    /**
     * Log a message at the TRACE level.
     * 
     * @param msg
     *            the message string to be logged
     * @since 1.4
     */
    public void trace(String msg);

    /**
     * Trace.
     *
     * @param template
     * @param arg
     */
    public void trace(String template, Object arg);

    /**
     * Trace.
     *
     * @param template
     * @param arg1
     * @param arg2
     */
    public void trace(String template, Object arg1, Object arg2);

    /**
     * Trace.
     *
     * @param template
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public void trace(String template, Object arg1, Object arg2, Object arg3);

    /**
     * Log a message at the TRACE level according to the specified format and arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the TRACE level.
     * </p>
     * 
     * @param template
     *            the template string
     * @param args
     *            an array of arguments
     * 
     * @since 1.4
     * @deprecated {@link #trace(Supplier)} is recommended
     */
    @Deprecated
    public void trace(String template, Object... args);

    /**
     * Log an exception (throwable) at the TRACE level with an accompanying message.
     * 
     * @param msg
     *            the message accompanying the exception
     * @param t
     *            the exception (throwable) to log
     * 
     * @since 1.4
     */
    public void trace(String msg, Throwable t);

    /**
     * Trace.
     *
     * @param t
     * @param msg
     */
    public void trace(Throwable t, String msg);

    /**
     * Trace.
     *
     * @param t
     * @param template
     * @param arg
     */
    public void trace(Throwable t, String template, Object arg);

    /**
     * Trace.
     *
     * @param t
     * @param template
     * @param arg1
     * @param arg2
     */
    public void trace(Throwable t, String template, Object arg1, Object arg2);

    /**
     * Trace.
     *
     * @param t
     * @param template
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public void trace(Throwable t, String template, Object arg1, Object arg2, Object arg3);

    /**
     * Trace.
     *
     * @param supplier
     */
    public void trace(Supplier<String> supplier);

    /**
     * Trace.
     *
     * @param supplier
     * @param t
     * @deprecated replaced by {@link #trace(Throwable, Supplier)}
     */
    @Deprecated
    public void trace(Supplier<String> supplier, Throwable t);

    /**
     * Trace.
     *
     * @param t
     * @param supplier
     */
    public void trace(Throwable t, Supplier<String> supplier);

    /**
     * Is the logger instance enabled for the DEBUG level?.
     *
     * @return True if this Logger is enabled for the DEBUG level, false otherwise.
     */
    public boolean isDebugEnabled();

    /**
     * Log a message at the DEBUG level.
     * 
     * @param msg
     *            the message string to be logged
     * @since 1.4
     */
    public void debug(String msg);

    /**
     * Debug.
     *
     * @param template
     * @param arg
     */
    public void debug(String template, Object arg);

    /**
     * Debug.
     *
     * @param template
     * @param arg1
     * @param arg2
     */
    public void debug(String template, Object arg1, Object arg2);

    /**
     * Debug.
     *
     * @param template
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public void debug(String template, Object arg1, Object arg2, Object arg3);

    /**
     * Log a message at the DEBUG level according to the specified format and arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the      level.
     * </p>
     * 
     * @param template
     *            the template string
     * @param args
     *            an array of arguments
     * 
     * @since 1.4
     * @deprecated {@link #debug(Supplier)} is recommended
     */
    @Deprecated
    public void debug(String template, Object... args);

    /**
     * Log an exception (throwable) at the DEBUG level with an accompanying message.
     * 
     * @param msg
     *            the message accompanying the exception
     * @param t
     *            the exception (throwable) to log
     * 
     * @since 1.4
     */
    public void debug(String msg, Throwable t);

    /**
     * Debug.
     *
     * @param t
     * @param msg
     */
    public void debug(Throwable t, String msg);

    /**
     * Debug.
     *
     * @param t
     * @param template
     * @param arg
     */
    public void debug(Throwable t, String template, Object arg);

    /**
     * Debug.
     *
     * @param t
     * @param template
     * @param arg1
     * @param arg2
     */
    public void debug(Throwable t, String template, Object arg1, Object arg2);

    /**
     * Debug.
     *
     * @param t
     * @param template
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public void debug(Throwable t, String template, Object arg1, Object arg2, Object arg3);

    /**
     * Debug.
     *
     * @param supplier
     */
    public void debug(Supplier<String> supplier);

    /**
     * Debug.
     *
     * @param supplier
     * @param t
     * @deprecated replaced by {@link #debug(Throwable, Supplier)}
     */
    @Deprecated
    public void debug(Supplier<String> supplier, Throwable t);

    /**
     * Debug.
     *
     * @param t
     * @param supplier
     */
    public void debug(Throwable t, Supplier<String> supplier);

    /**
     * Is the logger instance enabled for the INFO level?.
     *
     * @return True if this Logger is enabled for the INFO level, false otherwise.
     */
    public boolean isInfoEnabled();

    /**
     * Log a message at the INFO level.
     * 
     * @param msg
     *            the message string to be logged
     * @since 1.4
     */
    public void info(String msg);

    /**
     * Info.
     *
     * @param template
     * @param arg
     */
    public void info(String template, Object arg);

    /**
     * Info.
     *
     * @param template
     * @param arg1
     * @param arg2
     */
    public void info(String template, Object arg1, Object arg2);

    /**
     * Info.
     *
     * @param template
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public void info(String template, Object arg1, Object arg2, Object arg3);

    /**
     * Log a message at the INFO level according to the specified format and arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the INFO level.
     * </p>
     * 
     * @param template
     *            the template string
     * @param args
     *            an array of arguments
     * 
     * @since 1.4
     * @deprecated {@link #info(Supplier)} is recommended
     */
    @Deprecated
    public void info(String template, Object... args);

    /**
     * Log an exception (throwable) at the INFO level with an accompanying message.
     * 
     * @param msg
     *            the message accompanying the exception
     * @param t
     *            the exception (throwable) to log
     * 
     * @since 1.4
     */
    public void info(String msg, Throwable t);

    /**
     * Info.
     *
     * @param t
     * @param msg
     */
    public void info(Throwable t, String msg);

    /**
     * Info.
     *
     * @param t
     * @param template
     * @param arg
     */
    public void info(Throwable t, String template, Object arg);

    /**
     * Info.
     *
     * @param t
     * @param template
     * @param arg1
     * @param arg2
     */
    public void info(Throwable t, String template, Object arg1, Object arg2);

    /**
     * Info.
     *
     * @param t
     * @param template
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public void info(Throwable t, String template, Object arg1, Object arg2, Object arg3);

    /**
     * Info.
     *
     * @param supplier
     */
    public void info(Supplier<String> supplier);

    /**
     * Info.
     *
     * @param supplier
     * @param t
     * @deprecated replaced by {@link #info(Throwable, Supplier)}
     */
    @Deprecated
    public void info(Supplier<String> supplier, Throwable t);

    /**
     * Info.
     *
     * @param t
     * @param supplier
     */
    public void info(Throwable t, Supplier<String> supplier);

    /**
     * Is the logger instance enabled for the WARN level?.
     *
     * @return True if this Logger is enabled for the WARN level, false otherwise.
     */
    public boolean isWarnEnabled();

    /**
     * Log a message at the WARNING level.
     * 
     * @param msg
     *            the message string to be logged
     * @since 1.4
     */
    public void warn(String msg);

    /**
     * Warn.
     *
     * @param template
     * @param arg
     */
    public void warn(String template, Object arg);

    /**
     * Warn.
     *
     * @param template
     * @param arg1
     * @param arg2
     */
    public void warn(String template, Object arg1, Object arg2);

    /**
     * Warn.
     *
     * @param template
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public void warn(String template, Object arg1, Object arg2, Object arg3);

    /**
     * Log a message at the WARNING level according to the specified format and arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the WARNING level.
     * </p>
     * 
     * @param template
     *            the template string
     * @param args
     *            an array of arguments
     * 
     * @since 1.4
     * @deprecated {@link #warn(Supplier)} is recommended
     */
    @Deprecated
    public void warn(String template, Object... args);

    /**
     * Log an exception (throwable) at the WARNING level with an accompanying message.
     * 
     * @param msg
     *            the message accompanying the exception
     * @param t
     *            the exception (throwable) to log
     * 
     * @since 1.4
     */
    public void warn(String msg, Throwable t);

    /**
     * Warn.
     *
     * @param t
     * @param msg
     */
    public void warn(Throwable t, String msg);

    /**
     * Warn.
     *
     * @param t
     * @param template
     * @param arg
     */
    public void warn(Throwable t, String template, Object arg);

    /**
     * Warn.
     *
     * @param t
     * @param template
     * @param arg1
     * @param arg2
     */
    public void warn(Throwable t, String template, Object arg1, Object arg2);

    /**
     * Warn.
     *
     * @param t
     * @param template
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public void warn(Throwable t, String template, Object arg1, Object arg2, Object arg3);

    /**
     * Warn.
     *
     * @param supplier
     */
    public void warn(Supplier<String> supplier);

    /**
     * Warn.
     *
     * @param supplier
     * @param t
     * @deprecated replaced by {@link #warn(Throwable, Supplier)}
     */
    @Deprecated
    public void warn(Supplier<String> supplier, Throwable t);

    /**
     * Warn.
     *
     * @param t
     * @param supplier
     */
    public void warn(Throwable t, Supplier<String> supplier);

    /**
     * Is the logger instance enabled for the ERROR level?.
     *
     * @return True if this Logger is enabled for the ERROR level, false otherwise.
     */
    public boolean isErrorEnabled();

    /**
     * Log a message at the ERROR level.
     * 
     * @param msg
     *            the message string to be logged
     * @since 1.4
     */
    public void error(String msg);

    /**
     * Error.
     *
     * @param template
     * @param arg
     */
    public void error(String template, Object arg);

    /**
     * Error.
     *
     * @param template
     * @param arg1
     * @param arg2
     */
    public void error(String template, Object arg1, Object arg2);

    /**
     * Error.
     *
     * @param template
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public void error(String template, Object arg1, Object arg2, Object arg3);

    /**
     * Log a message at the ERROR level according to the specified format and arguments.
     * 
     * <p>
     * This form avoids superfluous object creation when the logger is disabled for the ERROR level.
     * </p>
     * 
     * @param template
     *            the template string
     * @param args
     *            an array of arguments
     * 
     * @since 1.4
     * @deprecated {@link #error(Supplier)} is recommended
     */
    @Deprecated
    public void error(String template, Object... args);

    /**
     * Log an exception (throwable) at the ERROR level with an accompanying message.
     * 
     * @param msg
     *            the message accompanying the exception
     * @param t
     *            the exception (throwable) to log
     * 
     * @since 1.4
     */
    public void error(String msg, Throwable t);

    /**
     * Error.
     *
     * @param t
     * @param msg
     */
    public void error(Throwable t, String msg);

    /**
     * Error.
     *
     * @param t
     * @param template
     * @param arg
     */
    public void error(Throwable t, String template, Object arg);

    /**
     * Error.
     *
     * @param t
     * @param template
     * @param arg1
     * @param arg2
     */
    public void error(Throwable t, String template, Object arg1, Object arg2);

    /**
     * Error.
     *
     * @param t
     * @param template
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public void error(Throwable t, String template, Object arg1, Object arg2, Object arg3);

    /**
     * Error.
     *
     * @param supplier
     */
    public void error(Supplier<String> supplier);

    /**
     * Error.
     *
     * @param supplier
     * @param t
     * @deprecated replaced by {@link #error(Throwable, Supplier)}
     */
    @Deprecated
    public void error(Supplier<String> supplier, Throwable t);

    /**
     * Error.
     *
     * @param t
     * @param supplier
     */
    public void error(Throwable t, Supplier<String> supplier);

}
