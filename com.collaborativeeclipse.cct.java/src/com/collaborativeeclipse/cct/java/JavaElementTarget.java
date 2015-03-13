package com.collaborativeeclipse.cct.java;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;

import com.collaborativeeclipse.cct.ITarget;

public class JavaElementTarget implements ITarget{

	public static String PREFIX = "JAVAELEMENT@";
	
	protected IJavaElement fJavaElement;
	
	public static boolean isJavaElementTarget(ITarget target){
		return target.getHandleIdentifier().startsWith(PREFIX);
	}
	
	public IJavaElement getJavaElement() {
		return fJavaElement;
	}

	public void setJavaElement(IJavaElement javaElement) {
		this.fJavaElement = javaElement;
	}

	public JavaElementTarget(String identifier){
		this.fJavaElement = JavaCore.create(identifier.substring(PREFIX.length()));
	}
	
	public JavaElementTarget(IJavaElement inner){
		this.fJavaElement = inner;
	}
	
	@Override
	public String getHandleIdentifier() {
		return PREFIX + fJavaElement.getHandleIdentifier();
	}

	@Override
	public boolean equals(Object target){
		if(target instanceof JavaElementTarget){
			return ((JavaElementTarget)target).getHandleIdentifier().equals(getHandleIdentifier());
		}else
			return false;
		
	}
	
	@Override
	public String toString() {
		return this.getHandleIdentifier();
	}

}
