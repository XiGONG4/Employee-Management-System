import java.util.*;

public class Employee implements Comparable<Employee> {
    private String name;
    private int annualLeaves; // Entitled annual leaves, given as a count of days
    // private ArrayList<Day> leaveDate;
    // private ArrayList<Day> backDate;
    // private ArrayList<Integer> timeForEachLeave;
    private int totalLeaves; // store the time the stuff already leave for AL and BL;
    private int sickLeaves;// store the time of SL
    // private boolean leaveOrNot;//whether Employee take leaves
    // private ArrayList<String> leaveType;
    private ArrayList<LeaveRecord> leaveList;
    private ArrayList<LeaveRecord> ActiveHeadPeriod;
    private ArrayList<String> LeadingTeam;
    private boolean takeBLorNot;// to check whether he take a BL

    public Employee(String n, int yle) {
        name = n;
        annualLeaves = yle;// Set name, annualLeaves
        totalLeaves = 0;// initialize the leave time;
        sickLeaves = 0;
        takeBLorNot = false;
        // leaveOrNot = false;
        // timeForEachLeave = new ArrayList<>();
        leaveList = new ArrayList<>();
        ActiveHeadPeriod = new ArrayList<>();
        LeadingTeam = new ArrayList<>();
        // leaveDate = new ArrayList<>();
        // backDate = new ArrayList<>();
        // leaveType = new ArrayList<>();
    }

    public String getName() {
        return name;/* simply return the name string */
    }

    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch) {
        for (Employee e : list) {
            if (e.getName().equals(nameToSearch)) {
                return e;
            }
        }
        return null;/* search the arrayList for the employee with the given name */
    }

    public int getAnl() {
        return annualLeaves;
    }

    @Override
    public int compareTo(Employee another) {
        if (this.name.equals(another.name))
            return 0;
        else if (this.name.compareTo(another.name) > 0)
            return 1;
        else
            return -1;
    }

    public void addLeaves(LeaveRecord l) {
        // leaveDate.add(leaveDate2);
        // backDate.add(backDate2);
        // leaveType.add(leaveType);
        /*try {
            // totalLeaves += leaveTime;
            if (annualLeaves - totalLeaves - leaveTime < 0) {
                throw new ExInsufficientBalance();
            }
            if (leaveType.equals("AL")) {
                totalLeaves += leaveTime;
                if (takeBLorNot == false) {
                    if (annualLeaves - totalLeaves < 7) {
                        throw new ExTakeBLPlease();
                    }
                }
                leaveList.add(new LeaveRecord_AL(leaveDate, backDate));
            } else if (leaveType.equals("BL")) {
                totalLeaves += leaveTime;
                leaveList.add(new LeaveRecord_BL(leaveDate, backDate));
                takeBLorNot = true;
            } else if (leaveType.equals("NL")) {
                leaveList.add(new LeaveRecord_NL(leaveDate, backDate));
            } else if (leaveType.equals("SL")) {
                sickLeaves += leaveTime;
                leaveList.add(new LeaveRecord_SL(leaveDate, backDate));
            } else
                 throw new ExInsufficientCommand();*/
        // System.out.println(l.startDay.toNumber());
        leaveList.add(l);
        Collections.sort(leaveList);
        /*} catch (ExTakeBLPlease e) {
            System.out.printf(
                    "The annual leave is invalid.\nThe current balance is %d days only.\nThe employee has not taken any block leave yet.\nThe employee can take at most %d days of non-block annual leave\n",
                    annualLeaves - totalLeaves, annualLeaves - totalLeaves - 7);
            System.out.println(e.getMessage());
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        } catch (ExInsufficientBalance e) {
            System.out.printf("%s %d days left only!\n", e.getMessage(), annualLeaves - totalLeaves);
        }*/

        // Collections.sort(leaveDate);
        // Collections.sort(backDate);
    }

    public void removeLeave(LeaveRecord ltosearch) {
        int index=0;
        // System.out.println(ltosearch.startDay.toString());
        for(LeaveRecord l : leaveList)
        {
            if(l.compareTo(ltosearch)==0)
            {
                leaveList.remove(index);
                break;
            }
            index++;
        }
        // leaveList.remove(ltosearch);
    }

    public int getremainingLeave() {
        return annualLeaves-totalLeaves;
    }

    public void listLeaves() {
        System.out.printf("%s:\n", name);
        if (leaveList.isEmpty()) {
            System.out.println("No leave record");
        } else {
            for (LeaveRecord l : leaveList) {
                System.out.println(l.toString());
            }
        }

    }

    public void listLeavesP() {
        if (leaveList.isEmpty()) {
            System.out.println("No leave record");
        } else {
            for (LeaveRecord l : leaveList) {
                System.out.println(l.toString());
            }
        }
    }

    public boolean getTakeBLorNot() {
        return takeBLorNot;
    }

    public void setTakeBLorNot(boolean b) {
        this.takeBLorNot = b;
    }

    public void addtotalLeave(int leaveTime) {
        totalLeaves += leaveTime;
    }

    public void addsickLeave(int leaveTime) {
        sickLeaves += leaveTime;
    }

    public boolean checkSLexceed(int leaveTime) {
        if(sickLeaves+leaveTime<=135) return false;
        else return true;
    }

    public int getsickLeave() {
        return this.sickLeaves;
    }

    public void subtotalLeave(int leaveTime) {
        this.totalLeaves -= leaveTime;
    }

    public void subsickLeave(int leaveTime) {
        this.sickLeaves -= leaveTime;
    }

    public LeaveRecord checkLeave(LeaveRecord ltosearch) {
        for(LeaveRecord l : leaveList)
        {
            if(ltosearch.startDay.toNumber() > l.startDay.toNumber() )
            {
                if(ltosearch.startDay.toNumber() < l.endDay.toNumber())
                {
                    return l;
                }
            }
            if(ltosearch.endDay.toNumber() > l.startDay.toNumber() )
            {
                if(ltosearch.endDay.toNumber() < l.endDay.toNumber())
                {
                    return l;
                }
            }
            if(l.startDay.toNumber() > ltosearch.startDay.toNumber() )
            {
                if(l.startDay.toNumber() < ltosearch.endDay.toNumber())
                {
                    return l;
                }
            }
            if(l.endDay.toNumber() > ltosearch.startDay.toNumber() )
            {
                if(l.endDay.toNumber() < ltosearch.endDay.toNumber())
                {
                    return l;
                }
            }
        }
        return null;
    }

    public void addActivePeriod(LeaveRecord l) {
        // System.out.println("Employee add method reached");
        ActiveHeadPeriod.add(l);
    }

    public void printActingPeriod(LeaveRecord ltosearch) {
        for(LeaveRecord l : ActiveHeadPeriod)
        {
            if(l.compareTo(ltosearch) == 0)
            {
                System.out.printf("%s: %s\n",l.supertoString(),this.getName());
            }
        }
    }

    public void removeActivePeriod(LeaveRecord l) {
        ActiveHeadPeriod.remove(l);
    }

    public ArrayList<String> getLeadingTeam() {
        return this.LeadingTeam;
        
    }

    public void addLeadingTeam(Team team) {
        LeadingTeam.add(team.getTeamName());
        Collections.sort(LeadingTeam);
    }

    public LeaveRecord checkActing(LeaveRecord ltosearch) {
        for(LeaveRecord l : ActiveHeadPeriod)
        {
            if(ltosearch.startDay.toNumber() > l.startDay.toNumber() )
            {
                if(ltosearch.startDay.toNumber() < l.endDay.toNumber())
                {
                    return l;
                }
            }
            if(ltosearch.endDay.toNumber() > l.startDay.toNumber() )
            {
                if(ltosearch.endDay.toNumber() < l.endDay.toNumber())
                {
                    return l;
                }
            }
            if(l.startDay.toNumber() > ltosearch.startDay.toNumber() )
            {
                if(l.startDay.toNumber() < ltosearch.endDay.toNumber())
                {
                    return l;
                }
            }
            if(l.endDay.toNumber() > ltosearch.startDay.toNumber() )
            {
                if(l.endDay.toNumber() < ltosearch.endDay.toNumber())
                {
                    return l;
                }
            }
        }
        return null;
    }

}
