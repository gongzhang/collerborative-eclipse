package com.collaborativeeclipse.cct.tagdata;

import com.collaborativeeclipse.cct.ITarget;

public interface ITargetDAO {

	void addTarget(ITarget target);

	boolean hasTarget(String handleIdentifier);

	void removeByIndentifier(String handleIdentifier);

}
