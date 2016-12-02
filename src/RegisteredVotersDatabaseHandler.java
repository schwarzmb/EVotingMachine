import java.sql.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RegisteredVotersDatabaseHandler {
	//this VotersFile contains the name of the text file used for holding 
	//Registered Voters prior to implementing a database and also contains the info for official login
	private File votersFile; 
	private File officialsFile;
	//constructor, it initializes the connection to the registeredVotersTest text file
	public RegisteredVotersDatabaseHandler(){
		votersFile = new File("registeredVotersTest.txt");
		officialsFile = new File("officialLogins.txt");
	}
	
	public boolean findOfficialLogin(String credentials){
		boolean rtnval = false;
		try{
			Scanner scan = new Scanner(officialsFile);
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				if(line.equals(credentials)){
					//login matches a login in the file, return true
					rtnval = true;
					break;
				}
			}//login not found
			scan.close();
		}catch(FileNotFoundException e){
			System.out.println("ERROR COULD NOT FIND THE FILE");
			e.printStackTrace();
		}
		return rtnval;
	}
	
	public boolean findVoterID(int ID){
		//this method takes an input of the voters ID from the controller, and loops through the file using a Scanner
		//and comparing to the ID's in there. If a match is found, true is returned. If not found false is returned.
		try{
			Scanner scan = new Scanner(votersFile);
			//while loop scans through file and compares to ID, if same returns true, if not found returns false.
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				String[] split = line.split(" ");
				int grabbedID = Integer.parseInt(split[0]);
				if(grabbedID == ID){
					//print is just for testing, will be removed
					//System.out.println("voterID found, voter ID is: " + grabbedID);
					scan.close();
					return true;
				}
			}//ID not found in file
			scan.close();
			System.out.println("VoterID not found in file.");
			return false;		
		}catch (FileNotFoundException e){
			System.out.println("ERROR COULD NOT FIND THE FILE");
			e.printStackTrace();
		}
		return false;
	}
	
	public String getVoterStatus(int ID) throws FileNotFoundException{
		//this method takes an input of the Voters ID from the controller, and loops through the file to determine
		//if the voter has already voted based on the voter status in the file. Voter status stored as string, yes means
		//already voted, no means they haven't yet. The controller will process the return.
		//this method assumes that the voterID is valid and has been checked by findVoterID already.
		Scanner scan = new Scanner(votersFile);
		String rtnval = null;
		//same while loop as in findVoterID, will change later so not repeating code.
		while(scan.hasNextLine()){
			String line = scan.nextLine();
			String[] split = line.split(" ");
			int grabbedID = Integer.parseInt(split[0]);
			//here if the ID is a match, then get the voting status in a string and return that.
			if(grabbedID == ID){
				rtnval = split[3];
				//System.out.println("The voter's voting status is: " + rtnval);
				scan.close();
				break;
			}
		}
		scan.close();
		return rtnval;
	}
	
	public void updateVoterStatus(int ID){
		//this method runs after the vote has been submitted by the voter. It takes the voters ID as a parameter
		//and updates their voting status in the file to reflect that they have already voted and cannot vote again.
		//this will be easier to do once database is made, with text file have to make new file, delete old and replace with new
	}

}
