package controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.MusicCount;
import models.PlayListGroupR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.mvc.Controller;
import play.mvc.Result;

public class Analysis extends Controller {
	
	final static Logger logger = LoggerFactory.getLogger(Analysis.class);
	
	public static Result viewmusiccount() throws JSONException, UnsupportedEncodingException {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		List<MusicCount> list = MusicCount.find.orderBy("size desc").findList();

		int breakSize = 0;
		for (MusicCount mc : list) {
			Map<String, Object> data = new HashMap<String, Object>();
			String code = mc.code;
			String title = "";
			int size = mc.size;
			
			List<PlayListGroupR> musicList = PlayListGroupR.find.where().eq("keycode", code).findList();
			if(musicList.size() > 0){
				title = musicList.get(0).title;
				data.put("title",  java.net.URLDecoder.decode(title, "utf-8"));
				data.put("size", size);
				datas.add(data);
				breakSize++;
			}
			
			if(breakSize > 4) break;
		}
		
		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarr = new JSONArray(datas);
		jsonobj.put("musiccount", jsonarr);
		
		return ok(jsonobj.toString());
	}
	
	
}
