package EpicVotingSystemIt2;

public class Staff
{
    private int id;
    private String name;
    private int voted; 
    private String password;
    private String timeStampString = null;
    

    public Staff(int id, String name, int voted, String password, String timeStampString){
            this.id = id;
            this.name = name;
            this.voted = voted;
            this.password = password;
            this.timeStampString= timeStampString;
    }


	public void setId(int id){
       this.id = id;
    }

    public void setName(String name){
            this.name = name;
    }

    public void setVoted(){
            this.voted = 1;
    }

    public int getId(){
       return id;
    }

    public String getName(){
            return name;
    }

    public int hasVoted(){
            return voted;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getTimeStampString() {
		return timeStampString;
	}

	public void setTimeStampString(String timeStampString) {
		this.timeStampString = timeStampString;
	}

	




}
