/*
 * Copyright (c) 2017, The Modern Way. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.themodernway.common.api.java.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.function.Supplier;

import com.themodernway.common.api.CommonOps;

public final class StringOps
{
    public static final String   CHARSET_UTF_8        = CommonOps.CHARSET_UTF_8;

    public static final String   COMMA_LIST_TOKENIZER = ",";

    public static final String   COMMA_LIST_SEPARATOR = ", ";

    public static final String   NULL_STRING          = null;

    public static final String   EMPTY_STRING         = "";

    public static final String   NULL_AS_STRING       = "null";

    public static final String   EMPTY_ARRAY_STRING   = "[]";

    public static final String[] EMPTY_STRING_ARRAY   = new String[0];

    public static final String   HEXIDECIMAL_STRING   = "0123456789ABCDEF";

    private StringOps()
    {
    }

    public static final Supplier<String> toSupplier(final String value)
    {
        return () -> value;
    }

    public static final String[] toArray(final Collection<String> collection)
    {
        final int size = collection.size();

        if (size < 1)
        {
            return EMPTY_STRING_ARRAY;
        }
        return collection.toArray(new String[size]);
    }

    public static final String[] toArray(final String... collection)
    {
        if ((null == collection) || (collection.length < 1))
        {
            return EMPTY_STRING_ARRAY;
        }
        return collection;
    }

    public static final String[] toUniqueArray(final Collection<String> collection)
    {
        Objects.requireNonNull(collection);

        if (collection.isEmpty())
        {
            return EMPTY_STRING_ARRAY;
        }
        final ArrayList<String> uniq = new ArrayList<String>(collection.size());

        for (final String s : collection)
        {
            if ((null != s) && (false == uniq.contains(s)))
            {
                uniq.add(s);
            }
        }
        return toArray(uniq);
    }

    public static final String[] toUniqueArray(final String... collection)
    {
        if ((null == collection) || (collection.length < 1))
        {
            return EMPTY_STRING_ARRAY;
        }
        final ArrayList<String> uniq = new ArrayList<String>(collection.length);

        for (int i = 0; i < collection.length; i++)
        {
            final String s = collection[i];

            if ((null != s) && (false == uniq.contains(s)))
            {
                uniq.add(s);
            }
        }
        return toArray(uniq);
    }

    public static final Collection<String> toUnique(final Collection<String> collection)
    {
        Objects.requireNonNull(collection);

        if (collection.isEmpty())
        {
            return collection;
        }
        final ArrayList<String> uniq = new ArrayList<String>(collection.size());

        for (final String s : collection)
        {
            if ((null != s) && (false == uniq.contains(s)))
            {
                uniq.add(s);
            }
        }
        return uniq;
    }

    public static final List<String> toUniqueStringList(String strings)
    {
        strings = requireTrimOrNull(strings);

        if (strings.contains(COMMA_LIST_TOKENIZER))
        {
            return Arrays.asList(toUniqueArray(tokenizeToStringCollection(strings, COMMA_LIST_TOKENIZER)));
        }
        else
        {
            return Arrays.asList(toUniqueArray(strings));
        }
    }

    public static final List<String> toUniqueStringList(final String[] strings)
    {
        return Arrays.asList(toUniqueArray(Objects.requireNonNull(strings)));
    }

    public static final List<String> toUniqueStringList(final Collection<String> strings)
    {
        return Arrays.asList(toUniqueArray(Objects.requireNonNull(strings)));
    }

    public static final String toPrintableString(final Collection<String> collection)
    {
        if (null == collection)
        {
            return NULL_AS_STRING;
        }
        if (collection.isEmpty())
        {
            return EMPTY_ARRAY_STRING;
        }
        return toPrintableString(toArray(collection));
    }

    public static final String toCommaSeparated(final Collection<String> collection)
    {
        Objects.requireNonNull(collection);

        final StringBuilder b = new StringBuilder();

        final Iterator<String> i = collection.iterator();

        while (i.hasNext())
        {
            final String v = i.next();

            b.append(v);

            if (i.hasNext())
            {
                b.append(COMMA_LIST_SEPARATOR);
            }
        }
        return b.toString();
    }

    public static final String toCommaSeparated(final String... collection)
    {
        return toCommaSeparated(Arrays.asList(collection));
    }

    public static final Collection<String> tokenizeToStringCollection(final String string)
    {
        return tokenizeToStringCollection(string, true, true);
    }

    public static final Collection<String> tokenizeToStringCollection(final String string, final String delimiters)
    {
        return tokenizeToStringCollection(string, delimiters, true, true);
    }

    public static final Collection<String> tokenizeToStringCollection(final String string, final boolean trim, final boolean ignore)
    {
        return tokenizeToStringCollection(string, COMMA_LIST_TOKENIZER, trim, ignore);
    }

    public static final Collection<String> tokenizeToStringCollection(final String string, final String delimiters, final boolean trim, final boolean ignore)
    {
        if (null == string)
        {
            return null;
        }
        final StringTokenizer st = new StringTokenizer(string, delimiters);

        final ArrayList<String> li = new ArrayList<String>();

        while (st.hasMoreTokens())
        {
            String token = st.nextToken();

            if (trim)
            {
                token = token.trim();
            }
            if ((false == ignore) || (token.length() > 0))
            {
                li.add(token);
            }
        }
        return li;
    }

    public static final String toPrintableString(final String... list)
    {
        if (null == list)
        {
            return NULL_AS_STRING;
        }
        if (list.length == 0)
        {
            return EMPTY_ARRAY_STRING;
        }
        final StringBuilder builder = new StringBuilder();

        builder.append('[');

        for (final String item : list)
        {
            if (null != item)
            {
                builder.append('"').append(escapeForJavaScript(item)).append('"');
            }
            else
            {
                builder.append(NULL_AS_STRING);
            }
            builder.append(COMMA_LIST_SEPARATOR);
        }
        final int sepr = COMMA_LIST_SEPARATOR.length();

        final int leng = builder.length();

        final int tail = builder.lastIndexOf(COMMA_LIST_SEPARATOR);

        if ((tail >= 0) && (tail == (leng - sepr)))
        {
            builder.setLength(leng - sepr);
        }
        return builder.append(']').toString();
    }

    public static final String toTrimOrNull(String string)
    {
        if (null == string)
        {
            return null;
        }
        if ((string = string.trim()).isEmpty())
        {
            return null;
        }
        return string;
    }

    public static final String toTrimOrElse(String string, final String otherwise)
    {
        string = toTrimOrNull(string);

        if (null == string)
        {
            return otherwise;
        }
        return string;
    }

    public static final String toTrimOrElse(String string, final Supplier<String> otherwise)
    {
        string = toTrimOrNull(string);

        if (null == string)
        {
            return otherwise.get();
        }
        return string;
    }

    public static final String requireTrimOrNull(final String string)
    {
        return Objects.requireNonNull(toTrimOrNull(string));
    }

    public static final String requireTrimOrNull(final String string, final String reason)
    {
        return Objects.requireNonNull(toTrimOrNull(string), Objects.requireNonNull(reason));
    }

    public static final String requireTrimOrNull(String string, final Supplier<String> reason)
    {
        string = toTrimOrNull(string);

        if (Objects.isNull(string)) // This seems baroque. I'm avoiding code quality triggers, but conforming to GWT 2.8 emulation. DSJ - 05/23/17
        {
            Objects.requireNonNull(string, reason.get());
        }
        return string;
    }

    public static final boolean isDigits(final String string)
    {
        if (null == string)
        {
            return false;
        }
        final int leng = string.length();

        if (leng < 1)
        {
            return false;
        }
        for (int i = 0; i < leng; i++)
        {
            if (false == Character.isDigit(string.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    public static final boolean isAlpha(final String string)
    {
        if (null == string)
        {
            return false;
        }
        final int leng = string.length();

        if (leng < 1)
        {
            return false;
        }
        for (int i = 0; i < leng; i++)
        {
            if (false == Character.isLetter(string.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    public static final boolean isAlphaOrDigits(final String string)
    {
        if (null == string)
        {
            return false;
        }
        final int leng = string.length();

        if (leng < 1)
        {
            return false;
        }
        for (int i = 0; i < leng; i++)
        {
            if (false == Character.isLetterOrDigit(string.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    public static final boolean isAlphaOrDigitsStartsAlpha(final String string)
    {
        if (null == string)
        {
            return false;
        }
        final int leng = string.length();

        if (leng < 1)
        {
            return false;
        }
        if (false == Character.isLetter(string.charAt(0)))
        {
            return false;
        }
        for (int i = 1; i < leng; i++)
        {
            if (false == Character.isLetterOrDigit(string.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }

    public static final boolean isVersionID(String string)
    {
        if (null == (string = toTrimOrNull(string)))
        {
            return false;
        }
        int leng = string.length();

        if (leng < 3)
        {
            return false;
        }
        if (string.charAt(0) == 'v')
        {
            leng = (string = string.substring(1).trim()).length();

            if (leng < 3)
            {
                return false;
            }
        }
        int dots = 0;

        boolean digi = false;

        for (int i = 0; i < leng; i++)
        {
            final char c = string.charAt(i);

            if (false == Character.isDigit(c))
            {
                if (c == '.')
                {
                    if (false == digi)
                    {
                        return false;
                    }
                    digi = false;

                    dots++;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                digi = true;
            }
        }
        return ((true == digi) && (dots > 0));
    }

    public static final String reverse(final String string)
    {
        if (null == string)
        {
            return string;
        }
        if (string.length() < 2)
        {
            return string;
        }
        return new StringBuilder(string).reverse().toString();
    }

    public static final String escapeForJavaScript(final String string)
    {
        if (null == string)
        {
            return NULL_AS_STRING;
        }
        if (string.isEmpty())
        {
            return string;
        }
        return escapeForJavaScript(string, new StringBuilder()).toString();
    }

    public static final StringBuilder escapeForJavaScript(final String string, final StringBuilder builder)
    {
        if (null == string)
        {
            return builder.append(NULL_AS_STRING);
        }
        final int leng = string.length();

        for (int i = 0; i < leng; i++)
        {
            final char c = string.charAt(i);

            if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || (c == ' ') || ((c >= '0') && (c <= '9')))
            {
                builder.append(c); // ASCII will be most common, this improves write speed about 5%, FWIW.
            }
            else
            {
                switch (c)
                {
                    case '"':
                        builder.append("\\\"");
                        break;
                    case '\\':
                        builder.append("\\\\");
                        break;
                    case '\b':
                        builder.append("\\b");
                        break;
                    case '\f':
                        builder.append("\\f");
                        break;
                    case '\n':
                        builder.append("\\n");
                        break;
                    case '\r':
                        builder.append("\\r");
                        break;
                    case '\t':
                        builder.append("\\t");
                        break;
                    case '/':
                        builder.append("\\/");
                        break;
                    default:
                        // Reference: http://www.unicode.org/versions/Unicode5.1.0/

                        if (((c >= '\u0000') && (c <= '\u001F')) || ((c >= '\u007F') && (c <= '\u009F')) || ((c >= '\u2000') && (c <= '\u20FF')))
                        {
                            final String unic = Integer.toHexString(c);

                            final int size = 4 - unic.length();

                            builder.append("\\u");

                            for (int k = 0; k < size; k++)
                            {
                                builder.append('0');
                            }
                            builder.append(unic.toUpperCase());
                        }
                        else
                        {
                            builder.append(c);
                        }
                        break;
                }
            }
        }
        return builder;
    }
}
