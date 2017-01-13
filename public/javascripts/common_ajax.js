function call_ajax_json(url, param, s_fn){
	$.ajax({
        type : 'POST',
        url : url,
        data : param,
        dataType : "json",
        success : function(data) {
            s_fn(data);
        },
        error : function(data) {
            alert("error");
        }
    });
}

function call_ajax_json_s(url, param, s_fn){
	$.ajax({
        type : 'POST',
        url : url,
        data : param,
        dataType : "json",
        async: false,
        success : function(data) {
            s_fn(data);
        },
        error : function(data) {
            alert("error");
        }
    });
}