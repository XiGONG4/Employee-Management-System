
public class ExEmployeeAlreadyinTeam extends Exception{
    public ExEmployeeAlreadyinTeam() {
        super("The employee has joined the team already!");
    }

    public ExEmployeeAlreadyinTeam(String message) {
        super(message);
    }
}
