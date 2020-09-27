package EpicVotingSystemIt2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class VotingController
{
    private ArrayList<Staff> staffs = new ArrayList<Staff>();
    private ArrayList<Candidate> candidates = new ArrayList<Candidate>();
    private ArrayList<Admin> admins = new ArrayList<Admin>();
    private Staff theStaff;
    private Candidate theCandidate;
    private Admin theAdmin;
   

	public VotingController(){
		loadAdminData();
        loadStaffData();
        loadCandidateData();    
    }

	
	
	public void loadAdminData(){
        try	{
             String fileName = "admin.txt";
             File theFile = new File(fileName);
             BufferedReader reader = new BufferedReader(new FileReader(theFile) );	
             String adminData;

         while( (adminData = reader.readLine() )!= null){
                 String[] adminDetails = adminData.split(",");	
                 int adminId = Integer.parseInt(adminDetails[0]);		
                 theAdmin = new Admin(adminId, adminDetails[1], adminDetails[2], adminDetails[3]);	
                 admins.add(theAdmin);	
             }
             reader.close();
         }catch(IOException e){
             System.out.println("Error! Loading issue!");
         }catch(Exception e){
            System.out.println("Error! Unknown problem! Please check again!");
        }
    }

	
	
	
    public Admin getAdmin(String adminAcct) {
    	Iterator<Admin> it = admins.iterator();
    	while(it.hasNext()) {
    	theAdmin = (Admin) it.next();
    	if(theAdmin.getAdminAcct().equalsIgnoreCase(adminAcct) ) {
    	   return theAdmin;
    	  }
    	}
    	return null;
    }
	
    public Admin getAdmin(int adminId) {
    	Iterator<Admin> it = admins.iterator();
    	while(it.hasNext()) {
    	theAdmin = (Admin) it.next();
    	if(theAdmin.getAdminId() == adminId ) {
    	   return theAdmin;
    	  }
    	}
    	return null;
    }
    
        
	
    
    public ArrayList<Admin> getAdmin(){
        return admins;
    }
    
     
    
   
    public void loadCandidateData(){
        try	{
             String fileName = "candidates.txt";
             File theFile = new File(fileName);
             BufferedReader reader = new BufferedReader(new FileReader(theFile) );	

             String candidateData;

         while( (candidateData = reader.readLine() )!= null)	
             {
                 String[] candidateDetails = candidateData.split(",");	
                 int code = Integer.parseInt(candidateDetails[0]);		
                 int votes = Integer.parseInt(candidateDetails[2]);
                 theCandidate = new Candidate(code, candidateDetails[1], votes);	
                 candidates.add(theCandidate);	
             }
             reader.close();
         }catch(IOException e){
             System.out.println("Error! There was a problem with loading staff names from file");
         }
    }

  
    
    
    public Candidate getCandidate(int candidateCode) {
        Iterator<Candidate> it = candidates.iterator();
        while(it.hasNext() ){
            theCandidate = (Candidate) it.next();
            if(theCandidate.getCandidateCode() == candidateCode) {
                return theCandidate;
            }
        }
        return null;
    }
    
    
    
    public ArrayList getCandidates(){
    	return candidates;
    }
    
    
    
    public void loadStaffData(){
         try {
             String fileName = "staff.txt";
             File theFile = new File(fileName);
             BufferedReader reader = new BufferedReader(new  FileReader(theFile) ); 

             String staffData;
             String[] staffDetails;

             while( (staffData = reader.readLine() ) != null)		
             {
                 staffDetails = staffData.split(",");
                 int id = Integer.parseInt(staffDetails[0]);
                 int voted = Integer.parseInt(staffDetails[2]);
                 theStaff = new Staff(id, staffDetails[1], voted, staffDetails[3], staffDetails[4]);
                 staffs.add(theStaff);		
             }
             reader.close();
         }
         catch(IOException e)	
         {
              System.out.println("Error! There was a problem with loading staff names from file");
         }
         catch(Exception e)	
         {
             System.out.println("Error! Unknown problem accoured during loading the staff names from file.");
         }
    }

   
    
    
    public Staff getStaff(int id){
        Iterator<Staff> it = staffs.iterator();
        while(it.hasNext() )		
        {
            theStaff = (Staff) it.next();
            if(theStaff.getId()== id)		
            {
                return theStaff;
            }
        }
        return null;
    }

    
    
    
    
    public ArrayList<Staff> getStaff(){
        return staffs;
    }
    
    
  
    
    
    public void recordVote(){
    	java.util.Date date= new java.util.Date();								
    	Timestamp theTimeStamp= new Timestamp(date.getTime());    																				
    	DateFormat theDateFormat = new SimpleDateFormat("dd-MM-YY hh:mm:ss");  
    	String timeStampString = theDateFormat.format(theTimeStamp);
        theStaff.setVoted();									
        theStaff.setTimeStampString(timeStampString);			
        theCandidate.addVote();									
        saveStaffData();										
        saveCandidateData();									
    }

    
    
    public void saveAdminData() {
    	try {
    		BufferedWriter writer = new BufferedWriter(new FileWriter("admin.txt"));
    		Iterator<Admin> it = admins.iterator();
    		String adminDetails;
    		while(it.hasNext()) {
    			theAdmin = (Admin)it.next();
    			adminDetails = theAdmin.getAdminId() + "," + theAdmin.getAdminName() + "," 
    					+ theAdmin.getAdminAcct() + "," + theAdmin.getAdminPw() + "\n";
    			writer.write(adminDetails);
    		}
    		writer.close();
    	}catch(IOException e) {
    		 System.out.println(e);
    	}
    }
    
    
 
    
    
    public void saveStaffData(){
        try{
        	BufferedWriter writer = new  BufferedWriter(new FileWriter("staff.txt") );
        	Iterator<Staff> it = staffs.iterator();
            String staffDetails;
            while(it.hasNext() ){
                theStaff = (Staff) it.next();
                staffDetails = theStaff.getId() + "," +theStaff.getName() + "," + theStaff.hasVoted() + "," + theStaff.getPassword() + "," +theStaff.getTimeStampString() + "\n";
                writer.write(staffDetails);
            }
            writer.close();
        }catch(IOException e) {
            System.out.println(e);
        }
    }

    
    
    
    public void saveCandidateData()
    {
        try
        {
            BufferedWriter writer = new  BufferedWriter(new FileWriter("candidates.txt") );	
            Iterator<Candidate> it = candidates.iterator();
            String candidateDetails;         
            while(it.hasNext() )		
			{
                theCandidate = (Candidate) it.next();
                candidateDetails = theCandidate.getCandidateCode() + "," +theCandidate.getName() + "," + theCandidate.getVotes() + "\n";
                writer.write(candidateDetails);
            }
            writer.close();
        }
        catch(IOException e)		
		{
        	System.out.println(e);
        }
    }
    


  
    public int getTotalVoters(){
        return staffs.size();
    }
    

}