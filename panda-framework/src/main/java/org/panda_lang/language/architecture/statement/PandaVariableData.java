/*
 * Copyright (c) 2020 Dzikoysk
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

package org.panda_lang.language.architecture.statement;

import org.panda_lang.language.architecture.type.Type;

public class PandaVariableData implements VariableData {

    private final String name;
    private final Type type;
    private final boolean mutable;
    private final boolean nillable;

    public PandaVariableData(Type type, String name, boolean mutable, boolean nillable) {
        if (type == null) {
            throw new IllegalArgumentException("Variable type cannot be null");
        }

        if (name == null) {
            throw new IllegalArgumentException("Variable name cannot be null");
        }

        this.name = name;
        this.type = type;
        this.mutable = mutable;
        this.nillable = nillable;
    }

    public PandaVariableData(Type type, String name) {
        this(type, name, true, true);
    }

    @Override
    public boolean isNillable() {
        return nillable;
    }

    @Override
    public boolean isMutable() {
        return mutable;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

}
