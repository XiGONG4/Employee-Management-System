
public class ExEmployeeNotFoundForTeam extends  Exception{
    public ExEmployeeNotFoundForTeam() {
        super("Employee not found!");
    }

    public ExEmployeeNotFoundForTeam(String message) {
        super(message);
    }
}
