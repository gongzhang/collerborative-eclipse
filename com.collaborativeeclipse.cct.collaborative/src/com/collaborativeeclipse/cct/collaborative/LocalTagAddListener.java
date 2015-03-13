package com.collaborativeeclipse.cct.collaborative;

import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.extension.ITagAddListener;
import com.collaborativeeclipse.cct.util.TagPaser;
import com.collaborativeeclipse.im.DisplayManager;
import com.collaborativeeclipse.im.IMCore;

public class LocalTagAddListener implements ITagAddListener {

	@Override
	public void addTag(ITag tag) {
		TagPaser paser = new TagPaser();
		DisplayManager.getDisplayManager().input(
				SymbolConsts.TAGSIGNAL + paser.createXml(tag)
						+ IMCore.MESSAGESTARTSIGNAL + "tag:" + tag.getData()
						+ " is added to "
						+ tag.getTarget().getHandleIdentifier());

	}

}
