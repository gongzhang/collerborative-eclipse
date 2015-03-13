package com.collaborativeeclipse.cct.example;

import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.ITarget;

public class TestTag implements ITag {
	private String string;
	private ITarget target;
	
	public TestTag(ITarget target){
		this.target = target;
	}
	
	@Override
	public String getData() {
		return string;
	}

	@Override
	public ITarget getTarget() {
		return target;
	}

	public void setData(String data){
		this.string = data;
	}
}
