package com.collaborativeeclipse.cct.java.exception;

import org.eclipse.jdt.core.dom.TagElement;

public class InvalidTagException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4813872717400711408L;
	
	private TagElement fErrorNode = null;
	
	private ITagExceptionHandler fExceptionHandler = null;
	
	public InvalidTagException(TagElement node) {
		fErrorNode = node;
		fExceptionHandler = new InvalidTagExceptionHandler(fErrorNode);
	}
	
	public void handle(){
		if (fExceptionHandler == null) {
			return;
		}
		else fExceptionHandler.handleException();
	}
}
