import models.PlayListGroup;
import models.PlayListGroupR;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.parser.ParseException;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import scala.concurrent.duration.Duration;
import util.CommonUtility;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import static org.joda.time.Seconds.secondsBetween;


public class Global extends GlobalSettings {
    @Override
    public void onStart(Application application) {
      Akka.system().scheduler().schedule(
            Duration.create(0, TimeUnit.SECONDS),
            Duration.create(1, TimeUnit.DAYS),
            new Runnable() {
                public void run() {
                	try{
   			    		schedulingInsert("http://apis.skplanetx.com/melon/charts/todaytopsongs?version=1&page=1&count=100&format=json&appKey=c4728efb-e0dd-3d31-8445-7624ac0307ce");
   			    	}catch(Exception e){
   			    		e.printStackTrace();
   			    	}
                }
            },
            Akka.system().dispatcher()
        );
    }
    
    public static void schedulingInsert(String _url) throws IOException, ParseException, JSONException{
    	URL url = new URL(_url);
        InputStreamReader reader = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
        JSONTokener tokenizer = new JSONTokener(reader);
        JSONObject jsonObject = new JSONObject(tokenizer);
        
        JSONObject melon = (JSONObject)(jsonObject.get("melon"));
    	JSONObject asongs = (JSONObject)(melon.get("songs"));
    	JSONArray songs = (JSONArray)asongs.get("song");
    	
    	PlayListGroup plg = new PlayListGroup(CommonUtility.getCurrentServerByFormat("yyyy년 MM월 dd일")+" 멜론 BEST 100곡", "ahrzosel1", "", new Long(0), "");
        plg.save();
    	
    	for(int i = 0 ; i < songs.length(); i++) {
	    	JSONObject obj1 = (JSONObject) songs.get(i);        
	    	saveYoutubeOne(obj1.get("songName").toString(), plg);
    	}
    }
    
    public static void saveYoutubeOne(String searchTitle, PlayListGroup plg) throws UnsupportedEncodingException, IOException, JSONException{
    	URL url = new URL("http://gdata.youtube.com/feeds/videos?start-index=1&max-results=1&alt=json&format=5&vq="+URLEncoder.encode(searchTitle, "UTF-8"));
        InputStreamReader reader = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8");
        JSONTokener tokenizer = new JSONTokener(reader);
        JSONObject jsonObject = new JSONObject(tokenizer);
        
        JSONObject feed = (JSONObject)(jsonObject.get("feed"));
        JSONArray entrise = (JSONArray)feed.get("entry");
        
        for(int i = 0 ; i < entrise.length(); i++) {
	    	JSONObject entry = (JSONObject) entrise.get(i);
	    	JSONObject titler = (JSONObject) entry.get("title");
	    	String title = (String) titler.get("$t");
	    	JSONObject idr = (JSONObject) entry.get("id");
	    	String oid = (String) idr.get("$t");
	    	String id = oid.substring(oid.lastIndexOf("/")+1);
			new PlayListGroupR(plg, id, title.replace("%", ""), "", new Long(0), "").save();
    	}
    }
}