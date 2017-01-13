package util;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.ResponseHandler;


public class ConvertFFmpeg {
	
	public static final String convPath = "/opt/conv/";
	public static final String zipName = "mp3Down.zip";
	/*public static void main(String[] args) throws Exception{
		System.out.println("convertTest");
		remoteConvert();
		System.out.println("convertEnd");
	}*/
	
	/*
	 * 
	 *  youtube-dl http://www.youtube.com/watch?v=g-6a9jC-K8I

		ffmpeg -i tlWpnLdPwvk.flv RodStewartMaggieMay.mp3
		
		ffmpeg -i  test.flv test.mp3
		
		rpm --import http://packages.atrpms.net/RPM-GPG-KEY.atrpms
	 * 
	 */
	
	public static File remoteConvert(String url) throws Exception{
		String primaryKeyCode = String.valueOf(System.nanoTime());
		String[] cmdLine = new String[]	{
				 "youtube-dl",
				 "-o",
				 convPath+primaryKeyCode+".flv",
                url
		};
		
		ProcessBuilder processBuilder = new ProcessBuilder(cmdLine);
		processBuilder.redirectErrorStream(true);
		Process process = null;
		
		try{
			process = processBuilder.start();
		}catch(Exception e){
			e.printStackTrace();
			process.destroy();
		}
		
		outLogInputStream(process.getInputStream());
		
		try{
			process.waitFor();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(process.exitValue() != 0){
			System.out.println("변환중 Error 발생");
		}
		
		process.destroy();
		
		callWebScript(primaryKeyCode);
		
		return new File(convPath+primaryKeyCode+".mp3");
	}
	
	private static void outLogInputStream(final InputStream is){
		int iTotalFrameSize = 0;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String cmd = null;
			while((cmd = br.readLine()) != null){
				/*if(cmd.indexOf("end") > -1 ){
					String totalFrame = cmd.substring(cmd.indexOf("end")+4, cmd.length());
					String cleanTotalFrame = totalFrame.substring(0, totalFrame.indexOf('.'));
					iTotalFrameSize = Integer.parseInt(cleanTotalFrame);
				}
				
				if(cmd.startsWith("frame")){
					int curConvFrameSize = Integer.parseInt(cmd.substring(6, 11).trim());
					System.out.println(Math.round((float)((float)curConvFrameSize/(float)iTotalFrameSize) * 100)+"%");
				}*/
				System.out.println(cmd);
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void callWebScript(String primaryKeyCode) throws Exception{
		String[] cmdLine = new String[]	{
				 "ffmpeg",
				 "-i",
				 convPath+primaryKeyCode+".flv",
				 convPath+primaryKeyCode+".mp3"
		};
		
		ProcessBuilder processBuilder = new ProcessBuilder(cmdLine);
		processBuilder.redirectErrorStream(true);
		Process process = null;
		
		try{
			process = processBuilder.start();
		}catch(Exception e){
			e.printStackTrace();
			process.destroy();
		}
		
		outLogInputStream(process.getInputStream());
		
		try{
			process.waitFor();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(process.exitValue() != 0){
			System.out.println("변환중 Error 발생");
		}
		
		process.destroy();
	}
	
}