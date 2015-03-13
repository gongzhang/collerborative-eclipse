package com.collaborativeeclipse.cct.tagdata;
import java.util.List;

import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.ITarget;
import com.collaborativeeclipse.cct.exception.TagCoreException;
import com.collaborativeeclipse.cct.extension.ITagAddListener;
import com.collaborativeeclipse.cct.extension.ITagDataProvider;
import com.collaborativeeclipse.cct.extension.ITagRemoveListener;


/**
 * a completive tagdatasystem should implement this interface
 * should care about synchronous
 */
public interface ITagAccess extends ITagAddListener,ITagRemoveListener,ITagDataProvider{
	List<ITag> getTagsFromTarget(ITarget target);
	void addTag(ITag tag) throws TagCoreException;
	void removeTag(ITag tag) throws TagCoreException;
	void removeTarget(ITarget target) throws TagCoreException;
}
