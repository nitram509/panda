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

package org.panda_lang.panda.language.interpreter.parser.autowired.handlers;

import org.jetbrains.annotations.NotNull;
import org.panda_lang.language.interpreter.parser.Context;
import org.panda_lang.language.interpreter.parser.LocalChannel;
import org.panda_lang.language.interpreter.parser.pipeline.Handler;
import org.panda_lang.language.interpreter.pattern.linear.LinearPattern;
import org.panda_lang.language.interpreter.token.Snippet;

public final class LinearPatternHandler implements Handler {

    private final LinearPattern linearPattern;

    public LinearPatternHandler(LinearPattern linearPattern) {
        this.linearPattern = linearPattern;
    }

    @Override
    public @NotNull Object handle(Context context, LocalChannel channel, Snippet source) {
        return linearPattern
                .match(source)
                .isMatched();
    }


}
