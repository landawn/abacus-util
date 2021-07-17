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
import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.landawn.abacus.annotation.JsonXmlField;
import com.landawn.abacus.annotation.Type.EnumBy;
import com.landawn.abacus.parser.SerializationConfig;
import com.landawn.abacus.util.BiMap;
import com.landawn.abacus.util.CharacterWriter;
import com.landawn.abacus.util.ClassUtil;
import com.landawn.abacus.util.N;
import com.landawn.abacus.util.Numbers;
import com.landawn.abacus.util.StringUtil;

/**
 *
 * @author Haiyang Li
 * @param <T>
 * @since 0.8
 */
public final class EnumType<T extends Enum<T>> extends SingleValueType<T> {
    public static final String ENUM = Enum.class.getSimpleName();

    private static final String NULL = "null";
    private final BiMap<Number, T> numberEnum = new BiMap<>();
    private final Map<T, String> enumJsonXmlNameMap;
    private final Map<String, T> jsonXmlNameEnumMap;
    private final EnumBy enumBy;

    private boolean hasNull = false;

    EnumType(final String enumClassName) {
        this(enumClassName, false);
    }

    EnumType(final String clsName, final boolean ordinal) {
        super(ordinal ? clsName + "(true)" : clsName, (Class<T>) getEnumClass(ClassUtil.forClass(clsName)));

        enumJsonXmlNameMap = new EnumMap<>(typeClass);
        jsonXmlNameEnumMap = new HashMap<>();

        for (T enumConstant : typeClass.getEnumConstants()) {
            numberEnum.put(enumConstant.ordinal(), enumConstant);

            String jsonXmlName = getJsonXmlName(enumConstant);
            enumJsonXmlNameMap.put(enumConstant, jsonXmlName);
            jsonXmlNameEnumMap.put(jsonXmlName, enumConstant);
        }

        try {
            hasNull = Enum.valueOf(typeClass, NULL) != null;
        } catch (Exception e) {
            // ignore;
        }

        enumBy = ordinal ? EnumBy.ORDINAL : EnumBy.NAME;
    }

    public EnumBy enumerated() {
        return enumBy;
    }

    @Override
    public boolean isSerializable() {
        return true;
    }

    @Override
    public boolean isImmutable() {
        return true;
    }

    @Override
    public String stringOf(T x) {
        return (jsonValueType == null) ? (x == null ? null : x.name()) : super.stringOf(x);
    }

    @Override
    public T valueOf(String str) {
        if (jsonValueType == null) {
            if (N.isNullOrEmpty(str) || (hasNull == false && NULL.equals(str))) {
                return null;
            }

            if (StringUtil.isAsciiDigtalInteger(str)) {
                return valueOf(Numbers.toInt(str));
            } else {
                final T val = jsonXmlNameEnumMap.get(str);

                if (val != null) {
                    return val;
                } else {
                    return Enum.valueOf(typeClass, str);
                }
            }
        } else {
            return super.valueOf(str);
        }
    }

    public T valueOf(final int value) {
        T result = numberEnum.get(value);

        if ((result == null) && (value != 0)) {
            throw new IllegalArgumentException("No " + typeClass.getName() + " for int value: " + value);
        }

        return result;
    }

    @Override
    public T get(final ResultSet rs, final int columnIndex) throws SQLException {
        if (jsonValueType == null) {
            if (enumBy == EnumBy.ORDINAL) {
                return valueOf(rs.getInt(columnIndex));
            } else {
                return valueOf(rs.getString(columnIndex));
            }
        } else {
            return super.get(rs, columnIndex);
        }
    }

    @Override
    public T get(final ResultSet rs, final String columnLabel) throws SQLException {
        if (jsonValueType == null) {
            if (enumBy == EnumBy.ORDINAL) {
                return valueOf(rs.getInt(columnLabel));
            } else {
                return valueOf(rs.getString(columnLabel));
            }
        } else {
            return super.get(rs, columnLabel);
        }
    }

    @Override
    public void set(final PreparedStatement stmt, final int columnIndex, final T x) throws SQLException {
        if (jsonValueType == null) {
            if (enumBy == EnumBy.ORDINAL) {
                stmt.setInt(columnIndex, (x == null) ? 0 : numberEnum.getByValue(x).intValue());
            } else {
                stmt.setString(columnIndex, (x == null) ? null : x.name());
            }
        } else {
            super.set(stmt, columnIndex, x);
        }
    }

    @Override
    public void set(final CallableStatement stmt, final String parameterName, final T x) throws SQLException {
        if (jsonValueType == null) {
            if (enumBy == EnumBy.ORDINAL) {
                stmt.setInt(parameterName, (x == null) ? 0 : numberEnum.getByValue(x).intValue());
            } else {
                stmt.setString(parameterName, (x == null) ? null : x.name());
            }
        } else {
            super.set(stmt, parameterName, x);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void writeCharacter(final CharacterWriter writer, final T x, final SerializationConfig<?> config) throws IOException {
        if (x == null) {
            writer.write(NULL_CHAR_ARRAY);
        } else {
            if (jsonValueType == null) {
                if (enumBy == EnumBy.ORDINAL) {
                    writer.writeInt(((Enum) x).ordinal());
                } else {
                    final char ch = config == null ? 0 : config.getStringQuotation();

                    if (ch == 0) {
                        writer.writeCharacter(enumJsonXmlNameMap.get(x));
                    } else {
                        writer.write(ch);
                        writer.writeCharacter(enumJsonXmlNameMap.get(x));
                        writer.write(ch);
                    }
                }
            } else {
                super.writeCharacter(writer, x, config);
            }
        }
    }

    private String getJsonXmlName(final T enumConstant) {
        try {
            final Field field = enumConstant.getClass().getField(((Enum<T>) enumConstant).name());

            if (field.isAnnotationPresent(JsonXmlField.class) && N.notNullOrEmpty(field.getAnnotation(JsonXmlField.class).name())) {
                return field.getAnnotation(JsonXmlField.class).name();
            }

            try {
                if (field.isAnnotationPresent(com.alibaba.fastjson.annotation.JSONField.class)
                        && N.notNullOrEmpty(field.getAnnotation(com.alibaba.fastjson.annotation.JSONField.class).name())) {
                    return field.getAnnotation(com.alibaba.fastjson.annotation.JSONField.class).name();
                }
            } catch (Throwable e) {
                // ignore
            }

            try {
                if (field.isAnnotationPresent(com.fasterxml.jackson.annotation.JsonProperty.class)
                        && N.notNullOrEmpty(field.getAnnotation(com.fasterxml.jackson.annotation.JsonProperty.class).value())) {
                    return field.getAnnotation(com.fasterxml.jackson.annotation.JsonProperty.class).value();
                }
            } catch (Throwable e) {
                // ignore
            }
        } catch (NoSuchFieldException | SecurityException e) {
            // should never happen.
        }

        return enumConstant.name();
    }

    private static Class<?> getEnumClass(final Class<?> cls) {
        return cls.isEnum() ? cls : cls.getEnclosingClass();
    }
}
