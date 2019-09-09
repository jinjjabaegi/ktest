package com.realtimetech.ktest.assertion;

import com.realtimetech.ktest.logger.Logger;
import com.realtimetech.ktest.logger.Logger.LogType;

public class Expect {
	/**
	 * 기대값이 true가 맞는지 검증하는 메소드
	 * 
	 * @param condition 기대값
	 */
	public static void expectTrue(boolean condition) {
		if (!condition) {
			printWarning("expectTrue");
		}
	}

	/**
	 * 기대값이 false가 맞는지 검증하는 메소드
	 * 
	 * @param condition 기대값
	 */
	public static void expectFalse(boolean condition) {
		if (condition) {
			printWarning("expectFalse");
		}
	}

	/**
	 * 기대값과 실제값이 같은지 검증하는 메소드
	 * 
	 * @param value1 기대값
	 * @param value2 실제값
	 */
	public static void expectEQ(Object value1, Object value2) {
		if (Number.class.isAssignableFrom(value1.getClass()) && Number.class.isAssignableFrom(value2.getClass())) {
			if (compareNumber((Number) value1, (Number) value2) != 0) {
				printWarning("expectEQ");
			}
		} else {
			if (!value1.equals(value2)) {
				printWarning("expectEQ");
			}
		}
	}

	/**
	 * 기대값과 실제값이 다른지 검증하는 메소드
	 * 
	 * @param value1 기대값
	 * @param value2 실제값
	 */
	public static void expectNE(Object value1, Object value2) {
		if (Number.class.isAssignableFrom(value1.getClass()) && Number.class.isAssignableFrom(value2.getClass())) {
			if (compareNumber((Number) value1, (Number) value2) == 0) {
				printWarning("expectNE");
			}
		} else {
			if (value1.equals(value2)) {
				printWarning("expectNE");
			}
		}
	}

	/**
	 * 기대값이 실제값보다 작은지 검증하는 메소드
	 * 
	 * @param value1 기대값
	 * @param value2 실제값
	 */
	public static void expectLT(Number value1, Number value2) {
		if (compareNumber(value1, value2) != -1) {
			printWarning("expectLT");
		}
	}

	/**
	 * 기대값이 실제값보다 작거나 같은지 검증하는 메소드
	 * 
	 * @param value1 기대값
	 * @param value2 실제값
	 */
	public static void expectLE(Number value1, Number value2) {
		if (compareNumber(value1, value2) == 1) {
			printWarning("expectLE");
		}
	}

	/**
	 * 기대값이 실제값보다 큰지 검증하는 메소드
	 * 
	 * @param value1 기대값
	 * @param value2 실제값
	 */
	public static void expecttGT(Number value1, Number value2) {
		if (compareNumber(value1, value2) != 1) {
			printWarning("expecttGT");
		}
	}

	/**
	 * 기대값이 실제값보다 크거나 같은지 검증하는 메소드
	 * 
	 * @param value1 기대값
	 * @param value2 실제값
	 */
	public static void expectGE(Number value1, Number value2) {
		if (compareNumber(value1, value2) == -1) {
			printWarning("expectGE");
		}
	}

	/**
	 * 기대 문자열과 실제 문자열이 같은지 검증하는 메소드
	 * 
	 * @param value1 기대값
	 * @param value2 실제값
	 */
	public static void expectSTREQ(String value1, String value2) {
		if (value1.equals(value2)) {
			printWarning("expectSTREQ");
		}
	}

	/**
	 * 기대 문자열과 실제 문자열이 다른지 검증하는 메소드
	 * 
	 * @param value1 기대값
	 * @param value2 실제값
	 */
	public static void expectSTRNE(String value1, String value2) {
		if (!value1.equals(value2)) {
			printWarning("expectSTRNE");
		}
	}

	/**
	 * 기대 문자열과 실제 문자열이 대소문자 상관없이 같은지 검증하는 메소드
	 * 
	 * @param value1 기대값
	 * @param value2 실제값
	 */
	public static void expectSTRCASEEQ(String value1, String value2) {

		if (value1.equalsIgnoreCase(value2)) {
			printWarning("expectSTRCASEEQ");
		}
	}

	/**
	 * 기대 문자열과 실제 문자열이 대소문자 상관없이 다른지 검증하는 메소드
	 * 
	 * @param value1 기대값
	 * @param value2 실제값
	 */
	public static void expectSTRCASENE(String value1, String value2) {
		if (!value2.equalsIgnoreCase(value2)) {
			printWarning("expectSTRCASENE");
		}
	}

	private static void printWarning(String msg) {
		StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];

		Logger.log(LogType.WARNING, msg + " at " + stackTrace.getFileName() + ":" + stackTrace.getLineNumber());

	}

	private static int compareNumber(Number left, Number right) {
		double epsilon = 0.000000000000001d;
		double value = 0;
		if (left == null || right == null) {
			return 2;
		} else {
			value = left.doubleValue() - right.doubleValue();

			if (Math.abs(value) < epsilon)
				return 0;
			else if (value < epsilon) {
				return -1;
			} else if (value > epsilon) {
				return 1;
			}
		}
		return 0;
	}
}
