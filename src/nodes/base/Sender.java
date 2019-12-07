package nodes.base;

import flow.Wire;

public interface Sender extends Node {
    Wire getOutput(int index);
}
