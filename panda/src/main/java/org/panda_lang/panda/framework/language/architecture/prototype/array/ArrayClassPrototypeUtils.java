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

package org.panda_lang.panda.framework.language.architecture.prototype.array;

import org.panda_lang.panda.framework.design.architecture.module.ModuleLoader;
import org.panda_lang.panda.framework.design.architecture.prototype.ClassPrototype;
import org.panda_lang.panda.utilities.commons.ArrayUtils;
import org.panda_lang.panda.utilities.commons.StringUtils;

public class ArrayClassPrototypeUtils {

    public static ArrayClassPrototype obtain(ModuleLoader loader, String arrayName) {
        ClassPrototype prototype = loader.forClass(arrayName.replace(PandaArray.IDENTIFIER, StringUtils.EMPTY));

        if (prototype == null) {
            return null;
        }

        int dimensions = StringUtils.countOccurrences(arrayName, PandaArray.IDENTIFIER);
        Class<?> arrayType = ArrayUtils.getDimensionalArrayType(prototype.getAssociated(), dimensions);
        Class<?> arrayClass = ArrayUtils.getArrayClass(arrayType);

        ArrayClassPrototype arrayPrototype = new ArrayClassPrototype(arrayClass, arrayType);
        loader.get(null).add(arrayPrototype);

        return arrayPrototype;
    }

}