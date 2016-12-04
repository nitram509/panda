/*
 * Copyright (c) 2015-2016 Dzikoysk
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

package org.panda_lang.framework.interpreter.parser;

import org.panda_lang.framework.interpreter.lexer.TokenReader;

public interface ParserPipeline {

    /**
     * @param tokenReader source
     * @return parser which fits to source
     */
    UnifiedParser handle(TokenReader tokenReader);

    /**
     * @param parserRepresentation specified parser representation which will be registered in pipeline
     */
    void registerParserRepresentation(ParserRepresentation parserRepresentation);

}