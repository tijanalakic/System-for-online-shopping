package waf.checkers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.http.Cookie;

import org.apache.catalina.connector.Request;

import waf.util.AttackLogger;
import waf.util.CommonNames;
import waf.util.MyLogger;

public class ParameterTamperingChecker {

	public static String ITEM_QTY = "quantity";
	public static String PRODUCT_PRICE = "price";

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

		String item_quantity = request.getParameter(ITEM_QTY);
		String product_price = request.getParameter(PRODUCT_PRICE);
		boolean result=false;
		if(product_price != null && !"".equals(product_price)) {
			if (checkProductPrice(product_price)) {
				AttackLogger.getLogger().logAttack(CommonNames.PARAMETER_TAMPERING, CommonNames.REQUEST, PRODUCT_PRICE, product_price, request);
				result=true;
			}
		}
		
		if (item_quantity != null && !"".equals(item_quantity))   {
		if (checkItemQuantity(item_quantity)) {
			AttackLogger.getLogger().logAttack(CommonNames.PARAMETER_TAMPERING, CommonNames.REQUEST, ITEM_QTY, item_quantity, request);
			result=true;
		}
	}
	return result;
}

	private static boolean checkCookie(Request request) throws UnsupportedEncodingException {

		Cookie cookiesArray[] = request.getCookies();
		String item_quantity = null;
		String product_price = null;
		boolean result=false;
		
		if (cookiesArray != null) {
			List<Cookie> cookies = Arrays.asList(cookiesArray);

			for (Cookie cookie : cookies) {

				String parameterName = cookie.getName();
				if (ITEM_QTY.equals(parameterName)) {
					item_quantity = URLDecoder.decode(cookie.getValue(), "UTF-8");
				} else if (PRODUCT_PRICE.equals(parameterName)) {
					product_price = URLDecoder.decode(cookie.getValue(), "UTF-8");
				}
				
			}
			if (item_quantity != null && !"".equals(item_quantity))   {
				if (checkItemQuantity(item_quantity)) {
					AttackLogger.getLogger().logAttack(CommonNames.PARAMETER_TAMPERING, CommonNames.COOKIE, ITEM_QTY, item_quantity, request);
					result=true;
				}
			}
			
			if(product_price != null && !"".equals(product_price)) {
			
			if (checkProductPrice(product_price)) {
				AttackLogger.getLogger().logAttack(CommonNames.PARAMETER_TAMPERING, CommonNames.COOKIE, PRODUCT_PRICE, product_price, request);
				result=true;
			}
		}
	}
		return result;
}

	private static boolean checkItemQuantity(String value) {
		
		try {
			double item_qty = Double.parseDouble(value);
			
			if(item_qty < 1 || item_qty >= 1000) {
				return true;
			}
		} catch (NumberFormatException e) {
			MyLogger.log(Level.SEVERE, e.getMessage(), e.getStackTrace());
			return true;
		}
		
		return false;	
	}

	private static boolean checkProductPrice(String value) {
		
		try {
			double product_price = Double.parseDouble(value);
			
			if(product_price < 0 || product_price>1000000) {
				return true;
			}
		} catch (NumberFormatException e) {
			MyLogger.log(Level.SEVERE, e.getMessage(), e.getStackTrace());
			return true;
		}
		
		return false;	
	}
}
