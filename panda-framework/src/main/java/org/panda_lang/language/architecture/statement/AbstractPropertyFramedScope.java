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

package org.panda_lang.language.architecture.statement;

import org.panda_lang.language.architecture.type.PropertyParameter;
import org.panda_lang.language.interpreter.source.Location;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPropertyFramedScope extends AbstractFramedScope implements StandardizedFramedScope {

    protected final List<PropertyParameter> parameters;

    public AbstractPropertyFramedScope(Location location, List<PropertyParameter> parameters) {
        super(location);
        this.parameters = parameters;
        this.addParameters(parameters);
    }

    protected List<? extends Variable> addParameters(List<? extends PropertyParameter> parameters) {
        List<Variable> variables = new ArrayList<>();

        for (PropertyParameter parameter : parameters) {
            variables.add(createVariable(parameter));
        }

        return variables;
    }

    public List<? extends PropertyParameter> getParameters() {
        return parameters;
    }

}
