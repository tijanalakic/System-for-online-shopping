package waf.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.catalina.connector.Request;

import waf.main.MyFilter;

public class AttackLogger {

	private static AttackLogger ATTACK_LOGGER;
	private static String ATTACK_LOGER_PATH = MyFilter.DATA_PATH + File.separator + "attackLogs.txt";

	private PrintWriter out;

	public AttackLogger() throws IOException {

		out = new PrintWriter(new BufferedWriter(new FileWriter(ATTACK_LOGER_PATH, true)), true);

	}

	public static AttackLogger getLogger() {

		if (ATTACK_LOGGER == null) {
			try {
				ATTACK_LOGGER = new AttackLogger();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ATTACK_LOGGER;
	}

	public void logAttack(String attackName, String parameterOrigin, String parameterName, String parameterValue, Request request) {
		String username;
		if(request.getRemoteUser()==null) {
			username="unknown";
		}
		else {
			username=request.getRemoteUser();
		}
		logAttack(attackName, parameterOrigin, parameterName, parameterValue, request.getRemoteAddr(),username);
	}

	public void logAttack(String attackName, String parameterOrigin, String parameterName, String parameterValue, String ip, String username) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		out.print(dtf.format(now));

		out.println("  Attack: " + attackName);
		out.println("Origin: " + parameterOrigin);
		out.println("Remote address: " + ip);
		out.println("Remote user:"+username);
		out.println("Parametar: " + parameterName + "  Value: " + parameterValue);
		out.println("");

	}
}
