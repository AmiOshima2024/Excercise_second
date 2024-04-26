package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.ImageException;
import exception.UserException;
import model.AllImages;

public class ImagesDao extends BaseDao {

	public ImagesDao() throws ImageException, UserException {
		super();
	}
	
	//image_pathテーブルに登録されている画像URLを取得する
	public List <AllImages> findImageUrl() throws ImageException {
		
		List<AllImages> imageUrlList = new ArrayList<>();
		try {
			String sql = "SELECT image_id, user_id, image_filename, image_path, deletion_datetime FROM image_management";
			
			//検索の実行
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			//検索結果から画像URLを取得してリストに格納する
			while (rs.next()) {
				int imageId = rs.getInt("image_id");
				int userId = rs.getInt("user_id");
				String imageFileName = rs.getString("image_filename");
				String imagePath = rs.getString("image_path");
				String deletionDatetime = rs.getString("deletion_datetime");
				
				AllImages images = new AllImages(imageId, userId, imageFileName, imagePath, deletionDatetime);
				imageUrlList.add(images);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ImageException("画像の取得に失敗しました");
		}
		return imageUrlList;
	}
	
	//画像情報をデータベースにアップロ-ードするメソッド
	public void upLoadImage(String fileName, String filePath, int userId) throws ImageException {
		try {
			String sql = "INSERT INTO image_management (image_filename, image_path, user_id) VALUES (?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, fileName);
			ps.setString(2, filePath);
			ps.setInt(3, userId);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ImageException("画像のアップロードに失敗しました");
		}
	}
	
	//アップロードされた画像情報を削除するメソッド
	public void deleteUploadImage(String fileName, String filePath) throws ImageException {
		
		try {
			String sql = "DELETE FROM image_management WHERE image_filename = ? AND image_path = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1,  fileName);
			ps.setString(2,  filePath);
			ps.executeUpdate();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ImageException("画像の削除に失敗しました");
		} 
		
	}
	
	//データベース内の画像の総数を取得し、その数を返すメソッド
	public long paginationDisplayImages() throws ImageException {
		long count = 0;
		try {
			String sql = "SELECT COUNT(*) FROM image_management";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				count = rs.getLong(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ImageException("ページングに失敗しました");
		}
		return count;
	}
	
	//ページネーション用のメソッド
	public List<AllImages> getImagesWithPagination(String fileName, String filePath, int limit, int offset) throws ImageException {
		try {
			String sql = "SELECT * FROM image_management LIMIT ? OFFSET ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, limit);
			ps.setInt(2,  offset);
			rs = ps.executeQuery();
			
			List<AllImages> images = new ArrayList<>();
			while (rs.next()) {
				AllImages image = new AllImages(
					rs.getInt("imageId"),
				    rs.getInt("userId"),
				    rs.getString("imageFileName"),
				    rs.getString("imagePath"),
				    rs.getString("deletionDatatime")
						
				);
				//結果から画像情報を取得してimageに設定する
				images.add(image);
			}
			return images;
			
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ImageException("ページングに失敗しました");
		} 
	}
	
	//写真表示用　画像URLのみを取得し、配列に格納する
	public String[] getImageUrls() throws ImageException {
		List<String> imageUrlArray = new ArrayList<>();
		try {
			String sql = "SELECT image_path FROM image_management";
			
			ps = con.prepareStatement(sql);
	        rs = ps.executeQuery();
	        
	        //検索結果から画像URLを取得してリストに格納する
	        while (rs.next()) {
	        	String imagePath = rs.getString("image_path");
	        	imageUrlArray.add(imagePath);
	        	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
	        throw new ImageException("画像の URL の取得に失敗しました");
		}
		return imageUrlArray.toArray(new String[0]);//要素数0で初期化し、新しく配列を作る

	}
	
}
