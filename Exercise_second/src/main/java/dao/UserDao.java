package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.UserException;
import model.LoginUser;

public class UserDao extends BaseDao{

	//スーパークラスのコンストラクタ(DB接続を実施)
	public UserDao() throws UserException {
		super();
	}
	
	//USER_MANAGEMENTテーブルに登録されているすべてのユーザー情報を検索する
	public List<LoginUser> findAllUser() throws UserException {
		//ユーザー情報のリスト
		ArrayList<LoginUser> userList = new ArrayList<>();
		try {
			String sql = "SELECT * FROM user_management";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			//検索結果からユーザーIDとユーザー名とログインIDとログインパスワードを取得してリストに格納する
			while(rs.next()) {
				int userId = rs.getInt("user_id");
				String userName = rs.getString("user_name");
				String loginId = rs.getString("login_id");
				String loginPassword = rs.getString("login_password");
				LoginUser loginuser = new LoginUser(userId, userName, loginId, loginPassword);
				userList.add(loginuser);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("ユーザー情報の取得に失敗しました");
		}
		
		return userList;
	} 
	
		//指定されたIDとパスワードの組み合わせでログイン処理を行う
		//組み合わせに該当するユーザーが存在しない場合は例外でログイン不可を通知する
		//	@param loginId ログインID
		//	@param loginpassword ログインパスワード
		//	@return loginUserユーザー情報
		//	@throws UserException DB接続NGの場合、ログインNGの場合に発生
		public LoginUser doLogin(String loginId, String loginPassword) throws UserException {
				LoginUser loginUser = null;
				try {
					//SQL検索実行
					String sql = "SELECT * FROM user_management WHERE login_id = ? AND login_password = ?";
					ps = con.prepareStatement(sql);
					ps.setString(1, loginId);
					ps.setString(2, loginPassword);
					rs = ps.executeQuery();
					while (rs.next()) {
						int userId = rs.getInt("user_id");
						String userName = rs.getString("user_name");
						String foundLoginId = rs.getString("login_id");
						String password = rs.getString("login_password");
						loginUser = new LoginUser(userId, userName, foundLoginId, password);
					}
					
					//ログイン結果を確認
					if (loginUser == null) {
						throw new UserException("ログインできませんでした");
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					throw new UserException("SQL実行中にエラーが発生しました");
				}
				return loginUser;
		}
			
			
}

