public abstract class LeaveRecord implements Comparable<LeaveRecord>{
    Day startDay;
    Day endDay;
    public LeaveRecord(Day startDay,Day endDay){ this.startDay = startDay;this.endDay = endDay;}

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

    public String supertoString() {
        return startDay.toString() + " to " + endDay.toString();
    }

}
