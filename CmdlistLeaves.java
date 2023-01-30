public class CmdlistLeaves implements Command{

    @Override
    public void execute(String[] cmdParts){
        Company co = Company.getInstance();
        if(cmdParts.length == 1)
        {
            co.listLeaves();
        }
        else if(cmdParts.length == 2)
        {
            String name = cmdParts[1];
            co.listLeaves(name);
        }
    }
}
