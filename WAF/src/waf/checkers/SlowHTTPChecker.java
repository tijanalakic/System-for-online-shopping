package waf.checkers;

import java.util.HashMap;
import java.util.logging.Level;

import org.apache.catalina.connector.Request;

import waf.util.AttackLogger;
import waf.util.CommonNames;
import waf.util.MyLogger;

public class SlowHTTPChecker {

	public static HashMap<String, Integer> NUMBER_OF_CONNECTIONS = new HashMap<String, Integer>();
	public static long INTERVAL = 10000;
	public static int CONNECTIONS_THRESHOLD = 10;

	public static void check(Request request) {
		
		try {

			if (!NUMBER_OF_CONNECTIONS.containsKey(request.getRemoteAddr())) {
				NUMBER_OF_CONNECTIONS.put(request.getRemoteAddr(), 0);
				countdown(request.getRemoteAddr());
			} else {
				NUMBER_OF_CONNECTIONS.put(request.getRemoteAddr(),
						NUMBER_OF_CONNECTIONS.get(request.getRemoteAddr()) + 1);
			}

		} catch (Exception e) {
			MyLogger.log(Level.SEVERE, e.getMessage(), e.getStackTrace());
		}
	}

	public static void countdown(String ip) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(INTERVAL);
					int numberOfConnections = NUMBER_OF_CONNECTIONS.get(ip);

					if (numberOfConnections > CONNECTIONS_THRESHOLD) {
						AttackLogger.getLogger().logAttack(CommonNames.SLOW_HTTP, CommonNames.REQUEST, "number of connections ", numberOfConnections+"", ip, "unknown");
			
					}
					NUMBER_OF_CONNECTIONS.remove(ip);


				} catch(InterruptedException e) {
					MyLogger.log(Level.SEVERE, e.getMessage(), e.getStackTrace());
				}
			}
		}).start();
}}
