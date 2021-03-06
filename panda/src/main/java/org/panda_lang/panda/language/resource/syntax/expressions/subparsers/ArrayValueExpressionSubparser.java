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

package org.panda_lang.panda.language.resource.syntax.expressions.subparsers;

import org.jetbrains.annotations.Nullable;
import org.panda_lang.language.architecture.expression.Expression;
import org.panda_lang.language.interpreter.parser.Context;
import org.panda_lang.language.interpreter.parser.expression.ExpressionContext;
import org.panda_lang.language.interpreter.parser.expression.ExpressionResult;
import org.panda_lang.language.interpreter.parser.expression.ExpressionSubparser;
import org.panda_lang.language.interpreter.parser.expression.ExpressionSubparserWorker;
import org.panda_lang.language.interpreter.parser.expression.ExpressionTransaction;
import org.panda_lang.language.interpreter.token.TokenInfo;
import org.panda_lang.language.resource.syntax.TokenTypes;
import org.panda_lang.language.resource.syntax.auxiliary.Section;
import org.panda_lang.language.resource.syntax.separator.Separators;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.array.ArrayAccessor;
import org.panda_lang.panda.language.resource.syntax.expressions.subparsers.assignation.array.ArrayValueAccessorParser;

public final class ArrayValueExpressionSubparser implements ExpressionSubparser {

    @Override
    public ExpressionSubparserWorker createWorker(Context context) {
        return new ArrayValueWorker().withSubparser(this);
    }

    @Override
    public String getSubparserName() {
        return "array-value";
    }

    private static final class ArrayValueWorker extends AbstractExpressionSubparserWorker {

        private static final ArrayValueAccessorParser PARSER = new ArrayValueAccessorParser();

        @Override
        public @Nullable ExpressionResult next(ExpressionContext context, TokenInfo token) {
            // require instance
            if (!context.hasResults()) {
                return null;
            }

            // require section token
            if (token.getType() != TokenTypes.SECTION) {
                return null;
            }

            Section section = token.toToken();

            // require square bracket section
            if (!section.getSeparator().equals(Separators.SQUARE_BRACKET_LEFT)) {
                return null;
            }

            // require index
            if (section.getContent().isEmpty()) {
                return ExpressionResult.error("Missing index expression", section.getOpeningSeparator());
            }

            Expression instance = context.getResults().pop();

            // expression cannot be null
            if (instance.isNull()) {
                return null;
            }

            // require array type
            if (!instance.getType().isArray()) {
                ExpressionResult.error("Cannot use array index on non-array return type", section.getContent());
            }

            ExpressionTransaction indexTransaction = context.getParser().parse(context.toContext(), section.getContent());
            context.commit(indexTransaction::rollback);
            Expression indexExpression = indexTransaction.getExpression();

            // require int as index
            if (!indexExpression.getType().getAssociatedClass().isAssignableTo(Integer.class)) {
                return ExpressionResult.error("Index of array has to be Integer", section.getContent());
            }

            // access the value
            ArrayAccessor accessor = PARSER.of(context.toContext(), section.getContent(), instance, indexExpression);
            return ExpressionResult.of(accessor.toExpression());
        }

    }

}
