function isIEBrowser(){
	if($.browser.msie==true) {
	   return true;
	}
}

function isIEBrowser7(){
	if($.browser.msie==true && $.browser.version == 7.0) {
	   return true;
	}
}

function isIEBrowser8(){
	var c = $.browser;
	if($.browser.msie==true && $.browser.version == 8.0) {
	   return true;
	}
}

function isIEBrowser9(){
	if($.browser.msie==true && $.browser.version == 9.0) {
	   return true;
	}
}