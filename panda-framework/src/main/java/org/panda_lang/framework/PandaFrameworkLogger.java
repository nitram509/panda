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

package org.panda_lang.framework;

import org.panda_lang.utilities.commons.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PandaFrameworkLogger {

    protected static Logger PANDA_FRAMEWORK_LOGGER = LoggerFactory.getLogger("Panda Framework");

    private PandaFrameworkLogger() { }

    /**
     * Print current JVM startup time.
     * The method should be called as fast as it is possible.
     */
    public static void printJVMUptime() {
        PandaFramework.getLogger().debug("");
        PandaFramework.getLogger().debug("JVM launch time: " + TimeUtils.getJVMUptime() + "ms (｡•́︿•̀｡)");
        PandaFramework.getLogger().debug("");
    }

    /**
     * Set logger used by the framework
     *
     * @param logger the logger to use
     */
    public static void setLogger(Logger logger) {
        PANDA_FRAMEWORK_LOGGER = logger;
    }

}