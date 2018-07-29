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

package org.panda_lang.panda.framework.language.interpreter.parsers.general.expression.callbacks.math;

import org.panda_lang.panda.framework.design.architecture.module.*;
import org.panda_lang.panda.framework.design.runtime.*;
import org.panda_lang.panda.framework.design.architecture.prototype.*;
import org.panda_lang.panda.framework.design.architecture.value.*;
import org.panda_lang.panda.framework.design.interpreter.token.*;
import org.panda_lang.panda.framework.design.runtime.expression.*;
import org.panda_lang.panda.framework.language.architecture.value.*;

import java.util.*;

public class MathExpressionCallback implements ExpressionCallback {

    private final ModuleRegistry registry;
    private final Stack<Object> mathStack;

    public MathExpressionCallback(ModuleRegistry registry, Stack<Object> mathStack) {
        this.mathStack = mathStack;
        this.registry = registry;
    }

    @Override
    public Value call(Expression expression, ExecutableBranch branch) {
        Stack<Value> values = new Stack<>();

        for (Object mathElement : mathStack) {
            if (mathElement instanceof Token) {
                Token operator = (Token) mathElement;

                Value a = values.pop();
                Value b = values.pop();

                // TODO: Impl add()/subtract()/multiply()/divide() methods for diff prototypes
                int aValue = a.getValue();
                int bValue = b.getValue();
                int cValue;

                // TODO: Dedicated callback for each type of operators
                switch (operator.getTokenValue()) {
                    case "+":
                        cValue = aValue + bValue;
                        break;
                    case "-":
                        cValue = aValue - bValue;
                        break;
                    case "*":
                        cValue = aValue * bValue;
                        break;
                    case "/":
                        cValue = aValue / bValue;
                        break;
                    default:
                        throw new PandaRuntimeException("Unknown operator");
                }

                Value c = new PandaValue(PandaModuleRegistryAssistant.forName(registry, "int"), cValue);
                values.push(c);
            }
            else {
                Expression mathExpression = (Expression) mathElement;
                values.push(mathExpression.getExpressionValue(branch));
            }
        }

        return values.pop();
    }

    public ClassPrototype getReturnType() {
        return PandaModuleRegistryAssistant.forName(registry, "int");
    }

}