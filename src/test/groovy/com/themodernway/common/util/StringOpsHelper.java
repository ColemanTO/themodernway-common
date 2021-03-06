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

package com.themodernway.common.util;

import java.util.Collection;
import java.util.List;

import com.themodernway.common.api.java.util.StringOps;

public class StringOpsHelper
{
    private String[] m_names;

    public StringOpsHelper(final String... names)
    {
        m_names = names;
    }

    String[] getNamesArray()
    {
        return m_names;
    }

    void setNamesArray(final String[] names)
    {
        m_names = names;
    }

    public List<String> getNames()
    {
        return StringOps.getSupplierUniqueStringArray(this::getNamesArray);
    }

    public void setNames(final Collection<String> names)
    {
        StringOps.setConsumerUniqueStringArray(names, this::setNamesArray);
    }

    public void setNamesByTokens(final String names)
    {
        StringOps.setConsumerUniqueStringArray(names, this::setNamesArray);
    }
}
