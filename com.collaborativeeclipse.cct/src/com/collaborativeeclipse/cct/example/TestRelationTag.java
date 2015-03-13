package com.collaborativeeclipse.cct.example;

import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.ITarget;

public class TestRelationTag implements ITag {

	ITarget target;
	ITarget relativeTarget;
	
	public TestRelationTag(ITarget target) {
		this.target = target;
	}

	
	public void setRelativeTarget(ITarget relativeTarget) {
		this.relativeTarget = relativeTarget;
	}

	@Override
	public String getData() {
		return relativeTarget.getHandleIdentifier();
	}

	@Override
	public ITarget getTarget() {
		return target;
	}

}
