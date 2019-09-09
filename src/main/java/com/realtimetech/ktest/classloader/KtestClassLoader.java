package com.realtimetech.ktest.classloader;

import java.net.URL;
import java.net.URLClassLoader;

public class KtestClassLoader extends URLClassLoader{

	public KtestClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}
}
