import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		System.out.print("Please input the file pathname: ");
		Scanner in = new Scanner(System.in);
		String filepathname = in.nextLine();
		Scanner inFile = null;
		try {
			inFile = new Scanner(new File(filepathname));
			String cmdLine1 = inFile.nextLine();
			String[] cmdParts1 = cmdLine1.split("\\|");
			System.out.println("\n> " + cmdLine1);
			SystemDate.createTheInstance(cmdParts1[1]);
			CmdstartNewDay.init();
			while (inFile.hasNext()) {
				String cmdLine = inFile.nextLine().trim();

				// Blank lines exist in data file as separators. Skip them.
				if (cmdLine.equals(""))
					continue;

				System.out.println("\n> " + cmdLine);

				// split the words in actionLine => create an array of word strings [!!! LEARN
				// !!!]
				String[] cmdParts = cmdLine.split("\\|");
				if (cmdParts[0].equals("hire"))
					(new CmdcreateEmployee()).execute(cmdParts);
				else if (cmdParts[0].equals("listEmployees"))
					(new CmdlistEmployees()).execute(cmdParts);
				else if (cmdParts[0].equals("undo"))
					RecordedCommand.undoOneCommand();
				else if (cmdParts[0].equals("redo"))
					RecordedCommand.redoOneCommand();
				else if (cmdParts[0].equals("startNewDay"))
					(new CmdstartNewDay()).execute(cmdParts);
				else if (cmdParts[0].equals("listTeams"))
					(new CmdlistTeams()).execute(cmdParts);
				else if (cmdParts[0].equals("setupTeam"))
					(new CmdsetupTeam()).execute(cmdParts);
				else if (cmdParts[0].equals("addTeamMember"))
					(new CmdaddTeamMember()).execute(cmdParts);
				else if (cmdParts[0].equals("listTeamMembers"))
					(new CmdlistTeamMember()).execute(cmdParts);
				else if (cmdParts[0].equals("listRoles"))
					(new CmdlistRole()).execute(cmdParts);
				else if (cmdParts[0].equals("takeLeave"))
					(new CmdtakeLeave()).execute(cmdParts);
				else if (cmdParts[0].equals("listLeaves"))
					(new CmdlistLeaves()).execute(cmdParts);
				else
					throw new ExInsufficientCommand();
			}
		} catch (ExInsufficientCommand e) {
			System.out.println("Insufficient command arguments!");
		} catch (ExTeamAlreadyExist e) {
			System.out.println(e.getMessage());
		} catch (ExEmployeeNotFound e) {
			System.out.println(e.getMessage());
		} finally {
			inFile.close();
			in.close();
		}

	}
}