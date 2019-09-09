package com.realtimetech.ktest.filter;

import java.util.LinkedList;
import java.util.List;

import com.realtimetech.ktest.annotation.Test;

public abstract class Filter {
	protected List<String> targets;

	public Filter() {
		this.targets = new LinkedList<String>();
	}

	public void addTarget(String target) {
		if (!targets.contains(target)) {
			this.targets.add(target);
		}
	}

	public abstract boolean filter(Test annotation);
}
