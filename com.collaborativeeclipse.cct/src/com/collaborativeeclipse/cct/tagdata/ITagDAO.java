package com.collaborativeeclipse.cct.tagdata;

import java.util.List;

import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.ITarget;

public interface ITagDAO {

	void addTag(ITag tag);

	List<ITag> getTagsByTarget(ITarget target);

	void remove(ITag tag);

	void removeByTarget(ITarget target);

	boolean hasTag(ITag tag);

}
