
public class ExInsufficientBalanceofSL extends Exception{
    public ExInsufficientBalanceofSL() {
        super("Insufficient balance of annual leaves.");
    }

    public ExInsufficientBalanceofSL(String message) {
        super(message);
    }
}
