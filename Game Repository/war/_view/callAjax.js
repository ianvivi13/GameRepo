function callAjax(x) {
	console.log("calling ajax");
	httpRequest = new XMLHttpRequest();
	if (!httpRequest) {
		console.log('Unable to create XMLHTTP instance');
		return false;
	}
	httpRequest.open('GET', 'GameAjaxServlet');
	httpRequest.send();
	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {		 
				console.log(httpRequest.responseText);
				if (httpRequest.responseText == x) {
					console.log("up to date");
				} else {
					console.log("not up to date");
					location.reload(true);
					return;
				}
			} else {
				console.log('Something went wrong..!!');
			}
		}
	}
	setTimeout(callAjax, 1000, x);
}
