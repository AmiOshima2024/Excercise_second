
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


