package flow;

public enum Bit {
    ZERO(0, "0"), ONE(1, "1");

    Bit(int value, String valueString) {
        this.value = value;
        this.valueString = valueString;
    }

    int value;
    public String valueString;

    public Bit invert() {
        return this == ZERO ? ONE : ZERO;
    }

    @Override
    public String toString() {
        return this.equals(ZERO) ? "ZERO" : "ONE";
    }
}
