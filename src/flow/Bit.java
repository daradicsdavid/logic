package flow;

public enum Bit {
    ZERO, ONE;

    public Bit invert() {
        return this == ZERO ? ONE : ZERO;
    }

    @Override
    public String toString() {
        return this.equals(ZERO) ? "ZERO" : "ONE";
    }
}
