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

package org.panda_lang.utilities.commons;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CamelCaseUtils {

    public static final String CAMEL_CASE_PATTERN = "(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])";

    private CamelCaseUtils() { }

    public static List<String> split(String camelCaseText) {
        return split(camelCaseText, element -> element);
    }

    public static List<String> split(String camelCaseText, Function<String, String> mapper) {
        return Arrays.stream(camelCaseText.split(CAMEL_CASE_PATTERN))
                .map(mapper)
                .collect(Collectors.toList());
    }

}
