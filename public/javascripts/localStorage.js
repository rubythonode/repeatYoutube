function addLocalStorage(key, value){
	var date = new Date();
	localStorage.setItem(YGRLR_SPARATOR1+date.getTime(), key+SPARATOR1+value);
};

function removeLocalStorage(key){
	localStorage.removeItem(key);
}

function consoleAllLocalStorage(){
	for(var i=0, len=localStorage.length; i<len; i++) {
	    var key = localStorage.key(i);
	    var value = localStorage[key];
	    console.log(key + " => " + value);
	}
}

function settingListToLocalStorage(){
	playList.length = 0;
	for(var i=0, len=localStorage.length; i<len; i++) {
	    var key = localStorage.key(i);
	    if(key.indexOf(YGRLR_SPARATOR1) == 0){
	    	var id = key.substring(YGRLR_SPARATOR1.length);
	    	playcnt = playList.push(id);
	    }
	}
}

function settingGroupYoutubeToLocalStorage(){
	$("#group_div_body").find("dd").remove();
	for(var i=0, len=localStorage.length; i<len; i++) {
	    var key = localStorage.key(i);
	    if(key.indexOf(YGRLR_SPARATOR1) == 0){
	    	console.log(key);
	    	var value = localStorage[key];
	    	var arrValue = value.split(SPARATOR1);
	    	var id = arrValue[0];
		    var title = decodeURIComponent(arrValue[1]);
		    var author = decodeURIComponent(arrValue[2]);
		    var alt = title +"("+author+")";
		    var html = '<dd class="overflow_ellipsis200" title="'+alt+'">'+
						    '<input name="rejection" id="'+id+'" value="'+id+'" class="input_radio" type="checkbox">'+
						    '<label for="'+id+'"><strong>'+title+'</strong>('+author+')</label>'+
					   '</dd>';
		    
		    $("#group_div_body").append(html);
	    }
	}
	settingListToStorage();
}