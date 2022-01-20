package waf.main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

import waf.checkers.ParameterTamperingChecker;
import waf.checkers.SQLInjectionChecker;
import waf.checkers.SlowHTTPChecker;
import waf.checkers.XSSChecker;
import waf.util.MyLogger;


public class MyFilter extends ValveBase{
	public boolean result = false;
	public static String DATA_PATH = System.getProperty("user.home")+File.separator + "WAF_log";
	
	static{
		File dataFile = new File(DATA_PATH);
		if (!dataFile.exists()) {
			dataFile.mkdir();
		}
	}
	
	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException  {
			System.out.println("Request invoked!");
			
			try {
				
				SlowHTTPChecker.check(request); 
				
				if(SQLInjectionChecker.check(request) || ParameterTamperingChecker.check(request) || XSSChecker.check(request) )
				{
					HttpSession session=request.getSession(false);
					session.setAttribute("attack", "attack");
					
				}
				getNext().invoke(request, response);
				
			
				
			} catch (Exception e) {
				MyLogger.log(Level.SEVERE, e.toString(), e.getStackTrace());
			}
			
		
	}
}
