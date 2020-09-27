package EpicVotingSystemIt2;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class AdminUnitTest {

	@Test
	public void test() {
		
		Admin test = new Admin(201234, "Donald Trump", "donald20", "donald12345");				
		Assert.assertEquals(201234, test.getAdminId());
		Assert.assertEquals("Donald Trump", test.getAdminName());
		Assert.assertEquals("donald20", test.getAdminAcct() );
		Assert.assertEquals("donald12345", test.getAdminPw() );		
		
		System.out.println("Start unit test!");	
		
		}


}
