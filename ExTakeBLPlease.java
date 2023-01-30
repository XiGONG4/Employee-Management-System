
public class ExTakeBLPlease extends Exception{
    public ExTakeBLPlease() {
        super("because of the need to reserve 7 days for a block leave.");
    }

    public ExTakeBLPlease(String message) {
        super(message);
    }
}
