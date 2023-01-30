
public class ExEmployeeAlreadyExist extends Exception{
    public ExEmployeeAlreadyExist() {
        super("Employee already exists!");
    }

    public ExEmployeeAlreadyExist(String message) {
        super(message);
    }
}
