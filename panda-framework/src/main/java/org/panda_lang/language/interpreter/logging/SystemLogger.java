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

package org.panda_lang.language.interpreter.logging;

import org.panda_lang.utilities.commons.console.Effect;

public final class SystemLogger implements Logger {

    private final Channel threshold;
    private final ErrorFormatter errorFormatter = new ErrorFormatter(this);

    public SystemLogger() {
        this(Channel.INFO);
    }

    public SystemLogger(Channel threshold) {
        this.threshold = threshold;
    }

    @Override
    public void log(Channel channel, String message) {
        if (channel.getPriority() >= threshold.getPriority()) {
            System.out.println(Effect.paint(message));
        }
    }

    @Override
    public void error(String message) {
        log(Channel.ERROR, "# " + message);
    }

    @Override
    public void exception(Throwable throwable) {
        errorFormatter.print(throwable);
    }

}
