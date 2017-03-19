/*
 * Copyright (c) 2015-2017 Dzikoysk
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

package org.panda_lang.panda.implementation.structure;

import org.panda_lang.framework.structure.Statement;

import java.util.List;

public interface Script {

    /**
     * @return selected statements by the specified class
     */
    <T extends Statement> List<T> select(Class<? extends T> statementClass);

    /**
     * @return list of the statement declarations
     */
    List<Statement> getStatements();

    /**
     * @return the script name, e.g. name of file or generated name
     */
    String getScriptName();

}