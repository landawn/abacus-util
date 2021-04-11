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
import com.landawn.abacus.util.Numbers;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public abstract class AbstractIntegerType extends NumberType<Number> {

    protected AbstractIntegerType(String typeName) {
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

        return N.stringOf(x.intValue());
    }

    /**
     *
     * @param str
     * @return
     */
    @SuppressWarnings("deprecation")
    @Override
    public Integer valueOf(String str) {
        if (N.isNullOrEmpty(str)) {
            return (Integer) defaultValue();
        }

        try {
            return Numbers.toInt(str);
        } catch (NumberFormatException e) {
            if (str.length() > 1) {
                char ch = str.charAt(str.length() - 1);

                if ((ch == 'l') || (ch == 'L') || (ch == 'f') || (ch == 'F') || (ch == 'd') || (ch == 'D')) {
                    return parseInt(com.landawn.abacus.util.InternalUtil.getCharsForReadOnly(str), 0, str.length() - 1);
                }
            }

            throw e;
        }
    }

    /**
     *
     * @param cbuf
     * @param offset
     * @param len
     * @return
     */
    @Override
    public Integer valueOf(char[] cbuf, int offset, int len) {
        return ((cbuf == null) || (len == 0)) ? ((Integer) defaultValue()) : (Integer) parseInt(cbuf, offset, len);
    }

    /**
     *
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public Integer get(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getInt(columnIndex);
    }

    /**
     *
     * @param rs
     * @param columnLabel
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public Integer get(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getInt(columnLabel);
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
        stmt.setInt(columnIndex, (x == null) ? 0 : x.intValue());
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
        stmt.setInt(parameterName, (x == null) ? 0 : x.intValue());
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
            IOUtil.write(writer, x.intValue());
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
            writer.writeInt(x.intValue());
        }
    }
}
