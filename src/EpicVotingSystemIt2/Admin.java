package EpicVotingSystemIt2;

public class Admin {
	private int adminId;
	private String adminName;
	private String adminAcct;
	private String adminPw;
	
	public Admin(int adminId, String adminName, String adminAcct, String adminPw) {
		this.adminId = adminId;
		this.adminName = adminName;
		this.adminAcct = adminAcct;
		this.adminPw = adminPw;
	   }
	
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public void setAdminAcct(String adminAcct) {
		this.adminAcct = adminAcct;
	}
	public void setAdminPw(String adminPw) {
		this.adminPw = adminPw;
	}

	public int getAdminId() {
		return adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public String getAdminAcct() {
		return adminAcct;
	}
	public String getAdminPw() {
		return adminPw;
	}
	
	   
}
