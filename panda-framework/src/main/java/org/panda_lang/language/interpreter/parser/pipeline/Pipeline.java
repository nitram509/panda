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

package org.panda_lang.language.interpreter.parser.pipeline;

import org.panda_lang.language.Failure;
import org.panda_lang.language.interpreter.parser.Context;
import org.panda_lang.language.interpreter.parser.LocalChannel;
import org.panda_lang.language.interpreter.parser.Parser;
import org.panda_lang.language.interpreter.parser.ParserRepresentation;
import org.panda_lang.language.interpreter.token.Snippet;
import org.panda_lang.utilities.commons.function.Option;
import org.panda_lang.utilities.commons.function.Result;

import java.util.Collection;

public interface Pipeline<P extends Parser> {

    /**
     * Search for the parser through the pipeline with untouched source
     *
     * @param context the current context
     * @param channel the channel between handler and parser
     * @param source the source
     * @return parser which fits to the source
     */
    Result<P, Option<Failure>> handle(Context context, LocalChannel channel, Snippet source);

    /**
     * Register the specified parser to
     *
     * @param parserRepresentation specified parser representation which will be registered in the pipeline
     */
    void register(ParserRepresentation<P> parserRepresentation);

    /**
     * @return a collection of registered parser
     */
    Collection<? extends ParserRepresentation<P>> getRepresentations();

    /**
     * Get total time of data handling
     *
     * @return total handle time in nanoseconds
     */
    long getHandleTime();

    /**
     * Get name of pipeline
     *
     * @return the name
     */
    String getName();

}
