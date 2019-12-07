package nodes.base;

public abstract class AbstractNode implements Node {
    private static int count = 0;
    private int id;

    public AbstractNode() {
        id = count;
        count++;
    }

    @Override
    public int getId() {
        return id;
    }

    public static void resetCount() {
        count = 0;
    }

    @Override
    public String toString() {
        return String.format("%s_%d", getType(), id);
    }
}
