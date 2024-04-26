<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.AllImages"%>
<%@ page import="controllers.ModalServlet"%>
<%@ page import="dao.ImagesDao"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>画像の拡大表示</title>
<script>
	//サーブレットから取得した画像URLの配列を使用してJavaScriptのコードを生成する
	var jsurlsArray = [];
	<%
        String[] urlsArray = (String[]) request.getAttribute("urlsArray");
        if (urlsArray != null) {
            for (String str : urlsArray) {
    %>
                jsurlsArray.push('<%= str %>');
    <%
            }
        }
    %>
    
 	// 画像のインデックスを保持する変数
 	var imagePath = '<%= request.getParameter("imagePath") %>';
 	var currentIndex = jsurlsArray.indexOf(imagePath);
	console.log(currentIndex);
 	
 	//画像の表示を更新する関数
 	function updateImage() {
	    var targetImage = document.getElementById("targetImage");
	    targetImage.src = jsurlsArray[currentIndex];
	}

	//次へボタンがクリックされた時の処理
	function modalNextImage() {
		if (currentIndex < jsurlsArray.length - 1) {
	        currentIndex++;
	        updateImage();
	    } else {
			return false;
		}
	}

	//前へボタンがクリックされた時の処理
	function modalPrevImage() {
		if (currentIndex > 0) {
			currentIndex--;
			updateImage();
		} else {
			return false;
		}
	}

	function backToAllImages() {
		location.href = "imagesUploadServlet";
	}

</script>
		
</head>
<body>
	<div class="displayExpandImage">
		<% String imagePath = request.getParameter("imagePath"); %>
		<% String expandImagePath = imagePath; %>
		<img src="<%= expandImagePath %>" id="targetImage">

		<button onclick="modalNextImage()">次へ</button>
		<button onclick="modalPrevImage()">前へ</button>
		<button onclick="backToAllImages()">一覧へ戻る</button>		
	</div>
	<script src="expandImage.js"></script>	
	<script src="pagination.js"></script>	
</body>
</html>