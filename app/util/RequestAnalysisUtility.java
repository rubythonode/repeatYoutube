package util;

import java.util.HashMap;
import java.util.Map;

import play.mvc.Http.Request;

/**
 * @author hycho
 */
public class RequestAnalysisUtility {
	public static String outputRequestBasicInfo(Request req){
		String time = CommonUtility.getCurrentServerTime()+CommonUtility.getDayOfWeek();
		String userAgent = req.getHeader("User-Agent");
		String acceptLanguage = req.getHeader("Accept-Language");
		
		return time+getLocaleCodeByAccecptLangueage(acceptLanguage)+
			   getOsCodeByUserAgent(userAgent)+
			   getBrowserCodeByUserAgent(userAgent);
	}
	
	public static String getRequestBasicInfo(Request req){
		
		return "";
	}
	
	/**
	 * userAgent 정보를 통해 브라우저의 code를 return한다.
	 * @param userAgent
	 * @return
	 * @author hycho
	 */
	public static String getBrowserCodeByUserAgent(String userAgent){
		if(userAgent.indexOf("MSIE") != -1){
			return "01";
		}
		
		if(userAgent.indexOf("FireFox") != -1){
			return "02";
		}
		
		if(userAgent.indexOf("Mozilla") != -1){
			return "03";
		}
			
		if(userAgent.indexOf("Opera") != -1){
			return "04";
		}
		
		if(userAgent.indexOf("Safari") != -1){
			return "05";
		}
		
		if(userAgent.indexOf("Mac") != -1){
			return "06";
		}
		return "99";
	}
	
	/**
	 * userAgent 정보를 통해 os code를 return한다.
	 * @param userAgent
	 * @return
	 * @author hycho
	 */
	public static String getOsCodeByUserAgent(String userAgent){
		if(userAgent.indexOf("Windows") != -1){
			return "01";
		}
		
		if(userAgent.indexOf("Linux") != -1){
			return "02";
		}
		
		if(userAgent.indexOf("Macintosh") != -1){
			return "03";
		}
			
		return "99";
	}
	
	public static String getLocaleCodeByAccecptLangueage(String langueage){
		if(langueage.indexOf("ko") != -1){
			return "01";
		}
		
		if(langueage.indexOf("en") != -1){
			return "02";
		}
		
		if(langueage.indexOf("jp") != -1){
			return "03";
		}
			
		return "99";
	}
}
