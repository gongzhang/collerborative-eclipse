package com.collaborativeeclipse.cct.java.analyzer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;

import com.collaborativeeclipse.cct.ITarget;
import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.extension.ITagAddListener;
import com.collaborativeeclipse.cct.java.ASTNodeTarget;
import com.collaborativeeclipse.cct.java.CommentTag;
import com.collaborativeeclipse.cct.java.JavaElementTarget;
import com.collaborativeeclipse.cct.java.RelationTag;

public class RelationTagAdder implements ITagAddListener {

	public static List<ITarget> done = new ArrayList<ITarget>();
	
	public static void reset(){
		done.clear();
	}
	
	@Override
	public void addTag(ITag tag) {
		System.out.println(tag);
		if (tag instanceof CommentTag && !done.contains(tag.getTarget())) {
			if(JavaElementTarget.isJavaElementTarget(tag.getTarget()) ){
				JavaElementTarget target = new JavaElementTarget(tag.getTarget().getHandleIdentifier());
				IJavaElement element = target.getJavaElement();
				
				if(element instanceof IType){
					try {
						for(IMethod method : ((IType) element).getMethods()){
							JavaElementTarget relativetarget = new JavaElementTarget(method);

							RelationTag rtag = new RelationTag();
							rtag.setTarget(tag.getTarget());
							rtag.setRelativeTarget(relativetarget.getHandleIdentifier());
							rtag.setKind("methods");
						}
					} catch (JavaModelException e) {
						assert(false);
					}
				}
			} else if(ASTNodeTarget.isASTNodeTarget(tag.getTarget())){
				//TODO
			}
		}

	}

}
