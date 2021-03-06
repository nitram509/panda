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

package org.panda_lang.panda.language.resource.syntax.scope;

import org.panda_lang.language.architecture.dynamic.AbstractExecutableStatement;
import org.panda_lang.language.architecture.expression.Expression;
import org.panda_lang.language.architecture.expression.ExpressionUtils;
import org.panda_lang.language.interpreter.logging.Logger;
import org.panda_lang.language.interpreter.source.Location;
import org.panda_lang.language.runtime.ProcessStack;
import org.panda_lang.utilities.commons.text.ContentJoiner;

final class LogStatement extends AbstractExecutableStatement {

    private final Logger logger;
    private final Expression[] expressions;

    LogStatement(Location location, Logger logger, Expression[] expressions) {
        super(location);
        this.logger = logger;
        this.expressions = expressions;
    }

    @Override
    public Object execute(ProcessStack stack, Object instance) throws Exception {
        Object[] values = ExpressionUtils.evaluate(stack, instance, expressions);
        logger.info(ContentJoiner.on(", ").join(values).toString());
        return values;
    }

}
