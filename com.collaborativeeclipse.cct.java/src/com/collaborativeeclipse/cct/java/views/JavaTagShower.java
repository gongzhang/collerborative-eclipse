package com.collaborativeeclipse.cct.java.views;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.swt.widgets.Tree;

import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.internal.views.TagShower;
import com.collaborativeeclipse.cct.java.JavaElementTarget;
import com.collaborativeeclipse.cct.java.analyzer.JavaTagCore;

/**
 * 专用于Java语言的标签视图。
 * @author 张弓
 * @version 1.00
 */
public class JavaTagShower extends TagShower {
	
	@Override
	protected String getTagComment(ITag tag) {
		return tag.getData().split("_:_")[0];
	}
	
	@Override
	protected String getTagKind(ITag tag) {
		return tag.getData().split("_:_")[1];
	}
	
	@Override
	protected String getTargetName(ITag tag) {
		return "i dont know, sorry"; // TODO Li Qiang, complete it.
	}
	
	@Override
	protected void refreshRelatedTags(ITag[] tags, Tree tree) {
		
	}

}
