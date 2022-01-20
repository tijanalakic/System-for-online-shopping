package waf.checkers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

import org.apache.catalina.connector.Request;

import waf.util.AttackLogger;
import waf.util.CommonNames;
import waf.util.MyLogger;

public class XSSChecker {

	private static Pattern[] PATTERNS = new Pattern[]{
	        // Script fragments
	        Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
	        // src='...'
	        Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
	        Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
	        // lonely script tags
	        Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
	        Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
	        // eval(...)
	        Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
	        // expression(...)
	        Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
	        // javascript:...
	        Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
	        // vbscript:...
	        Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
	        // onload(...)=...
	        Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
	    };
	
	
	public static boolean check(Request request) {
		try {

			if(checkRequest(request) || checkCookie(request)) {
				return true;
			}
		} catch (Exception e) {
			MyLogger.log(Level.SEVERE, e.getMessage(), e.getStackTrace());
		}
		return false;
	}

	private static boolean checkRequest(Request request) throws Exception {

		Enumeration<String> parameters = request.getParameterNames();

		while (parameters.hasMoreElements()) {

			String parameterName = parameters.nextElement();
			String parameterValue = request.getParameter(parameterName);

			if (checkKeyword(parameterValue)) {
				AttackLogger.getLogger().logAttack(CommonNames.XSS, CommonNames.REQUEST, parameterName, parameterValue, request);
				return true;
			}
		}
		return false;
	}

	private static boolean checkCookie(Request request) throws UnsupportedEncodingException {

		Cookie cookiesArray[] = request.getCookies();

		if (cookiesArray != null) {
			List<Cookie> cookies = Arrays.asList(cookiesArray);

			for (Cookie cookie : cookies) {

				String parameterName = cookie.getName();
				String parameterValue = URLDecoder.decode(cookie.getValue(), "UTF-8");

				if (checkKeyword(parameterValue)) {
					AttackLogger.getLogger().logAttack(CommonNames.XSS, CommonNames.COOKIE, parameterName, parameterValue, request);
					return true;
				}
			}
		}
		return false;
	}

	private static boolean checkKeyword(String value) {

		  value = value.replaceAll("\0", "");

          // Remove all sections that match a pattern
          for (Pattern scriptPattern : PATTERNS){
             if (scriptPattern.matcher(value).matches()) {
            	 return true;
             }
          }

		return false;
	}
}

