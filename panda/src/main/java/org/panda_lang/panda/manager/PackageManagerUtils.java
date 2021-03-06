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

package org.panda_lang.panda.manager;

import org.panda_lang.language.architecture.Environment;
import org.panda_lang.language.interpreter.source.Source;
import org.panda_lang.language.interpreter.source.PandaURLSource;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class PackageManagerUtils {

    private PackageManagerUtils() { }

    /**
     * Load module
     *
     * @param moduleDirectory the module directory to load
     * @throws IOException when module directory or module file does not exist
     */
    public static void loadToEnvironment(Environment environment, File moduleDirectory) throws IOException {
        File packageInfoFile = new File(moduleDirectory, PackageManagerConstants.PACKAGE_INFO);

        if (!packageInfoFile.exists()) {
            environment.getLogger().debug("Skipping non-panda dependency directory " + moduleDirectory);
            return;
        }

        PackageDocument packageInfo = new PackageDocumentFile(packageInfoFile).getContent();

        environment.getModulePath().include(moduleDirectory.getName(), () -> {
            Source source = PandaURLSource.fromFile(new File(moduleDirectory, Objects.requireNonNull(packageInfo.getMainScript())));
            environment.getInterpreter().interpret(source);
        });
    }

}
