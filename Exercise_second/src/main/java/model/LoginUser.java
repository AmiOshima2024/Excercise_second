package model;

public class LoginUser {
	
	private int userId;
	private String userName;
	private String loginId;
	private String loginPassword;
	
	//コンストラクタ　初期化
	public LoginUser(int userId, String userName, String loginId, String loginPassword) {
		this.userId = userId;
		this.userName = userName;
		this.loginId = loginId;
		this.loginPassword = loginPassword;
	}

	//メソッド
	public int getUserId() {
		return userId;
	}
	
	public void setId(int userId) {
		this.userId = userId;
	}
	
	public String getuserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	public String getLoginId() {
		return loginId;
	}
	
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public String getLoginPassword() {
		return loginPassword;
	}
	
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	
}
