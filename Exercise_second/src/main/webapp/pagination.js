
function backButton() {
	// URLを取得
	let url = new URL(window.location.href);
	console.log(url);
	// URLSearchParamsオブジェクトを取得
	let params = url.searchParams;
	
	let value = parseInt(params.get('page'));
	
	if(value > 1) {
		params.set('page', value - 1);
		let newUrl = url.toString()
		window.location.href = newUrl;
	} else {
		return false;
	}
}

function nextButton() {
	let totalPages = document.getElementById("pageLink").getAttribute("data-totalpages");
	// URLを取得
	let url = new URL(window.location.href);

	// URLSearchParamsオブジェクトを取得
	let params = url.searchParams;
	
	let value = parseInt(params.get('page'));
	if(value != totalPages) {
		params.set('page', value + 1);
		let newUrl = url.toString();
		window.location.href = newUrl;
	} else if (value == totalPages) {
		return false;
	}
}

function fiveBuckButton() {
	// URLを取得
	let url = new URL(window.location.href);

	// URLSearchParamsオブジェクトを取得
	let params = url.searchParams;
	
	let value = parseInt(params.get('page'));
	if(value >= 6) {
		params.set('page', value - 5);
		let newUrl = url.toString();
		window.location.href = newUrl;
	} else if(value <= 5) {
		return false;
	}
}

function fiveNextButton() {
	let totalPages = document.getElementById("pageLink").getAttribute("data-totalpages");
	// URLを取得
	let url = new URL(window.location.href);

	// URLSearchParamsオブジェクトを取得
	let params = url.searchParams;
	
	let value = parseInt(params.get('page'));
	if(value < totalPages - 5) {
		params.set('page', value + 5);
		let newUrl = url.toString();
		window.location.href = newUrl;
	} else {
		return false;
	}
}

//function displayDots() {
//	
//	var liBackDots = document.getElementById('backDots');
//	var liNextDots = document.getElementById('nextDots');
//	
//	let span = '';
//	
//	let totalPages = document.getElementById("pageLink").getAttribute("data-totalpages");
//	
//	//URLを取得
//	let url = new URL(window.location.href);
//
//	// URLSearchParamsオブジェクトを取得
//	let params = url.searchParams;
//	
//	let value = parseInt(params.get('page'));
//	
//	//現在のページはURLのpage=valueの数字で判断する
//	let currentPage = value;
//	
//	if (currentPage > totalPages - 4) {
//		span += `<span class="dots">...</span>`; 
//	} else if (currentPage == 1) {
//		span += `<span class="dots" style="display:none">...</span>`;
//	} else {
//		span += `<span class="dots" style="display:none">...</span>`;
//	}
//	
//	if (currentPage < totalPages - 5) {
//		span += `<span class="dots">...</span>`
//	}
//	
//	liBackDots.innerHTML = span;
//	liNextDots.innerHTML = span;
//}
//displayDots();

function controlPageLinks() {
    let totalPages = document.getElementById("pageLink").getAttribute("data-totalpages");
    let currentPage = parseInt("<%= currentPage %>"); // 現在のページを取得
    const maxVisiblePages = 6; // 現在のページから表示するページ数

    let startPage, endPage;
    if (totalPages < maxVisiblePages) {
        startPage = 1;
        endPage = totalPages;
    } else {
        if (currentPage <= Math.ceil(maxVisiblePages / 2)) {
            startPage = 1;
            endPage = maxVisiblePages;
        } else if (currentPage > totalPages - Math.floor(maxVisiblePages / 2)) {
            startPage = totalPages - maxVisiblePages + 1;
            endPage = totalPages;
        } else {
            startPage = currentPage - Math.floor(maxVisiblePages / 2);
            endPage = currentPage + Math.ceil(maxVisiblePages / 2) - 1;
        }
    }

    // 表示を制御する
    let pageLinks = document.querySelectorAll("#liPageNumber");
    pageLinks.forEach((link, index) => {
        let pageNumber = index + 1;
        if (pageNumber < startPage || pageNumber > endPage) {
            link.style.display = "none"; // 表示しない
        } else {
            link.style.display = "block"; // 表示する
        }
    });
}

