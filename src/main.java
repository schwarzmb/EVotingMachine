import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) throws IOException {
		VotingInterface vI = new VotingInterface();
		RegisteredVotersDatabaseHandler rVDH = new RegisteredVotersDatabaseHandler();
		VoteDatabaseHandler vDH = new VoteDatabaseHandler();
		ElectionOfficialInterface eOI = new ElectionOfficialInterface();
		VoteController voteControl = new VoteController(vI, rVDH, vDH);
		ElectionOfficialController officialControl = new ElectionOfficialController(eOI, vDH, rVDH);
		
		//need to start the system up and then decide what mode the current machine will be in.
		//the adinistrator is understood to initialize all the machines before voting to the voting interface,
		//except for the machine that they will be using which will be initialized to the election official interface
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to the Schwarz and Turner voting machine.");
		Object mode = new Object();
		boolean chooseFunction = false;
		while(!chooseFunction){
			System.out.println("\nIf this machine is for the election official then enter the word 'official'.\n"
				+ "If it is a voting machine then enter the word 'vote' and press enter: ");
			boolean validInput = false;
			while(!validInput){
				String selection = scan.nextLine();
				if(selection.equals("official")){
					mode = officialControl;
					validInput = true;
				}else if(selection.equals("vote")){
					mode = voteControl;
					validInput = true;
				}else{
					System.out.println("You did not choose a valid mode. "
							+ "Try again and enter either 'official' or 'vote': ");
				}
			}	
			System.out.println("\nThe mode chosen for this machine is: " + mode.getClass().getName() + "\n"
					+ "To confirm the mode and start the system type in 'yes'. Any other input will make "
					+ "you rechoose the mode.");
			String confirmation = scan.nextLine();
			if(confirmation.equals("yes")){
				chooseFunction = true;	
			}	
		}	
		//now the logic for getting into the correct system need to base off of what class Object is.
		if(mode.getClass().getName().equals("ElectionOfficialController")){
			boolean correctLogin = false;
			//int count = 0;
			while(!correctLogin){
				correctLogin = officialControl.verifyOfficialLogin();
			}
			officialControl.actionForInput();
		}else if(mode.getClass().getName().equals("VoteController")){
			boolean canVote = false;
			while(!canVote){
				canVote = voteControl.verifyVoterLogin();
			}
			int[] finalVote = voteControl.selectCandiates();
			voteControl.submitSelectedCandidates(finalVote);
		}
		scan.close();
	}

}
