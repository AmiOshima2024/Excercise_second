package model;

public class AllImages {
	
	private int imageId;
	private int userId;
	private String imageFileName;
	private String imagePath;
	private String deletionDatatime;
	
	public AllImages(int imageId, int userId, String imageFileName, String imagePath, String deletionDatatime) {
		this.imageId = imageId;
		this.userId = userId;
		this.imageFileName = imageFileName;
		this.imagePath = imagePath;
		this.deletionDatatime = deletionDatatime;
	}
	
	//画像IDをセット・返す
	public int getImageId() {
		return imageId;
	}
	
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	
	//ユーザーIDをセット・返す
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	//画像ファイル名をセット・返す
	public String getImageFileName() {
		return imageFileName;
	}
	
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	//画像パスをセット・返す
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	//削除日時をセット・返す
	public String geDeletionDatatime() {
		return deletionDatatime;
	}
	
	public void setDeletionDatatime(String deletionDatatime) {
		this.deletionDatatime = deletionDatatime;
	}
	
}
