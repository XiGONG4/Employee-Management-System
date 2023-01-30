
public class ExEarlierThanSystemtate  extends Exception{
    public ExEarlierThanSystemtate() {
        super("Wrong Date. Leave start date must not be earlier than the system date ");
    }

    public ExEarlierThanSystemtate(String message) {
        super(message);
    }
}
