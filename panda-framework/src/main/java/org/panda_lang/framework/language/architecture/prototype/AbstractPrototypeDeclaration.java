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

package org.panda_lang.framework.language.architecture.prototype;

import org.panda_lang.framework.design.architecture.module.Module;
import org.panda_lang.framework.design.architecture.module.ModuleLoader;
import org.panda_lang.framework.design.architecture.prototype.Declaration;
import org.panda_lang.framework.design.architecture.prototype.State;
import org.panda_lang.framework.design.architecture.prototype.Reference;
import org.panda_lang.framework.design.architecture.prototype.Visibility;
import org.panda_lang.framework.design.interpreter.source.Source;
import org.panda_lang.framework.language.architecture.prototype.array.ArrayClassPrototypeFetcher;

import java.util.ArrayList;
import java.util.Collection;

abstract class AbstractPrototypeDeclaration implements Declaration {

    protected final String name;
    protected final Module module;
    protected final Source source;
    protected final Class<?> associated;
    protected final State state;
    protected final Visibility visibility;
    protected final Collection<Reference> supers = new ArrayList<>(1);

    protected AbstractPrototypeDeclaration(String name, Module module, Source source, Class<?> associated, State state, Visibility visibility) {
        this.name = name;
        this.module = module;
        this.source = source;
        this.associated = associated;
        this.state = state;
        this.visibility = visibility;
    }

    @Override
    public void addSuper(Reference reference) {
        supers.add(reference);
    }

    @Override
    public boolean isClassOf(String className) {
        if (this.getName().equals(className)) {
            return true;
        }

        return this.associated != null && this.associated.getSimpleName().equals(className);
    }

    @Override
    public boolean isAssignableFrom(Declaration prototype) { // this (Panda Class | Java Class) isAssociatedWith
        if (prototype == null) {
            return true;
        }

        return prototype.equals(this)
                || PandaPrototypeUtils.isAssignableFrom(associated, prototype.getAssociatedClass())
                || PandaPrototypeUtils.hasCommonPrototypes(supers, prototype.getSupers());
    }

    @Override
    public Reference toArray(ModuleLoader loader) {
        return ArrayClassPrototypeFetcher.getArrayOf(getModule(), this, 1);
    }

    @Override
    public Collection<Reference> getSupers() {
        return supers;
    }

    @Override
    public Visibility getVisibility() {
        return visibility;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public Class<?> getAssociatedClass() {
        return associated;
    }

    @Override
    public Source getSource() {
        return source;
    }

    @Override
    public Module getModule() {
        return module;
    }

    @Override
    public String getName() {
        return name;
    }

}