
public class ExTeamAlreadyExist extends Exception{
    public ExTeamAlreadyExist() {
        super("Team already exists!");
    }

    public ExTeamAlreadyExist(String message) {
        super(message);
    }
}
