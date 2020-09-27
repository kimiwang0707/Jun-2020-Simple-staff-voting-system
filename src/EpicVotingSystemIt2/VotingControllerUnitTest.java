package EpicVotingSystemIt2;

import static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

public class VotingControllerUnitTest {

	@Test
	public void test() {
		
		VotingController test = new VotingController();
		
		test.getAdmin(1);
		test.getCandidate(3);
		test.getStaff(2);
		test.getCandidates();
		test.recordVote();
		test.saveCandidateData();
		test.saveStaffData();
		test.getTotalVoters();
		
		System.out.println("Start unit test!");	
		}


}