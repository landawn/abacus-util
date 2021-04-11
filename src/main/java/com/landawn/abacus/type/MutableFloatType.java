/*
 * Copyright (c) 2016, Haiyang Li. All rights reserved.
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
import com.landawn.abacus.util.MutableFloat;
import com.landawn.abacus.util.N;
import com.landawn.abacus.util.Numbers;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public class MutableFloatType extends MutableType<MutableFloat> {

    public static final String MUTABLE_FLOAT = MutableFloat.class.getSimpleName();

    protected MutableFloatType() {
        super(MUTABLE_FLOAT);
    }

    @Override
    public Class<MutableFloat> clazz() {
        return MutableFloat.class;
    }

    /**
     *
     * @param x
     * @return
     */
    @Override
    public String stringOf(MutableFloat x) {
        return x == null ? null : N.stringOf(x.floatValue());
    }

    /**
     *
     * @param str
     * @return
     */
    @Override
    public MutableFloat valueOf(String str) {
        return N.isNullOrEmpty(str) ? null : MutableFloat.of(Numbers.toFloat(str));
    }

    /**
     *
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public MutableFloat get(ResultSet rs, int columnIndex) throws SQLException {
        return MutableFloat.of(rs.getFloat(columnIndex));
    }

    /**
     *
     * @param rs
     * @param columnLabel
     * @return
     * @throws SQLException the SQL exception
     */
    @Override
    public MutableFloat get(ResultSet rs, String columnLabel) throws SQLException {
        return MutableFloat.of(rs.getFloat(columnLabel));
    }

    /**
     *
     * @param stmt
     * @param columnIndex
     * @param x
     * @throws SQLException the SQL exception
     */
    @Override
    public void set(PreparedStatement stmt, int columnIndex, MutableFloat x) throws SQLException {
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
    public void set(CallableStatement stmt, String parameterName, MutableFloat x) throws SQLException {
        stmt.setFloat(parameterName, (x == null) ? 0 : x.floatValue());
    }

    /**
     *
     * @param writer
     * @param x
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void write(Writer writer, MutableFloat x) throws IOException {
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
    public void writeCharacter(CharacterWriter writer, MutableFloat x, SerializationConfig<?> config) throws IOException {
        if (x == null) {
            writer.write(NULL_CHAR_ARRAY);
        } else {
            writer.write(x.floatValue());
        }
    }
}
