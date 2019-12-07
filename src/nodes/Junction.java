package nodes;

import flow.Bit;
import nodes.base.AbstractGate;

public class Junction extends AbstractGate {
    public Junction(int outputCount) {
        super(1, outputCount);
    }

    public Junction() {
        super(1, 2);
    }

    @Override
    protected Bit calculateResult(Bit[] bits) {
        return bits[0];
    }

    @Override
    public String getType() {
        return "JUNCTION";
    }
}
