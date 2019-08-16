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

package org.panda_lang.panda.framework.language.architecture.dynamic.block.looping;

import org.panda_lang.panda.framework.design.runtime.flow.Flow;
import org.panda_lang.panda.framework.design.runtime.expression.Expression;
import org.panda_lang.panda.framework.design.runtime.flow.ControlFlow;
import org.panda_lang.panda.framework.language.runtime.flow.PandaControlFlowCallback;
import org.panda_lang.panda.framework.language.architecture.dynamic.AbstractBlock;

public class WhileBlock extends AbstractBlock implements PandaControlFlowCallback {

    private final Expression expression;

    public WhileBlock(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute(Flow flow) {
        flow.callFlow(super.getStatementCells(), this);
    }

    @Override
    public void call(ControlFlow controlFlow, Flow flow) {
        while (expression.evaluate(flow).getValue()) {
            controlFlow.reset();
            controlFlow.call();

            if (controlFlow.isEscaped() || flow.isInterrupted()) {
                break;
            }
        }
    }

}

