<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.AllImages"%>
<%@ page import="exception.ImageException"%>
<%@ page import="exception.UserException"%>
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
<link rel="stylesheet" href="styles.css">
</head>

<body>

	<div class="content-wrapper">
		<!-- ログイン情報を表示 -->
		<%
		//サーブレットからﾛｸﾞｲﾝIDデータの受け取り
		String strloginId = (String) session.getAttribute("loginId");
		%>
		<div id="loginAndLogout">
			<p id="strLoginId"><%= strloginId %>さん</p>
			<!-- ログアウト機能 -->
			<p id="logoutLink"><a href="LogoutServlet">ログアウト</a></p>		
		</div>
				
			<h3>写真一覧</h3>
			<!-- 写真一覧表示 -->
			<div id="allPicturesWrapper">
				<% List<AllImages> imageUrlList = (List<AllImages>) request.getAttribute("imageUrlList"); %>
				<% long allRecordsWithPagination = (long) request.getAttribute("allRecordsWithPagination"); %>
				<% List<AllImages> imagesWithPagination = (List<AllImages>) request.getAttribute("imagesWithPagination"); %>
				<% for (AllImages image : imagesWithPagination) { %>	
				<% String imagePath = URLDecoder.decode(image.getImagePath(), StandardCharsets.UTF_8.toString()); %>
				<div id="picturesWrapper">
				 	<img id="allPictures" src="<%= imagePath %>" onclick="expandImage('<%= imagePath %>')">
				</div>
			<% } %>
			</div>			
			<!-- ページリンク部分 -->
			<div class="pagenumbers" id="pagination">
				<% int limit = 9; %>
				<ul id="pageLink" data-totalpages="<%= (int)Math.ceil((double)allRecordsWithPagination / limit) %>">
					<!-- 前へボタン -->
					<div id="backButton">
						<button id="back" onclick="backButton()"><a> < </a></button>
						<button id="fiveBack" onclick="fiveBuckButton()"><a> << </a></button>
					</div>
					<!-- ページリンク -->
					<div id="pageLinkWrapper">
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
					</div>
					<!-- 次へボタン -->
					<div id="nextButton">
						<button id="next" onclick="nextButton()"><a> > </a></button>
						<button id="fiveNext" onclick="fiveNextButton()"><a> >> </a></button>
					</div>
				</ul>
  			</div>
	</div>
		
	<!-- 画像のアップロード機能 -->
	<div id="uploadWrapper">
		<form id="uploadForm" action="imagesUploadServlet" method="post" enctype="multipart/form-data">
			<p>アップロード画像：</p>
			<input type="file" name="img">
			<button type="submit">画像をアップロードする</button>
		</form>
	</div>

	<!-- アップロードした画像の削除機能 -->
	<div id="deleteawrapper">
		<form id="deleteForm" action="ImagesDeleteServlet" method="post" accept-charset="UTF-8">
			<div id="labelForNameAndPath">
				<label for ="fileName"><p>削除したい画像のファイル名：</p></label>
				<label for ="filePath"><p>削除したい画像のファイルパス：</p></label>
			</div>
			
			<% 
			String fileName = (String)request.getAttribute("fileName");
			String filePath = (String)request.getAttribute("filePath");
			%>
			<div id="inputForNameAndPath">
				<input type="text" name="fileName" value="<%= fileName %>"> 
				<input type="text" name="filePath" value="<%= filePath %>">
				<button type="submit" name="delete">アップロードした画を削除する</button>
			</div>
		</form>
	</div>
	
	<script src="expandImage.js"></script>
	<script src="pagination.js"></script>
</body>
</html>
