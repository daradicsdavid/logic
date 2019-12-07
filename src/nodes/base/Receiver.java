package nodes.base;

import flow.Wire;

public interface Receiver extends Node {
    void setInput(int index, Wire input);

    void update();
}
