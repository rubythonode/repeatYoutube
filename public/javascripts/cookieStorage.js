// 荑좏궎 留뚮즺��
var expires = 50;
var xExpires = -1;

function addCookieStorage(value){
	for(var i=0; i<playList.length; i++){
		if(playList[i] == value){
			return playList.length;
		}
	}
	playList.push(value);
	return playList.length;
};

function removeCookieStorage(num){
    playList.splice(num, 1);
}

function removeAllCookieStorage(){
	var theCookies = document.cookie.split(';');
	for (var i=1; i<=theCookies.length; i++) {
		var cookieKeyValue = theCookies[i-1];
    	var key = $.trim(cookieKeyValue.split("=")[0]);
	    if(key.indexOf(YGRLR_SPARATOR1) == 0){
	    	removeCookieStorage(key);
	    }
	}
}

function consoleAllCookieStorage(){
    var theCookies = document.cookie.split(';');
    for (var i = 1 ; i <= theCookies.length; i++) {
        console.log(i + " => " + theCookies[i-1]);
    }
}

function settingGroupYoutubeToCookieStorage(){
	var num = 0;
	var theCookies = document.cookie.split(';');
	$("#group_div_body").find("dd").remove();
	
	for (var i=0; i<playList.length; i++) {
		var value = playList[i];
	    var arrValue = value.split(SPARATOR1);
	    var id = decodeURIComponent(arrValue[0]);
	    var title = decodeURIComponent(arrValue[1]);
	    var alt = title;
	    var html = '<dd class="overflow_ellipsis200" title="'+alt+'">'+
					    '<input name="rejection" id="'+id+'" value="'+id+'" class="input_radio" type="checkbox" num="'+i+'">'+
					    '<label id="'+id+'" onclick=playVideoList('+(i)+') style="cursor:pointer">'+(i+1)+'. '+title+'</label>'+
				   '</dd>';
	    
	    $("#group_div_body").append(html);
	}
}

function changeCssCookieToLabel(id, css){
	$("#group_div_body").find("dd").each(function(i, elem){
		var $label = $(elem).find("label");
		if($label.attr("id") != id){
			$label.removeClass(css);
		}else{
			$label.addClass(css);
		}
	});
}

function getAllPlayListParams(){
	var params = {};
	for (var i=0; i<playList.length; i++) {
    	var value = playList[i];
    	if(value != null && value != "null"){
//    		var arrValue = value.split(SPARATOR1);
//	    	var id = arrValue[0];
//		    params[id] = arrValue[1];
    		params[i] = value;
    	}
	}
	return params;
}

function settingPlayList($playList){
	var num = 0;
	removeAllCookieStorage();
	playList.length = 0;
	$("#group_div_body").find("dd").remove();
	
	$.each($playList, function(i, obj){
		playcnt = addCookieStorage(obj.keycode+SPARATOR1+obj.title);
		var html = '<dd class="overflow_ellipsis200" title="'+decodeURIComponent(obj.title)+'">'+
		'<input name="rejection" id="'+obj.keycode+'" value="'+obj.keycode+'" kname="'+decodeURIComponent(obj.title)+'" class="input_radio" type="checkbox" num="'+i+'">'+
		//'<a onclick=downloadmp3('+num+',this) style="cursor:pointer;padding-:50px;"><img src="/assets/images/download/down.png" style="width:20px;height:20px"></img></a>'+
	    '<label id="'+obj.keycode+'" onclick=playVideoList('+(num++)+') style="display:inline;cursor:pointer;width:200px;">'+(i+1)+'. '+decodeURIComponent(obj.title)+'</label>'+
	    '</dd>';
    
		$("#group_div_body").append(html);
	});
	
}