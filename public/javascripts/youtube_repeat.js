var player;
var startPage = 1;
var rowCount = 20;

var playType = PLAYTYPE_ONE;

var playList = new Array();
var playnum = 0;
var playcnt = 0;

var tag = document.createElement('script');
tag.src = "//www.youtube.com/iframe_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

function onYouTubeIframeAPIReady(){
	player = new YT.Player('player',{
		height: '390',
		width: '689',
		events:{
			onReady: onPlayerReady,
			onStateChange : onPlayerStateChange
		}
	});
}

function playVideo(id, title, author){
	playcnt = addCookieStorage(decodeURIComponent(id)+SPARATOR1+decodeURIComponent(title));
	initVideo(id);
	settingGroupYoutube();
	changeCssCookieToLabel(id, "font_bold");
}

function initVideo(id){
	player.cueVideoById({
		'videoId': id,
		'suggestedQuality': 'large'
	});
	player.playVideo();
	twoSearchYoutube(id);
	
	// event log를 쌓기 위해 넣어 놓음
	call_ajax_json('/repeat/logplay', {code:id}, function(data){});	
}

function playVideoList(num){
	if(playcnt == num){
		num = 0;
	}
	
	var id = playList[num].split(SPARATOR1)[0];
	initVideo(id);
	changeCssCookieToLabel(id, "font_bold");
	playnum = num;
}

function downloadmp3(num, obj){
	if(playcnt == num){
		num = 0;
	}
	
	var id = playList[num].split(SPARATOR1)[0];
	
	$(obj).find("img").attr("src","/assets/images/download/clock.png");
	$(obj).prop("onclick", null);
	
	call_ajax_json_s('/repeat/download', {dwcode:id}, function(data){
		if(data != null){
			$(obj).find("img").attr("src","/assets/images/download/chat.png");
			$("#dwcode").val(data.keyName);
			$("#downloadFrm").submit();
		}
	});
}

function onPlayerStateChange(event){
	switch(event.target.getPlayerState()){
		case -1:
			break;
		case 0:
			if(playType == PLAYTYPE_AUTO){
				playVideoList(playnum+1);
			}else{
				player.playVideo();
			}
			
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 5:
			break;
		default :
			break;
	}
}

function onPlayerReady(event){}

function searchYoutube(max, startPage){
	var searchName = $("#searchName").val();
	var url = "http://gdata.youtube.com/feeds/videos?start-index="+startPage+"&max-results="+rowCount+"&alt=json-in-script&callback=?&format=5&vq="+searchName;
	
	$.getJSON(url, function(data){
		var feed = data.feed;
		var entries = feed.entry || [];
		var gallery = $('#youtubeList');
		
		gallery.empty();
		
		if(entries.length > 0){
			for (var i = 0; i < entries.length; i++) {
				createListItem1Append(gallery, entries[i]);
			}
		}else{}
	});
}

function twoSearchYoutube(id){
	var url = "http://gdata.youtube.com/feeds/api/videos/"+id+"/related?v=2&alt=json&callback=?";
	
	$.getJSON(url, function(data){
		var feed = data.feed;
		var entries = feed.entry || [];
		var gallery = $('#youtubeRightList');
		
		gallery.empty();
		
		if(entries.length > 0){
			for (var i = 0; i < entries.length; i++) {
				createListItem2Append(gallery, entries[i]);
			}
		}else{}
	});
}


function createListItem1Append($list, entry){
	var title = entry.title.$t;
	var thumburl = entry.media$group.media$thumbnail[0].url;
	var tyvideoid = entry.id.$t.substring(entry.id.$t.lastIndexOf("/")+1);
	var author = entry.author[0].name.$t;
	var description = entry.media$group.media$description.$t;
	
	var html = '<li>'+
	    '<a href="#" >'+
	    	'<span class="thumb" onclick=playVideo("'+tyvideoid+'","'+encodeURIComponent(title)+'","'+encodeURIComponent(author)+'")>'+
	    		'<img src="'+thumburl+'" alt="" width="120">'+
	    		'<em>'+author+'</em>'+
	    	'</span>'+
	    	'<strong onclick=playVideo("'+tyvideoid+'","'+encodeURIComponent(title)+'","'+encodeURIComponent(author)+'")>'+title+'</strong>'+
	    '</a>'+
	    '<p>'+description+'</p>'+
	'</li>';
	
	$list.append(html);
}

function createListItem2Append($list, entry){
	var title = entry.title.$t;
	var thumburl = entry.media$group.media$thumbnail[0].url;
	var tyvideoid = entry.media$group.yt$videoid.$t;
	var author = entry.author[0].name.$t;
	
	var html = '<li>'+
	    '<a href="#" >'+
	    	'<span class="thumb" onclick=playVideo("'+tyvideoid+'","'+encodeURIComponent(title)+'","'+encodeURIComponent(author)+'")>'+
	    		'<img src="'+thumburl+'" alt="" width="120">'+
	    		'<em>'+author+'</em>'+
	    	'</span>'+
	    	'<strong onclick=playVideo("'+tyvideoid+'","'+encodeURIComponent(title)+'","'+encodeURIComponent(author)+'")>'+title+'</strong>'+
	    '</a>'+
	'</li>';
	
	$list.append(html);
}


function clickSearchYoutube(){
	searchYoutube(rowCount, startPage);
}
