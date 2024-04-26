package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ImagesDao;
import exception.ImageException;
import exception.UserException;
import model.AllImages;

/**
 * Servlet implementation class AllImagesServlet
 */
@WebServlet("/AllImagesServlet")
public class AllImagesServlet extends HttpServlet {
	

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nextPage = null;
		try {
			//画像一覧の取得
			ImagesDao imagesDao = new ImagesDao();
			List<AllImages> imageUrlList = imagesDao.findImageUrl();
			
			
			//取得した画像情報をリクエストスコープにセット
			request.setAttribute("imageUrlList", imageUrlList);
			
			//allimages.jspに遷移する準備
			nextPage = "allimages.jsp";
			
		} catch(ImageException e) {
			String message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "true");
			
			nextPage = "login.jsp";
		} catch (UserException e) {
			String message = e.getMessage();
			request.setAttribute("message", message);
			request.setAttribute("error", "true");
			
			nextPage = "login.jsp";
		}
		
		//次の画面に遷移
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		System.out.println(session);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("allimages.jsp");
		requestDispatcher.forward(request, response);
	}

}
