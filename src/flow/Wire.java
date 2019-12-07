package flow;

import nodes.base.Receiver;
import nodes.base.Sender;

public class Wire {
    private Receiver end = null;
    private Bit value = Bit.ZERO;

    public Receiver getEnd() {
        return end;
    }

    public Bit getValue() {
        return value;
    }

    public void setValue(Bit value) {
        if (end != null && !value.equals(this.value)) {
            end.update();
        }
        this.value = value;
    }

    public static void connect(Sender start, int outputIndex, Receiver end, int inputIndex) {
        Wire wire = start.getOutput(outputIndex);
        end.setInput(inputIndex, wire);
        wire.end = end;
        end.update();
    }

    @Override
    public String toString() {
        return String.format("Wire to %s [%s]", end != null ? end : "<>", value);
    }
}
