import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class VoteController{	
	private VotingInterface voteView;
	private RegisteredVotersDatabaseHandler votersHandler;
	private VoteDatabaseHandler voteHandler;
	
	public VoteController(VotingInterface vI, RegisteredVotersDatabaseHandler rVDB, VoteDatabaseHandler vDB){
		//this is the constructor for the VoteController class. It will initialize the attributes with 
		//the appropriate variables for them.
		this.voteView = vI;
		this.votersHandler = rVDB;
		this.voteHandler = vDB;
	}
	
	public void ActionPerformed(ActionEvent e){
		//method that takes the input from the view and performs the approprate method based on the event
	}
	
	public boolean verifyVoterLogin() throws FileNotFoundException{
		//this method obtains the entered voterID from the VotingInterface and checks that the ID is 
		//registered to a registered voter. This is checked in the RegisteredVotersDatabaseHandler. If the voter
		//is found, then they will next need to have their status checked in the checkVoterStatus method.
		int ID = voteView.voterLoginForm();
		boolean idFound = votersHandler.findVoterID(ID);
		boolean rtnval = false;
		if(idFound){
			//ID was found
			System.out.println("The ID provided was found in the registered voters file");
			boolean voterStatus = this.checkVoterStatus(ID);
			if(voterStatus){
				//voter hasn't voted yet
				System.out.println("You have been successfully logged into the voting system.\n");
				rtnval = true;
			}else{
				System.out.println("You already voted in this election and may not do so again.\n");
			}
		}else{
			System.out.println("The ID provided was not found. Try again.\n");
		}
		return rtnval;
	}
	
	//helper method used in verifyVoterLogin to check Voter Status. 
	private boolean checkVoterStatus(int ID) throws FileNotFoundException{
		//this method checks that the voter hasn't already voted yet. This is checked from the registeredVotersDatabase
		//which is done through the getVoterStatus() method of the handler. If already voted then the person 
		//is not logged in, if they have not already voted then they are logged into the system and the UI updates
		//to show the candidates able to vote for.
		boolean rtnval = false;
		if(votersHandler.getVoterStatus(ID).equals("no")){
			//voter hasn't voted yet, return true
			rtnval = true;	
		}
		return rtnval;
	}
	
	//this method is what actually selects the candidates from the interface.
	public int[] selectCandiates() throws FileNotFoundException{
		ArrayList<String> candidates = voteHandler.candidateList();
		//placeholder for currentVote
		int[] currentVotes = new int[candidates.size()];
		boolean confirm = false;
		while(!confirm){
			for(int i = 0; i < candidates.size(); i++){
				String currPosition = candidates.get(i);
				System.out.println(currPosition);
				/*while loop to check if the entered id is an actual candidateID, if not make them reenter.
				MAY NEED TO REMOVE ONCE USING UI TO ALLOW FOR PEOPLE NOT TO VOTE FOR CERTAIN POSITIONS, but
				for now with the text interface making it so that it is necessary to choose an actual candidate 
				to prevent entry errors*/
				boolean actualID = false;
				//using a counter to keep track of how many times entered. on second loop it'll print message
				int count = 0;
				while(!actualID){
					if(count > 0){
						System.out.println("That was not a valid candidate ID. Try again.");
					}
					int vote = voteView.voteForCandidate(currPosition);
					actualID = this.checkCandidateID(vote);
					count+=1;
					//now add to currentVotes if actualID == true since that is a real ID
					if(actualID == true){
						currentVotes[i] = vote;
					}
				}
				//currentVotes[i] = vote;
			}
			confirm = voteView.confirmVote(this.showCurrentVotes(currentVotes));
		}
		return currentVotes;
	}
	
	//this helper method is used to check that the id entered when voting by the voter is an actual candidate ID
	private boolean checkCandidateID(int id) throws FileNotFoundException{
		boolean realID = false;
		for(int i = 0; i < voteHandler.numberOfCandidates(); i++){
			if(id == voteHandler.candidateID(i)){
				//real candidateID, make realID = true
				realID = true;
				break;
			}
		}
		return realID;
	}
	
	//helper method for reviewing votes. creates a string showing who is currently voted for.
	//I copied code from the ElectionOfficialController so that'll need to be dealt with later when using database.
	private String showCurrentVotes(int[] votes) throws FileNotFoundException{
		//ArrayList<String> nameAndID = voteHandler.candidateNameAndID();
		StringBuffer stringBuff = new StringBuffer("");
		for(int i = 0; i < voteHandler.numberOfCandidates(); i++){
			String candidateName = voteHandler.candidateName(i);
			int candidateID = voteHandler.candidateID(i);
			for(int j = 0; j < votes.length; j++){
				if(votes[j] == candidateID){
					stringBuff.append(candidateName + "\n");
					break;
				}
			}
		}
		return stringBuff.toString();
	}
	
	public void submitSelectedCandidates(int[] votes) throws IOException{
		//this method will send the selected candidates to the voteDatabaseHandler to add to their vote amount.
		//this method is performed when the select votes button is pressed, and after the confirm vote message is shown.
		//creating a local int[] to store each vote before its submitted, that way we can check and make sure each
		//candidate is only getting voted for once and not multiple times.
		int[]tempVotes = new int[votes.length];
		for(int i = 0; i < votes.length; i++){
			int vote = votes[i];
			//this IntStream checks if the tempVotes already contains voteID for the candidate and returns true if so
			boolean isMultiple = IntStream.of(tempVotes).anyMatch(x -> x == vote);
			if(!isMultiple){
				tempVotes[i] = vote;
				this.voteHandler.addVoteForCandidate(vote);
			}	
		}
	}
	
}
