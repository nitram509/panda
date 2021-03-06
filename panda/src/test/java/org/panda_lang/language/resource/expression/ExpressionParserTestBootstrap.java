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

package org.panda_lang.language.resource.expression;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.panda_lang.language.PandaFrameworkException;
import org.panda_lang.language.architecture.expression.Expression;
import org.panda_lang.language.architecture.module.ModuleLoaderUtils;
import org.panda_lang.language.architecture.statement.VariableData;
import org.panda_lang.language.interpreter.parser.Components;
import org.panda_lang.language.interpreter.parser.Context;
import org.panda_lang.language.interpreter.parser.PandaParserFailure;
import org.panda_lang.language.interpreter.parser.expression.ExpressionParser;
import org.panda_lang.language.interpreter.token.Snippet;
import org.panda_lang.language.interpreter.token.SourceStream;
import org.panda_lang.language.architecture.statement.PandaVariableData;
import org.panda_lang.language.interpreter.lexer.PandaLexerUtils;
import org.panda_lang.language.interpreter.parser.expression.PandaExpressionParser;
import org.panda_lang.language.interpreter.token.PandaSourceStream;
import org.panda_lang.panda.language.resource.syntax.expressions.PandaExpressions;
import org.panda_lang.utilities.commons.StringUtils;

import java.util.HashMap;

class ExpressionParserTestBootstrap {

    private static ExpressionParser PARSER;
    private static Context DATA;

    @BeforeAll
    public static void load() {
        PARSER = new PandaExpressionParser(PandaExpressions.getExpressionSubparsers());
        DATA = prepareData();
    }

    @BeforeEach
    public void emptyLine() {
        System.out.println(StringUtils.EMPTY);
    }

    protected static Context prepareData() {
        return ExpressionContextUtils.createFakeContext(context -> new HashMap<VariableData, Object>() {{
            put(new PandaVariableData(ModuleLoaderUtils.requireType(context, String.class), "variable"), null);
            put(new PandaVariableData(ModuleLoaderUtils.requireType(context, String.class).toArray(context.getComponent(Components.TYPE_LOADER)), "array"), null);
            put(new PandaVariableData(ModuleLoaderUtils.requireType(context, int.class), "i", true, false), null);
        }});
    }

    protected static void parse(String source, String message) {
        parse(source, PandaParserFailure.class, message);
    }

    protected static void parse(String source, Class<? extends Throwable> clazz, String message) {
        Throwable throwable = Assertions.assertThrows(clazz, () -> parse(source));
        Assertions.assertEquals(message, throwable.getLocalizedMessage());
        System.out.println(source + ": " + message);
    }

    protected static void parse(String src) {
        Snippet source = PandaLexerUtils.convert(ExpressionParserTestBootstrap.class.getSimpleName(), src);
        SourceStream stream = new PandaSourceStream(source);

        DATA.withComponent(Components.SOURCE, source);
        DATA.withComponent(Components.CURRENT_SOURCE, source);
        DATA.withComponent(Components.STREAM, stream);

        Expression expression = PARSER.parse(DATA, stream).getExpression();

        if (stream.hasUnreadSource()) {
            throw new PandaFrameworkException("Unread source: " + stream.toSnippet());
        }

        System.out.println(source + ": " + expression);
    }

}
