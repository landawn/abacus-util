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

import com.landawn.abacus.parser.SerializationConfig;
import com.landawn.abacus.util.CharacterWriter;
import com.landawn.abacus.util.N;
import com.landawn.abacus.util.Objectory;
import com.landawn.abacus.util.WD;

/**
 *
 * @author Haiyang Li
 * @since 0.8
 */
public final class CharacterArrayType extends ObjectArrayType<Character> {

    CharacterArrayType() {
        super(Character[].class);
    }

    /**
     *
     * @param x
     * @return
     */
    @Override
    public String stringOf(Character[] x) {
        if (x == null) {
            return null;
        } else if (x.length == 0) {
            return "[]";
        }

        final StringBuilder sb = Objectory.createStringBuilder();
        sb.append(WD._BRACKET_L);

        for (int i = 0, len = x.length; i < len; i++) {
            if (i > 0) {
                sb.append(ELEMENT_SEPARATOR);
            }

            if (x[i] == null) {
                sb.append(NULL_CHAR_ARRAY);
            } else {
                sb.append(WD.QUOTATION_S);
                sb.append(x[i]);
                sb.append(WD.QUOTATION_S);
            }
        }

        sb.append(WD._BRACKET_R);

        String str = sb.toString();

        Objectory.recycle(sb);

        return str;
    }

    /**
     *
     * @param str
     * @return
     */
    @Override
    public Character[] valueOf(String str) {
        if (str == null) {
            return null;
        } else if (str.length() == 0 || "[]".equals(str)) {
            return N.EMPTY_CHAR_OBJ_ARRAY;
        }

        String[] strs = split(str);
        int len = strs.length;
        Character[] a = new Character[len];

        if (len > 0) {
            boolean isQuoted = false;

            if ((strs[0].length() > 1) && ((strs[0].charAt(0) == WD._QUOTATION_S) || (strs[0].charAt(0) == WD._QUOTATION_D))) {
                isQuoted = true;
            }

            if (isQuoted) {
                for (int i = 0; i < len; i++) {
                    if (strs[i].length() == 4 && strs[i].equals(NULL_STRING)) {
                        a[i] = null;
                    } else {
                        a[i] = elementType.valueOf(strs[i].substring(1, strs[i].length() - 1));
                    }
                }
            } else {
                for (int i = 0; i < len; i++) {
                    if (strs[i].length() == 4 && strs[i].equals(NULL_STRING)) {
                        a[i] = null;
                    } else {
                        a[i] = elementType.valueOf(strs[i]);
                    }
                }
            }
        }

        return a;
    }

    /**
     *
     * @param writer
     * @param x
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public void write(Writer writer, Character[] x) throws IOException {
        if (x == null) {
            writer.write(NULL_CHAR_ARRAY);
        } else {
            writer.write(WD._BRACKET_L);

            for (int i = 0, len = x.length; i < len; i++) {
                if (i > 0) {
                    writer.write(ELEMENT_SEPARATOR);
                }

                if (x[i] == null) {
                    writer.write(NULL_CHAR_ARRAY);
                } else {
                    writer.write(x[i]);
                }
            }

            writer.write(WD._BRACKET_R);
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
    public void writeCharacter(CharacterWriter writer, Character[] x, SerializationConfig<?> config) throws IOException {
        if (x == null) {
            writer.write(NULL_CHAR_ARRAY);
        } else {
            writer.write(WD._BRACKET_L);

            char charQuotation = (config == null) ? WD.CHAR_0 : config.getCharQuotation();

            for (int i = 0, len = x.length; i < len; i++) {
                if (i > 0) {
                    writer.write(ELEMENT_SEPARATOR);
                }

                if (x[i] == null) {
                    writer.write(NULL_CHAR_ARRAY);
                } else {
                    if (charQuotation > 0) {
                        writer.write(charQuotation);
                        writer.writeCharacter(x[i]);
                        writer.write(charQuotation);
                    } else {
                        writer.writeCharacter(x[i]);
                    }
                }
            }

            writer.write(WD._BRACKET_R);
        }
    }

    /**
     *
     * @param x
     * @return
     */
    @Override
    public String toString(Character[] x) {
        if (x == null) {
            return NULL_STRING;
        }

        final StringBuilder sb = Objectory.createStringBuilder();

        sb.append(WD._BRACKET_L);

        for (int i = 0, len = x.length; i < len; i++) {
            if (i > 0) {
                sb.append(ELEMENT_SEPARATOR);
            }

            if (x[i] == null) {
                sb.append(NULL_CHAR_ARRAY);
            } else {
                sb.append(x[i]);
            }
        }

        sb.append(WD._BRACKET_R);

        String str = sb.toString();

        Objectory.recycle(sb);

        return str;
    }
}
