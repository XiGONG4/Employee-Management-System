import java.util.ArrayList;

public class CmdstartNewDay extends RecordedCommand {
    private static ArrayList<String> dates = new ArrayList<>();
    private static ArrayList<String> dump = new ArrayList<>();
    private SystemDate sys;

    public static void init() {
        dates.add(SystemDate.getInstance().toString());
    }

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length < 2) {
                throw new ExInsufficientCommand();
            }
            sys = SystemDate.getInstance();
            String newdate = cmdParts[1];
            dates.add(newdate);
            sys.set(newdate);
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExInsufficientCommand e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void undoMe() {
        sys = SystemDate.getInstance();
        int index = dates.size() - 1;
        dump.add(dates.get(index));
        dates.remove(index);
        sys.set(dates.get(index - 1));
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        sys = SystemDate.getInstance();
        int indexdump = dump.size() - 1;
        sys.set(dump.get(indexdump));
        dates.add(dump.get(indexdump));
        dump.remove(indexdump);
        addUndoCommand(this);
    }
}