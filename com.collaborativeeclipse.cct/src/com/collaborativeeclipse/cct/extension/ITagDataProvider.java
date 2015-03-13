package com.collaborativeeclipse.cct.extension;

import java.util.List;

import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.ITarget;

public interface ITagDataProvider {
	List<ITag> getTagsFromTarget(ITarget target);
}
