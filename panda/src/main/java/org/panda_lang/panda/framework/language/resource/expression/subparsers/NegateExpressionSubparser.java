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

package org.panda_lang.panda.framework.language.resource.expression.subparsers;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.panda.framework.language.interpreter.parser.expression.AbstractExpressionSubparserWorker;
import org.panda_lang.panda.framework.design.interpreter.token.TokenRepresentation;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.design.interpreter.parser.expression.ExpressionContext;
import org.panda_lang.panda.framework.design.interpreter.parser.expression.ExpressionResult;
import org.panda_lang.panda.framework.design.interpreter.parser.expression.ExpressionSubparser;
import org.panda_lang.panda.framework.design.interpreter.parser.expression.ExpressionSubparserWorker;
import org.panda_lang.panda.framework.language.resource.syntax.operator.Operators;

public class NegateExpressionSubparser implements ExpressionSubparser {

    @Override
    public ExpressionSubparserWorker createWorker() {
        return new NegateWorker().withSubparser(this);
    }

    @Override
    public int getMinimalRequiredLengthOfSource() {
        return 2;
    }

    @Override
    public String getSubparserName() {
        return "negate";
    }

    private static class NegateWorker extends AbstractExpressionSubparserWorker {

        @Override
        public @Nullable ExpressionResult next(ExpressionContext context, TokenRepresentation token) {
            if (!token.contentEquals(Operators.NOT)) {
                return null;
            }

            Expression expression = context.getParser().parse(context.getContext(), context.getDiffusedSource());
            return ExpressionResult.of(new NegateLogicalExpression(expression).toExpression());
        }

    }

}