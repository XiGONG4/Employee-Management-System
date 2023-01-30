public class CmdlistTeamMember implements Command{

    @Override
    public void execute(String[] cmdParts){
        Company co = Company.getInstance();
        co.listTeamMember();
    }
    
}
