package nodes;

import exceptions.LogicException;
import flow.Bit;
import flow.Wire;
import nodes.base.AbstractNode;
import nodes.base.Sender;

public class Source extends AbstractNode implements Sender {
    Wire output;

    public Source() {
        output = new Wire();
    }

    @Override
    public Wire getOutput(int index) {
        if (index != 0) {
            throw new LogicException();
        }
        return output;
    }

    @Override
    public String getType() {
        return "SOURCE";
    }

    public void switchOn() {
        output.setValue(Bit.ONE);
    }

    public void switchOff() {
        output.setValue(Bit.ZERO);
    }


}
