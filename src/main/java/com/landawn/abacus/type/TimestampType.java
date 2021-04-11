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

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;

import com.landawn.abacus.util.DateUtil;
import com.landawn.abacus.util.N;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public class TimestampType extends AbstractDateType<Timestamp> {

    public static final String TIMESTAMP = Timestamp.class.getSimpleName();

    TimestampType() {
        super(TIMESTAMP);
    }

    TimestampType(String typeName) {
        super(typeName);
    }

    @Override
    public Class<Timestamp> clazz() {
        return Timestamp.class;
    }

    /**
     *
     * @param str
     * @return
     */
    @Override
    public Timestamp valueOf(String str) {
        return N.isNullOrEmpty(str) ? null : (N.equals(str, SYS_TIME) ? DateUtil.currentTimestamp() : DateUtil.parseTimestamp(str));
    }

    /**
     *
     * @param cbuf
     * @param offset
     * @param len
     * @return
     */
    @Override
    public Timestamp valueOf(char[] cbuf, int offset, int len) {
        if ((cbuf == null) || (len == 0)) {
            return null;
        }

        if (cbuf[offset + 4] != '-') {
            try {
                return DateUtil.createTimestamp(parseLong(cbuf, offset, len));
            } catch (NumberFormatException e) {
                // ignore;
            }
        }

        return valueOf(String.valueOf(cbuf, offset, len));
    }

    /**
     *
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public Timestamp get(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getTimestamp(columnIndex);
    }

    /**
     *
     * @param rs
     * @param columnLabel
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public Timestamp get(ResultSet rs, String columnLabel) throws SQLException {
        return rs.getTimestamp(columnLabel);
    }

    /**
     *
     * @param stmt
     * @param columnIndex
     * @param x
     * @throws SQLException the SQL exception
     */
    @Override
    public void set(PreparedStatement stmt, int columnIndex, Timestamp x) throws SQLException {
        stmt.setTimestamp(columnIndex, x);
    }

    /**
     *
     * @param stmt
     * @param parameterName
     * @param x
     * @throws SQLException the SQL exception
     */
    @Override
    public void set(CallableStatement stmt, String parameterName, Timestamp x) throws SQLException {
        stmt.setTimestamp(parameterName, x);
    }
}
