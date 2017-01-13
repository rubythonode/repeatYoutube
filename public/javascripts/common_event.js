function setKeyDownEvent(keyCode, $obj, fn){
	$($obj).bind("keydown", function(e){
		if(e.keyCode == keyCode){
			fn();
		}
	});
}