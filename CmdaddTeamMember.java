public class CmdaddTeamMember extends RecordedCommand {
    private String tname,mname;

    @Override
    public void execute(String[] cmdParts) {

        try {
            if(cmdParts.length < 3)
            {
                throw new ExInsufficientCommand();
            }
            Company co = Company.getInstance();
            tname = cmdParts[1];
            boolean flag = co.checkTeamDuplicate(tname);
            if(flag == false)
            {
                throw new ExTeamNotFound();
            }
            mname = cmdParts[2];
            flag = co.checkduplicate(mname);
            if (flag == false) {
                throw new ExEmployeeNotFound();
            }
            flag = co.checkTeamMemberDuplicate(tname,mname);
            if(flag == true)
            {
                throw new ExEmployeeAlreadyinTeam();
            }
            co.addTeamEmployee(tname,mname);
            addUndoCommand(this); // <====== store this command (addUndoCommand is implemented in
                                  // RecordedCommand.java)
            clearRedoList(); // <====== There maybe some commands stored in the redo list. Clear them.

            System.out.println("Done.");
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeAlreadyinTeam e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        } catch (ExTeamNotFound e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void undoMe() {
        Company co = Company.getInstance();
        co.removeFromTeam(tname,mname);
        addRedoCommand(this);

    }

    @Override
    public void redoMe() {
        Company co = Company.getInstance();
        co.addTeamEmployee(tname,mname);
        addUndoCommand(this);
    }

}
