/*
 * Copyright (c) 2018, The Modern Way. All rights reserved.
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

package com.themodernway.common.api.hash;

import com.themodernway.common.api.java.util.CommonOps;
import com.themodernway.common.api.java.util.StringOps;

public final class Hasher implements IHasher
{
    private final IHash512 m_hash;

    public Hasher(final IHash512 hash)
    {
        m_hash = CommonOps.requireNonNull(hash);
    }

    @Override
    public String sha512(final String text)
    {
        return m_hash.sha512(CommonOps.requireNonNull(text));
    }

    @Override
    public String sha512(final String text, final String salt)
    {
        return sha512(CommonOps.requireNonNull(text), salt, (salt.length() + 1) * 2);
    }

    @Override
    public String sha512(String text, final String salt, int iter)
    {
        CommonOps.requireNonNull(text);

        CommonOps.requireNonNull(salt);

        if (iter < 2)
        {
            return sha512(text + salt);
        }
        if ((iter < 5) || (iter > 25))
        {
            iter = 13;
        }
        for (int i = 0; i < iter; i++)
        {
            if ((i % 2) == 0)
            {
                text = text + salt;
            }
            else
            {
                text = salt + text;
            }
            text = StringOps.reverse(sha512(text));
        }
        return text;
    }
}
