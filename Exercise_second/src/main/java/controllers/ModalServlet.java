package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ImagesDao;
import exception.ImageException;
import exception.UserException;

/**
 * Servlet implementation class ModalServlet
 */
@WebServlet("/ModalServlet")
public class ModalServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//データベースにある画像URLを配列として作る
		ImagesDao imagesDao = null;
		try {
			imagesDao = new ImagesDao();
		} catch (ImageException | UserException e) {
			e.printStackTrace();
			System.out.println("imagesDaoオブジェクトが取得できません");
		}
		String[] urlsArray = null;
		try {
			urlsArray = imagesDao.getImageUrls();
		} catch (ImageException e) {
			e.printStackTrace();
			System.out.println("画像url配列が取得できません");
		}
		
		//取得した画像URLの配列をリクエスト属性に設定する
		request.setAttribute("urlsArray", urlsArray);
		
		// expandImage.jspにフォワードする
        RequestDispatcher rd = request.getRequestDispatcher("/expandImage.jsp");
        rd.forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}