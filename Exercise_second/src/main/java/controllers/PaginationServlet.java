package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ImagesDao;
import exception.ImageException;
import exception.UserException;
import model.AllImages;

/**
 * Servlet implementation class PaginationServlet
 */
@WebServlet("/PaginationServlet")
public class PaginationServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ImagesDao imagesDao = null;
		try {
			imagesDao = new ImagesDao();
		} catch (ImageException | UserException e) {
			e.printStackTrace();
			System.out.println("imagesDaoオブジェクトが取得できません");
		}
		List<AllImages> paginationImagesArray = null;
		try {
			paginationImagesArray = imagesDao.findImageUrl();
		} catch (ImageException e) {
			e.printStackTrace();
			System.out.println("画像一覧が取得できません");
		}
		
		//取得した画像URLの配列をリクエスト属性に設定する
		request.setAttribute("paginationImagesArray", paginationImagesArray);
		
		// allimage.jspにフォワードする
        RequestDispatcher rd = request.getRequestDispatcher("/allimages.jsp");
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
