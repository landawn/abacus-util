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

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Timestamp;
import java.util.Calendar;

import com.landawn.abacus.util.DateUtil;
import com.landawn.abacus.util.N;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public class CalendarType extends AbstractCalendarType<Calendar> {

    public static final String CALENDAR = Calendar.class.getSimpleName();

    CalendarType() {
        super(CALENDAR);
    }

    CalendarType(String typeName) {
        super(typeName);
    }

    @Override
    public Class<Calendar> clazz() {
        return Calendar.class;
    }

    /**
     *
     * @param str
     * @return
     */
    @Override
    public Calendar valueOf(String str) {
        return N.isNullOrEmpty(str) ? null : (N.equals(str, SYS_TIME) ? DateUtil.currentCalendar() : DateUtil.parseCalendar(str));
    }

    /**
     *
     * @param cbuf
     * @param offset
     * @param len
     * @return
     */
    @Override
    public Calendar valueOf(char[] cbuf, int offset, int len) {
        if ((cbuf == null) || (len == 0)) {
            return null;
        }

        if (cbuf[offset + 4] != '-') {
            try {
                return DateUtil.createCalendar(parseLong(cbuf, offset, len));
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
    public Calendar get(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp value = rs.getTimestamp(columnIndex);

        return (value == null) ? null : DateUtil.createCalendar(value);
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
        Timestamp value = rs.getTimestamp(columnLabel);

        return (value == null) ? null : DateUtil.createCalendar(value);
    }
}
