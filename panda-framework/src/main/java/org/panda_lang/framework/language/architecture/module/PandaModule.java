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

package org.panda_lang.framework.language.architecture.module;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.framework.design.architecture.module.Module;
import org.panda_lang.framework.design.architecture.module.ReferencesMap;
import org.panda_lang.framework.design.architecture.prototype.Reference;
import org.panda_lang.framework.language.architecture.prototype.array.ArrayClassPrototypeFetcher;
import org.panda_lang.framework.language.architecture.prototype.array.PandaArray;
import org.panda_lang.utilities.commons.function.CachedSupplier;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Supplier;

public class PandaModule extends PandaModules implements Module {

    protected final String name;
    protected final ReferencesMap references;
    protected Module parent;

    public PandaModule(@Nullable Module parent, String name) {
        this.name = name;
        this.parent = parent;
        this.references = new PandaReferencesMap();
    }

    public PandaModule(String name) {
        this(null, name);
    }

    @Override
    public void add(String name, Class<?> associatedClass, Supplier<Reference> reference) {
        this.references.put(name, associatedClass, new CachedSupplier<>(reference));
    }

    @Override
    public int countUsedPrototypes() {
        return references.countUsedPrototypes();
    }

    @Override
    public int countReferences() {
        return references.size();
    }

    @Override
    public boolean isSubmodule(Module module) {
        Optional<Module> parentModule = module.getParent();

        while (parentModule.isPresent()) {
            Module parent = parentModule.get();

            if (parent.equals(this)) {
                return true;
            }

            parentModule = parent.getParent();
        }

        return false;
    }

    @Override
    public Optional<Reference> forClass(Class<?> associatedClass) {
        return references.forClass(associatedClass);
    }

    @Override
    public Optional<Reference> forName(CharSequence prototypeName) {
        if (name.endsWith(PandaArray.IDENTIFIER)) {
            return ArrayClassPrototypeFetcher.fetch(this, name);
        }

        return references.forName(prototypeName);
    }

    @Override
    public Collection<Entry<String, Supplier<Reference>>> getReferences() {
        Collection<Entry<String, Supplier<Reference>>> entries = references.getReferences();

        for (Module submodule : getModules()) {
            entries.addAll(submodule.getReferences());
        }

        return entries;
    }

    @Override
    public Optional<Module> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return (parent != null ? parent.toString() + ":" : "") + name;
    }

}