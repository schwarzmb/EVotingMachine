import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class VoteDatabaseHandler {
	//this voteFile contains the name of a text file used to hold votes prior to implementing a database
	private String voteFileName;
	private File voteFile;
	private File candidates;
	
	public VoteDatabaseHandler(){
		//constructor for the class
		voteFileName = ("VotesTest.txt");
		voteFile = new File(voteFileName);
		candidates = new File("CandidatesIDsTest.txt");
	}
	
	
	public void addVoteForCandidate(int candidateID) throws IOException{
		//this method takes a candiateID as a parameter from the controller when the vote has been submitted,
		//and adds the candidateID on a new line in the voteFile. This is counted as a vote for the candidate
		//whose ID was added.
		String tempID = String.valueOf(candidateID);
		FileWriter candidateWriter = new FileWriter(voteFileName, true);
		candidateWriter.write("\n" + tempID);
		candidateWriter.close();
	}
	
	public ArrayList<Integer> getVotes() throws FileNotFoundException{
		//this method loops through the voteFile and obtains every vote cast and adds the candidate ID for each cast vote 
		//to an ArrayList of type int. This ArrayList is returned to the controller
		//which then groups the votes by candidate
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		Scanner scan = new Scanner(voteFile);
		while(scan.hasNextLine()){
			int addVote = Integer.parseInt(scan.nextLine());
			returnList.add(addVote);
		}
		scan.close();
		return returnList;
	}
	
	public ArrayList<String> candidateList() throws FileNotFoundException {
		ArrayList<String> returnList = new ArrayList<String>();
		Scanner candidateScan = new Scanner(candidates);
		StringBuffer stringBuff = new StringBuffer("");
		while(candidateScan.hasNextLine()){
			String line = candidateScan.nextLine();
			String[] split = line.split(" ");
			//trying something new, making it so it breaks up into parts by what role candidate is running for
			if(split[0].equals("||")){
				String[] tempArray = Arrays.copyOfRange(split, 1, split.length);
				//used helper method arrayToString to make to string in proper format.
				stringBuff.append(arrayToString(tempArray) + "\n");
			}else if(split[0].equals("break")){
				//this is the end of the candidates for that position. 
				//Add to the stringbuffer and then reset stringbuff to ("")
				String pos = stringBuff.toString();
				returnList.add(pos);
				stringBuff.setLength(0);
			}else if(split[0].equals("end")){
				//this is the end of the list. break from loop
				break;		
			}else{
				//this is where the candidates and their info gets added to the stringBuffer.
				int candidateID = Integer.parseInt(split[0]);
				//the split.length -1 is the candidates name without the Party. making party separate
				int delim = split.length -1;
				String candidateName = arrayToString(Arrays.copyOfRange(split, 1, delim));
				String party = arrayToString(Arrays.copyOfRange(split, delim, split.length));
				String currentCandidate = (candidateName + " (" + party + "): " +  candidateID + "\n");
				stringBuff.append(currentCandidate);		
			}
		}
		candidateScan.close();
		return returnList;
	}
	
	private boolean isInteger(String input){
		try{
			Integer.parseInt(input);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	private String arrayToString(String[] list){
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(String item : list){
			if(!first){
				sb.append(" ");
			}
			sb.append(item);
			first = false;
		}
		return sb.toString();
	}

	//going to make methods to return the candidates name and their id with parameter for location in arrayList
	
	//returns the candidateID for a specific index
	public int candidateID(int index) throws FileNotFoundException{
		ArrayList<String> nameAndID = this.candidateNameAndID();
		String[] candidateSplit = nameAndID.get(index).split(" ");
		return Integer.parseInt(candidateSplit[0]);
	}
	
	//returns the candidateName for a specific index 
	public String candidateName(int index) throws FileNotFoundException{
		ArrayList<String> nameAndID = this.candidateNameAndID();
		String[] candidateSplit = nameAndID.get(index).split(" ");
		String candidateName = arrayToString(Arrays.copyOfRange(candidateSplit, 1, candidateSplit.length));
		return candidateName;
	}
	
	//this method returns the number of candidates to be used in the voteController for looping.
	public int numberOfCandidates() throws FileNotFoundException{
		ArrayList<String> nameAndID = this.candidateNameAndID();
		int count = nameAndID.size();
		return count;
	}
	
	//returns the candidateNames and their ID as a string in an ArrayList, with ID coming first then candidate name
	private ArrayList<String> candidateNameAndID() throws FileNotFoundException{
		ArrayList<String> rtnArrayList = new ArrayList<String>();
		Scanner candidateScan = new Scanner(candidates);
		while(candidateScan.hasNextLine()){
			String temp = candidateScan.nextLine();
			String[] split = temp.split(" ");
			if(this.isInteger(split[0])){
				int candidateID = Integer.parseInt(split[0]);
				int delim = split.length -1;
				String candidateName = arrayToString(Arrays.copyOfRange(split, 1, delim));
				String nameAndID = (candidateID + " " + candidateName);
				rtnArrayList.add(nameAndID);
				}
			}
		candidateScan.close();
		return rtnArrayList;
	}
	
}
