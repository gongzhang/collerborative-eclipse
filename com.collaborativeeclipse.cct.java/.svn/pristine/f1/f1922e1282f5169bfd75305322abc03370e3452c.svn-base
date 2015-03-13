package com.collaborativeeclipse.cct.java.actions;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.ASTVisitor;

public abstract class FinderASTVisitor extends ASTVisitor {

	protected String typeString = null;
	protected String nameString = null;
	// if find element, finderCounter = 1, else = 0
	protected boolean hasFound = false;

	public FinderASTVisitor(String[] params) {
		typeString = params[0];
		nameString = params[1];
	}
}
