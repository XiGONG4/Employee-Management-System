import java.util.*;
public class Team implements Comparable<Team>{
    private String teamName;
    private Employee head;
    private ArrayList<Employee> TeamMember;
    private Day dateSetup; //the date this team was setup
    private ArrayList<Employee> ActiveHeadList;
    private ArrayList<LeaveRecord> ActingPeriod;
    public String getTeamName()
    {
        return this.teamName;
    }
    public Team(String n, Employee hd)
    {
        teamName=n;
        head=hd;//Set teamName, head, setup date
        TeamMember = new ArrayList<>();
        ActiveHeadList = new ArrayList<>();
        ActingPeriod = new ArrayList<>();
        TeamMember.add(hd);
        hd.addLeadingTeam(this);
        dateSetup=SystemDate.getInstance().clone();
    }
    public static void list(ArrayList<Team> list)
    {
        //Learn: "-" means left-aligned
        System.out.printf("%-30s%-10s%-13s\n", "Team Name", "Leader","Setup Date");
        for (Team t : list)
        System.out.printf("%-30s%-10s%-13s\n",t.teamName,t.head.getName(),t.dateSetup.toString()); //display t.teamName, etc..
    }
    @Override
    public int compareTo(Team another)
    {
        if (this.teamName.equals(another.teamName)) return 0;
        else if (this.teamName.compareTo(another.teamName)>0) return 1;
        else return -1;
    }
    public static Team searchTeam(ArrayList<Team> allTeams, String tname) {
        for (Team t : allTeams) {
            if(t.getTeamName().equals(tname))
            {
                return t;
            }
        }
        return null;
    }
    public Employee findMember(String mname) {
        for(Employee e : TeamMember)
        {
            if(e.getName().equals(mname))
            {
                return e;
            }
        }
        return null;
    }
    public void addTeamEmployee(Employee e) {
        this.TeamMember.add(e);
        Collections.sort(TeamMember);
    }
    public Object getLeaderName() {
        return this.head.getName();
    }
    public void listTeamMember() {
        for(Employee e : TeamMember)
        {   
            if(e == this.head)
            {
                System.out.printf("%s (Head of Team)\n",head.getName());
            }
            else{
                System.out.printf("%s\n",e.getName());
            }
            
        }
        
    }
    public void removeMember(Employee e) {
        this.TeamMember.remove(e);
    }
    public void setActiveHead(Employee activeHead,LeaveRecord l) {
        //System.out.println("team set method reached");
        ActiveHeadList.add(activeHead);
        Collections.sort(ActiveHeadList);
        ActingPeriod.add(l);
        Collections.sort(ActingPeriod);
    }
    public boolean checkAH() {
        // if(ActiveHeadList.size() == 0)
        // {
        //     return false;
        // }
        // else return true;
        return !(ActiveHeadList.isEmpty()); 
    }
    public void listActiveHead() {
        System.out.println("Acting heads:");
        for(LeaveRecord l : ActingPeriod)
        {
            for(Employee e : ActiveHeadList)
            {
                e.printActingPeriod(l);
            }
        }
        // for(Employee e : ActiveHeadList)
        // {
        //     for(LeaveRecord l : ActingPeriod)
        //     {
        //         e.printActingPeriod(l);
        //     }
        // }
    }
    public boolean checkAHDuplicate(Employee etosearch) {
        for(Employee e : ActiveHeadList)
        {
            if(e.getName().equals(etosearch.getName()))
            {
                return false;
            }
        }
        return true;
    }
    public void setActiveHead(LeaveRecord l) {
        ActingPeriod.add(l);
        Collections.sort(ActingPeriod);
    }
    public void removeActiveHead(LeaveRecord l) {
        ActingPeriod.remove(l);
    }
    public void removeActiveHead(Employee activeHead, LeaveRecord l) {
        ActiveHeadList.remove(activeHead);
        ActingPeriod.remove(l);
    }
    public boolean checkLeaveEqual(LeaveRecord ltosearch) {
        for(LeaveRecord l : ActingPeriod)
        {
            if(l.compareTo(ltosearch) == 0)
            {
                return true;
            }
        }
        return false;
    }
}
