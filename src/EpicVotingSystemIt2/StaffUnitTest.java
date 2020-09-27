package EpicVotingSystemIt2;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class StaffUnitTest {

	@Test
	public void test() {
		Staff test = new Staff(201234, "Donald Trump", 0, "donald12345", "02-06-20 12:04:42");				
		Assert.assertEquals(201234, test.getId());
		Assert.assertEquals("Donald Trump", test.getName());
		Assert.assertEquals(0, test.hasVoted() );
		Assert.assertEquals("donald12345", test.getPassword() );
		Assert.assertEquals("02-06-20 12:04:42", test.getTimeStampString() );
		
		System.out.println("Start unit test!");	
		
		}


}
