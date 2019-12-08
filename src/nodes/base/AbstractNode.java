package nodes.base;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractNode)) return false;
        AbstractNode that = (AbstractNode) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
