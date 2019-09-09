package com.realtimetech.ktest;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.realtimetech.ktest.annotation.After;
import com.realtimetech.ktest.annotation.Before;
import com.realtimetech.ktest.annotation.Ignore;
import com.realtimetech.ktest.annotation.Test;
import com.realtimetech.ktest.assertion.AssertException;
import com.realtimetech.ktest.classloader.KtestClassLoader;
import com.realtimetech.ktest.classloader.UnsafeAllocator;
import com.realtimetech.ktest.filter.Filter;
import com.realtimetech.ktest.filter.GroupFilter;
import com.realtimetech.ktest.filter.IgnoreFilter;
import com.realtimetech.ktest.filter.TestFilter;
import com.realtimetech.ktest.logger.Logger;
import com.realtimetech.ktest.logger.Logger.LogType;
import com.realtimetech.ktest.testbed.TestBed;

public class Ktest {
	private String rootPath;

	private UnsafeAllocator unsafeAllocator;
	private KtestClassLoader ktestClassLoader;

	private List<Filter> filters;

	private Map<String, List<TestItem>> tests;
	private Map<String, TestBed> testBeds;

	private class TestItem {
		String testName;
		Object instance;
		Method after;
		Method test;
		Method before;
	}

	public Ktest(String rootPath) {
		this.rootPath = rootPath;
		this.tests = new HashMap<String, List<TestItem>>();
		this.testBeds = new HashMap<String, TestBed>();
		this.filters = new LinkedList<Filter>();
		this.unsafeAllocator = UnsafeAllocator.create();
	}

	// project absolute path
	public static void main(String[] args) {
		Ktest ktest = new Ktest(args[0] + "/bin/");
		ktest.setFilters(args);
		ktest.initialize();
		ktest.runAllTest();
	}

	private void setFilters(String[] datas) {
		Filter curFilter = null;
		for (String data : datas) {
			switch (data) {
			case "-g": {
				curFilter = new GroupFilter();
				this.filters.add(curFilter);
				break;
			}
			case "-t": {
				curFilter = new TestFilter();
				this.filters.add(curFilter);
				break;
			}
			case "-i": {
				curFilter = new IgnoreFilter();
				this.filters.add(curFilter);
				break;
			}
			default: {
				if (curFilter != null) {
					curFilter.addTarget(data);
				}
				break;
			}
			}
		}
	}

	public static List<String> searchClassesInDir(List<String> classes, File dir, String packagePrefix) {
		String prefix = "";
		if (dir.isDirectory()) {
			if (!dir.getName().equals("bin"))
				prefix = packagePrefix + dir.getName() + ".";
			for (File f : dir.listFiles()) {
				searchClassesInDir(classes, f, prefix);
			}
		} else {
			String fileName = dir.getName();
			if (!fileName.endsWith(".class"))
				return null;
			String className = packagePrefix + fileName.substring(0, fileName.length() - ".class".length());
			classes.add(className);
		}
		return classes;
	}

	private boolean filtering(Test annotation) {
		for (Filter filter : this.filters) {
			if (!filter.filter(annotation)) {
				return false;
			}
		}
		return true;
	}

	private void initialize() {
		File file = new File(rootPath);
		if (file.exists()) {
			try {
				this.ktestClassLoader = new KtestClassLoader(new URL[] { file.toURI().toURL() }, Ktest.class.getClassLoader());
				List<String> classNames = searchClassesInDir(new ArrayList<String>(), file, "");
				for (String className : classNames) {
					Method defaultAfter = null;
					Method defaultBefore = null;
					Class<?> clazz = ktestClassLoader.loadClass(className);
					Object instance = null;

					if (TestBed.class.isAssignableFrom(clazz)) {
						instance = this.unsafeAllocator.newInstance(clazz);
						TestBed testBed = (TestBed) instance;
						this.testBeds.put(testBed.getName(), testBed);
					}

					Map<String, TestItem> itemMap = new HashMap<String, TestItem>();
					for (Method method : clazz.getMethods()) {
						if (method.isAnnotationPresent(Test.class)) {
							Test annotation = method.getAnnotation(Test.class);

							if (!filtering(annotation) || method.isAnnotationPresent(Ignore.class)) {
								continue;
							}

							TestItem testItem = new TestItem();

							String testGroup = annotation.testGroupName();
							String testName = annotation.testName();

							if (testGroup.equals(""))
								testGroup = "Default";

							if (instance == null)
								instance = this.unsafeAllocator.newInstance(clazz);

							testItem.test = method;
							testItem.testName = testName;
							testItem.instance = instance;

							if (!tests.containsKey(testGroup)) {
								tests.put(testGroup, new ArrayList<TestItem>());
							}

							itemMap.put(testName, testItem);
							tests.get(testGroup).add(testItem);
						}
					}

					for (Method method : clazz.getMethods()) {
						TestItem testItem = null;
						if (method.isAnnotationPresent(Before.class)) {
							Before annotation = method.getAnnotation(Before.class);
							String targetTestName = annotation.tagetTestName();

							if (targetTestName.equals("")) {
								defaultBefore = method;
								continue;
							}
							testItem = itemMap.get(targetTestName);

							if (testItem != null) {
								testItem.before = method;
							}

						} else if (method.isAnnotationPresent(After.class)) {
							After annotation = method.getAnnotation(After.class);
							String targetTestName = annotation.tagetTestName();

							if (targetTestName.equals("")) {
								defaultAfter = method;
								continue;
							}

							testItem = itemMap.get(targetTestName);

							if (testItem != null) {
								testItem.after = method;
							}
						}
					}

					for (String testName : itemMap.keySet()) {
						TestItem testItem = itemMap.get(testName);
						if (defaultBefore != null && testItem.before == null) {
							testItem.before = defaultBefore;
						}
						if (defaultAfter != null && testItem.after == null) {
							testItem.after = defaultAfter;
						}
					}
				}

			} catch (MalformedURLException | ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void runAllTest() {
		int totalTestNum = 0;

		boolean result = true;

		double totalTime = 0;
		double groupTime = 0;
		double curTime = 0;

		TestBed testBed = null;

		for (String groupName : tests.keySet()) {
			List<TestItem> testList = tests.get(groupName);
			totalTestNum += testList.size();
		}

		Logger.log(LogType.DEFAULT, "Running " + totalTestNum + " test from " + tests.keySet().size() + " test groups");

		for (String groupName : this.tests.keySet()) {
			List<TestItem> testList = this.tests.get(groupName);
			testBed = this.testBeds.get(groupName);

			Logger.log(LogType.GROUP, testList.size() + " tests from " + groupName + " test group");
			groupTime = 0;
			for (TestItem testItem : testList) {
				result = true;
				Logger.log(LogType.START, "Run " + testItem.testName);

				try {
					testBed.setUp();
				} catch (NullPointerException e) {

				} catch (Exception e) {
					Logger.log(LogType.WARNING, "Error while setting " + testBed.getName() + " TestBed - setUp");
					e.printStackTrace();
				}

				try {
					testItem.before.invoke(testItem.instance);
				} catch (NullPointerException e) {

				} catch (Exception e) {
					Logger.log(LogType.WARNING, "Error while setting " + testItem.testName + " in @Before Method");
				}

				try {
					long startTime = System.nanoTime();
					testItem.test.invoke(testItem.instance, testBed);
					long endTime = System.nanoTime();

					curTime = (endTime - startTime) / 1000000.0;
					groupTime += curTime;
				} catch (AssertException e) {
					result = false;
					Logger.log(LogType.FAIL, e.getMessage());

				} catch (Exception e) {
					result = false;

					Logger.log(LogType.FAIL, "Error in " + testItem.testName);
					e.printStackTrace();
				}

				try {
					testItem.after.invoke(testItem);
				} catch (NullPointerException e) {

				} catch (Exception e) {
					Logger.log(LogType.WARNING, "Error while setting " + testItem.testName + " in @After Method");
				}

				try {
					testBed.tearDown();
				} catch (NullPointerException e) {

				} catch (Exception e) {
					Logger.log(LogType.WARNING, "Error while setting " + testBed.getName() + " TestBed - tearDown");
					e.printStackTrace();
				}

				if (result)
					Logger.log(LogType.PASS, testItem.testName + " is Passed (" + curTime + "ms)");
			}
			Logger.log(LogType.GROUP, testList.size() + " tests from " + groupName + " test group (" + groupTime + "ms)");
			totalTime += groupTime;
		}
		Logger.log(LogType.DEFAULT, totalTestNum + " tests ran. (" + totalTime + "ms)");
	}
}