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
 * Servlet implementation class ImagesDeleteServlet
 */
@WebServlet("/ImagesDeleteServlet")
public class ImagesDeleteServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String deleteRequest = request.getParameter("delete");

		if (deleteRequest != null) {
			String fileName = request.getParameter("fileName"); // fileName を取得
			String filePath = request.getParameter("filePath"); // filePath を取得

			if (fileName != null && filePath != null) {
				HttpSession session = request.getSession();
				session.setAttribute("fileName", fileName);
				session.setAttribute("filePath", filePath);

				if (fileName != null && filePath != null) {
					try {
						ImagesDao imagesDao = new ImagesDao();
						imagesDao.deleteUploadImage(fileName, filePath);

						//削除後、画像一覧を再取得して一覧に表示

						List<AllImages> imageUrlList = imagesDao.findImageUrl();
						request.setAttribute("imageUrlList", imageUrlList);
					} catch (ImageException | UserException e) {
						e.printStackTrace();
						request.setAttribute("deleteErrorMEssage", "画像の削除に失敗しました");
					}
				}
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("/allimages.jsp");
		rd.forward(request, response);

	}
}