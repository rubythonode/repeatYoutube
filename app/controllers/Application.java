package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.mvc.Controller;
import play.mvc.Result;
import util.RequestAnalysisUtility;
import util.UAgentInfo;
import views.html.index;
import views.html.mobileIndex;

public class Application extends Controller {
	
	final static Logger logger = LoggerFactory.getLogger(Application.class);

    public static Result index() {
    	logger.info("Application Index Start !!!!!");
		RequestAnalysisUtility.outputRequestBasicInfo(request());
    	response().setContentType("text/html; charset=utf-8");
    	request().acceptLanguages();
        UAgentInfo uAgentInfo = new UAgentInfo(request().getHeader(USER_AGENT),request().getHeader(ACCEPT));
        if(uAgentInfo.isMobile){
        	return redirect(routes.Repeat.grouplisthtml());
        }else{
        	return ok(index.render("Welcome Repeat Youtube!!"));
        }
    }
    
    public static Result mobileIndex() {
    	logger.info("Application Mobile Index Start !!!!!");
		RequestAnalysisUtility.outputRequestBasicInfo(request());
    	response().setContentType("text/html; charset=utf-8");
    	request().acceptLanguages();
    	return redirect(routes.Repeat.grouplisthtml());
    }
}
