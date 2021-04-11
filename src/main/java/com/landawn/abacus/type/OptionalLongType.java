/*
 * Copyright (c) 2016, Haiyang Li. All rights reserved.
 */

package com.landawn.abacus.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;
import java.io.Writer;

import com.landawn.abacus.parser.SerializationConfig;
import com.landawn.abacus.util.CharacterWriter;
import com.landawn.abacus.util.IOUtil;
import com.landawn.abacus.util.N;
import com.landawn.abacus.util.Numbers;
import com.landawn.abacus.util.u.OptionalLong;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public class OptionalLongType extends AbstractOptionalType<OptionalLong> {

    public static final String OPTIONAL_LONG = OptionalLong.class.getSimpleName();

    protected OptionalLongType() {
        super(OPTIONAL_LONG);
    }

    @Override
    public Class<OptionalLong> clazz() {
        return OptionalLong.class;
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
     * Checks if is optional or nullable.
     *
     * @return true, if is optional or nullable
     */
    @Override
    public boolean isOptionalOrNullable() {
        return true;
    }

    /**
     *
     * @param x
     * @return
     */
    @Override
    public String stringOf(OptionalLong x) {
        return x == null || x.isPresent() == false ? null : String.valueOf(x.get());
    }

    /**
     *
     * @param str
     * @return
     */
    @Override
    public OptionalLong valueOf(String str) {
        return N.isNullOrEmpty(str) ? OptionalLong.empty() : OptionalLong.of(Numbers.toLong(str));
    }

    /**
     *
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public OptionalLong get(ResultSet rs, int columnIndex) throws SQLException {
        final Object obj = rs.getObject(columnIndex);

        return obj == null ? OptionalLong.empty() : OptionalLong.of(obj instanceof Long ? (Long) obj : Numbers.toLong(obj.toString()));
    }

    /**
     *
     * @param rs
     * @param columnLabel
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public OptionalLong get(ResultSet rs, String columnLabel) throws SQLException {
        final Object obj = rs.getObject(columnLabel);

        return obj == null ? OptionalLong.empty() : OptionalLong.of(obj instanceof Long ? (Long) obj : Numbers.toLong(obj.toString()));
    }

    /**
     *
     * @param stmt
     * @param columnIndex
     * @param x
     * @throws SQLException the SQL exception
     */
    @Override
    public void set(PreparedStatement stmt, int columnIndex, OptionalLong x) throws SQLException {
        if (x == null || x.isPresent() == false) {
            stmt.setNull(columnIndex, java.sql.Types.BIGINT);
        } else {
            stmt.setLong(columnIndex, x.get());
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
    public void set(CallableStatement stmt, String parameterName, OptionalLong x) throws SQLException {
        if (x == null || x.isPresent() == false) {
            stmt.setNull(parameterName, java.sql.Types.BIGINT);
        } else {
            stmt.setLong(parameterName, x.get());
        }
    }

    /**
     *
     * @param writer
     * @param x
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void write(Writer writer, OptionalLong x) throws IOException {
        if (x == null) {
            writer.write(NULL_CHAR_ARRAY);
        } else {
            IOUtil.write(writer, x.get());
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
    public void writeCharacter(CharacterWriter writer, OptionalLong x, SerializationConfig<?> config) throws IOException {
        if (x == null) {
            writer.write(NULL_CHAR_ARRAY);
        } else {
            writer.write(x.get());
        }
    }
}
