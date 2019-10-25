package org.zerock.service;

import java.util.ArrayList;
import java.util.List;

public enum SampleService {

	INSTANCE;
	
	private List<String> list;
	
	SampleService() {
		list = new ArrayList<>();
	}
	
	public void add(String str) {
		list.add(str);
	}
	
	public List<String> getList(){
		
		return list;
	}
}
