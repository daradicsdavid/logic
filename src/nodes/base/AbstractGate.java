package nodes.base;

import exceptions.LogicException;
import flow.Bit;
import flow.Wire;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGate extends AbstractNode implements Gate {
    private List<Wire> inputs;
    private List<Wire> outputs;

    public AbstractGate(int inputCount, int outputCount) {
        if (inputCount < 1 || outputCount < 1) {
            throw new LogicException();
        }
        inputs = new ArrayList<>(inputCount);
        outputs = new ArrayList<>(outputCount);
        for (int i = 0; i < outputCount; i++) {
            outputs.add(new Wire());
        }
    }

    @Override
    public void setInput(int index, Wire input) {
        if (index < 0 || index > inputs.size()) {
            throw new LogicException();
        }
        inputs.add(index, input);
    }

    @Override
    public Wire getOutput(int index) {
        if (index < 0 || index > outputs.size()) {
            throw new LogicException();
        }
        return outputs.get(index);
    }

    @Override
    public boolean isOperable() {
        for (Wire input : inputs) {
            if (input == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void update() {
        if (!isOperable()) {
            return;
        }
        Bit[] bits = new Bit[inputs.size()];
        for (int i = 0; i < inputs.size(); i++) {
            bits[i] = inputs.get(i).getValue();
        }
        Bit result = calculateResult(bits);
        for (Wire output : outputs) {
            output.setValue(result);
        }
    }

    protected abstract Bit calculateResult(Bit[] bits);
}
