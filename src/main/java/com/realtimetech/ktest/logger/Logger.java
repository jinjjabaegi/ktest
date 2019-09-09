package com.realtimetech.ktest.logger;

public class Logger {
	public enum LogType {
		START("RUN"), PASS("PASS"), FAIL("FAILED"), WARNING("WARNING"), DEFAULT("=========="), GROUP("----------"),
		TEMP("TEMP");
		String stateStr;

		private LogType(String stateStr) {
			this.stateStr = stateStr;
		}

		public String getState() {
			return stateStr;
		}
	}

	/**
	 * log를 출력하는 메소드
	 * 
	 * @param state 출력할 log의 타입 (사용자 임의 log일 경우 LogType.TEMP)
	 * @param msg   출력할 메세지
	 */
	public static void log(LogType state, String msg) {
		String logType = state.getState();
		int position = (int) Math.round(logType.length() / 2.0);
		
		String str1 = logType.substring(0, position);
		String str2 = logType.substring(position, logType.length());
		
		System.out.println(String.format("[%5s%-5s] %s", str1, str2, msg));
	}
}
