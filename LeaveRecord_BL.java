public class LeaveRecord_BL extends LeaveRecord {

    public LeaveRecord_BL(Day startDay, Day endDay) {
        super(startDay, endDay);
    }

    @Override
    public int compareTo(LeaveRecord another) {
        if(this.startDay.compareTo(another.startDay)==0)
        {
            return 0;
        }
        else if(this.startDay.compareTo(another.startDay) > 0)
        {
            return 1;
        }
        else return -1;
    }
    @Override
    public String toString()
    {
        return startDay.toString() + " to " + endDay.toString() + " [BL]"; 
    }

    public String supertoString() {
        return startDay.toString() + " to " + endDay.toString();
    }
}
