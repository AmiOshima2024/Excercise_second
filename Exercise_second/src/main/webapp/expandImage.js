
function expandImage(imagePath) {
	window.location.href = "ModalServlet?imagePath=" + imagePath;
}
		
// 画像のインデックスを保持する変数
//var jsurlsArray = new Array();
//var currentIndex = jsurlsArray.indexOf('<%= request.getParameter("imagePath") %>');

//画像の表示を更新する関数
//function updateImage() {
//    var targetImage = document.getElementById("targetImage");
//    targetImage.src = jsurlsArray[currentIndex];
//}

// 「次へ」ボタンがクリックされたときの処理
//function modalNextImage() {
//	if (currentIndex < jsurlsArray.length - 1) {
//        currentIndex++;
//        updateImage();
//    }
//}