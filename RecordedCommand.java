import java.util.*;

public abstract class RecordedCommand implements Command {
    public abstract void undoMe();

    public abstract void redoMe();

    /* undolist and redolist; */
    private static ArrayList<RecordedCommand> undoList = new ArrayList<>();
    private static ArrayList<RecordedCommand> redoList = new ArrayList<>();

    /* adding command to the list */
    protected static void addUndoCommand(RecordedCommand cmd) {
        undoList.add(cmd);
    }

    protected static void addRedoCommand(RecordedCommand cmd) {
        redoList.add(cmd);
    }

    /* clear redo list */
    protected static void clearRedoList() {
        redoList.clear();
    }

    /* carry out undo/redo */
    public static void undoOneCommand() {
        if (undoList.size()<=0) {
            System.out.println("Nothing to undo.");
            return;
        } else{
            //undoList.remove(undoList.size() - 1).undoMe();
            RecordedCommand r= undoList.remove(undoList.size() - 1);
            r.undoMe();
        }
    }

    public static void redoOneCommand() {
        if (redoList.size()<=0) {
            System.out.println("Nothing to redo.");
            return;
        } else{
            RecordedCommand r = redoList.remove(redoList.size() - 1);
            r.redoMe();
        }

    }
}