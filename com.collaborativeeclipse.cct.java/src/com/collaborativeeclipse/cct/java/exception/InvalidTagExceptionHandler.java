package com.collaborativeeclipse.cct.java.exception;

import org.eclipse.jdt.core.dom.TagElement;

public class InvalidTagExceptionHandler implements ITagExceptionHandler {
	private TagElement fErrorNode = null;
	
	public InvalidTagExceptionHandler(TagElement node) {
		fErrorNode = node;
	}
	@Override
	public void handleException() {
		// TODO Auto-generated method stub
		System.out.println("Invalid Tag:" + fErrorNode);
	}

}
