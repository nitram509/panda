package org.panda_lang.panda.core.statement;

import org.panda_lang.panda.core.Alice;
import org.panda_lang.panda.core.Essence;
import org.panda_lang.panda.core.parser.essential.util.EqualityBuilder;
import org.panda_lang.panda.core.parser.essential.util.Numeric;
import org.panda_lang.panda.lang.BooleanEssence;

public class Equality implements Executable {

    private EqualityBuilder equalityBuilder;

    public Equality(EqualityBuilder equalityBuilder) {
        this.equalityBuilder = equalityBuilder;
    }

    @Override
    public Essence execute(Alice alice) {
        Operator operator = equalityBuilder.getOperator();
        RuntimeValue oneRuntimeValue = equalityBuilder.getOne();
        RuntimeValue otherRuntimeValue = equalityBuilder.getOther();
        boolean flag = false;

        if (operator == Operator.EQUALS_TO || operator == Operator.NOT_EQUALS_TO) {
            Essence oneFactorValue = oneRuntimeValue.getValue(alice);
            Essence otherFactorValue = otherRuntimeValue.getValue(alice);

            flag = oneFactorValue.equals(otherFactorValue);
            flag = (operator == Operator.EQUALS_TO) == flag;
        }
        else {
            //TODO

            Numeric oneNumber = oneRuntimeValue.getValue(alice);
            Numeric otherNumber = otherRuntimeValue.getValue(alice);

            float one = oneNumber.getFloat();
            float other = otherNumber.getFloat();

            switch (equalityBuilder.getOperator()) {
                case GREATER_THAN:
                    flag = one > other;
                    break;
                case GREATER_THAN_OR_EQUAL_TO:
                    flag = one >= other;
                    break;
                case LESS_THAN:
                    flag = one < other;
                    break;
                case LESS_THAN_OR_EQUAL_TO:
                    flag = one <= other;
                    break;
                default:
                    break;
            }
        }

        return new BooleanEssence(flag);
    }

}
