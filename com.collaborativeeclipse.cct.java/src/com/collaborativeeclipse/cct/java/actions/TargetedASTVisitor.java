package com.collaborativeeclipse.cct.java.actions;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;

public abstract class TargetedASTVisitor extends ASTVisitor {

	private int offset;
	private AddTagAction editorAction;

	public TargetedASTVisitor(int offset, AddTagAction action) {
		super();
		this.offset = offset;
		editorAction = action;
	}

	protected AddTagAction getOwner() {
		return editorAction;
	}

	protected void visitNull(ASTNode node) {
		if (offset >= node.getStartPosition()
				&& offset <= node.getStartPosition() + node.getLength()) {
			this.getOwner().setTargetNode(null);
			this.getOwner().setTargetJavaElement(null);
		}
	}

	protected void visitSimpleName(SimpleName node) {
		if (offset >= node.getStartPosition()
				&& offset <= node.getStartPosition() + node.getLength()) {
			this.getOwner().setTargetNode(node);
			IBinding binding = node.resolveBinding();
			IJavaElement javaElement = binding.getJavaElement();
			if (binding instanceof IVariableBinding
					&& javaElement.getElementType() == IJavaElement.LOCAL_VARIABLE) {
				IVariableBinding variableBinding = (IVariableBinding) binding;
				this.getOwner().setLocalVariableId(
						variableBinding.getVariableId());
//				System.out.println(variableBinding.getVariableId());
			}
			else
				this.getOwner().setLocalVariableId(-1);
			this.getOwner().setTargetJavaElement(javaElement);
		}
	}

	protected void visitReturnStatement(ReturnStatement node) {
		if (offset >= node.getStartPosition()
				&& offset <= node.getStartPosition() + node.getLength()) {
			ASTNode tempnode = node.getParent();
			while (!(tempnode instanceof MethodDeclaration))
				tempnode = tempnode.getParent();
			SimpleName name = ((MethodDeclaration) tempnode).getName();
			this.getOwner().setTargetNode(name);
			this.getOwner().setTargetJavaElement(
					name.resolveBinding().getJavaElement());
		}
	}
}
