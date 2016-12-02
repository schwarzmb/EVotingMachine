import java.awt.event.ActionEvent;
import java.util.Scanner;

import javax.swing.*;

public class VotingInterface {
	//private JFrame voteFrame;
	//private JPasswordField enterVoterID;
	//private JRadioButton candidate1, candidate2;
	//private JButton submitButton, login;
	//private JOptionPane confirmVoteMsg, reviewVoteMsg;
	
	public VotingInterface(){
		//constructor for the voting interface.
		//The UI will be set up here by initializing the swing items with their action events and handlers/listeners
		
	}
	
 	public int voterLoginForm(){
 		//this class will run first, it contains a JPasswordField and JButton which asks the 
 		//voter for their voterID and then when they press the login button. 
 		Scanner loginScan = new Scanner(System.in);
 		System.out.println("Welcome to the voting machine. "
 				+ "Enter your driver license number to login(9 digits max):");
 		int enteredID = loginScan.nextInt();
 		loginScan.nextLine();
 		//loginScan.close();
 		return enteredID;
 	}
 	
 	//takes a list of candidates as a parameter to show who can be voted for
 	public int voteForCandidate(String candidates){
 		//this method runs after the voter is verified and logged in. It will display the 
 		//candidates and the JRadioButtons associated with them.
 		int chosenCandidate;
 		Scanner chooseCandidate = new Scanner(System.in);
 		System.out.println("Enter the candidate ID of the candidate that you want to vote for:  ");
 		chosenCandidate = chooseCandidate.nextInt();
 		chooseCandidate.nextLine();
 		//chooseCandidate.close();
 		return chosenCandidate;
 	}
 	
 	public boolean confirmVote(String currVotes){
 		System.out.print("Your current votes are for:\n" + currVotes);
 		System.out.println("Do you wish to confirm and submit your votes? Once done votes are finalized.");
 		System.out.println("Enter yes to confirm, anything else will not confirm your votes.");
 		Scanner confirm = new Scanner(System.in);
 		if(confirm.nextLine().equals("yes")){
 			//confirm.close();
 			System.out.println("Your votes are to be submitted and you will be logged out. Thanks!");
 			return true;
 		}else{
 			//confirm.close();
 			return false;
 		}	
 	}
 	
}
