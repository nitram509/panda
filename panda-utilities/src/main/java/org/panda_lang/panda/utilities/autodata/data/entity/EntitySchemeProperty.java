/*
 * Copyright (c) 2015-2019 Dzikoysk
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

package org.panda_lang.panda.utilities.autodata.data.entity;

import org.panda_lang.panda.utilities.commons.annotations.Annotations;

import java.lang.reflect.Method;

public final class EntitySchemeProperty {

    private final String name;
    private final Class<?> type;
    private final Annotations annotations;
    private final Method associatedMethod;

    EntitySchemeProperty(String name, Class<?> type, Annotations annotations, Method associatedMethod) {
        this.name = name;
        this.type = type;
        this.annotations = annotations;
        this.associatedMethod = associatedMethod;
    }

    Method getAssociatedMethod() {
        return associatedMethod;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public Class<?> getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}