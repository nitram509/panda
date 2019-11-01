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

package org.panda_lang.panda.shell.cli;

import org.panda_lang.framework.design.architecture.Application;
import org.panda_lang.framework.language.interpreter.messenger.PandaMessenger;
import org.panda_lang.panda.Panda;
import org.panda_lang.panda.PandaConstants;
import org.panda_lang.panda.PandaFactory;
import org.panda_lang.panda.manager.ModuleManager;
import org.panda_lang.panda.shell.PandaShell;
import org.panda_lang.utilities.commons.function.ThrowingRunnable;
import org.tinylog.configuration.Configuration;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.Optional;

@Command(name = "panda", version = "Panda " + PandaConstants.VERSION)
public final class PandaCommand implements ThrowingRunnable {

    private final PandaShell shell;

    @Parameters(index = "0", paramLabel = "<script>", description = "script to load")
    private File script;

    @Option(names = { "--version", "-V" }, versionHelp = true, description = "display current version of panda")
    private boolean versionInfoRequested;

    @Option(names = { "--help", "-H" }, usageHelp = true, description = "display help message")
    private boolean usageHelpRequested;

    @Option(names = { "--level", "-L" }, description = "set level of logging", paramLabel="<level>")
    private String level;

    public PandaCommand(PandaShell shell) {
        this.shell = shell;
    }

    @Override
    public void run() throws Exception {
        CommandLine commandLine = new CommandLine(this);

        if (level != null) {
            Configuration.set("level", level);
        }

        if (usageHelpRequested) {
            CommandLine.usage(this, System.out);
        }

        if (versionInfoRequested) {
            commandLine.printVersionHelp(System.out);
        }

        if (script == null) {
            return;
        }

        if (script.getName().endsWith("panda.hjson")) {
            ModuleManager moduleManager = new ModuleManager(new PandaMessenger(shell.getLogger()), script.getParentFile());
            moduleManager.install(script);
            moduleManager.run(script);
            return;
        }

        Panda panda = new PandaFactory().createPanda(shell.getLogger());
        Optional<Application> application = panda.getLoader().load(script, script.getParentFile());

        if (!application.isPresent()) {
            shell.getLogger().error("Cannot load application");
            return;
        }

        application.get().launch();
    }

}