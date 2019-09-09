package com.realtimetech.ktest.assertion;

public class Assert {
	/**
	 * 기대값이 true가 맞는지 검증하는 메소드
	 * (아닐 시 AssertException 강제 발생)
	 * 
	 * @param condition 	기대값 
	 */
	public static void assertTrue(boolean condition) {
		if (!condition) {
			throw new AssertException("assertTrue");
		}
	}

	/**
	 * 기대값이 false가 맞는지 검증하는 메소드
	 * (아닐 시 AssertException 강제 발생)
	 * 
	 * @param condition 	기대값
	 */
	public static void assertFalse(boolean condition) {
		if (condition) {
			throw new AssertException("assertFalse");
		}
	}

	/**
	 * 기대값과 실제값이 같은지 검증하는 메소드
	 * (아닐 시 AssertException 강제 발생)
	 * 
	 * @param value1 	기대값
	 * @param value2 	실제값
	 */
	public static void assertEQ(Object value1, Object value2) {
		if (Number.class.isAssignableFrom(value1.getClass()) && Number.class.isAssignableFrom(value2.getClass())) {
			if (compareNumber((Number) value1, (Number) value2) != 0) {
				throw new AssertException("assertEQ");
			}
		} else {
			if (!value1.equals(value2)) {
				throw new AssertException("assertEQ");
			}
		}
	}

	/**
	 * 기대값과 실제값이 다른지 검증하는 메소드
	 * (아닐 시 AssertException 강제 발생)
	 * 
	 * @param value1 	기대값
	 * @param value2 	실제값
	 */
	public static void assertNE(Object value1, Object value2) {
		if (Number.class.isAssignableFrom(value1.getClass()) && Number.class.isAssignableFrom(value2.getClass())) {
			if (compareNumber((Number) value1, (Number) value2) == 0) {
				throw new AssertException("assertNE");
			}
		} else {
			if (value1.equals(value2)) {
				throw new AssertException("assertNE");
			}
		}
	}

	/**
	 * 기대값이 실제값보다 작은지 검증하는 메소드
	 * (아닐 시 AssertException 강제 발생)
	 * 
	 * @param value1 	기대값
	 * @param value2 	실제값
	 */
	public static void assertLT(Number value1, Number value2) {
		if (compareNumber(value1, value2) != -1) {
			throw new AssertException("assertNE");
		}
	}
	/**
	 * 기대값이 실제값보다 작거나 같은지 검증하는 메소드
	 * (아닐 시 AssertException 강제 발생)
	 * 
	 * @param value1 	기대값
	 * @param value2 	실제값
	 */
	public static void assertLE(Number value1, Number value2) {
		if (compareNumber(value1, value2) == 1) {
			throw new AssertException("assertNE");
		}
	}

	/**
	 * 기대값이 실제값보다 큰지 검증하는 메소드
	 * (아닐 시 AssertException 강제 발생)
	 * 
	 * @param value1 	기대값
	 * @param value2 	실제값
	 */
	public static void assertGT(Number value1, Number value2) {
		if (compareNumber(value1, value2) != 1) {
			throw new AssertException("assertNE");
		}
	}
	/**
	 * 기대값이 실제값보다 크거나 같은지 검증하는 메소드
	 * (아닐 시 AssertException 강제 발생)
	 * 
	 * @param value1 	기대값
	 * @param value2 	실제값
	 */
	public static void assertGE(Number value1, Number value2) {
		if (compareNumber(value1, value2) == -1) {
			throw new AssertException("assertNE");
		}
	}

	/**
	 * 기대 문자열과 실제 문자열이 같은지  검증하는 메소드
	 * (아닐 시 AssertException 강제 발생)
	 * 
	 * @param value1 	기대값
	 * @param value2 	실제값
	 */
	public static void assertSTREQ(String value1, String value2) {
		if (value1.equals(value2)) {
			throw new AssertException("assertNE");
		}
	}

	/**
	 * 기대 문자열과 실제 문자열이 다른지  검증하는 메소드
	 * (아닐 시 AssertException 강제 발생)
	 * 
	 * @param value1 	기대값
	 * @param value2 	실제값
	 */
	public static void assertSTRNE(String value1, String value2) {
		if (!value1.equals(value2)) {
			throw new AssertException("assertNE");
		}
	}

	/**
	 * 기대 문자열과 실제 문자열이 대소문자 상관없이 같은지  검증하는 메소드
	 * (아닐 시 AssertException 강제 발생)
	 * 
	 * @param value1 	기대값
	 * @param value2 	실제값
	 */
	public static void assertSTRCASEEQ(String value1, String value2) {

		if (value1.equalsIgnoreCase(value2)) {
			throw new AssertException("assertNE");
		}
	}

	/**
	 * 기대 문자열과 실제 문자열이 대소문자 상관없이 다른지  검증하는 메소드
	 * (아닐 시 AssertException 강제 발생)
	 * 
	 * @param value1 	기대값
	 * @param value2 	실제값
	 */
	public static void assertSTRCASENE(String value1, String value2) {
		if (!value2.equalsIgnoreCase(value2)) {
			throw new AssertException("assertNE");
		}
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
