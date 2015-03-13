package com.collaborativeeclipse.cct.example.extension;

import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.extension.ITagAddListener;

public class TagAddMonitor implements ITagAddListener {

	@Override
	public void addTag(ITag tag) throws Exception {
		System.out.println("Monitor:tag"+tag+"added!");
		
	}


}
