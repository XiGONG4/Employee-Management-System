public interface Command {
    void execute(String[] cmdParts) throws ExInsufficientCommand, ExOutOfRange,ExEmployeeAlreadyExist, ExTeamAlreadyExist, ExEmployeeNotFound;
}
