import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class ElectionOfficialController implements ActionListener{
	//private static final VoteDatabaseHandler VoteDatabaseHandler = null;
	private ElectionOfficialInterface officialView;
	private VoteDatabaseHandler voteHandler;
	private RegisteredVotersDatabaseHandler votersHandler;
	
	public ElectionOfficialController(ElectionOfficialInterface eOI, 
			VoteDatabaseHandler vDB, RegisteredVotersDatabaseHandler rVDB){
		//this is the constructor for the ElectionOfficialController class. This will initialize the attributes
		//with the appropriate variables for them.
		this.officialView = eOI;
		this.voteHandler = vDB;
		this.votersHandler = rVDB;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//method that takes the input from the view and performs the approprate method based on the event
		//WILL IMPLEMENT WHEN DOING THE UI		
	}
	
	//this method makes sure that the official's name and password match.
	public boolean verifyOfficialLogin(){
		boolean rtnval = false;
		String credentials = officialView.officialLoginForm();
		boolean loginMatch = votersHandler.findOfficialLogin(credentials);
		if(loginMatch){
			System.out.println("You have been logged into the election official interface.\n");
			rtnval = true;
		}else{
			System.out.println("Incorrect name and password combination. Try again: \n");
		}
		return rtnval;
	}
	
	public String tallyLocalVotes() throws FileNotFoundException{
		//this method will run when the localVoteTallyButton is pressed in the view. It will take a parameter
		//of ArrayList of type integer which is gotten from the getVotes() method in the vote database.
		//this method will get the list of votes cast for each candidate ID, and will process this information
		//into a readable form and will return a String containing the current amount of local votes for each candidate
		ArrayList<Integer> localVoteList = voteHandler.getVotes();
		StringBuffer stringBuff = new StringBuffer("");
		for(int i = 0; i < voteHandler.numberOfCandidates(); i++){
			int candidateID = voteHandler.candidateID(i);
			String candidateName = voteHandler.candidateName(i);
			int voteAmount = Collections.frequency(localVoteList, candidateID);
			String tally = ("The current amount of votes for "+ candidateName + " is: "+ voteAmount+"\n");
			stringBuff.append(tally);
		}
		return(stringBuff.toString());	
		
	}
	
	public String generateOfficialReport() throws FileNotFoundException{
		//this class will only be able to be run once the polls have closed for the day. This method generates
		//an official report containing the final results of the local votes in a string. This will be encrypted 
		//and sent to the appropriate place where the official report should be sent.
		String localTally = this.tallyLocalVotes();
		String report = ("The official tally of the results at this polling location are:\n" + localTally);
		return report;
	}
	
	
	//this is the method for performing an action based on the official's text input. This will be 
	//replaced by the actionPerformed method(s) when swing is implemented
	public void actionForInput() throws FileNotFoundException{
		boolean exit = false;
		while(!exit){
			int input = officialView.getInput();
			switch(input){
			case 1:
				String localTally = this.tallyLocalVotes();
				System.out.println("The current tally of the local votes is below:\n" + localTally);
				break;
			case 2:
				System.out.println(this.generateOfficialReport());
				break;
			case 9:
				System.out.println("The program is about to be exited. Bye!");
				exit = true;
				break;
			}
		}
	}

}
