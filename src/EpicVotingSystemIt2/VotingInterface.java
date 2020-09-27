package EpicVotingSystemIt2;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.IOException;


public class VotingInterface{
	
	private VotingController vc;
	private Staff theStaff;
	private Candidate theCandidate;
	private Admin theAdmin;
	private int numberOfAdmin = 0;
	private int numberOfStaff = 0;
	private int numberOfCandidates = 0;
	private String startDateForVotesString = null;
	private int daysCanVote = 7;
	private Date dateTest;
	

	private BufferedReader in = new BufferedReader( new InputStreamReader( System.in ));
	

	
	public static void main(String[] args){

		VotingInterface vi = new VotingInterface();	
		vi.start();
	}

	
	
	public void start(){
		vc = new VotingController();	
		commenceVoting(); 	
	}


	public void commenceVoting(){
			boolean systemQuit = false;
		while (!systemQuit){
			String input = null;
			System.out.println("\n\t\t===================== eVoting System =====================\n\n");
			System.out.println("Enter \"v\" to Vote as staff \nOR \"a\" to login in as system administrator \nOR \"h\" for help: \n");
			input = getInput();

		if (input.equalsIgnoreCase("V") ){
			timeRange();	
		}else if (input.equalsIgnoreCase("A") )	{
			validateAdmin();	
		}else if (input.equalsIgnoreCase("h") ){	
			displayHelp();
		}else{
			System.out.println("Your input was not recognised");	
			}
		}
	}
	
	
	private String getInput(){
			String theInput = "";
		try{
			theInput = in.readLine().trim();
		}catch(IOException e){
			System.out.println(e);
		}return theInput;
	}
	

	
	
	public void manageVote(){
		boolean moveOn = false; 
		int count = 0;
	while (moveOn == false){
		System.out.print("Please enter your staff ID or \"q\" to quit:");
		String input =getInput();	
		count++;	
	if (count > 2){
		moveOn = true;
		System.out.println("\nYou have entered an incorrect ID too many times");
	}else if(input.equalsIgnoreCase("q") ){
		moveOn = true;
		}else	
	{
	try{
		theStaff = vc.getStaff(Integer.parseInt(input) );
		if(theStaff.hasVoted() == 1){
			System.out.println("\nYou have voted and cannot vote again\nGood bye...!");
			moveOn = true;
		}else if (theStaff.hasVoted() == 0){
			checkPassword();
			moveOn = true;
	}else{
			System.out.println("There seems to be a problem.  Contact your administrator");
		 }
	}catch(NumberFormatException e){
		System.out.println("Invalid entry - you must enter a number\nPlease try again");
	}catch(NullPointerException e){
		System.out.println("Error, not found");
		}
		}	
	}
		System.out.print("going back to main screen...");	
	}	

	
	
	
	private void checkPassword(){

	int passAttemptCount = 0;
	String input = null;
	String thePassword = theStaff.getPassword();	
	System.out.print("Please enter your password or \"q\" to quit: ");
	input = getInput();

	while(!input.equals(thePassword) ){
		passAttemptCount++;      	 
	if(input.equalsIgnoreCase("q")  ){	
		commenceVoting();	
	}else if(passAttemptCount > 2){	
		System.out.println("\nYou have entered the wrong password three times\nGoing back to main screen...");
		commenceVoting();	
	}else{
		System.out.print("\nYour password was incorrect\nPlease enter your password again or \"q\" to quit: ");
	}
		input = getInput();
	}
		getStaffVote();	
	}
	

	
	
	private void getStaffVote(){
		int candidateCode;
		boolean retry = true;
		displayVotingScreen();	

		while (retry){
			System.out.print("\n\nEnter your candidate's code OR enter Q to quit voting : ");
	try{
		String input = getInput();
	if (input.equalsIgnoreCase("Q") ){
		retry = false;
	}else{
		candidateCode = Integer.parseInt(input);	
		theCandidate = vc.getCandidate(candidateCode);	
		System.out.print("\nYou have selected " + theCandidate.getName()+ ". \n\n"
				+ "Enter  Y to confirm or any other key to Cancel, then press ENTER : ");

	if (getInput().equalsIgnoreCase("y")){
		vc.recordVote();	
		System.out.println("\n\nThanks for voting " + theStaff.getName() + ". Bye!!!");
		retry = false;
	}else{
			displayVotingScreen();	
		 }
	}
	}catch(NumberFormatException e)	{
		System.out.println("That was not a number you entered\nPlease try again");
	}catch(NullPointerException e){
		System.out.println("This candidate code does not exit\nPlease try again");
	}catch(Exception e){	
		System.out.println("We have a problem, please contact your administrator");
		}
	}
	}
	


	
	public void displayVotingScreen(){

		System.out.println("\nWelcome "+ theStaff.getName()+"!\n");
		setNumberOfCandidates(0);
		ArrayList candidates = vc.getCandidates();	

		Iterator it = candidates.iterator();
		System.out.println("\tCode\tCandidate Name\t");
		System.out.println("\t====\t==============\t");
	
		while(it.hasNext() ){   
			theCandidate = (Candidate)it.next();
			System.out.println("\t" + theCandidate.getCandidateCode() + "\t" + theCandidate.getName());
			setNumberOfCandidates(getNumberOfCandidates() + 1);	
			}
	}

	
	
	

	private void validateAdmin(){
		boolean adminQuit = false;
		int count = 0;
		String inputAcct = null;
		String inputPw = null;
		while (!adminQuit){
			count++;	
			System.out.println("\nWelcome to Administrator Login Interface. \nEnter your account name: \nOr enter \"Q\" to quit : " );
			String input = getInput();
		if(input.equalsIgnoreCase("q")) { 
			adminQuit = true;
		}else if(count > 2){
			adminQuit = true;
			System.out.println("Sorry! You have input wrong acount name too many times!");
		}else{
			try {
			theAdmin = vc.getAdmin(input);
			checkAdminPassword();
			adminQuit = true;
			}catch(NullPointerException e) {
				System.out.println("Admin not found! Please check again!");				
			}catch(Exception e) {
				System.out.println("Unknow error! Please check again!");
				}
			}
	    }
		System.out.print("going back to main screen...");
	}
		
		
	
		public void checkAdminPassword() {
			
			int pwAttemptCount = 0;
			String inputPw = null;
			String adminPw = theAdmin.getAdminPw();	
			System.out.println("Enter your password: \nOr enter \"Q\" to quit: ");
			inputPw = getInput();

			while(!inputPw.equals(adminPw) ){
				pwAttemptCount++;      	 
			if(inputPw.equalsIgnoreCase("q")  ){	
				commenceVoting();	
			}else if(pwAttemptCount > 2){	
				System.out.println("\nSorry! You have input wrong password more than 3 times!\nGoing back to main screen...");
				commenceVoting();	
			}else{
				System.out.print("\nYour password was incorrect\nPlease enter your password again or \"Q\" to quit: ");
				}
				inputPw = getInput();
			}
				manageAdmin();	
		}
			
		
			
	


	
	private boolean manageAdmin(){
		boolean adminQuit = false;
		boolean systemQuit = false;
		while (!adminQuit){
			System.out.println("\nWelcome, "+ theAdmin.getAdminName()+"!\n");
			System.out.println("======================= Admin Menu ======================== ");
			System.out.println("\n-----------------------------------------------------------\n"
					+ "Please enter \"E\" to end voting.\n-----------------------------------------------------------\n"
					+ "Please enter \"R\" to view voting report.\n-----------------------------------------------------------\n"
					+ "Please enter \"A\" to view, delete, add administrator.\n-----------------------------------------------------------\n"
					+ "Please enter \"S\" to view, delete, add staff.\n-----------------------------------------------------------\n"
					+ "Please enter \"C\" to view, delete, add candidates.\n-----------------------------------------------------------\n"
					+ "Please enter \"D\" to set the date and time range of voting.\n-----------------------------------------------------------\n"
					+ "Please enter \"Q\" to quit and return to the main menu.\n-----------------------------------------------------------\n\n");
			String input = getInput();
			
			switch(input.toLowerCase() ){
				case "q": input.equalsIgnoreCase("q");
				System.out.println("Going back to main menu...");	
				commenceVoting();
				adminQuit = true;
				break;

				case "e": input.equalsIgnoreCase("e");
				adminQuit = true;
				systemQuit = true;
	
				setStartDateForVotesString(null);
				System.out.println("\nSorry! Voting System Closed Now!\nPlease contact your administrator for details!\n"
						+ "System administrator must set a new start date and time range to open the system! (Please check the admin menu)");
				break;
	
				case "r": input.equalsIgnoreCase("r");
				printVoteResults();	
				break;
				
				case "a": input.equalsIgnoreCase("a");
				displayAdmin();
				break;
	
				case "s": input.equalsIgnoreCase("s");
				displayAdminStaff();
				break;	

				case "c": input.equalsIgnoreCase("c");
				displayAdminCandidates();
				break;
	
				case "d": input.equalsIgnoreCase("d");
				changeVoteTime();
				break;
	
				default: System.out.println("Wrong input, please check again!");
				}
			}return systemQuit;
		}

		
	
	public void printVoteResults(){
		ArrayList candidates = vc.getCandidates();	
		int totalVoters = vc.getTotalVoters();	
		double totalVoted = 0;
		int candidateVotes = 0;
		DecimalFormat df = new DecimalFormat("###.##");

		Iterator it = candidates.iterator();
		System.out.println("\n\t\t    VOTING STATISTICS");
		System.out.println("\t\t=========================\n");
		System.out.println("Code\tName\t\t\tVotes\t(Vote%)");
		System.out.println("____\t____\t\t\t_____\t______\n");

	
		while(it.hasNext() ) {
			theCandidate = (Candidate) it.next();
			totalVoted += theCandidate.getVotes();	
		}
			it = candidates.iterator();
	
		while(it.hasNext() ) {
			theCandidate = (Candidate) it.next();
			candidateVotes = theCandidate.getVotes();	
			System.out.println(theCandidate.getCandidateCode() + "\t" + theCandidate.getName() + "\t\t" +
					candidateVotes +"\t(" + df.format((candidateVotes/totalVoted)*100) +"%)");
		}

		System.out.println("\nNumbers on voting list: " + totalVoters);
		System.out.println("Numbers voted: " + (int)totalVoted + " (" + df.format((totalVoted/totalVoters)*100)  + "%)\n");	
		System.out.println("\n----------------------------------------------------------------\n"
			+ "Please enter \"H\" to check the staff list who have voted."
			+ "\n----------------------------------------------------------------\n"
			+ "Please enter \"N\" to check the staff list who have not voted."
			+ "\n----------------------------------------------------------------\n"
			+ "Please enter any other letter and number to return the admin menu."
			+ "\n----------------------------------------------------------------\n\n");

		String input = getInput();
		if(input.equalsIgnoreCase("h") ){
			checkVotedStaff();
		}else if(input.equalsIgnoreCase("n") ){
			checkNotVotedStaff();
		}else{
			System.out.println("...going back to admin menu ");
		}
	}
	
	


	public void checkVotedStaff(){
		setNumberOfStaff(0);
		ArrayList staffs = vc.getStaff();	
		Iterator it = staffs.iterator();
		System.out.println("\n\t\t=================Staff who have voted=================\t\t\n\n");
		System.out.println("\t\tID\t\tStaff Name\t\tTimestamp");
		System.out.println("\t\t--\t\t----------\t\t---------\n");
	
		while(it.hasNext() ){   
			theStaff = (Staff)it.next();
			if(theStaff.hasVoted() == 1){
				System.out.println("\t\t" + theStaff.getId() + "\t\t" + theStaff.getName() + "\t\t" + theStaff.getTimeStampString() );
			}else{

			}
	
			setNumberOfStaff(getNumberOfStaff() + 1);	
		}
	
		System.out.println("\n-------------------------------------------------------------\n"
			+ "Please enter \"N\" to check staff list who have not voted."
			+ "\n-------------------------------------------------------------\n"
			+ "Please enter any other letter or number to go back to admin menu.\n"
			+ "-------------------------------------------------------------\n");
		String input = getInput();
		if(input.equalsIgnoreCase("n") ){	
			checkNotVotedStaff();
		}else{
			System.out.println("...going back to admin menu ");
		}
	}
	
	
	

	public void checkNotVotedStaff(){
		setNumberOfStaff(0);
		ArrayList staffs = vc.getStaff();	
		Iterator it = staffs.iterator();
		System.out.println("\n\t\t===============Staff who have not voted===============\t\t\n\n");
		System.out.println("\t\tID\t\tStaff Name");
		System.out.println("\t\t--\t\t----------");
			
		while(it.hasNext() ){   
			theStaff = (Staff)it.next();
			if(theStaff.hasVoted() == 0){
				System.out.println("\t\t" + theStaff.getId() + "\t\t" + theStaff.getName() );
			}
				setNumberOfStaff(getNumberOfStaff() + 1);	
			}

			System.out.println("\n-------------------------------------------------------------\n"
					+ "Please enter \"H\" to check staff list who have voted."
					+ "\n-------------------------------------------------------------\n"
					+ "Please enter any other letter or number to go back to admin menu.\n"
					+ "-------------------------------------------------------------\n");
			String input = getInput();
			if(input.equalsIgnoreCase("h") ){
				checkVotedStaff();
			}else{
				System.out.println("...going back to admin menu ");
			}
	
	}


	
	
	
	public void displayAdmin(){
		setNumberOfAdmin(0);
		ArrayList admins = vc.getAdmin();	
		Iterator it = admins.iterator();
		System.out.println("\t===================== Administrator List =====================\n");
		System.out.println("\tID\tAdmin Name\tAdmin Account\tPassword");	
		System.out.println("\t--\t----------\t-------------\t--------\n");
	
		while(it.hasNext() ){   
			theAdmin = (Admin)it.next();
			System.out.println("\t" + theAdmin.getAdminId() + "\t" + theAdmin.getAdminName() + "\t" +
					theAdmin.getAdminAcct() + "\t" + theAdmin.getAdminPw() );
			setNumberOfAdmin(getNumberOfAdmin() + 1);
		}
		System.out.println("\n---------------------------------------------\n"
			+ "Enter \"A\" to add an administrator\n---------------------------------------------\n"
			+ "Enter \"D\" to delete an administrator\n---------------------------------------------\n"
			+ "Enter \"Q\" to quit\n---------------------------------------------\n");
		String input =getInput();
		if(input.equalsIgnoreCase("a") ){
			addAdmincheck();
		}else if(input.equalsIgnoreCase("d") ){
			deleteAdmin();
		}else if (input.equalsIgnoreCase("q") )	{
	     manageAdmin();
		}else	
		{ 
			System.out.println("Error! Illegal input. Please check again!");
		}
	}
	
	
		
	

	public void addAdmincheck(){
		ArrayList<Admin> admins = vc.getAdmin();	
		boolean again = false;
		do{
			int adminId = 0;	
			String adminName; 
			String adminAcct = null;
			String adminPw;
			boolean validAdminId = false;
	
			System.out.print("Please enter a new admin ID: ");
			String input = getInput();
			boolean isNumeric = false;
			while (validAdminId == false){
		
		try {  
			adminId = Integer.parseInt(input); 
			isNumeric = true;
		} catch(NumberFormatException nfe) {  
			isNumeric = false;  
			}  
		
		if(input.equalsIgnoreCase("q") ){
			System.out.println("Going back to previous screen...");
			displayAdmin();
		}else if( isNumeric) {
			theAdmin = vc.getAdmin(adminId); 

		if (vc.getAdmin(adminId ) == null ){
			validAdminId = true;
		}else {
			validAdminId = false;
			System.out.println("\nID already exists...");
			System.out.print("Please enter a new admin ID: ");
			input = getInput();
			}	
		}else{
			System.out.println("Error! Illegal input, please check again!");
			System.out.print("Please enter a new admin ID: ");
			input = getInput();
		}
	}	
			adminId = Integer.parseInt(input);	
			
			boolean nameCheck = false;
			while(!nameCheck ){
				System.out.println("Please enter the name of new administrator: ");         	
				input = getInput();
			if (input.length() <= 2){	
				System.out.println("\nError! Name should be more than 2 characters!");
			}else if (input.matches(".*\\d.*") || input.contains("^.*[^a-zA-Z0-9 ].*$")){  
				System.out.println("\nError! Name has illegal symbol! Please check again!");
			}else if(!input.contains(" ") ){
				System.out.println("\nPError! Please use space to seperate first and last name!");
			}else if( (!input.matches("^.*[^a-zA-Z0-9 ].*$") )&& input.contains(" ") ){ 
				nameCheck = true; 
			}else{
				System.out.println("Error! Please try again");
			}
		}
			adminName = input;
			
			
			boolean acctCheck = false;
			while(!acctCheck ){
				System.out.println("Please enter the account name of new administrator: ");         	
				input = getInput();
			if (input.length() <= 2){	
				System.out.println("\nError! Name should be more than 2 characters!");
			}else if (input.contains("^.*[^a-zA-Z0-9-_].*$")){  
				System.out.println("\nError! Name has illegal symbol! Only \"-\" and \"_\" symbol are allowed to use!");
			}else if( (!input.matches("^.*[^a-zA-Z0-9-_].*$") )){ 
				acctCheck = true; 
			}else{
				System.out.println("Error! Wrong format! Please try again");
			}
		}			
			if (vc.getAdmin(adminAcct ) == null ){
				acctCheck = true;
			}else{
				acctCheck = false;
				System.out.println("\nAccount name already exists...");
				System.out.print("Please enter a new admin account name: ");
				input = getInput();					
			}	
			
			adminAcct = input;
						
			
			
			boolean containingComma = false;
			while(!containingComma){
				System.out.println("Please enter a password for the new administrator: ");
				input = getInput();
				if(input.contains(",") ){
					System.out.println("Error! Comma is not allowed! Please try again!");
				}else if(input.length() <= 2 ){
					System.out.println("Password should be at least 3 characters!");
				}else{
					containingComma = true;
				}
			}
			adminPw = input;	
			theAdmin = new Admin(adminId, adminName, adminAcct, adminPw);
			admins.add(theAdmin);
			vc.saveAdminData();	
			System.out.print("\nThe new admin has been added!\n----------------------------------------------------\n"
					+ "Enter \"Q\" to quit\n----------------------------------------------------\n"
					+ "Enter other letter or number to add another staff: \n"
					+ "----------------------------------------------------\n\n");
			input = getInput();
			if(input.equalsIgnoreCase("q") ){
				again = false;
				System.out.println("You are exiting");
			}else{
				again = true;
				}
			}while(again == true);
	}


	
	
	public void deleteAdmin(){
		ArrayList<Admin> admins = vc.getAdmin();	
		boolean again = true;
		do{
			int adminId = 0;
			boolean validAdminId = false;
			System.out.println("Enter an Admin ID to delete\n-----------------------------------------\n"
					+ "Enter \"Q\" to quit: \n-----------------------------------------\n");
			String input = getInput();
			boolean isNumeric = false;
			while (validAdminId == false){
		try{  
			adminId = Integer.parseInt(input); 
			isNumeric = true;
		}catch(NumberFormatException nfe){  
			isNumeric = false;  
		}  
		
		if(input.equalsIgnoreCase("q") ){
			System.out.println("Going back to previous screen...");
			validAdminId = true;
			again = false;
		}else if( isNumeric){
			theAdmin = vc.getAdmin(adminId); 	

		if ( vc.getAdmin(adminId) == null ){
			validAdminId = false;
			System.out.println("\nThis Admin ID not found in system...");
			System.out.println("Enter an Admin ID to delete\n-----------------------------------------\n"
					+ "Enter \"Q\" to quit: \n-----------------------------------------\n");
			input = getInput();
		}else{
			validAdminId = true;
			adminId = Integer.parseInt(input);
			int adminCode = 0;
			int adminCount = 0;
			boolean finding = true;
			Iterator it = admins.iterator();
			while(it.hasNext() && finding){	
				theAdmin = (Admin)it.next();
				adminCode = theAdmin.getAdminId();

		if(adminCode == adminId){
			finding = false;
		}else{
			adminCount++;
			}
			setNumberOfAdmin(getNumberOfAdmin() + 1);
		}
	
			System.out.println("\nPlease confirm to delete: \n\n" + theAdmin.getAdminName() + 
					" (Admin ID: " + theAdmin.getAdminId() + "  |  Admin Account Name: " + theAdmin.getAdminAcct() + ")" +"\n");
			System.out.println("-----------------------------------------\nEnter \"Y\" for "
					+ "YES\n-----------------------------------------\n"
					+ "Enter any other letter or number for NO: \n-----------------------------------------\n");
			input = getInput();
	
		if (input.equalsIgnoreCase("y") ){
			admins.remove(adminCount);			
			vc.saveAdminData();
			System.out.println("\n----------------------------------------------\n\tThis administrator has been deleted\n"
					+ "----------------------------------------------\n");
		}else{
			System.out.println("\nYou have cancelled the operation!\n");	
		}	
			System.out.print("Enter \"Q\" to quit\n----------------------------------------------------\n"
					+ "Enter other letter or number to delete another administrator: \n"
					+ "----------------------------------------------------\n\n");
			input = getInput();
			if(input.equalsIgnoreCase("q") ){
				again = false;
				System.out.println("");
			}else{
					again = true;
				}
			}
		}else{
			System.out.println("Error! Illegal input! Please check again!");
			System.out.println("Please enter an admin ID: ");
			input = getInput();	
			validAdminId = false;
			}
			} 
		}while(again == true);
	}

	


	public void displayAdminCandidates(){
		setNumberOfCandidates(0);
		ArrayList candidates = vc.getCandidates();	
		Iterator it = candidates.iterator();
		System.out.println("\n\t=========== Candidate List===========\t\n\n");
		System.out.println("\tID\tCandidate Name");
		System.out.println("\t----\t--------------");
	
		while(it.hasNext() ){   	
			theCandidate = (Candidate)it.next();
			System.out.println("\t" + theCandidate.getCandidateCode() + "\t" + theCandidate.getName());
			setNumberOfCandidates(getNumberOfCandidates() + 1);
		}
	
			System.out.println("\n-----------------------------------------\n"
					+ "Enter \"A\" to add candidate\n-----------------------------------------\n"
					+ "Enter \"D\" to delete candidate\n-----------------------------------------\n"
					+ "Enter \"Q\" to quit: \n-----------------------------------------\n");
			String input = getInput();
			if(input.equalsIgnoreCase("a") ){
				addAdminCandidate();
			}else if(input.equalsIgnoreCase("d") ){
				deleteAdminCandidate();
			}else if (input.equalsIgnoreCase("q") ){
				manageAdmin();
			}else{ 
					System.out.println("Error! Illegal input! Please check again!.");
			}

			System.out.println("...going back to previous screen");
	}

	
	
	
	
	public void addAdminCandidate(){
		ArrayList<Candidate> candidates = vc.getCandidates();	
		boolean again = false;
		do {
			int candidateCode = 0;	
			String name; 
			String dept;
			boolean validID = false;
	
	System.out.println("Please enter a new candidate code: ");
	String input = getInput();
	boolean isNumeric = false;
	while (validID == false){
	try {  
		candidateCode = Integer.parseInt(input); 
		isNumeric = true;
	}catch(NumberFormatException nfe)  {  
		isNumeric = false;  
	}if(input.equalsIgnoreCase("q") ){
		System.out.println("Going back to previous screen...");
		displayAdminCandidates();	
	}else if( isNumeric){
		theCandidate = vc.getCandidate(candidateCode); 
	
		if ( vc.getCandidate(candidateCode) == null ){
			validID = true;
		}else{
			validID = false;
			System.out.println("\nCandidate code already exists...");
			System.out.println("Please enter a new candidate code: ");
			input = getInput();	
			}
		}else{
			System.out.println("Error! Illegal input! Please check again!");
			System.out.println("Please enter a new candidate code: ");
			input = getInput();	
			}
	}
		candidateCode = Integer.parseInt(input);	
		boolean nameCheck = false;
		while(!nameCheck ){
			System.out.println("Please enter the name of new candidate: ");         	
			input = getInput();
		if (input.length() <= 2){	
			System.out.println("\nError! Name should be more than 2 characters!");
		}else if (input.matches(".*\\d.*") || input.contains("^.*[^a-zA-Z0-9 ].*$")){  
			System.out.println("\nError! Name has illegal symbol! Please check again!");
		}else if(!input.contains(" ") ){
			System.out.println("\nPError! Please use space to seperate first and last name!");
		}else if( (!input.matches("^.*[^a-zA-Z0-9 ].*$") )&& input.contains(" ") ){ 
			nameCheck = true; 
		}else{
			System.out.println("Error! Please try again");
			}
		}
			name = input;
			theCandidate = new Candidate(candidateCode, name, 0);
			candidates.add(theCandidate);
			vc.saveCandidateData();	
			System.out.print("\nThe new candidate has been added!\n----------------------------------------------------\n"
					+ "Enter \"Q\" to quit\n----------------------------------------------------\n"
					+ "Enter other letter or number to add another candidate: \n"
					+ "----------------------------------------------------\n\n");
			input = getInput();
			if(input.equalsIgnoreCase("q") ){
				again = false;
				System.out.println("You are exiting.");
			}else{
					again = true;
				 }
			}while(again == true);	
			}

	
	
	public void deleteAdminCandidate(){
		ArrayList<Candidate> candidates = vc.getCandidates();	
		boolean again = true;
		do {
			int candidateCode = 0;
			boolean validID = false;
			System.out.println("Enter the code of candidate to delete\n-----------------------------------------\n"
					+ "Enter \"Q\" to quit: \n-----------------------------------------\n");
			String input = getInput();
			boolean isNumeric = false;
			while (validID == false){
			try {  
				candidateCode = Integer.parseInt(input); 
				isNumeric = true;
				} catch(NumberFormatException nfe) {  
					isNumeric = false;  
				}  
			if(input.equalsIgnoreCase("q") ){
				System.out.println("Going back to previous screen...");
				validID = true;
				again = false;
				}else if( isNumeric){
					theCandidate = vc.getCandidate(candidateCode); 	
			if ( vc.getCandidate(candidateCode) == null){
				validID = false;
				System.out.println("\nError! No candidate found in system!");
				System.out.println("Enter the code of candidate to delete\n-----------------------------------------\n"
						+ "Enter \"Q\" to quit: \n-----------------------------------------\n");
				input = getInput();
				}else{
					validID = true;
					candidateCode = Integer.parseInt(input);	
					int code = 0;
					int count = 0;
					boolean finding = true;
					Iterator it = candidates.iterator();
					while(it.hasNext() && finding){	
						theCandidate = (Candidate)it.next();
						code = theCandidate.getCandidateCode();
	
					if(code == candidateCode){
						finding = false;
					}else{
						count++;
						}
					setNumberOfCandidates(getNumberOfCandidates() + 1);
					}	
					
					System.out.println("\nPlease confirm to delete \n" + theCandidate.getName()  + " (candidate code: " + theCandidate.getCandidateCode() + ")");
					System.out.println("-----------------------------------------\nEnter \"Y\" for YES\n-----------------------------------------\n"
							+ "Enter any other letter or number for NO: \n-----------------------------------------\n");
					input = getInput();
					if (input.equalsIgnoreCase("y") ){
						candidates.remove(count);	
						vc.saveCandidateData();
						System.out.println("\n----------------------------------------------\n\t"
								+ "The candidate has been deleted\n"
								+ "----------------------------------------------\n");
					}else{
						System.out.println("You have cancelled your operation!\n");
					}
					System.out.println("Enter \"Q\" to quit\n----------------------------------------------------\n"
							+ "Enter other letter or number to delete another candidate: \n"
							+ "----------------------------------------------------\n\n");
					input = getInput();
					if(input.equalsIgnoreCase("q") ){
						again = false;
						System.out.println("You are exiting.");
					}else{
							again = true;
						}
					}
				}else{
					System.out.println("Error! Illegal input, please check again!");
					System.out.println("Please enter a candidate code: ");
					input = getInput();
					validID = false;
					}
			} 	
		}while(again == true);


	}




	public void displayAdminStaff(){
		setNumberOfStaff(0);
		ArrayList staffs = vc.getStaff();	
		Iterator it = staffs.iterator();
		System.out.println("\tID\tStaff Name\t\tPassword");	
		System.out.println("\t==\t==========\t\t========\n");
	
		while(it.hasNext() ){   
			theStaff = (Staff)it.next();
			System.out.println("\t" + theStaff.getId() + "\t" + theStaff.getName() + "\t\t" + theStaff.getPassword() );
			setNumberOfStaff(getNumberOfStaff() + 1);
		}
		System.out.println("\n---------------------------------------------\n"
			+ "Enter \"A\" to add a staff\n---------------------------------------------\n"
			+ "Enter \"D\" to delete a staff\n---------------------------------------------\n"
			+ "Enter \"Q\" to quit\n---------------------------------------------\n");
		String input =getInput();
		if(input.equalsIgnoreCase("a") ){
			adminAddStaffcheck();
		}else if(input.equalsIgnoreCase("d") ){
			deleteAdminStaff();
		}else if (input.equalsIgnoreCase("q") )	{
	     manageAdmin();
		}else	
		{ 
			System.out.println("Error! Illegal input. Please check again!");
		}
	}
	
	
		

	public void adminAddStaffcheck(){
		ArrayList<Staff> staffs = vc.getStaff();	
		boolean again = false;
		do{
			int id = 0;	
			String name; 
			String password;
			boolean validID = false;
	
			System.out.print("Please enter a new staff ID: ");
			String input = getInput();
			boolean isNumeric = false;
			while (validID == false){
		
		try {  
			id = Integer.parseInt(input); 
			isNumeric = true;
		} catch(NumberFormatException nfe) {  
			isNumeric = false;  
			}  
		
		if(input.equalsIgnoreCase("q") ){
			System.out.println("Going back to previous screen...");
			displayAdminStaff();
		}else if( isNumeric) {
			theStaff = vc.getStaff(id); 

		if (vc.getStaff(id ) == null ){
			validID = true;
		}else {
			validID = false;
			System.out.println("\nID already exists...");
			System.out.print("Please enter a new staff ID: ");
			input = getInput();
			}	
		}else{
			System.out.println("Error! Illegal input, please check again!");
			System.out.print("Please enter a new staff ID: ");
			input = getInput();
		}
	}	
			id = Integer.parseInt(input);	
			boolean nameCheck = false;
			while(!nameCheck ){
				System.out.println("Please enter the name of new candidate: ");         	
				input = getInput();
			if (input.length() <= 2){	
				System.out.println("\nError! Name should be more than 2 characters!");
			}else if (input.matches(".*\\d.*") || input.contains("^.*[^a-zA-Z0-9 ].*$")){  
				System.out.println("\nError! Name has illegal symbol! Please check again!");
			}else if(!input.contains(" ") ){
				System.out.println("\nPError! Please use space to seperate first and last name!");
			}else if( (!input.matches("^.*[^a-zA-Z0-9 ].*$") )&& input.contains(" ") ){ 
				nameCheck = true; 
			}else{
				System.out.println("Error! Please try again");
			}
		}
			name = input;
			boolean containingComma = false;
			while(!containingComma){
				System.out.println("Please enter a password for the new staff member: ");
				input = getInput();
				if(input.contains(",") ){
					System.out.println("Error! Comma is not allowed! Please try again!");
				}else if(input.length() <= 3 ){
					System.out.println("Password should be more than 3 characters!");
				}else{
					containingComma = true;
				}
			}
			password = input;	
			theStaff = new Staff(id, name, 0, password, null);
			staffs.add(theStaff);
			vc.saveStaffData();	
			System.out.print("\nThe new staff has been added!\n----------------------------------------------------\n"
					+ "Enter \"Q\" to quit\n----------------------------------------------------\n"
					+ "Enter other letter or number to add another staff: \n"
					+ "----------------------------------------------------\n\n");
			input = getInput();
			if(input.equalsIgnoreCase("q") ){
				again = false;
				System.out.println("You are exiting");
			}else{
				again = true;
				}
			}while(again == true);
	}



	
	
	public void deleteAdminStaff(){
		ArrayList<Staff> staffs = vc.getStaff();	
		boolean again = true;
		do{
			int id = 0;
			boolean validID = false;
			System.out.println("Enter the staff ID to delete\n-----------------------------------------\n"
					+ "Enter \"Q\" to quit: \n-----------------------------------------\n");
			String input = getInput();
			boolean isNumeric = false;
			while (validID == false){
		try{  
			id = Integer.parseInt(input); 
			isNumeric = true;
		}catch(NumberFormatException nfe){  
			isNumeric = false;  
		}  
		
		if(input.equalsIgnoreCase("q") ){
			System.out.println("Going back to previous screen...");
			validID = true;
			again = false;
		}else if( isNumeric){
			theStaff = vc.getStaff(id); 	

		if ( vc.getStaff(id) == null ){
			validID = false;
			System.out.println("\nThis Staff ID not found in system...");
			System.out.println("Enter the staff ID to delete\n-----------------------------------------\n"
					+ "Enter \"Q\" to quit: \n-----------------------------------------\n");
			input = getInput();
		}else{
			validID = true;
			id = Integer.parseInt(input);
			int code = 0;
			int count = 0;
			boolean finding = true;
			Iterator it = staffs.iterator();
			while(it.hasNext() && finding){	
				theStaff = (Staff)it.next();
				code = theStaff.getId();

		if(code == id){
			finding = false;
		}else{
			count++;
			}
			setNumberOfStaff(getNumberOfStaff() + 1);
		}
	
			System.out.println("\nPlease confirm to delete \n" + theStaff.getName() + 
					" (Staff ID: " + theStaff.getId() + ")");
			System.out.println("-----------------------------------------\nEnter \"Y\" for "
					+ "YES\n-----------------------------------------\n"
					+ "Enter any other letter or number for NO: \n-----------------------------------------\n");
			input = getInput();
	
		if (input.equalsIgnoreCase("y") ){
			staffs.remove(count);			
			vc.saveStaffData();
			System.out.println("\n----------------------------------------------\n\tThe staff has been deleted\n"
					+ "----------------------------------------------\n");
		}else{
			System.out.println("\nYou have cancelled the operation!\n");	
		}	
			System.out.print("Enter \"Q\" to quit\n----------------------------------------------------\n"
					+ "Enter other letter or number to delete another staff: \n"
					+ "----------------------------------------------------\n\n");
			input = getInput();
			if(input.equalsIgnoreCase("q") ){
				again = false;
				System.out.println("");
			}else{
					again = true;
				}
			}
		}else{
			System.out.println("Error! Illegal input! Please check again!");
			System.out.println("Please enter a Staff ID: ");
			input = getInput();	
			validID = false;
			}
			} 
		}while(again == true);
	}

	
	
	

	
	public void timeRange(){
		java.util.Date date= new java.util.Date();	
		SimpleDateFormat theNewDateFormat = new SimpleDateFormat("dd-MM-yyyy");	
		String currentDateString = theNewDateFormat.format(date.getTime() );	

		String startDateString = getStartDateForVotesString();	
		int dateRange = getDaysCanVote();

		if(getStartDateForVotesString() == null){
				System.out.println("\nSorry! Voting System Closed Now!\nPlease contact your administrator for details!\n"
				+ "System administrator must set a new start date and time range to open the system!\n"
				+ "(Admin please log in to check the admin menu)\n");
		
		}else{
				Calendar calendarVar = Calendar.getInstance();
		 try{
			 	calendarVar.setTime(theNewDateFormat.parse(startDateString));
		 	}catch (ParseException e1){
		 			System.out.println("Error! Cannot set the start date! Please check again!");
		 	}
	
		 			calendarVar.add(Calendar.DATE, dateRange);  
		 			String endDateString = theNewDateFormat.format(calendarVar.getTime() );  


		 Date currentDate = null;	
		 try{
			 currentDate = new SimpleDateFormat("dd-MM-yyyy").parse(currentDateString);
		 	}catch (ParseException e){
		 			System.out.println("Error! Cannot set the current date! Please check again!");
		 	}

		 	Date startDate = null;	
		 	try{
		 		startDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDateString);
		 	   }catch (ParseException e){
		 		System.out.println("Error! Cannot set the start date! Please check again!");
		 	   }

		 	Date endDate = null;	
		 	try{
		 		endDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDateString);
		 		}catch (ParseException e){
		 				System.out.println("Error: Cannot set the end date! Please check again!");
		 		}

		if(!currentDate.after(endDate) && !currentDate.before(startDate) ){
		 		manageVote();	
		}else if(currentDate == null || startDate == null || endDate == null){
			System.out.println("Error dates could not be set correctly");
		}else{
			System.out.println("I am sorry the current date: " + currentDateString + " is out of the date range. "
					+ "\n(You can only vote between: " + startDateString + " and " + endDateString + ")\n"
							+ "going back to main screen...");
		   	}
		}
	}


	
	
	public void changeVoteTime(){
		if(getStartDateForVotesString() == null){
			System.out.println("Currently there is no start date");
		}else{
			int currentDateRange = getDaysCanVote();	
			String originalStartDate =  getStartDateForVotesString();	
			Calendar calendarVar = Calendar.getInstance();
			SimpleDateFormat theNewDateFormat = new SimpleDateFormat("dd-MM-yyyy");

			try{
				calendarVar.setTime(theNewDateFormat.parse(originalStartDate));
				}catch (ParseException e1){
					System.out.println("Error! Cannot set the original start date! Please check again!");
				}

		calendarVar.add(Calendar.DATE, currentDateRange);  
		String endDateString = theNewDateFormat.format(calendarVar.getTime() ); 
		System.out.println("\nThe Voting system currently opens on the " + originalStartDate + " "
				+ "and lasts for " + currentDateRange + " days\nThat makes a close date of: " + endDateString + "\n");
	}
		System.out.println("Confirm to set new time?\n(\"Y\" for YES or \"N\" for NO): ");
		String input = getInput();

		while(!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n") ){
			System.out.print("Illegal input!\nPlease enter \"Y\" for YES or \"N\" for NO: ");
			input = getInput();
		}if(input.equalsIgnoreCase("y") ){
			while(!input.equalsIgnoreCase("q") ){
				System.out.println("\n----------------------------------------------------\n"
						+ "Enter \"S\" to set the voting start date.\n"
						+ "----------------------------------------------------\n"
						+ "Enter \"D\" to set the time range of voting system.\n"
						+ "----------------------------------------------------\n"
						+ "Enter \"Q\" to quit:\n"
						+ "----------------------------------------------------\n");
						input = getInput();
	

			if(input.equalsIgnoreCase("S") ){	
				setVoteDate();
			}else if(input.equalsIgnoreCase("d") ){
				setTimeRange();
			}else if(input.equalsIgnoreCase("q") ){
				System.out.println("...going back to previous screen");
			}else{
				System.out.println("Illegal input! Please check again!");
			}
		}
		}else if(input.equalsIgnoreCase("n") ){
			System.out.println("...going back to admin screen");
			manageAdmin(); 	
		}else{
			System.out.println("Error! Please contact your administrator.");
			}
	
	}
	

	
	
	public void setTimeRange(){
		System.out.print("Please enter the total days you want the voting system to be opened: ");
		boolean isNumeric = false;
		int inputNumber;
	
		while(isNumeric == false){
			String input = getInput();	
		try{  
			inputNumber = Integer.parseInt(input); 
			isNumeric = true;
			} catch(NumberFormatException nfe) {  
				isNumeric = false;  
			}

		if(isNumeric){
			inputNumber = Integer.parseInt(input);
			if(inputNumber >= 1){
				setDaysCanVote(inputNumber);
				int currentDateRange = getDaysCanVote();
				System.out.println("The new date range is " + currentDateRange + " days");
			}else{
				System.out.println("Error! Days must be larger than 0!");
			}
	}else{
		System.out.print("Illegal input! Please check again: ");
		}
	}
		isNumeric = false; 
	}
	

	
	
	public void setVoteDate(){
		boolean realDate = false;
		String combinedDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		while(!realDate){
			int year = 0;	
			String month = null;
			int date = 0;
			combinedDate = null;
			System.out.println("\nPlease input the date step by step according to remind.\n");

		while(year < 2019 || year > 2099){	
			boolean isNumeric = false;
			System.out.println("Enter which year to open the voting system (Format: yyyy e.g. 2020): ");
			String input = getInput();

		try{  
			year = Integer.parseInt(input); 
			isNumeric = true;
			}catch(NumberFormatException nfe) {  
			isNumeric = false;  
		} if(isNumeric){	
			year = Integer.parseInt(input);
			if(year < 2019 || year > 2099) {	
				System.out.println("The year has to be between 2019 and 2099");
				}else{
						System.out.println("Set already!");
					 }
	
				}else{
						System.out.print("Error! Illegal input, please check again: ");
					 }
			isNumeric = false;
		}
			
			System.out.println("The year you set is:" + year);	
		while(month == null){
			System.out.println("\nEnter which month to open the voting system (Format: mm e.g. 01 or 11): ");
			String input = getInput();
		switch (input) {
			case "01": month = "01";break;
			case "02": month = "02";break;
			case "03": month = "03";break;
			case "04": month = "04";break;
			case "05": month = "05";break;
			case "06": month = "06";break;
			case "07": month = "07";break;
			case "08": month = "08";break;
			case "09": month = "09";break;
			case "10": month = "10";break;
			case "11": month = "11";break;
			case "12": month = "12";break;
			default: 	System.out.println("Wrong input format, please try again!");	
			month = null;break;	
			}
		}	
			System.out.println("The month you set is: " + month);

			while(date < 1 || date > 31){
				System.out.println("\nEnter which day to open the voting system (format: dd e.g. 03 or 25): ");
				String input = getInput();	
				boolean isNumeric = false;
			while(!isNumeric){
		try{  
				date = Integer.parseInt(input); 
				isNumeric = true;
		}catch(NumberFormatException nfe) {  
				isNumeric = false; 
				System.out.print(" Illegal input or invalid day, please check again: ");
				input = getInput();
				}	
		}if (date < 1 || date > 31)	{
			System.out.println("Day must be a number between 01 and 31.");
			}
			date = Integer.parseInt(input);	
			isNumeric = false;		
			}	
			System.out.println("The day you set is: " + date);	
			combinedDate = date + "-" + month+ "-" + year;	
		try {
			sdf.setLenient(false);	
			setDateTest(sdf.parse(combinedDate) );
			System.out.println("\n" + combinedDate + " is a vaild date\n");	
			realDate = true;
		} catch (ParseException e) {
			System.out.println("Sorry, " + combinedDate + " is an invaild date, please try again!\n");
			realDate = false;
			}	
		}
			String str = sdf.format(dateTest);	
			setStartDateForVotesString(str);
			Date nowDate = new Date();

			if(realDate && nowDate.after(dateTest) ){
				System.out.println(combinedDate + " has already happened (Now is: " + nowDate + ")."
						+ "\nBut the date you set has still been saved");	
			}else{
				System.out.println("\nThe date you set: " + combinedDate + " has been saved");	
				}

	}
	

	
	
	public void displayHelp() {		
		System.out.println("\n\t\t===================== Help Menu =====================\n\n");
		System.out.println("1. When can you vote?\nAnswer: You can only vote when the administrator open the voting system.\n");
		System.out.println("2. How many times can I vote?\nAnswer: Only 1 time.\n");
		System.out.println("3. Can I cancel or change my vote?\nAnswer: No, you cannot. Please consider clearly to vote.\n");
		System.out.println("4. How can I do if I forgot my ID or password?\nAnswer: Please feel free to contact your administrator.\n");
		System.out.println("5. Is there any time range to vote?\nAnswer: Yes, please contact your administrator for the time range for voting.\n");
		System.out.println("6. How many candidates I can vote?\nAnswer: Only 1 candidate can be chosen.\n\n");
	}
	

	
	

	public int getDaysCanVote() {
		return daysCanVote;
	}

	public void setDaysCanVote(int daysCanVote) {
		this.daysCanVote = daysCanVote;
	}

	public String getStartDateForVotesString() {
		return startDateForVotesString;
	}

	public void setStartDateForVotesString(String startDateForVotesString) {
		this.startDateForVotesString = startDateForVotesString;
	}
	public Date getDateTest() {
		return dateTest;
	}

	public void setDateTest(Date dateTest) {
		this.dateTest = dateTest;
	}
	public int getNumberOfStaff() {
		return numberOfStaff;
	}

	public void setNumberOfStaff(int numberOfStaff) {
		this.numberOfStaff = numberOfStaff;
	}

	public int getNumberOfCandidates() {
		return numberOfCandidates;
	}
	public void setNumberOfCandidates(int numberOfCandidates) {
		this.numberOfCandidates = numberOfCandidates;
	}
	public int getNumberOfAdmin() {
		return numberOfAdmin;
	}
	public void setNumberOfAdmin(int numberOfAdmin) {
		this.numberOfAdmin = numberOfAdmin;
	}
	
}