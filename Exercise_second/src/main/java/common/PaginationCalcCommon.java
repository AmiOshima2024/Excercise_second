package common;

import java.util.List;

import dao.ImagesDao;
import exception.ImageException;
import exception.UserException;
import model.AllImages;

public class PaginationCalcCommon {
	private int maxPage;
	
	public PaginationCalcCommon() throws UserException {
		try {
			ImagesDao imagesDao = new ImagesDao();
			
			List<AllImages> paginationImages = imagesDao.findImageUrl();
			
			//1ページあたりの表示件数（pageSize）から、最大ページ数を計算
			//データベースから取得したリストを表示するために必要なページ数
			maxPage = 1;
			if ( paginationImages.size() > 0) {
				//pageSizeには1ページで表示する件数を設定
				int pageSize = 9;
				
				double div = (double) paginationImages.size() / (double) pageSize;
				maxPage = (int) Math.ceil(div);
			}
			
		} catch (ImageException e) {
			e.printStackTrace();
			System.out.println("画像リストを取得できません");
		}
	}
	public int maxImagePieces() {
		return maxPage;
	}
}
