package com.collaborativeeclipse.cct.java.exception;

import org.eclipse.jdt.core.dom.TagElement;

public class IllegalTagException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4642056762777328764L;
	
	private TagElement fErrorNode = null;
	
	private ITagExceptionHandler fExceptionHandler = null;
	
	public IllegalTagException (TagElement node) {
		fErrorNode = node;
		fExceptionHandler = new IllegalTagExceptionHandler(fErrorNode);
	}
	
	public void handle(){
		if (fExceptionHandler == null) {
			return;
		}
		else fExceptionHandler.handleException();
	}
}
