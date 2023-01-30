public class ExPleaseUseAL extends Exception{
    public ExPleaseUseAL() {
        super("To apply annual leave for 6 days or less, you should use the normal Annual Leave (AL) type.");
    }

    public ExPleaseUseAL(String message) {
        super(message);
    }
}