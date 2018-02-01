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

package org.panda_lang.panda.language.structure.prototype.mapper;

import org.panda_lang.panda.Panda;
import org.panda_lang.panda.language.runtime.PandaRuntimeException;
import org.panda_lang.panda.language.structure.prototype.mapper.loaders.ClassPrototypeMappingAnnotationLoader;

import java.util.ArrayList;
import java.util.Collection;

public class ClassPrototypeMappingManager {

    private final Panda panda;
    private final Collection<Class<?>> loadedClasses;

    public ClassPrototypeMappingManager(Panda panda) {
        this.panda = panda;
        this.loadedClasses = new ArrayList<>();
    }

    public void loadAnnotatedMappings() {
        ClassPrototypeMappingAnnotationLoader loader = new ClassPrototypeMappingAnnotationLoader(this);
        Collection<Class<?>> classes = loader.load();

        for (Class<?> clazz : classes) {
            this.loadClass(clazz);
        }
    }

    private void loadPackageMappings(String packageName) {
        throw new PandaRuntimeException("Not implemented");
    }

    private void loadClass(Class<?> clazz) {
        loadedClasses.add(clazz);
    }

    public Collection<Class<?>> loadedClasses() {
        return loadedClasses;
    }

    public void generate() {
        ClassPrototypeMappingGenerator generator = new ClassPrototypeMappingGenerator(this);
        generator.generate();
    }

    public Panda getPanda() {
        return panda;
    }

}
