package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import models.PlayListGroup;
import models.PlayListGroupR;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.data.DynamicForm;
import play.data.DynamicForm.Dynamic;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import util.ConvertFFmpeg;
import util.RequestAnalysisUtility;
import views.html.mobileIndex;
import views.html.mobileplayer;
import views.html.mobileplaylist;

public class Repeat extends Controller {

	final static Logger logger = LoggerFactory.getLogger(Repeat.class);

	public static Result newlist() throws JSONException {
		DynamicForm form = Form.form().bindFromRequest();
		Dynamic dm = form.get();
		Map<String, Object> dataMap = dm.getData();
		Set<String> keySet = dataMap.keySet();
		String title = form.get("title");
		String password = form.get("password");

		Object[] hmKeys = keySet.toArray();
		Arrays.sort(hmKeys, keyStringSort);

		PlayListGroup plg = new PlayListGroup(title, password, "", new Long(0),
				"");
		for (Object hmKey : hmKeys) {
			String _key = (String) hmKey;
			if (_key.equals("title") || _key.equals("password"))
				continue;

			String data = (String) dataMap.get(_key);
			String[] arrData = data.split(String.valueOf((char) 2));
			String code = arrData[0];
			String playTitle = arrData[1];
			plg.save();
			new PlayListGroupR(plg, code, playTitle, "", new Long(0), "")
					.save();
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", "success");
		return ok(jsonobj.toString());
	}

	public static Result save() throws JSONException {
		DynamicForm form = Form.form().bindFromRequest();
		Dynamic dm = form.get();
		Map<String, Object> dataMap = dm.getData();
		Set<String> keySet = dataMap.keySet();
		String id = form.get("id");
		String password = form.get("password");

		Object[] hmKeys = keySet.toArray();
		Arrays.sort(hmKeys, keyStringSort);

		List<PlayListGroup> pl = PlayListGroup.find.where()
				.eq("id", Long.parseLong(id)).eq("password", password)
				.findList();

		for (PlayListGroup plg : pl) {
			for (PlayListGroupR dpr : PlayListGroupR.find.where()
					.eq("group.id", plg.id).findList()) {
				dpr.delete();
			}

			for (Object hmKey : hmKeys) {
				String _key = (String) hmKey;
				if (_key.equals("id") || _key.equals("password"))
					continue;

				String data = (String) dataMap.get(_key);
				String[] arrData = data.split(String.valueOf((char) 2));
				String code = arrData[0];
				String playTitle = arrData[1];
				new PlayListGroupR(plg, code, playTitle, "", new Long(0), "")
						.save();
			}
		}

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", "success");
		return ok(jsonobj.toString());
	}

	public static Result grouplist() throws JSONException {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		List<PlayListGroup> list = PlayListGroup.find.order("id desc")
				.findList();

		for (PlayListGroup group : list) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", group.id);
			data.put("title", group.title);
			datas.add(data);
		}

		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarr = new JSONArray(datas);
		jsonobj.put("grouplist", jsonarr);
		return ok(jsonobj.toString());
	}

	public static Result grouplisthtml() throws JSONException {
		List<PlayListGroup> list = PlayListGroup.find.order("id desc")
				.findList();

		return ok(mobileIndex.render("Welcome Repeat Youtube!!", list));
	}

	public static Result playlist() throws JSONException {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		DynamicForm form = Form.form().bindFromRequest();
		String groupId = form.get("groupId");

		logger.info(RequestAnalysisUtility.outputRequestBasicInfo(request())
				+ "01" + groupId);

		List<PlayListGroupR> playGroupList = PlayListGroupR.find.where()
				.eq("group.id", groupId).orderBy("id asc").findList();

		for (PlayListGroupR playGroup : playGroupList) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("title", playGroup.title);
			data.put("keycode", playGroup.keycode);
			datas.add(data);
		}

		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarr = new JSONArray(datas);
		jsonobj.put("playlist", jsonarr);
		return ok(jsonobj.toString());
	}

	public static Result playlisthtml() throws JSONException {
		DynamicForm form = Form.form().bindFromRequest();
		String groupId = form.get("groupId");
		logger.info(RequestAnalysisUtility.outputRequestBasicInfo(request())
				+ "01" + groupId);
		List<PlayListGroupR> playGroupList = PlayListGroupR.find.where()
				.eq("group.id", groupId).orderBy("id asc").findList();
		return ok(mobileplaylist.render("Welcome Repeat Youtube!!",
				playGroupList, groupId));
	}

	public static Result playerhtml() throws JSONException {
		DynamicForm form = Form.form().bindFromRequest();
		String groupId = form.get("groupId");
		String keycode = form.get("keycode");
		return ok(mobileplayer.render("Welcome Repeat Youtube!!", keycode,
				groupId));
	}

	public static Result searchgrouplist() throws JSONException {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		DynamicForm form = Form.form().bindFromRequest();
		String text = form.get("text");

		List<PlayListGroup> playGroupList = PlayListGroup.find.where()
				.ilike("title", "%" + text + "%").findList();

		for (PlayListGroup playGroup : playGroupList) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("title", playGroup.title);
			data.put("id", playGroup.id);
			datas.add(data);
		}

		JSONObject jsonobj = new JSONObject();
		JSONArray jsonarr = new JSONArray(datas);
		jsonobj.put("grouplist", jsonarr);
		return ok(jsonobj.toString());
	}

	public static Result logplay() throws JSONException {
		DynamicForm form = Form.form().bindFromRequest();
		String code = form.get("code");

		logger.info(RequestAnalysisUtility.outputRequestBasicInfo(request())
				+ "02" + code);

		JSONObject jsonobj = new JSONObject();
		jsonobj.put("result", "success");
		return ok(jsonobj.toString());
	}

	public static Result mDownLoad() throws Exception {
		byte[] buffer = new byte[1024];
		DynamicForm form = Form.form().bindFromRequest();
		String codes = form.get("dwcode");
		//String url = "http://www.youtube.com/watch?v="+code;
		
		String [] urls = codes.split(String.valueOf((char)1)); //여기
		FileOutputStream fos = new FileOutputStream(ConvertFFmpeg.convPath+ConvertFFmpeg.zipName);
		ZipOutputStream zos = new ZipOutputStream(fos);
		
		for(String data : urls){
			String [] datas = data.split(String.valueOf((char)2)); //여기
			
			File file = ConvertFFmpeg.remoteConvert("http://www.youtube.com/watch?v="+datas[0]);
			zos.putNextEntry(new ZipEntry(datas[1]+".mp3"));
			FileInputStream in = new FileInputStream(file);
			
			int len;
			while ((len = in.read(buffer)) > 0){
				zos.write(buffer, 0, len);
			}
			in.close();
		}
		
		zos.closeEntry();
		zos.close();
		
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("keyName", ConvertFFmpeg.zipName);
		return ok(jsonobj.toString());
	}

	public static Result fileDownLoad() throws Exception{
		DynamicForm form = Form.form().bindFromRequest();
		String code = form.get("dwcode");
		
		String filename = code;
		response().setHeader("Content-disposition","attachment; filename="+filename);
		return ok(new File(ConvertFFmpeg.convPath+filename));
	} 
	
	public static Comparator<Object> keyStringSort = new Comparator<Object>() {
		@Override
		public int compare(Object s1, Object s2) {
			char[] c1 = ((String)s1).toCharArray();
			char[] c2 = ((String)s2).toCharArray();
			int index1 = 0, index2 = 0;
			int count1 = c1.length, count2 = c2.length;
			long val1, val2;

			while (index1 < count1 && index2 < count2) {
				if (c1[index1] >= '0' && c1[index1] <= '9' && c2[index2] >= '0'
						&& c2[index2] <= '9') {
					
					val1 = c1[index1] - '0';
					index1++;
					while (index1 < count1 && c1[index1] >= '0'
							&& c1[index1] <= '9') {
						val1 *= 10;
						val1 += c1[index1] - '0';
						index1++;
					}
					val2 = c2[index2] - '0';
					index2++;
					while (index2 < count2 && c2[index2] >= '0'
							&& c2[index2] <= '9') {
						val2 *= 10;
						val2 += c2[index2] - '0';
						index2++;
					}

					if (val1 == val2)
						continue;
					else if (val1 < val2)
						return -1;
					else
						return 1;
				} else if (c1[index1] == c2[index2]) {
					index1++;
					index2++;
					continue;
				} else if (c1[index1] < c2[index2])
					return -1;
				else
					return 1;
			}

			if (index1 >= count1 && index2 >= count2)
				return 0;
			else if (index1 >= count1)
				return -1;
			else
				return 1;
		}
	};

}
