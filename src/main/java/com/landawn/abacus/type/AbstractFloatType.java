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
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.landawn.abacus.parser.SerializationConfig;
import com.landawn.abacus.util.CharacterWriter;
import com.landawn.abacus.util.IOUtil;
import com.landawn.abacus.util.N;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public abstract class AbstractFloatType extends NumberType<Number> {

    protected AbstractFloatType(String typeName) {
        super(typeName);
    }

    /**
     *
     * @param x
     * @return
     */
    @Override
    public String stringOf(Number x) {
        if (x == null) {
            return null;
        }

        return N.stringOf(x.floatValue());
    }

    /**
     *
     * @param st
     * @return
     */
    @Override
    public Float valueOf(String st) {
        try {
            return N.isNullOrEmpty(st) ? ((Float) defaultValue()) : Float.valueOf(st);
        } catch (NumberFormatException e) {
            if (st.length() > 1) {
                char ch = st.charAt(st.length() - 1);

                if ((ch == 'l') || (ch == 'L') || (ch == 'f') || (ch == 'F') || (ch == 'd') || (ch == 'D')) {
                    return Float.valueOf(st.substring(0, st.length() - 1));
                }
            }

            throw e;
        }
    }

    /**
     *
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public Float get(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getFloat(columnIndex);
    }

    /**
     *
     * @param rs
     * @param columnLabel
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public Float get(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getFloat(columnLabel);
    }

    /**
     *
     * @param stmt
     * @param columnIndex
     * @param x
     * @throws SQLException the SQL exception
     */
    @Override
    public void set(PreparedStatement stmt, int columnIndex, Number x) throws SQLException {
        stmt.setFloat(columnIndex, (x == null) ? 0 : x.floatValue());
    }

    /**
     *
     * @param stmt
     * @param parameterName
     * @param x
     * @throws SQLException the SQL exception
     */
    @Override
    public void set(CallableStatement stmt, String parameterName, Number x) throws SQLException {
        stmt.setFloat(parameterName, (x == null) ? 0 : x.floatValue());
    }

    /**
     *
     * @param writer
     * @param x
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void write(Writer writer, Number x) throws IOException {
        if (x == null) {
            writer.write(NULL_CHAR_ARRAY);
        } else {
            IOUtil.write(writer, x.floatValue());
        }
    }

    /**
     *
     * @param writer
     * @param x
     * @param config
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void writeCharacter(CharacterWriter writer, Number x, SerializationConfig<?> config) throws IOException {
        if (x == null) {
            writer.write(NULL_CHAR_ARRAY);
        } else {
            IOUtil.write(writer, x.floatValue());
        }
    }
}
