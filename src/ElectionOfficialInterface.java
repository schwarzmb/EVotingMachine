import java.awt.event.ActionEvent;
import java.util.Scanner;
import javax.swing.*;

public class ElectionOfficialInterface {
	//private JButton localVoteTallyButton, submitOfficialReportButton;
	//private JOptionPane tallyResult;
	
	public ElectionOfficialInterface(){
		//this is the constructor for the election official interface. 
		//The UI will be set up here by initializing the buttons with their action events and handlers/listeners

	}
	//this method logs the official in
	public String officialLoginForm(){
		Scanner loginScan = new Scanner(System.in);
		System.out.println("Enter your first and last name then your password to login. \n"
				+ "Example Format: Randy Marsh 123456789");
		String line = loginScan.nextLine();
		return line;
	}
	
	//this method is used to hold the text interface for the current submission
	public int getInput(){
		Scanner myscan = new Scanner(System.in);
		System.out.println("Welcome to the Election Official System Enter the number of what you want to do:");
		System.out.println("1) get a tally of the local votes.\n2) Generate an official Report.\n9) Exit the program");
		int selected = myscan.nextInt();
		myscan.nextLine();
		//myscan isn't closed because when I close it it automatically skips waiting for user input the 2nd time
		//temporary fix 
		return selected;	
	}
	
}
