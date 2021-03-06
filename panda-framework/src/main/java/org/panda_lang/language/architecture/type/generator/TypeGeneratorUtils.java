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

package org.panda_lang.language.architecture.type.generator;

import org.panda_lang.language.architecture.module.Module;
import org.panda_lang.language.architecture.type.Type;
import org.panda_lang.language.architecture.module.TypeLoader;
import org.panda_lang.language.architecture.type.PandaPropertyParameter;

import java.lang.reflect.Parameter;

final class TypeGeneratorUtils {

    private TypeGeneratorUtils() { }

    static PandaPropertyParameter[] toParameters(TypeLoader typeLoader, Module module, Parameter[] parameters) {
        PandaPropertyParameter[] mappedParameters = new PandaPropertyParameter[parameters.length];

        for (int index = 0; index < parameters.length; index++) {
            Parameter parameter = parameters[index];
            Type type = typeLoader.load(module, parameter.getType());
            mappedParameters[index] = new PandaPropertyParameter(index, type, parameter.getName(), parameter.isVarArgs(), false, false);
        }

        return mappedParameters;
    }

}
