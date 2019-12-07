package nodes;

import exceptions.LogicException;
import flow.Bit;
import flow.Wire;
import nodes.base.AbstractNode;
import nodes.base.Receiver;

public class Led extends AbstractNode implements Receiver {
    Wire input;
    boolean on;

    public Led() {
        on = false;
    }

    @Override
    public void setInput(int index, Wire input) {
        if (index != 0) {
            throw new LogicException();
        }
        this.input = input;
    }

    @Override
    public void update() {
        on = Bit.ONE.equals(input.getValue());
    }

    @Override
    public String getType() {
        return "LED";
    }

    public boolean isOn() {
        return on;
    }
}
