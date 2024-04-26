package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.ImagesDao;
import exception.ImageException;
import exception.UserException;
import model.AllImages;

@WebServlet("/imagesUploadServlet")
@MultipartConfig
public class imagesUploadServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/expandImage.jsp");
		rd.forward(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//画像のリストを再取得して、リクエストにセットする
		ImagesDao imagesDao;
		try {
			//画像をアップロードする際に、user_managementテーブルに存在するuser_id値を使用する
			HttpSession session = request.getSession();
			
			// user_idの値をデバッグ出力する
			System.out.println("セッションから取得したユーザーID: " + session.getAttribute("user_id"));
			
			Integer userIdObj = (Integer) session.getAttribute("user_id");

			if (userIdObj != null) {
			    int userId = userIdObj.intValue();
			    //ユーザーIDが正しく取得できた場合の処理
			    System.out.println("ユーザーID: " + userId);
				//name属性がimgのファイルをpartオブジェクトとして取得する
				Part part = request.getPart("img");
				//Calenderオブジェクトを取得
				Calendar c = Calendar.getInstance();
				//日付と時刻のフォーマット指定
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDHHmmss");
				//カレンダーから日付と時刻を取得し、指定したフォーマットに変換
				String timeStamp = sdf.format(c.getTime());
				//ファイル名を取得
				String fileName = timeStamp + part.getSubmittedFileName();
				
				//画像を保存するディレクトリを指定
				String uploadDirectory = getServletContext().getRealPath("/upload") + File.separator + fileName;
				
				//画像の相対パスを生成
				String relativeImagePath = "upload/" + fileName;
				
					//ディレクトリがなかったら、file作る
				File uploadDirFile = new File(uploadDirectory);
				if(!uploadDirFile.exists()) {
					uploadDirFile.mkdirs();
				}
					
				part.write(uploadDirectory);
					
				imagesDao = new ImagesDao();
				imagesDao.upLoadImage(fileName, relativeImagePath, userId);
				List<AllImages> imageUrlList = imagesDao.findImageUrl();
					
				request.setAttribute("imageUrlList", imageUrlList);
					
				request.setAttribute("fileName", fileName);
					
				request.setAttribute("user_id", userId);
				
				//もし、相対パスがスペースを含んでいたら、URLをエンコードする
				if (!relativeImagePath.contains(" ")) {
					request.setAttribute("relativeImagePath", relativeImagePath);
				} else {
					String pathWithSpace = relativeImagePath;
					String encodePath = URLEncoder.encode(pathWithSpace, "UTF-8");
					request.setAttribute("relativeImagePath", encodePath);
				}
				
				RequestDispatcher rd = request.getRequestDispatcher("/allimages.jsp");
				rd.forward(request, response);
			} else {
			    // セッションからユーザーIDが取得できない場合の処理
			    System.out.println("セッションからユーザーIDが取得できませんでした。");
			}
			
		} catch (ImageException e) {
			e.printStackTrace();
		} catch (UserException e) {
			e.printStackTrace();
		}
	}
}
