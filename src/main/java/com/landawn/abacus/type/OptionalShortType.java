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
import com.landawn.abacus.util.u.OptionalShort;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public class OptionalShortType extends AbstractOptionalType<OptionalShort> {

    public static final String OPTIONAL_SHORT = OptionalShort.class.getSimpleName();

    protected OptionalShortType() {
        super(OPTIONAL_SHORT);
    }

    @Override
    public Class<OptionalShort> clazz() {
        return OptionalShort.class;
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
    public String stringOf(OptionalShort x) {
        return x == null || x.isPresent() == false ? null : String.valueOf(x.get());
    }

    /**
     *
     * @param str
     * @return
     */
    @Override
    public OptionalShort valueOf(String str) {
        return N.isNullOrEmpty(str) ? OptionalShort.empty() : OptionalShort.of(Numbers.toShort(str));
    }

    /**
     *
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public OptionalShort get(ResultSet rs, int columnIndex) throws SQLException {
        final Object obj = rs.getObject(columnIndex);

        return obj == null ? OptionalShort.empty() : OptionalShort.of(obj instanceof Short ? (Short) obj : Numbers.toShort(obj.toString()));
    }

    /**
     *
     * @param rs
     * @param columnLabel
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public OptionalShort get(ResultSet rs, String columnLabel) throws SQLException {
        final Object obj = rs.getObject(columnLabel);

        return obj == null ? OptionalShort.empty() : OptionalShort.of(obj instanceof Short ? (Short) obj : Numbers.toShort(obj.toString()));
    }

    /**
     *
     * @param stmt
     * @param columnIndex
     * @param x
     * @throws SQLException the SQL exception
     */
    @Override
    public void set(PreparedStatement stmt, int columnIndex, OptionalShort x) throws SQLException {
        if (x == null || x.isPresent() == false) {
            stmt.setNull(columnIndex, java.sql.Types.SMALLINT);
        } else {
            stmt.setShort(columnIndex, x.get());
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
    public void set(CallableStatement stmt, String parameterName, OptionalShort x) throws SQLException {
        if (x == null || x.isPresent() == false) {
            stmt.setNull(parameterName, java.sql.Types.SMALLINT);
        } else {
            stmt.setShort(parameterName, x.get());
        }
    }

    /**
     *
     * @param writer
     * @param x
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void write(Writer writer, OptionalShort x) throws IOException {
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
    public void writeCharacter(CharacterWriter writer, OptionalShort x, SerializationConfig<?> config) throws IOException {
        if (x == null) {
            writer.write(NULL_CHAR_ARRAY);
        } else {
            writer.write(x.get());
        }
    }
}
