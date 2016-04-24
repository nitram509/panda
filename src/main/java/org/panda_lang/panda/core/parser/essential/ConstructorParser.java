package org.panda_lang.panda.core.parser.essential;

import org.panda_lang.panda.core.Alice;
import org.panda_lang.panda.core.Essence;
import org.panda_lang.panda.core.parser.ParserInfo;
import org.panda_lang.panda.core.parser.PandaException;
import org.panda_lang.panda.core.parser.Parser;
import org.panda_lang.panda.core.statement.Executable;
import org.panda_lang.panda.core.statement.RuntimeValue;
import org.panda_lang.panda.core.statement.Runtime;
import org.panda_lang.panda.core.statement.Vial;

import java.util.Stack;

public class ConstructorParser implements Parser {

    @Override
    public Runtime parse(final ParserInfo parserInfo) {
        String source = parserInfo.getSourceCode();
        source = source.substring(4);

        StringBuilder node = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        String clazz = null;
        boolean s = false,
                p = false;

        char[] chars = source.toCharArray();
        for (int i = 0; i < source.length(); i++) {
            char c = chars[i];

            if (c == '"') {
                s = !s;
            }
            else if (s) {
                node.append(c);
                continue;
            }
            else if (p) {
                if (c == '(') {
                    stack.push(c);
                }
                else if (c == ')') {
                    stack.pop();
                    if (stack.size() == 0) {
                        break;
                    }
                }
                node.append(c);
                continue;
            }
            else if (node.length() == 0 && Character.isWhitespace(c)) {
                continue;
            }

            switch (c) {
                case '(':
                    clazz = node.toString();
                    node.setLength(0);
                    stack.push(c);
                    p = true;
                    break;
                case ';':
                    break;
                default:
                    node.append(c);
                    break;
            }
        }

        String params = node.toString();
        node.setLength(0);
        parserInfo.setSourceCode(params);

        final FactorParser parser = new FactorParser();
        final RuntimeValue[] runtimeValues = parser.splitAndParse(parserInfo);
        final Vial vial = parserInfo.getDependencies().getVial(clazz);

        if (vial == null) {
            final Runtime runtime = new Runtime();
            final String vialName = clazz;

            parserInfo.getPandaParser().addPostProcess(new Runnable() {
                @Override
                public void run() {
                    final Vial vial = parserInfo.getPandaParser().getDependencies().getVial(vialName);
                    if (vial == null) {
                        PandaException exception = new PandaException("ConstructorParser: Vial '" + vialName + "' not found", parserInfo.getSourcesDivider());
                        parserInfo.getPandaParser().throwException(exception);
                        return;
                    }
                    runtime.setExecutable(new Executable() {
                        @Override
                        public Essence execute(Alice alice) {
                            return vial.initializeInstance(alice);
                        }
                    });
                }
            });

            return runtime;
        }

        return new Runtime(null, new Executable() {
            @Override
            public Essence execute(Alice alice) {
                return vial.initializeInstance(alice);
            }
        }, runtimeValues);
    }

}
