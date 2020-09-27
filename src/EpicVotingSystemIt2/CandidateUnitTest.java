package EpicVotingSystemIt2;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class CandidateUnitTest {

	@Test
	public void test() {
		Candidate test = new Candidate(1, "Donald Trump", 10);		
		
		Assert.assertEquals("Donald Trump", test.getName());
		Assert.assertEquals(1, test.getCandidateCode() );
		Assert.assertEquals(10, test.getVotes() );
		
		System.out.println("Start unit test!");	
		
		}


}