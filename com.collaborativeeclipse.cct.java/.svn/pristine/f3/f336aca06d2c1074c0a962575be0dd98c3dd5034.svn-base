package com.collaborativeeclipse.cct.java.exception;

import org.eclipse.jdt.core.dom.TagElement;

public class IllegalTagExceptionHandler implements ITagExceptionHandler {
	private TagElement fErrorNode = null;
	
	public IllegalTagExceptionHandler(TagElement node) {
		fErrorNode = node;
	}
	@Override
	public void handleException() {
		// TODO Auto-generated method stub
		System.out.println("Illegal Tag:" + fErrorNode);
	}

}
