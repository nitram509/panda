package org.panda_lang.panda.core.parser.util;

import org.panda_lang.panda.core.Alice;
import org.panda_lang.panda.core.syntax.Essence;
import org.panda_lang.panda.core.syntax.Executable;
import org.panda_lang.panda.core.syntax.NamedExecutable;

public class SimplifiedNamedExecutable implements NamedExecutable {

    private final Executable executable;
    private String name;

    public SimplifiedNamedExecutable(Executable executable) {
        this.executable = executable;
    }

    @Override
    public Essence run(Alice alice) {
        return executable.run(alice);
    }

    public Executable getExecutable() {
        return executable;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}