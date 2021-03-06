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

package com.landawn.abacus.parser;

import java.util.Collection;
import java.util.Map;

import com.landawn.abacus.util.DateTimeFormat;
import com.landawn.abacus.util.N;
import com.landawn.abacus.util.NamingPolicy;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public class KryoSerializationConfig extends SerializationConfig<KryoSerializationConfig> {

    protected static final boolean defaultWriteClass = false;

    private boolean writeClass = defaultWriteClass;

    public KryoSerializationConfig() {
    }

    /**
     * Checks if is write class.
     *
     * @return true, if is write class
     */
    public boolean isWriteClass() {
        return writeClass;
    }

    /**
     * Sets the write class.
     *
     * @param writeClass
     * @return
     */
    public KryoSerializationConfig setWriteClass(boolean writeClass) {
        this.writeClass = writeClass;

        return this;
    }

    /**
     * Gets the char quotation.
     *
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public char getCharQuotation() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the char quotation.
     *
     * @param charQuotation
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public KryoSerializationConfig setCharQuotation(char charQuotation) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the string quotation.
     *
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public char getStringQuotation() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the string quotation.
     *
     * @param stringQuotation
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public KryoSerializationConfig setStringQuotation(char stringQuotation) {
        throw new UnsupportedOperationException();
    }

    /**
     * The default format is: <code>LONG</code>.
     *
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public DateTimeFormat getDateTimeFormat() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the date time format.
     *
     * @param dateTimeFormat
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public KryoSerializationConfig setDateTimeFormat(DateTimeFormat dateTimeFormat) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if is pretty format.
     *
     * @return true, if is pretty format
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public boolean isPrettyFormat() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the pretty format.
     *
     * @param prettyFormat
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public KryoSerializationConfig setPrettyFormat(boolean prettyFormat) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the indentation.
     *
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public String getIndentation() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the indentation.
     *
     * @param indentation
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public KryoSerializationConfig setIndentation(String indentation) {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the prop naming policy.
     *
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public NamingPolicy getPropNamingPolicy() {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the prop naming policy.
     *
     * @param propNamingPolicy
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public KryoSerializationConfig setPropNamingPolicy(NamingPolicy propNamingPolicy) {
        throw new UnsupportedOperationException();
    }

    /**
     * Support circular reference.
     *
     * @return true, if successful
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public boolean supportCircularReference() {
        throw new UnsupportedOperationException();
    }

    /**
     * Support circular reference.
     *
     * @param supportCircularReference
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public KryoSerializationConfig supportCircularReference(boolean supportCircularReference) {
        throw new UnsupportedOperationException();
    }

    /** 
     *
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public boolean writeBigDecimalAsPlain() {
        throw new UnsupportedOperationException();
    }

    /** 
     *
     * @param writeBigDecimalAsPlain
     * @return
     * @deprecated UnsupportedOperationException
     */
    @Deprecated
    @Override
    public KryoSerializationConfig writeBigDecimalAsPlain(boolean writeBigDecimalAsPlain) {
        throw new UnsupportedOperationException();
    }

    //    /**
    //     *
    //     * @return
    //     */
    //    @Override
    //    public KryoSerializationConfig copy() {
    //        final KryoSerializationConfig copy = new KryoSerializationConfig();
    //
    //        copy.setIgnoredPropNames(this.getIgnoredPropNames());
    //        copy.setCharQuotation(this.getCharQuotation());
    //        copy.setStringQuotation(this.getStringQuotation());
    //        copy.setDateTimeFormat(this.getDateTimeFormat());
    //        copy.setExclusion(this.getExclusion());
    //        copy.setSkipTransientField(this.isSkipTransientField());
    //        copy.setPrettyFormat(this.isPrettyFormat());
    //        copy.supportCircularReference(this.supportCircularReference());
    //        copy.writeBigDecimalAsPlain(this.writeBigDecimalAsPlain());
    //        copy.setIndentation(this.getIndentation());
    //        copy.setPropNamingPolicy(this.getPropNamingPolicy());
    //        copy.setIgnoredPropNames(this.getIgnoredPropNames());
    //        copy.writeClass = this.writeClass;
    //
    //        return copy;
    //    }

    @Override
    public int hashCode() {
        int h = 17;
        h = 31 * h + N.hashCode(getIgnoredPropNames());
        h = 31 * h + N.hashCode(getExclusion());
        h = 31 * h + N.hashCode(isSkipTransientField());
        h = 31 * h + N.hashCode(getIgnoredPropNames());
        h = 31 * h + N.hashCode(writeClass);

        return h;
    }

    /**
     *
     * @param obj
     * @return true, if successful
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof KryoSerializationConfig) {
            KryoSerializationConfig other = (KryoSerializationConfig) obj;

            if (N.equals(getIgnoredPropNames(), other.getIgnoredPropNames()) && N.equals(getExclusion(), other.getExclusion())
                    && N.equals(isSkipTransientField(), other.isSkipTransientField()) && N.equals(getIgnoredPropNames(), other.getIgnoredPropNames())
                    && N.equals(writeClass, other.writeClass)) {

                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "{ignoredPropNames=" + N.toString(getIgnoredPropNames()) + ", exclusion=" + N.toString(getExclusion()) + ", skipTransientField="
                + N.toString(isSkipTransientField()) + ", ignoredPropNames=" + N.toString(getIgnoredPropNames()) + ", writeClass=" + N.toString(writeClass)
                + "}";
    }

    /**
     * The Class KSC.
     */
    public static final class KSC extends KryoSerializationConfig {

        /**
         *
         * @return
         */
        public static KryoSerializationConfig create() {
            return new KryoSerializationConfig();
        }

        /**
         *
         * @param writeClass
         * @return
         * @deprecated
         */
        @Deprecated
        public static KryoSerializationConfig of(boolean writeClass) {
            return create().setWriteClass(writeClass);
        }

        /**
         *
         * @param exclusion
         * @param ignoredPropNames
         * @return
         * @deprecated
         */
        @Deprecated
        public static KryoSerializationConfig of(Exclusion exclusion, Map<Class<?>, Collection<String>> ignoredPropNames) {
            return create().setExclusion(exclusion).setIgnoredPropNames(ignoredPropNames);
        }

        /**
         *
         * @param writeClass
         * @param exclusion
         * @param ignoredPropNames
         * @return
         * @deprecated
         */
        @Deprecated
        public static KryoSerializationConfig of(boolean writeClass, Exclusion exclusion, Map<Class<?>, Collection<String>> ignoredPropNames) {
            return create().setWriteClass(writeClass).setExclusion(exclusion).setIgnoredPropNames(ignoredPropNames);
        }
    }
}
