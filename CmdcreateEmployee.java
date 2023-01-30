public class CmdcreateEmployee extends RecordedCommand {
    private String name;
    private int anl;
    private Employee e;

    @Override
    public void execute(String[] cmdParts) {

        try {
            if(cmdParts.length < 3)
            {
                throw new ExInsufficientCommand();
            }
            Company co = Company.getInstance();
            name = cmdParts[1];
            boolean flag = co.checkduplicate(name);
            anl = Integer.parseInt(cmdParts[2]);
            if (flag == true) {
                throw new ExEmployeeAlreadyExist();
            }
            if (anl > 300) {
                throw new ExOutOfRange();
            }
            e = co.createEmployee(name, anl);

            addUndoCommand(this); // <====== store this command (addUndoCommand is implemented in
                                  // RecordedCommand.java)
            clearRedoList(); // <====== There maybe some commands stored in the redo list. Clear them.

            System.out.println("Done.");
        } catch (ExEmployeeAlreadyExist e) {
            System.out.println(e.getMessage());
        } catch (ExOutOfRange e) {
            System.out.println(e.getMessage());
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void undoMe() {
        Company co = Company.getInstance();
        co.remove(e);
        addRedoCommand(this);

    }

    @Override
    public void redoMe() {
        Company co = Company.getInstance();
        co.addEmployee(e);
        addUndoCommand(this);
    }

}
