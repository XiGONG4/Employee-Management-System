public class ExOutOfRange extends Exception {
    public ExOutOfRange() {
        super("Out of range (Entitled Annual Leaves should be within 0-300)!");
    }

    public ExOutOfRange(String message) {
        super(message);
    }
}
