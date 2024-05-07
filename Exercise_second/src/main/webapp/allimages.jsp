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
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>


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
		<%= strloginId %>さん

		<!-- ログアウト機能 -->
		<a href="LogoutServlet">ログアウト</a>

		<!-- 写真一覧表示 -->
		<div class="imgList" id="paginationImgList">
			<!-- AllImagesServlet -->
			<%--
			List<AllImages> imageUrlList = (List<AllImages>) request.getAttribute("imageUrlList");
			--%>
			<%--
			if (imageUrlList == null || imageUrlList.isEmpty()) {
			--%>
				<!-- imageUrlListがnullまたは空の場合、エラーメッセージを表示 -->
				<!-- p>画像が見つかりません。</p>
				<%--
			} else {
			--%>
				<!-- imageUrlListに画像情報がある場合、画像を表示 -->
				<%--
					for (int i = 0; i < imageUrlList.size() && i < ConstantCommon.MAX_ITEMS; i++) {
						AllImages image = imageUrlList.get(i);
				--%>
					 <!--img src="<%--= image.getImagePath() %>"  onclick="expandImage('<%= image.getImagePath() %>')" >
				<%--
}
				--%>
			<%--
			} --%>
			
			<!-- PaginationServlet ページネーション部分 -->
			<div class="pagenumbers" id="pagination">
			<% List<AllImages> imageUrlList = (List<AllImages>) request.getAttribute("imageUrlList"); %>
			<% long allRecordsWithPagination = (long) request.getAttribute("allRecordsWithPagination"); %>
			<% List<AllImages> imagesWithPagination = (List<AllImages>) request.getAttribute("imagesWithPagination"); %>
			<% for (AllImages image : imagesWithPagination) { %>	
				 <% String imagePath = URLDecoder.decode(image.getImagePath(), StandardCharsets.UTF_8.toString()); %>
				<img src="<%= imagePath %>" onclick="expandImage('<%= imagePath %>')">
			<% } %>
				
				<% int limit = 9; %>
				<ul id="pageLink" data-totalpages="<%= (int)Math.ceil((double)allRecordsWithPagination / limit) %>">
					<button id="back" onclick="backButton()"><a> < </a></button>
					<button id="fiveBack" onclick="fiveBuckButton()"><a> << </a></button>
					
					<% int totalPages = (int)Math.ceil((double)allRecordsWithPagination / limit); %>
					<% int currentPage = (int) request.getAttribute("currentPage"); %>
					<% int maxVisiblePages = 6; %>
					<% boolean dotsDisplayed = false; %>
					<% int offset = (int) request.getAttribute("offset"); %>

					<% for (int i = 1; i <= totalPages; i++) { %>
    					<% if (i < currentPage - 5 || i > currentPage + 5) { %>
        					<% if(!dotsDisplayed) { %>
            					<li>...</li>
            					<% dotsDisplayed = true; %>
        					<% }
    					 } %>
    					<!--  現在のページの前後5ページ以内にある場合はページリンクを表示し、それ以外の場合は<li>要素を非表示-->
    					<% if (i >= currentPage - 5 && i <= currentPage + 5) { %>
    						<li>
        						<% String pageLink = "PaginationServlet?page=" + i; %>       				
        				    	<a href="<%= pageLink %>" <% if (i == currentPage) { %>class="active"<% } %>><%= i %></a>   				
    						</li>
    					<% } else { %>
    							<li style="display:none;"></li>
    					<% } %>
    					
					<% } %>
					<button id="next" onclick="nextButton()"><a> > </a></button>
					<button id="fiveNext" onclick="fiveNextButton()"><a> >> </a></button>
				</ul>
  			</div>
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
	
	<script src="expandImage.js"></script>
	<script src="pagination.js"></script>
</body>
</html>
