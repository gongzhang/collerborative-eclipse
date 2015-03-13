package com.collaborativeeclipse.cct.java;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;

import com.collaborativeeclipse.cct.ITarget;

public class LocalVariableTarget implements ITarget {

	public static String PREFIX = "LOCALVARIABLE@";

	protected IJavaElement fParentMethod;
	protected int fID = -1;

	public LocalVariableTarget(IJavaElement inner, int id) {
		this.fParentMethod = inner;
		this.fID = id;
	}

	public LocalVariableTarget(String identifier) {
		this.fParentMethod = JavaCore.create(identifier.substring(identifier
				.indexOf('#') + 1));
		this.fID = Integer.parseInt(identifier.substring(PREFIX.length())
				.split("#", 2)[0]);
	}

	public static boolean isLocalVariableTarget(ITarget target) {
		return target.getHandleIdentifier().startsWith(PREFIX);
	}

	@Override
	public String getHandleIdentifier() {
		return PREFIX + getIDFormatString()
				+ fParentMethod.getHandleIdentifier();
	}

	public IJavaElement getParentMethod() {
		return fParentMethod;
	}
	
	public void setParentMethod(IJavaElement javaElement) {
		this.fParentMethod = javaElement;
	}
	
	private String getIDFormatString() {
		return fID + "#";
	}
	
	@Override
	public String toString() {
		return this.getHandleIdentifier();
	}

}
