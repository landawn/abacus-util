/*
* Copyright (C) 2008 The Guava Authors
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

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.time.Duration;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Note: Copied from Google Guava under Apache License v2.0
 * <br />
 * <br />
 * 
 * An object that measures elapsed time in nanoseconds. It is useful to measure elapsed time using
 * this class instead of direct calls to {@link System#nanoTime} for a few reasons:
 *
 * <ul>
 *   <li>An alternate time source can be substituted, for testing or performance reasons.
 *   <li>As documented by {@code nanoTime}, the value returned has no absolute meaning, and can only
 *       be interpreted as relative to another timestamp returned by {@code nanoTime} at a different
 *       time. {@code Stopwatch} is a more effective abstraction because it exposes only these
 *       relative values, not the absolute ones.
 * </ul>
 *
 * <p>Basic usage:
 *
 * <pre>{@code
 * Stopwatch stopwatch = Stopwatch.createStarted();
 * doSomething();
 * stopwatch.stop(); // optional
 *
 * Duration duration = stopwatch.elapsed();
 *
 * log.info("time: " + stopwatch); // formatted string like "12.3 ms"
 * }</pre>
 *
 * <p>Stopwatch methods are not idempotent; it is an error to start or stop a stopwatch that is
 * already in the desired state.
 *
 * <p>When testing code that uses this class, use {@link #createUnstarted(Ticker)} or {@link
 * #createStarted(Ticker)} to supply a fake or mock ticker. This allows you to simulate any valid
 * behavior of the stopwatch.
 *
 * <p><b>Note:</b> This class is not thread-safe.
 *
 * <p><b>Warning for Android users:</b> a stopwatch with default behavior may not continue to keep
 * time while the device is asleep. Instead, create one like this:
 *
 * <pre>{@code
 * Stopwatch.createStarted(
 *      new Ticker() {
 *        public long read() {
 *          return android.os.SystemClock.elapsedRealtimeNanos();
 *        }
 *      });
 * }</pre>
 *
 * @author Kevin Bourrillion
 * @since 10.0
 */
public final class Stopwatch {

    /** The ticker. */
    private final Ticker ticker;

    /** The is running. */
    private boolean isRunning;

    /** The elapsed nanos. */
    private long elapsedNanos;

    /** The start tick. */
    private long startTick;

    /**
     * Creates (but does not start) a new stopwatch using {@link System#nanoTime} as its time source.
     *
     * @return
     * @since 15.0
     */
    public static Stopwatch createUnstarted() {
        return new Stopwatch();
    }

    /**
     * Creates (but does not start) a new stopwatch, using the specified time source.
     *
     * @param ticker
     * @return
     * @since 15.0
     */
    public static Stopwatch createUnstarted(Ticker ticker) {
        return new Stopwatch(ticker);
    }

    /**
     * Creates (and starts) a new stopwatch using {@link System#nanoTime} as its time source.
     *
     * @return
     * @since 15.0
     */
    public static Stopwatch createStarted() {
        return new Stopwatch().start();
    }

    /**
     * Creates (and starts) a new stopwatch, using the specified time source.
     *
     * @param ticker
     * @return
     * @since 15.0
     */
    public static Stopwatch createStarted(Ticker ticker) {
        return new Stopwatch(ticker).start();
    }

    /**
     * Instantiates a new stopwatch.
     */
    Stopwatch() {
        this.ticker = Ticker.systemTicker();
    }

    /**
     * Instantiates a new stopwatch.
     *
     * @param ticker
     */
    Stopwatch(Ticker ticker) {
        this.ticker = N.checkArgNotNull(ticker, "ticker");
    }

    /**
     * Returns {@code true} if {@link #start()} has been called on this stopwatch, and {@link #stop()}
     * has not been called since the last call to {@code start()}.
     *
     * @return true, if is running
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Starts the stopwatch.
     *
     * @return this {@code Stopwatch} instance
     * @throws IllegalStateException if the stopwatch is already running.
     */
    public Stopwatch start() {
        N.checkState(!isRunning, "This stopwatch is already running.");
        isRunning = true;
        startTick = ticker.read();
        return this;
    }

    /**
     * Stops the stopwatch. Future reads will return the fixed duration that had elapsed up to this
     * point.
     *
     * @return this {@code Stopwatch} instance
     * @throws IllegalStateException if the stopwatch is already stopped.
     */
    public Stopwatch stop() {
        long tick = ticker.read();
        N.checkState(isRunning, "This stopwatch is already stopped.");
        isRunning = false;
        elapsedNanos += tick - startTick;
        return this;
    }

    /**
     * Sets the elapsed time for this stopwatch to zero, and places it in a stopped state.
     *
     * @return this {@code Stopwatch} instance
     */
    public Stopwatch reset() {
        elapsedNanos = 0;
        isRunning = false;
        return this;
    }

    /**
     *
     * @return
     */
    private long elapsedNanos() {
        return isRunning ? ticker.read() - startTick + elapsedNanos : elapsedNanos;
    }

    /**
     * Returns the current elapsed time shown on this stopwatch, expressed in the desired time unit,
     * with any fraction rounded down.
     * 
     * <p><b>Note:</b> the overhead of measurement can be more than a microsecond, so it is generally
     * not useful to specify {@link TimeUnit#NANOSECONDS} precision here.
     * 
     * <p>It is generally not a good idea to use an ambiguous, unitless {@code long} to represent
     * elapsed time. Therefore, we recommend using {@link #elapsed()} instead, which returns a
     * strongly-typed {@link Duration} instance.
     *
     * @param desiredUnit
     * @return
     * @since 14.0 (since 10.0 as {@code elapsedTime()})
     */
    public long elapsed(TimeUnit desiredUnit) {
        return desiredUnit.convert(elapsedNanos(), NANOSECONDS);
    }

    /**
     *
     * @return
     */
    public Duration elapsed() {
        return Duration.ofNanos(elapsedNanos());
    }

    /**
     *  Returns a string representation of the current elapsed time.
     *
     * @return
     */
    @Override
    public String toString() {
        long nanos = elapsedNanos();

        TimeUnit unit = chooseUnit(nanos);
        double value = (double) nanos / NANOSECONDS.convert(1, unit);

        // Too bad this functionality is not exposed as a regular method call
        return formatCompact4Digits(value) + " " + abbreviate(unit);
    }

    /**
     * Format compact 4 digits.
     *
     * @param value
     * @return
     */
    static String formatCompact4Digits(double value) {
        return String.format(Locale.ROOT, "%.4g", value);
    }

    /**
     *
     * @param nanos
     * @return
     */
    private static TimeUnit chooseUnit(long nanos) {
        if (DAYS.convert(nanos, NANOSECONDS) > 0) {
            return DAYS;
        }
        if (HOURS.convert(nanos, NANOSECONDS) > 0) {
            return HOURS;
        }
        if (MINUTES.convert(nanos, NANOSECONDS) > 0) {
            return MINUTES;
        }
        if (SECONDS.convert(nanos, NANOSECONDS) > 0) {
            return SECONDS;
        }
        if (MILLISECONDS.convert(nanos, NANOSECONDS) > 0) {
            return MILLISECONDS;
        }
        if (MICROSECONDS.convert(nanos, NANOSECONDS) > 0) {
            return MICROSECONDS;
        }
        return NANOSECONDS;
    }

    /**
     *
     * @param unit
     * @return
     */
    private static String abbreviate(TimeUnit unit) {
        switch (unit) {
            case NANOSECONDS:
                return "ns";
            case MICROSECONDS:
                return "\u03bcs"; // μs
            case MILLISECONDS:
                return "ms";
            case SECONDS:
                return "s";
            case MINUTES:
                return "min";
            case HOURS:
                return "h";
            case DAYS:
                return "d";
            default:
                throw new AssertionError();
        }
    }
}