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

package org.panda_lang.panda.framework.language.resource.parsers.overall.prototype.parameter;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.panda.framework.design.architecture.prototype.parameter.Parameter;
import org.panda_lang.panda.framework.design.interpreter.parser.Parser;
import org.panda_lang.panda.framework.design.interpreter.parser.ParserData;
import org.panda_lang.panda.framework.design.interpreter.token.Token;
import org.panda_lang.panda.framework.design.interpreter.token.TokenRepresentation;
import org.panda_lang.panda.framework.design.interpreter.token.TokenType;
import org.panda_lang.panda.framework.design.interpreter.token.snippet.Snippet;
import org.panda_lang.panda.framework.design.interpreter.token.snippet.SnippetUtils;
import org.panda_lang.panda.framework.language.architecture.module.ModuleLoaderUtils;
import org.panda_lang.panda.framework.language.architecture.prototype.standard.parameter.PandaParameter;
import org.panda_lang.panda.framework.language.interpreter.parser.PandaParserException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParameterParser implements Parser {

    public List<Parameter> parse(ParserData data, @Nullable Snippet snippet) {
        if (SnippetUtils.isEmpty(snippet)) {
            return Collections.emptyList();
        }

        TokenRepresentation[] tokenRepresentations = snippet.toArray();
        List<Parameter> parameters = new ArrayList<>(tokenRepresentations.length / 3 + 1);

        if (snippet.size() == 0) {
            return parameters;
        }

        for (int i = 0; i + 1 < tokenRepresentations.length; i += 3) {
            TokenRepresentation parameterTypeRepresentation = tokenRepresentations[i];
            TokenRepresentation parameterNameRepresentation = tokenRepresentations[i + 1];

            String parameterType = parameterTypeRepresentation.getToken().getValue();
            String parameterName = parameterNameRepresentation.getToken().getValue();

            Parameter parameter = new PandaParameter(ModuleLoaderUtils.getReferenceOrThrow(data, parameterType, snippet), parameterName);
            parameters.add(parameter);

            if (i + 2 < tokenRepresentations.length) {
                TokenRepresentation separatorRepresentation = tokenRepresentations[i + 2];
                Token separator = separatorRepresentation.getToken();

                if (separator.getType() != TokenType.SEPARATOR) {
                    throw new PandaParserException("Unexpected token " + separatorRepresentation);
                }
            }
        }

        return parameters;
    }

}