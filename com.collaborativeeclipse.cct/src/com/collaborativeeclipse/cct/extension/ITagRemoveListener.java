package com.collaborativeeclipse.cct.extension;

import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.ITarget;

public interface ITagRemoveListener {

		void removeTag(ITag tag) throws Exception;
		void removeTarget(ITarget target) throws Exception;


}
