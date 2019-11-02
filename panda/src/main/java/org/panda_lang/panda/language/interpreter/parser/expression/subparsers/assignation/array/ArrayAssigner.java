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

package org.panda_lang.panda.language.interpreter.parser.expression.subparsers.assignation.array;

import org.panda_lang.framework.design.architecture.expression.Expression;
import org.panda_lang.framework.design.architecture.prototype.Prototype;
import org.panda_lang.framework.design.runtime.ProcessStack;
import org.panda_lang.framework.language.architecture.expression.DynamicExpression;
import org.panda_lang.framework.language.runtime.PandaRuntimeException;

public final class ArrayAssigner implements DynamicExpression {

    private final ArrayAccessor accessor;
    private final Expression value;

    public ArrayAssigner(ArrayAccessor accessor, Expression value) {
        this.accessor = accessor;
        this.value = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object evaluate(ProcessStack stack, Object instance) throws Exception {
        Integer index = accessor.getIndex(stack, instance);

        if (index == null) {
            throw new PandaRuntimeException("Index cannot be null");
        }

        return accessor.getArrayInstance(stack, instance)[index] = value.evaluate(stack, instance);
    }

    @Override
    public Prototype getReturnType() {
        return accessor.getReturnType();
    }

}
