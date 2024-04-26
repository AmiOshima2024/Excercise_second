
//page : 移動先のページ数 label : 表示するラベル isEnable : リンクの活性状態
function createPagination(currentPage, maxPage, visiblePage) {
	//表示するページの範囲を計算
	let startPage = currentPage - visiblePage;
	let endPage = currentPage + visiblePage;
	
	//<ul class="pagination">;
	let pagination = document.createElement('ul');
    pagination.className = 'pagination';
	
	//前のページ
	let back = createPaginationLink(currentPage - 1, '<', startPage > 1)
	pagination.appendChild(back);
	
	//5ページ前
	let fiveBack = createPaginationLink(currentPage - 5, '<<', startPage < currentPage - 5);
	pagination.appendChild(fiveBack);
	
	//次のページ
	let next = createPaginationLink(currentPage + 1, '>', endPage < maxPage);
	pagination.appendChild(next);
	
	//5ページ後
	let fiveNext = createPaginationLink(currentPage + 5, '>>', startPage < currentPage + 5);
	pagination.appendChild(fiveNext);
	
	// ページ数が表示可能なページ数を超えている場合は、maxPageをvisiblePageに設定
    if (maxPage > visiblePage * 2 + 1) {
        maxPage = visiblePage * 2 + 1;
    }
	
	//ページ番号
	if (startPage < 1) {
		endPage += -(startPage - 1);
		startPage = 1;
	} else if (maxPage < endPage) {
		startPage -= (endPage - maxPage)
		if (startPage < 1) {
			startPage = 1
		}
		endPage = maxPage;
	}
	
	for (let i = startPage; i <= endPage && i <= maxPage; i++) {
		let number = createPaginationLink(i, i, true);
		if (i === currentPage) {
			number.classList.add('active');
		}
		pagination.appendChild(number);
	}
	//生成したpaginationを設定
	var ulPagination = document.getElementById('pagination');
	ulPagination.innerHTML = '';
	ulPagination.appendChild(pagination);
}

createPagination();




