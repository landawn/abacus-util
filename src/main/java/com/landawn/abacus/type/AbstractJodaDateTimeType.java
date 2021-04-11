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

package com.landawn.abacus.type;

import java.io.IOException;
import java.io.Writer;

import org.joda.time.base.BaseDateTime;
import org.joda.time.format.DateTimeFormatter;

import com.landawn.abacus.parser.SerializationConfig;
import com.landawn.abacus.util.CharacterWriter;
import com.landawn.abacus.util.DateTimeFormat;
import com.landawn.abacus.util.DateUtil;

/**
 *
 * @author Haiyang Li
 * @param <T>
 * @since 0.8
 */
public abstract class AbstractJodaDateTimeType<T extends BaseDateTime> extends AbstractType<T> {

    protected static final DateTimeFormatter iso8601DateTimeFT = org.joda.time.format.DateTimeFormat.forPattern(DateUtil.ISO_8601_DATETIME_FORMAT);

    protected static final DateTimeFormatter iso8601TimestampFT = org.joda.time.format.DateTimeFormat.forPattern(DateUtil.ISO_8601_TIMESTAMP_FORMAT);

    protected AbstractJodaDateTimeType(String typeName) {
        super(typeName);
    }

    /**
     * Checks if is joda date time.
     *
     * @return true, if is joda date time
     */
    @Override
    public boolean isJodaDateTime() {
        return true;
    }

    /**
     * Checks if is comparable.
     *
     * @return true, if is comparable
     */
    @Override
    public boolean isComparable() {
        return true;
    }

    /**
     *
     * @param x
     * @return
     */
    @Override
    public String stringOf(T x) {
        return (x == null) ? null : iso8601TimestampFT.print(x);
    }

    /**
     *
     * @param writer
     * @param x
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void write(Writer writer, T x) throws IOException {
        if (x == null) {
            writer.write(NULL_CHAR_ARRAY);
        } else {
            iso8601TimestampFT.printTo(writer, x);
        }
    }

    /**
     *
     * @param writer
     * @param x
     * @param config
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @SuppressWarnings("null")
    @Override
    public void writeCharacter(CharacterWriter writer, T x, SerializationConfig<?> config) throws IOException {
        if (x == null) {
            writer.write(NULL_CHAR_ARRAY);
        } else {
            boolean isQuote = (config != null) && (config.getStringQuotation() != 0) && (config.getDateTimeFormat() != DateTimeFormat.LONG);

            if (isQuote) {
                writer.write(config.getStringQuotation());
            }

            if ((config == null) || (config.getDateTimeFormat() == null)) {
                iso8601TimestampFT.printTo(writer, x);
            } else {
                switch (config.getDateTimeFormat()) {
                    case LONG:
                        writer.write(x.getMillis());

                        break;

                    case ISO_8601_DATETIME:
                        iso8601DateTimeFT.printTo(writer, x);

                        break;

                    case ISO_8601_TIMESTAMP:
                        iso8601TimestampFT.printTo(writer, x);

                        break;

                    default:
                        throw new RuntimeException("unsupported operation");
                }
            }

            if (isQuote) {
                writer.write(config.getStringQuotation());
            }
        }
    }
}
