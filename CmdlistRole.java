public class CmdlistRole implements Command {

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length < 2) {
                throw new ExInsufficientCommand();
            }
            Company co = Company.getInstance();
            String mname = cmdParts[1];
            if(!co.checkduplicate(mname))
            {
                throw new ExEmployeeNotFound();
            }
            co.listRole(mname);
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        }

    }

}
