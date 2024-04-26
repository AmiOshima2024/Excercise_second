package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.UserException;

//Daoクラスの共通処理をまとめたスーパークラス
public class BaseDao {
	
	//DBへの接続・SQL文事前解析・SELECT文の結果保持
	protected Connection con = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;
	
	//コンストラクタ　初期処理DB接続
	public BaseDao() throws UserException {
		getConnection();
	}

	//DBに接続する
	private void getConnection() throws UserException {
		try {
			if (con == null) {
				Class.forName("com.mysql.cj.jdbc.Driver");
				String url = "jdbc:mysql://localhost/exercise_second";
				String user = "root";
				String password = "";
				
				con = DriverManager.getConnection(url, user, password);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new UserException("JDBCドライバが見つかりません");
		} catch (SQLException e) {
				e.printStackTrace();
				throw new UserException("SQL実行中に例外が発生しました");
		}
		
	}
	
	//DBとの接続を解除する
	protected void close() throws UserException {
		try {
			if (con != null) {
				con.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new UserException("close処理中に例外が発生しました");
		}
		
	}
}
