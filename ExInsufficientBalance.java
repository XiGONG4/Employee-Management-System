
public class ExInsufficientBalance extends Exception{
    public ExInsufficientBalance() {
        super("Insufficient balance of annual leaves.");
    }

    public ExInsufficientBalance(String message) {
        super(message);
    }
}
