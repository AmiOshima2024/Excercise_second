package controllers;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
		
		request.setCharacterEncoding("UTF-8");
		
		String fileName = null;
		String filePath = null;
		int limit = 9;
		int page = 1;
		
		String pageParam = request.getParameter("page");
		if (pageParam != null && !pageParam.isEmpty()) {
			page = Integer.parseInt(pageParam);
		}
		int offset = (page - 1) * limit;
		
		ImagesDao imagesDao = null;
		try {
			imagesDao = new ImagesDao();
			//画像一覧の取得
			List<AllImages> imageUrlList = imagesDao.findImageUrl();						
			request.setAttribute("imageUrlList", imageUrlList);
			
			long allRecordsWithPagination = imagesDao.getAllImagesWithPagination();
			List<AllImages> imagesWithPagination = imagesDao.getImagesWithPagination(fileName, filePath, limit, offset);
			for (AllImages image : imagesWithPagination) {
				String imagePathEncoded = URLEncoder.encode(image.getImagePath(), StandardCharsets.UTF_8.toString());
				image.setImagePath(imagePathEncoded);
			}

			request.setAttribute("allRecordsWithPagination", allRecordsWithPagination);
			request.setAttribute("imagesWithPagination", imagesWithPagination);
			//現在のページに1が入る
			request.setAttribute("currentPage", page);
			request.setAttribute("offset", offset);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("allimages.jsp");
			requestDispatcher.forward(request, response);
		} catch (ImageException | UserException e) {
			System.out.println("ページネーションのための画像が取得できません");
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//imagesDaoから画像を9枚取得・全てのレコードを取得
		request.setCharacterEncoding("UTF-8");
		
		try {
			String fileName = null;
			String filePath = null;
			int limit = 9;
			int page = 1;
			
			String pageParam = request.getParameter("page");
			if (pageParam != null && !pageParam.isEmpty()) {
				page = Integer.parseInt(pageParam);
			}
			int offset = (page - 1) * limit;
			ImagesDao imagesDao = new ImagesDao();
			//画像一覧の取得
			List<AllImages> imageUrlList = imagesDao.findImageUrl();						
			request.setAttribute("imageUrlList", imageUrlList);
			 
			long allRecordsWithPagination = imagesDao.getAllImagesWithPagination();
			List<AllImages> imagesWithPagination = imagesDao.getImagesWithPagination(fileName, filePath, limit, offset);
			//URLをデコードする
			for (AllImages image : imagesWithPagination) {
				String imagePathDecoded = URLDecoder.decode(image.getImagePath(), StandardCharsets.UTF_8.toString());
				image.setImagePath(imagePathDecoded);
			}

			request.setAttribute("allRecordsWithPagination", allRecordsWithPagination);
			request.setAttribute("imagesWithPagination", imagesWithPagination);
			//現在のページに1が入る
			request.setAttribute("currentPage", page);
			request.setAttribute("offset", offset);			 
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("allimages.jsp");
			requestDispatcher.forward(request, response);
		} catch (ImageException | UserException e) {
			System.out.println("データベースが取得できません");
			e.printStackTrace();
		} 
		
	}
}


