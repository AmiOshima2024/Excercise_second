<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.AllImages"%>
<%@ page import="exception.ImageException"%>
<%@ page import="exception.UserException"%>
<%@ page import="common.ConstantCommon"%>
<%@ page import="common.PaginationCalcCommon"%>
<%@ page import="dao.ImagesDao"%>
<%@ page import="controllers.ModalServlet"%>
<%@ page import="controllers.PaginationServlet"%>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>写真一覧</title>
</head>

<body>

	<div class="content-wrapper">

		<!-- ログイン情報を表示 -->
		<%
		//サーブレットからﾛｸﾞｲﾝIDデータの受け取り
		String strloginId = (String) session.getAttribute("loginId");
		%>
		<%= strloginId%>さん

		<!-- ログアウト機能 -->
		<a href="LogoutServlet">ログアウト</a>

		<!-- 写真一覧表示 -->
		<div class="imgList" id="paginationImgList">
			<!-- allimages.jsp -->
			<%
			List<AllImages> imageUrlList = (List<AllImages>) request.getAttribute("imageUrlList");
			%>
			<%
			if (imageUrlList == null || imageUrlList.isEmpty()) {
			%>
				<!-- imageUrlListがnullまたは空の場合、エラーメッセージを表示 -->
				<p>画像が見つかりません。</p>
				<%
			} else {
			%>
				<!-- imageUrlListに画像情報がある場合、画像を表示 -->
				<%
					for (int i = 0; i < imageUrlList.size() && i < ConstantCommon.MAX_ITEMS; i++) {
						AllImages image = imageUrlList.get(i);
				%>
					<img src="<%= image.getImagePath() %>"  onclick="expandImage('<%= image.getImagePath() %>')" >
				<%
}
				%>
			<%
			} %>
		</div>
	</div>	
	<!-- 画像のアップロード機能 -->
	<form action="imagesUploadServlet" method="post" enctype="multipart/form-data">
		アップロード画像：<input type="file" name="img">
		<button type="submit">画像をアップロードする</button>
	</form>

	<!-- アップロードした画像の削除機能 -->
	<form action="ImagesDeleteServlet" method="post">
		<label for ="fileName">削除したい画像のファイル名：</label>
		<label for ="filePath">削除したい画像のファイルパス：</label>
		<input type="text" name="fileName" value="<%=request.getAttribute("fileName")%>"> 
		<input type="text" name="filePath" value="<%=request.getAttribute("filePath")%>">
		<button type="submit" name="delete">アップロードした画を削除する</button>
	</form>
	
	<!-- ページネーション部分 -->
	<script>
		var paginationImagesArray = [
			<%
        	List<AllImages> paginationImagesArray = (List<AllImages>) request.getAttribute("paginationImagesArray");
        		if (paginationImagesArray != null) {
            		for (AllImages images : paginationImagesArray) {
            			for (int i = 0; i < paginationImagesArray.size() && i < ConstantCommon.MAX_ITEMS; i++) { %>
            				'<%= images.getImagePath() %>'
            <%			}
    		%>				
    		<%	
        			}
        		}
			%>
		];
		
		var currentPage = 1;
		<% PaginationCalcCommon paginationCalcCommon = new PaginationCalcCommon();
			int maxItems = paginationCalcCommon.maxImagePieces();
		%>
		var maxItems = maxItems;

    	// List<AllImages> paginationImagesArrayのインデックスを保持する変数
    	var paginationCurrentIndex = -1;
    	
     	var paginationImagePath = '<%= request.getParameter("imagePath") %>';
     	if (paginationImagesArray.indexOf(paginationImagePath) !== -1) {
         	//９枚の画像を一つのインデックスとして取得したい
     		paginationCurrentIndex = paginationImagesArray.indexOf(paginationImagePath);
         }

     	function movePage() {
         	//画像を表示するdivを取得
			var paginationImgList = document.getElementById("paginationImgList"); 
			//divの中にimgタグを９個作る
			for (let i = 1; i <= maxItems; i++) {
				var imgTag = document.createElement('img');
				imgTag.src = paginationImagesArray[paginationCurrentIndex];
				paginationImgList.appendChild(imgTag);
			}
        }

     	function back() {
			if(currentPage != 1) {
				currentPage--;
				paginationCurrentIndex = paginationCurrentIndex - 1;
				movePage();
			} else {
				return false;
			}
        }

        function next() {
			if(currentPage != maxItems){
				currentPage++;
				paginationCurrentIndex = paginationCurrentIndex + 1;
				movePage();
			} else {
				return false;
			}
        }

	</script>
	
	<div class="pagination">
		<button onclick='back'> < </button>
		<button onclick='fiveBack'> << </button>
		<button onclick='next'> > </button>
		<button onclick='fiveNext'> >> </button>
  	</div>
  	
	<script src="expandImage.js"></script>
</body>
</html>
