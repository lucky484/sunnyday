function nextPage(url) {
	var page = $("#e_page").html();
	var pages = $("#e_pages").html();
	if (page < pages) {
		drawPage(eval(page + "+ 1"));
	}
}
function prePage(url) {
	var page = $("#e_page").html();
	if (page > 1) {
		drawPage(eval(page + "- 1"));
	}
}
function homePage() {
	var page = $("#e_page").html();
	if (1 != page) {
		drawPage();
	}
}

function lastPage() {
	var page = $("#e_page").html();
	var pages = $("#e_pages").html();
	if (page != pages) {
		drawPage(pages);
	}
}

// 检查数据是否为null
function checkData(data) {
	if (!data || data == '0') {
		return '—';
	} else {
		return data;
	}
}