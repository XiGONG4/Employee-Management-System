import java.util.ArrayList;
import java.util.Collections;

public class Company {
    private ArrayList<Employee> allEmployee;
    private ArrayList<Team> allTeams;
    // private ArrayList<Day> leaveDate;
    // private ArrayList<Day> backDate;
    private static Company instance = new Company();

    private Company() {
        allEmployee = new ArrayList<>();
        allTeams = new ArrayList<>();
        // leaveDate = new ArrayList<>();
        // backDate = new ArrayList<>();
        /* create the 2 arrayLists */ }

    public static Company getInstance() {
        return instance;
    }

    public void listTeams() // See how it is called in main()
    {
        Team.list(allTeams); // allTeams
    }

    public void listEmployees() {
        for (Employee e : allEmployee) {
            System.out.println(e.getName() + " (Entitled Annual Leaves: " + e.getAnl() + " days)");
        }
    }

    public Employee createEmployee(String n, int m) // See how it is called in main()
    {
        Employee e = new Employee(n, m);
        allEmployee.add(e);
        Collections.sort(allEmployee); // allEmployees
        return e; // the return value is useful for later undoable command.
    }

    public void addEmployee(Employee e) {
        allEmployee.add(e);
        Collections.sort(allEmployee);
    }

    public void remove(Employee e) {
        this.allEmployee.remove(e);
    }

    public Team createTeam(String tname, String lname) // See how it is called in main()
    {
        Employee e = Employee.searchEmployee(allEmployee, lname);
        Team t = new Team(tname, e);
        allTeams.add(t);
        Collections.sort(allTeams); // allTeams
        return t; // the return value is useful for later undoable command.
    }

    public void addTeam(Team t) {
        allTeams.add(t);
        Collections.sort(allTeams);
    }

    public void removeTeam(Team t) {
        allTeams.remove(t);
    }

    public boolean checkduplicate(String name) {
        Employee e = Employee.searchEmployee(allEmployee, name);
        if (e == null) {
            return false;
        } else
            return true;
    }

    public boolean checkTeamDuplicate(String tname) {
        Team t = Team.searchTeam(allTeams, tname);
        if (t == null) {
            return false;
        } else
            return true;
    }

    public void listRole(String name) {
        try {
            boolean flag = checkduplicate(name);
            if (flag == false) {
                throw new ExEmployeeNotFound();
            }
            flag = false;
            Boolean Role = false;
            Employee etofind = null;
            Team tarTeam = null;
            for (Team t : allTeams) {
                etofind = t.findMember(name);
                if (etofind != null) {
                    Role = true;
                    tarTeam = t;
                    flag = false;
                    if (t.getLeaderName().equals(name)) {
                        flag = true;
                    }
                    if (flag == true) {
                        System.out.printf("%s (Head of Team)\n", tarTeam.getTeamName());
                    } else {
                        System.out.printf("%s\n", tarTeam.getTeamName());
                    }
                }
                etofind = null;
            }
            if (Role == false) {
                System.out.println("No role");
            }
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        }

    }

    public boolean checkTeamMemberDuplicate(String tname, String mname) {
        Team t = Team.searchTeam(allTeams, tname);
        Employee e = t.findMember(mname);
        if (e == null) {
            return false;
        } else
            return true;
    }

    public void addTeamEmployee(String tname, String mname) {
        Team t = Team.searchTeam(allTeams, tname);
        Employee e = Employee.searchEmployee(allEmployee, mname);
        t.addTeamEmployee(e);
    }

    public void listTeamMember() {
        for (Team t : allTeams) {
            System.out.printf("%s:\n", t.getTeamName());
            t.listTeamMember();
            if (t.checkAH()) {
                t.listActiveHead();
            }
        }
    }

    public Team searchTeam(String tname) {
        return Team.searchTeam(allTeams, tname);
    }

    public Employee searchEmployee(String mname) {
        return Employee.searchEmployee(allEmployee, mname);
    }

    public void removeFromTeam(String tname, String mname) {
        Team t = this.searchTeam(tname);
        Employee e = this.searchEmployee(mname);
        t.removeMember(e);
    }

    public int checkTeamLeaderNum(String mname) {
        int result = 0;
        for (Team t : allTeams) {
            if (t.getLeaderName().equals(mname)) {
                result++;
            }
        }
        return result;
    }

    public void listLeaves(String name) {
        Employee e = Employee.searchEmployee(allEmployee, name);
        e.listLeavesP();
    }

    public void listLeaves() {
        for (Employee e : allEmployee) {

            e.listLeaves();
        }
    }

    public void takeLeave(String mname, LeaveRecord l) {
        Employee e = Employee.searchEmployee(allEmployee, mname);
        e.addLeaves(l);

    }

    public void removeLeave(String mname, LeaveRecord l) {
        Employee e = Employee.searchEmployee(allEmployee, mname);
        e.removeLeave(l);
    }

    public Employee getEmployee(String mname) {
        return Employee.searchEmployee(allEmployee, mname);
    }

    public void setActiveHead(String targetTeam, String tempActiveHead, LeaveRecord l) {
        // System.out.println("company set method reached");
        Team t = Team.searchTeam(allTeams, targetTeam);
        Employee activeHead = Employee.searchEmployee(allEmployee, tempActiveHead);
        if (!t.checkAHDuplicate(activeHead)) {
            t.setActiveHead(l);
        } else {
            t.setActiveHead(activeHead, l);
        }

        activeHead.addActivePeriod(l);
    }

    public void removeActiveHead(String targetTeam, String tempActiveHead, LeaveRecord l) {
        Team t = Team.searchTeam(allTeams, targetTeam);
        Employee activeHead = Employee.searchEmployee(allEmployee, tempActiveHead);
        if (t.checkAHDuplicate(activeHead)) {
            t.removeActiveHead(l);
        } else {
            t.removeActiveHead(activeHead, l);
        }
        activeHead.removeActivePeriod(l);
    }

    public String getLeadingTeam(String name) {
        for (Team t : allTeams) {
            if (t.getLeaderName().equals(name)) {
                return t.getTeamName();
            }
        }
        return null;
    }

    public String actingHeadIn(String mname,LeaveRecord l) {
        Employee e = Employee.searchEmployee(allEmployee, mname);
        for (Team t : allTeams) {
            if (!t.checkAHDuplicate(e)) {
                if(t.checkLeaveEqual(l))
                {
                    return t.getTeamName();
                }
            }
        }
        return null;
    }

}