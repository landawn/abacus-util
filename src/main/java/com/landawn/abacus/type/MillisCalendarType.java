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

import java.util.Calendar;

import com.landawn.abacus.util.DateUtil;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public class MillisCalendarType extends CalendarType {

    public static final String MILLIS_CALENDAR = "MillisCalendar";

    MillisCalendarType() {
        super(MILLIS_CALENDAR);
    }

    /**
     *
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public Calendar get(ResultSet rs, int columnIndex) throws SQLException {
        long lng = rs.getLong(columnIndex);

        return (lng == 0) ? null : DateUtil.createCalendar(lng);
    }

    /**
     *
     * @param rs
     * @param columnLabel
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public Calendar get(ResultSet rs, String columnLabel) throws SQLException {
        long lng = rs.getLong(columnLabel);

        return (lng == 0) ? null : DateUtil.createCalendar(lng);
    }

    /**
     *
     * @param stmt
     * @param columnIndex
     * @param x
     * @throws SQLException the SQL exception
     */
    @Override
    public void set(PreparedStatement stmt, int columnIndex, Calendar x) throws SQLException {
        stmt.setLong(columnIndex, (x == null) ? 0 : x.getTimeInMillis());
    }

    /**
     *
     * @param stmt
     * @param parameterName
     * @param x
     * @throws SQLException the SQL exception
     */
    @Override
    public void set(CallableStatement stmt, String parameterName, Calendar x) throws SQLException {
        stmt.setLong(parameterName, (x == null) ? 0 : x.getTimeInMillis());
    }
}
