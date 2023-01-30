public class ExPleaseUseBL extends Exception{
    public ExPleaseUseBL() {
        super("To apply annual leave for 7 days or more, please use the Block Leave (BL) type.");
    }

    public ExPleaseUseBL(String message) {
        super(message);
    }
}
