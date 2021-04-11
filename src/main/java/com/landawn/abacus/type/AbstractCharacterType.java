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
import java.sql.Types;

import com.landawn.abacus.parser.SerializationConfig;
import com.landawn.abacus.util.CharacterWriter;
import com.landawn.abacus.util.IOUtil;
import com.landawn.abacus.util.N;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public abstract class AbstractCharacterType extends AbstractPrimaryType<Character> {

    protected AbstractCharacterType(String typeName) {
        super(typeName);
    }

    /**
     *
     * @param x
     * @return
     */
    @Override
    public String stringOf(Character x) {
        return (x == null) ? null : N.stringOf(x.charValue());
    }

    /**
     *
     * @param str
     * @return
     */
    @Override
    public Character valueOf(String str) {
        // NullPointerException Here
        // return N.isNullOrEmpty(st) ? defaultValue()
        // : (char) ((st.length() == 1) ? st.charAt(0) : Integer.parseInt(st));
        if (N.isNullOrEmpty(str)) {
            return defaultValue();
        }

        return N.parseChar(str);
    }

    /**
     *
     * @param cbuf
     * @param offset
     * @param len
     * @return
     */
    @Override
    public Character valueOf(char[] cbuf, int offset, int len) {
        // NullPointerException Here
        // return ((cbuf == null) || (len == 0)) ? defaultValue()
        // : ((len == 1) ? cbuf[offset] : (char) N.parseInt(cbuf, offset, len));
        if (N.isNullOrEmpty(cbuf)) {
            return defaultValue();
        }

        return (len == 1) ? cbuf[offset] : (char) parseInt(cbuf, offset, len);
    }

    /**
     *
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public Character get(ResultSet rs, int columnIndex) throws SQLException {
        final Object x = rs.getObject(columnIndex);

        if (x == null) {
            return (char) 0;
        } else if (x instanceof Number) {
            return (char) (((Number) x).intValue());
        } else {
            return x.toString().charAt(0);
        }
    }

    /**
     *
     * @param rs
     * @param columnLabel
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public Character get(ResultSet rs, String columnLabel) throws SQLException {
        final Object x = rs.getObject(columnLabel);

        if (x == null) {
            return (char) 0;
        } else if (x instanceof Number) {
            return (char) (((Number) x).intValue());
        } else {
            return x.toString().charAt(0);
        }
    }

    /**
     *
     * @param stmt
     * @param columnIndex
     * @param x
     * @throws SQLException the SQL exception
     */
    @Override
    public void set(PreparedStatement stmt, int columnIndex, Character x) throws SQLException {
        if (x == null) {
            stmt.setNull(columnIndex, Types.INTEGER);
        } else {
            stmt.setInt(columnIndex, x.charValue());
        }
    }

    /**
     *
     * @param stmt
     * @param parameterName
     * @param x
     * @throws SQLException the SQL exception
     */
    @Override
    public void set(CallableStatement stmt, String parameterName, Character x) throws SQLException {
        if (x == null) {
            stmt.setNull(parameterName, Types.INTEGER);
        } else {
            stmt.setInt(parameterName, x.charValue());
        }
    }

    /**
     *
     * @param writer
     * @param x
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void write(Writer writer, Character x) throws IOException {
        if (x == null) {
            writer.write(NULL_CHAR_ARRAY);
        } else {
            IOUtil.write(writer, x);
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
    public void writeCharacter(CharacterWriter writer, Character x, SerializationConfig<?> config) throws IOException {
        if (x == null) {
            writer.write(NULL_CHAR_ARRAY);
        } else {
            final char ch = config == null ? 0 : config.getCharQuotation();

            if (ch == 0) {
                writer.writeCharacter(x);
            } else {
                writer.write(ch);
                writer.writeCharacter(x);
                writer.write(ch);
            }
        }
    }
}