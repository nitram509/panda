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

package org.panda_lang.panda.language.interpreter.parsers.prototype.parsers.constructor;

import org.panda_lang.panda.framework.design.architecture.prototype.ClassPrototype;
import org.panda_lang.panda.design.architecture.prototype.constructor.ConstructorScope;
import org.panda_lang.panda.framework.design.architecture.prototype.constructor.PrototypeConstructor;
import org.panda_lang.panda.design.architecture.prototype.constructor.PandaConstructor;
import org.panda_lang.panda.framework.design.architecture.prototype.parameter.Parameter;
import org.panda_lang.panda.design.architecture.prototype.parameter.ParameterUtils;
import org.panda_lang.panda.design.interpreter.parser.defaults.ScopeParser;
import org.panda_lang.panda.design.interpreter.parser.generation.CasualParserGenerationAssistant;
import org.panda_lang.panda.design.interpreter.parser.linker.PandaScopeLinker;
import org.panda_lang.panda.design.interpreter.parser.linker.ScopeLinker;
import org.panda_lang.panda.language.interpreter.parsers.PandaPipelines;
import org.panda_lang.panda.design.interpreter.parser.pipeline.registry.ParserRegistration;
import org.panda_lang.panda.design.interpreter.parser.PandaComponents;
import org.panda_lang.panda.design.interpreter.token.AbyssPatternAssistant;
import org.panda_lang.panda.design.interpreter.token.AbyssPatternBuilder;
import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.parser.UnifiedParser;
import org.panda_lang.panda.framework.design.interpreter.parser.generation.casual.CasualParserGenerationCallback;
import org.panda_lang.panda.framework.design.interpreter.parser.generation.casual.CasualParserGenerationLayer;
import org.panda_lang.panda.framework.design.interpreter.parser.generation.util.LocalCallback;
import org.panda_lang.panda.framework.design.interpreter.token.TokenizedSource;
import org.panda_lang.panda.framework.language.interpreter.token.pattern.abyss.AbyssPattern;
import org.panda_lang.panda.framework.language.interpreter.token.pattern.abyss.redactor.AbyssRedactor;
import org.panda_lang.panda.language.interpreter.parsers.prototype.parsers.parameter.ParameterParser;
import org.panda_lang.panda.language.interpreter.parsers.prototype.scope.ClassScope;
import org.panda_lang.panda.language.interpreter.PandaSyntax;

import java.util.List;

@ParserRegistration(target = PandaPipelines.PROTOTYPE, parserClass = ConstructorParser.class, handlerClass = ConstructorParserHandler.class)
public class ConstructorParser implements UnifiedParser {

    protected static final AbyssPattern PATTERN = new AbyssPatternBuilder()
            .compile(PandaSyntax.getInstance(), "constructor ( +** ) { +* }")
            .build();

    @Override
    public void parse(ParserData data) {
        CasualParserGenerationAssistant.delegateImmediately(data, new ConstructorExtractorCallbackCasual());
    }

    @LocalCallback
    private static class ConstructorExtractorCallbackCasual implements CasualParserGenerationCallback {

        @Override
        public void call(ParserData delegatedData, CasualParserGenerationLayer nextLayer) {
            AbyssRedactor redactor = AbyssPatternAssistant.traditionalMapping(PATTERN, delegatedData, "parameters", "constructor-body");
            delegatedData.setComponent("redactor", redactor);

            TokenizedSource parametersSource = redactor.get("parameters");
            ParameterParser parameterParser = new ParameterParser();
            List<Parameter> parameters = parameterParser.parse(delegatedData, parametersSource);

            ConstructorScope constructorScope = new ConstructorScope(parameters);
            ParameterUtils.addAll(constructorScope.getVariables(), parameters, 0);
            delegatedData.setComponent("constructor-scope", constructorScope);

            ClassPrototype prototype = delegatedData.getComponent("class-prototype");
            ClassScope classScope = delegatedData.getComponent("class-scope");

            PrototypeConstructor constructor = new PandaConstructor(prototype, classScope, constructorScope);
            delegatedData.setComponent("constructor", constructor);
            prototype.getConstructors().add(constructor);

            nextLayer.delegateAfter(new ConstructorBodyCallbackCasual(), delegatedData);
        }

    }

    @LocalCallback
    private static class ConstructorBodyCallbackCasual implements CasualParserGenerationCallback {

        @Override
        public void call(ParserData delegatedData, CasualParserGenerationLayer nextLayer) {
            ClassScope classScope = delegatedData.getComponent("class-scope");

            ConstructorScope constructorScope = delegatedData.getComponent("constructor-scope");
            delegatedData.setComponent("scope", constructorScope);

            ScopeLinker linker = new PandaScopeLinker(classScope);
            linker.pushScope(constructorScope);
            delegatedData.setComponent(PandaComponents.SCOPE_LINKER, linker);

            AbyssRedactor redactor = delegatedData.getComponent("redactor");
            TokenizedSource body = redactor.get("constructor-body");

            ScopeParser scopeParser = new ScopeParser(constructorScope);
            scopeParser.parse(delegatedData, body);
        }

    }

}