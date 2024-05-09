package controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

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
		
		request.setCharacterEncoding("UTF-8");
		String deleteRequest = request.getParameter("delete");

		if (deleteRequest != null) {
			String fileName = request.getParameter("fileName"); 
			String filePath = request.getParameter("filePath"); 

			if (fileName != null && filePath != null) {
				HttpSession session = request.getSession();
				session.setAttribute("fileName", fileName);
				session.setAttribute("filePath", filePath);

				if (fileName != null && filePath != null) {
					try {
						ImagesDao imagesDao = new ImagesDao();
						
						long allRecordsWithPagination = imagesDao.getAllImagesWithPagination();
						
						int limit = 9;			
						int page = 1;
						
						String currentPageParam = request.getParameter("page");
						if (currentPageParam != null && !currentPageParam.isEmpty()) {
							page = Integer.parseInt(currentPageParam);
						}
						
						int offset = (page - 1) * limit;
						List<AllImages> imagesWithPagination = imagesDao.getImagesWithPagination(fileName, filePath, limit, offset);
						
						request.setAttribute("allRecordsWithPagination", allRecordsWithPagination);
						
						request.setAttribute("imagesWithPagination", imagesWithPagination);
						
						//現在のページに1が入る
						request.setAttribute("currentPage", page);
						request.setAttribute("offset", offset);
						
						String relativeImagePath = "upload/" + fileName;
						
						imagesDao.deleteUploadImage(fileName, filePath);
						//削除後、画像一覧を再取得して一覧に表示
						List<AllImages> imageUrlList = imagesDao.findImageUrl();
						request.setAttribute("imageUrlList", imageUrlList);
						
						//相対パスがスペースを含んでいたら、URLをエンコードする
						if (!relativeImagePath.contains(" ")) {
							request.setAttribute("relativeImagePath", relativeImagePath);
						} else {
							String pathWithSpace = relativeImagePath;
							String encodePath = URLEncoder.encode(pathWithSpace, "UTF-8");
							request.setAttribute("relativeImagePath", encodePath);
						}												
						response.sendRedirect("PaginationServlet?page=" + page);
					} catch (ImageException | UserException e) {
						e.printStackTrace();
						request.setAttribute("deleteErrorMEssage", "画像の削除に失敗しました");
					}
				}
			}
		}
	}
}