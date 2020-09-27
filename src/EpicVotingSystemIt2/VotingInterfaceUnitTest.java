package EpicVotingSystemIt2;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
public class VotingInterfaceUnitTest {

	@Test
	public void test() {
		
		VotingInterface test = new VotingInterface();
		
		test.setNumberOfAdmin(8);
		assertEquals(8, test.getNumberOfAdmin() );
		
		test.setNumberOfStaff(99);
		assertEquals(99, test.getNumberOfStaff() );	
		
		test.setNumberOfCandidates(6);
		assertEquals(6, test.getNumberOfCandidates() );
		
		test.setDaysCanVote(90);
		assertEquals(90, test.getDaysCanVote() );
		
		test.setStartDateForVotesString("01-06-2020");
		assertEquals("01-06-2020", test.getStartDateForVotesString() );	
		
		test.setDateTest(new Date() );
		assertEquals(new Date(), test.getDateTest() );
		
		System.out.println("Start unit test!");	
				
		
	}

}
