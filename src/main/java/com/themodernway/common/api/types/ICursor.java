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

package com.themodernway.common.api.types;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

import com.themodernway.common.api.java.util.CommonOps;

public interface ICursor<T> extends Iterator<T>, IFailable, Closeable
{
    default public <A extends Collection<? super T>> A into(final A target)
    {
        CommonOps.requireNonNull(target);

        while (hasNext())
        {
            target.add(next());
        }
        try
        {
            close();
        }
        catch (final IOException e)
        {
            onFailure(e);
        }
        return target;
    }

    @Override
    default public void forEachRemaining(final Consumer<? super T> action)
    {
        CommonOps.requireNonNull(action);

        while (hasNext())
        {
            action.accept(next());
        }
        try
        {
            close();
        }
        catch (final IOException e)
        {
            onFailure(e);
        }
    }

    @Override
    default public void onFailure(final Throwable throwable)
    {
        throwable.printStackTrace();
    }

    @Override
    default public void close() throws IOException
    {
    }

    @Override
    default public void remove()
    {
        throw new UnsupportedOperationException("remove()");
    }
}
