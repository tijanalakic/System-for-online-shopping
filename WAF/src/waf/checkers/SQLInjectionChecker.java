package waf.checkers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.http.Cookie;

import org.apache.catalina.connector.Request;

import waf.util.AttackLogger;
import waf.util.CommonNames;
import waf.util.MyLogger;

public class SQLInjectionChecker {

	public static List<String> SQL_KEYWORDS = Arrays.asList("#", "'", "*", "=", "or", "--", "drop", "select", "update",
			"insert", "delete", "create", "alter", "drop", "table");

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
				AttackLogger.getLogger().logAttack(CommonNames.SQL_INJECTION, CommonNames.REQUEST, parameterName, parameterValue, request);
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
					AttackLogger.getLogger().logAttack(CommonNames.SQL_INJECTION, CommonNames.COOKIE, parameterName, parameterValue, request);
					return true;
				}
			}
		}
		return false;
	}

	private static boolean checkKeyword(String value) {

		for (String keyword : SQL_KEYWORDS) {
			if (value.toLowerCase().contains(keyword.toLowerCase())) {
				return true;
			}
		}

		return false;
	}

}
