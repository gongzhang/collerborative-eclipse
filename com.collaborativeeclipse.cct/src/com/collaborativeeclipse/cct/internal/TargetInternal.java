package com.collaborativeeclipse.cct.internal;

import com.collaborativeeclipse.cct.ITarget;

public class TargetInternal implements ITarget {

	private String handleIdentifier;
	
	public void setHandleIdentifier(String handleIndentifier) {
		this.handleIdentifier = handleIdentifier;
	}
	@Override
	public String getHandleIdentifier() {
			return handleIdentifier;
	}

}
