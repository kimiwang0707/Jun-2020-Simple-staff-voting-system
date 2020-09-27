package EpicVotingSystemIt2;

public class Candidate {

    int candidateCode = 999;
    String name = null;
    int votes = 0; 

    public Candidate(int candidateCode, String name, int votes)
    {
        this.candidateCode = candidateCode;
        this.name = name;
        this.votes = votes;
    }
    

    public int getCandidateCode ()
    {
        return candidateCode;
    }

    public String getName()
    {
        return  name;
    }

    public int getVotes()
    {
        return  votes;
    }

    public void setCandidateCode(int candidateCode) {
		this.candidateCode = candidateCode;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setVotes(int votes) {
		this.votes = votes;
	}


	public void addVote()
    {
        votes++;
    }



}
