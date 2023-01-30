public class CmdsetupTeam extends RecordedCommand {
    private String tname;
    private String lname;
    private Team t;

    @Override
    public void execute(String[] cmdParts) throws ExTeamAlreadyExist, ExEmployeeNotFound {

        try {
            if (cmdParts.length < 3) {
                throw new ExInsufficientCommand();
            }
            Company co = Company.getInstance();
            tname = cmdParts[1];
            lname = cmdParts[2];
            boolean flag = co.checkduplicate(lname);
            if (flag == false) {
                throw new ExEmployeeNotFound();
            }
            flag = co.checkTeamDuplicate(tname);
            if (flag == true) {
                throw new ExTeamAlreadyExist();
            }
            t = co.createTeam(tname, lname);

            addUndoCommand(this); // <====== store this command (addUndoCommand is implemented in
                                  // RecordedCommand.java)
            clearRedoList(); // <====== There maybe some commands stored in the redo list. Clear them.

            System.out.println("Done.");
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExTeamAlreadyExist e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void undoMe() {
        Company co = Company.getInstance();
        co.removeTeam(t);
        addRedoCommand(this);

    }

    @Override
    public void redoMe() {
        Company co = Company.getInstance();
        co.addTeam(t);
        addUndoCommand(this);
    }

}
