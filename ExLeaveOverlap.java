
public class ExLeaveOverlap extends Exception{
    public ExLeaveOverlap() {
        super("Leave overlapped: ");
    }

    public ExLeaveOverlap(String message) {
        super(message);
    }
}
