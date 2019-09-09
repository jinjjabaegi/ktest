package com.realtimetech.ktest.testbed;

public abstract class TestBed {
	/**
	 * TestBed명을 반환하는 메서드 (TestBed명은 Test가 TestBed를 참조할 때 사용된다.)
	 * 
	 * @return : String TestBed명
	 */
	public abstract String getName();

	/**
	 * Test 시작 전 호출되는 메서드 (테스트 환경 구축할 때 사용)
	 */
	public abstract void setUp();

	/**
	 * Test가 끝난 뒤 실행되는 메서드 (테스트에 사용된 자원들을 해제할 때 사용)
	 */
	public abstract void tearDown();
}
