package EpicVotingSystemIt2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AdminUnitTest.class, StaffUnitTest.class,
		CandidateUnitTest.class, VotingControllerUnitTest.class, 
		VotingInterfaceUnitTest.class })

public class AllUnitTest {
	
}