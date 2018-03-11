/*
 * Copyright (c) 2015-2018 Dzikoysk
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

package org.panda_lang.panda.mapper;

import org.junit.Test;
import org.panda_lang.panda.Panda;
import org.panda_lang.panda.bootstrap.PandaBootstrap;
import org.panda_lang.panda.framework.language.PandaFramework;
import org.panda_lang.panda.language.structure.prototype.mapper.ClassPrototypeMappingManager;
import org.panda_lang.panda.framework.design.architecture.prototype.ClassPrototype;
import org.panda_lang.panda.language.syntax.PandaSyntax;

import java.util.Collection;

public class ClassPrototypeMapperTest {

    @Test
    public void testMapper() {
        Panda panda = new PandaBootstrap()
                .syntax(PandaSyntax.getInstance())
                .get();

        ClassPrototypeMappingManager mappingManager = new ClassPrototypeMappingManager();
        mappingManager.loadClass(TestClass.class);

        Collection<ClassPrototype> prototypes = mappingManager.generate();

        for (ClassPrototype prototype : prototypes) {
            PandaFramework.getLogger().info(prototype.toString());
        }
    }

    class TestClass {

    }

}