package nodes;

import flow.Bit;
import nodes.base.AbstractGate;

public class And extends AbstractGate {

    public And(int inputCount) {
        super(inputCount, 1);
    }

    public And() {
        super(2, 1);
    }

    @Override
    protected Bit calculateResult(Bit[] bits) {
        for (Bit bit : bits) {
            if (bit == Bit.ZERO) {
                return Bit.ZERO;
            }
        }
        return Bit.ONE;
    }

    @Override
    public String getType() {
        return "AND";
    }
}
