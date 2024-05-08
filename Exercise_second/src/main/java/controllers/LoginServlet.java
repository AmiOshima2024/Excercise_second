package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import exception.UserException;
import model.LoginUser;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
		requestDispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//ログイン用
		String loginId = request.getParameter("loginId");
		String loginPassword = request.getParameter("loginPassword");
		String nextPage = "login.jsp";
		
		if(loginId != null && !loginId.isEmpty() && loginPassword != null && !loginPassword.isEmpty()) {
			try {
				//ログイン処理 DBにIDとパスワードの組み合わせのユーザーがいるかチェック。
				//ログインできない場合は例外UserExceptionを発生。
				UserDao userDao = new UserDao();
				LoginUser loginUser = userDao.doLogin(loginId, loginPassword);
				
				// デバッグ出力
			    System.out.println("ログインユーザーのID: " + loginUser.getUserId());
				
				//ログインできているときはログインしたユーザーの情報をセッションにセット。ログイン状態とみなす。
				HttpSession session = request.getSession(false);
				System.out.println(session);
				session.setAttribute("loginUser", loginUser);
				//データベースへ画像をアップロードするためにユーザーIDをセッションにセット
				session.setAttribute("user_id", loginUser.getUserId());
				
				//画像一覧ページでログイン情報表示用
				session.setAttribute("loginId", loginId);
				
				//PaginationServletの1ページ目へ遷移する準備
				response.sendRedirect("PaginationServlet?page=1");
				return;
				
			} catch (UserException e) {
				//ログインできなかった場合はメッセージをセットしてlogin.jspに表示
				String message = e.getMessage();
				request.setAttribute("message", message);
				request.setAttribute("error", "true");
				
				//ログイン画面に遷移する準備
				nextPage = "login.jsp";
			}
		} else {
			request.setAttribute("message", "ログインIDとログインパスワードを両方入力してください。");
			request.setAttribute("error", "true");
		}
		
		//次の画面に遷移
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
		
	}  

}
