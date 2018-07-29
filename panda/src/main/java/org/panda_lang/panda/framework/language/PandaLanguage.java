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

package org.panda_lang.panda.framework.language;

import org.panda_lang.panda.Panda;
import org.panda_lang.panda.framework.design.interpreter.parser.pipeline.ParserRegistrationLoader;
import org.panda_lang.panda.framework.design.interpreter.lexer.Syntax;
import org.panda_lang.panda.framework.design.interpreter.parser.pipeline.registry.PipelineRegistry;
import org.panda_lang.panda.framework.language.interpreter.PandaSyntax;
import org.panda_lang.panda.framework.language.interpreter.parsers.prototype.registry.ClassPrototypeModel;

import java.util.Collection;

public class PandaLanguage implements Language {

    private final PipelineRegistry pipelineRegistry;
    private Syntax syntax;
    private Collection<Collection<Class<? extends ClassPrototypeModel>>> mappings;

    public PandaLanguage() {
        this.syntax = PandaSyntax.getInstance();

        ParserRegistrationLoader parserRegistrationLoader = new ParserRegistrationLoader();
        this.pipelineRegistry = parserRegistrationLoader.load(Panda.class);
    }

    public void setMappings(Collection<Collection<Class<? extends ClassPrototypeModel>>> mappings) {
        this.mappings = mappings;
    }

    public void setSyntax(Syntax syntax) {
        this.syntax = syntax;
    }

    public Collection<Collection<Class<? extends ClassPrototypeModel>>> getMappings() {
        return mappings;
    }

    @Override
    public PipelineRegistry getParserPipelineRegistry() {
        return pipelineRegistry;
    }

    @Override
    public Syntax getSyntax() {
        return syntax;
    }

}
