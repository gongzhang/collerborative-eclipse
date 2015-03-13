package com.collaborativeeclipse.cct.java.analyzer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;

import com.collaborativeeclipse.cct.CodeTagCore;
import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.ITarget;
import com.collaborativeeclipse.cct.java.CommentTag;
import com.collaborativeeclipse.cct.java.JavaElementTarget;
import com.collaborativeeclipse.cct.java.LocalVariableTarget;
import com.collaborativeeclipse.cct.java.TagsData;
import com.collaborativeeclipse.cct.java.actions.AddTagAction;
import com.collaborativeeclipse.cct.java.actions.FinderASTVisitor;
import com.collaborativeeclipse.cct.java.actions.TargetedASTVisitor;
import com.collaborativeeclipse.cct.java.exception.IllegalTagException;
import com.collaborativeeclipse.cct.java.exception.InvalidTagException;

public final class JavaTagCore {
	public static final String TAG_TAG = "@tag";
	
	private static IBinding fTempTargetBinding = null;
	private static boolean fFindResult;
	
	public static void addTag(IJavaElement targetJavaElement, String kind, String comment, int variableID) {
		ITarget target = null;
		if (variableID == -1)
			target = new JavaElementTarget(targetJavaElement);
		else {
			IMethod method = (IMethod) targetJavaElement.getAncestor(IJavaElement.METHOD);
			target = new LocalVariableTarget(method, variableID);
		}
		CommentTag ct = new CommentTag();
		ct.setTarget(target);
		ct.setComment(comment);
		ct.setKind(kind);
		CodeTagCore.getInstance().addTag(ct);
	}
	
	public static String getFormatTagContent(IJavaElement targetJavaElement, String tagKind, String tagContent) {
		return "[" + getTargetType(targetJavaElement.getElementType()) + "]" +
		targetJavaElement.getElementName() + "(" + tagKind + ") : " + tagContent;
	}
	
	public static void refreshAllTags() {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projectsList = workspaceRoot.getProjects();
		for (IProject project : projectsList) {
			IJavaProject javaProject = JavaCore.create(project);
			System.out.println("[Project] " + javaProject.getElementName());
			JavaTagCore.visitAllFiles(javaProject);
		}
		// TODO clear DataBase
	}
	
	public static void visitAllFiles(IJavaProject project) {
		try {
			IPackageFragmentRoot[] packageRoots = project
					.getPackageFragmentRoots();
			for (IPackageFragmentRoot packageRoot : packageRoots) {
				if (packageRoot.getKind() == IPackageFragmentRoot.K_SOURCE) {
					IJavaElement[] javaElements = packageRoot.getChildren();
					for (IJavaElement javaElement : javaElements) {
						IPackageFragment packageFragment = (IPackageFragment) javaElement;
						ICompilationUnit[] compilationUnits = packageFragment
								.getCompilationUnits();
						for (ICompilationUnit compilationUnit : compilationUnits) {
							System.out.println(compilationUnit.getPath());
							visitCompilationUnit(compilationUnit);
						}
					}
				}
			}
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void showTagInfo(IJavaElement targetJavaElement, int variableId) {
		if (targetJavaElement == null)
			return;
		ITarget target = null;
		if (variableId == -1) {
			target = new JavaElementTarget(targetJavaElement);
		}
		else if (targetJavaElement.getElementType() == IJavaElement.LOCAL_VARIABLE) {
			IMethod method = (IMethod) targetJavaElement
					.getAncestor(IJavaElement.METHOD);
			target = new LocalVariableTarget(method, variableId);
		}
		else return;
		List<ITag> targetTags = CodeTagCore.getInstance().getTagsFromTarget(target);
		System.out.println("The Tags of " + targetJavaElement.getElementName() + ":");
		for (ITag targetTag : targetTags) {
			CommentTag ct = new CommentTag(targetTag);
			System.out.println("("+ct.getKind()+")"+ct.getComment());
		}
		CodeTagCore.getInstance().showTag(new TagsData(targetTags));
	}
	
	public static void getTargetByCursorOffset(CompilationUnit compilationUnit,
			int offset, AddTagAction action) {
		compilationUnit.accept(new TargetedASTVisitor(offset, action) {
			@Override
			public boolean visit(SimpleName node) {
				visitSimpleName(node);
				return true;
			}

			@Override
			public boolean visit(ReturnStatement node) {
				visitReturnStatement(node);
				return true;
			}
			
			@Override
			public void preVisit(ASTNode node) {
				visitNull(node);
			}
		});
	}
	private static void visitCompilationUnit(ICompilationUnit compilationUnit) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(compilationUnit);
		parser.setResolveBindings(true);
		CompilationUnit unit = (CompilationUnit) parser.createAST(null);
		unit.accept(new ASTVisitor() {
			@SuppressWarnings("unchecked")
			public boolean visit(Javadoc node) {
				List<TagElement> tags = (List<TagElement>) node.tags();
				//Get declaration node
				ASTNode parentNode = node.getParent();
				for (TagElement tag : tags) {
					if (TAG_TAG.equals(tag.getTagName())) {
						try {
							verifyTag(tag, parentNode);
						} catch (IllegalTagException e) {
							e.handle();
						} catch (InvalidTagException e) {
							e.handle();
						}
					}
				}
				return false;
			}
		});
	}
	
	private static void verifyTag(TagElement tag, ASTNode parentNode) throws IllegalTagException, InvalidTagException {
		List texts = tag.fragments();
		String tagText = "";
		for (Object text : texts) {
			if (text instanceof TextElement) {
				tagText += ((TextElement) text).getText();
			}
		}
		String[] params = parserTag(tagText, tag);
		int variableId = -1;
		IBinding binding = findTarget(parentNode, params, tag);
		IJavaElement javaElement = binding.getJavaElement();
		if (binding instanceof IVariableBinding 
				&& javaElement.getElementType() == IJavaElement.LOCAL_VARIABLE) {
			IVariableBinding variableBinding = (IVariableBinding) binding;
			variableId = variableBinding.getVariableId();
		}
		TagHandler(javaElement, params, variableId);
	}
	
	private static String[] parserTag(String source, TagElement tag) throws IllegalTagException {
		// 正则: 满足['words']'words'('words') : 'anything'
		// TODO 其他格式, Tag's tag...
		Pattern ptn = Pattern.compile("\\x20\\x5B\\w+\\x5D\\w+\\x28\\w+\\x29\\x20:\\x20.+");
		Matcher mtc = ptn.matcher(source);
		if (mtc.matches()) {
			String[] strs = source.split("\\x29 : ", 2);
			String[] strs2 = strs[0].split("]");
			String[] strs3 = strs2[1].split("\\x28");
			//Element Type, Element Name, Tag Kind, Tag Content
			return new String[] { strs2[0].substring(2), strs3[0], strs3[1], strs[1] };
		} else
			throw new IllegalTagException(tag);
	}
	
	private static IBinding findTarget(ASTNode parentNode, String[] params, TagElement tag) throws InvalidTagException {
		fFindResult = false;
		fTempTargetBinding = null;
		parentNode.accept(new FinderASTVisitor(params) {
			public boolean visit(SimpleName node) {
				if (!hasFound) {
					if (node.getFullyQualifiedName()
							.equals(nameString)) {
						IJavaElement je = node
								.resolveBinding()
								.getJavaElement();
						if (je.getElementType() == getTypeInt(typeString)) {
							hasFound = true;
							fTempTargetBinding = node.resolveBinding();
						}
					}
				}
				return true;
			}
			public void endVisit (MethodDeclaration node) {
				if (hasFound)
					fFindResult = true;
				else
					fFindResult = false;
				System.out.println("Search Finished.");
			}
			public void endVisit (FieldDeclaration node) {
				if (hasFound)
					fFindResult = true;
				else
					fFindResult = false;
				System.out.println("Search Finished.");
			}
		});
		if (!fFindResult)
			throw new InvalidTagException(tag);
		else return fTempTargetBinding;
	}
	
	private static int getTypeInt(String type) {
		if (type.equals("Variable"))
			return IJavaElement.LOCAL_VARIABLE;
		else if (type.equals("Class"))
			return IJavaElement.TYPE;
		else if (type.equals("Method"))
			return IJavaElement.METHOD;
		else if (type.equals("Field"))
			return IJavaElement.FIELD;
		else
			return -1;
	}
	
	public static String getTargetType(int elementType) {
		switch (elementType) {
		case IJavaElement.FIELD:
			return "Field";
		case IJavaElement.METHOD:
			return "Method";
		case IJavaElement.TYPE:
			return "Class";
		case IJavaElement.LOCAL_VARIABLE:
			return "Variable";
		default:
			return "Unknown";
		}
	}
	
	private static void TagHandler(IJavaElement javaElement, String[] tagParams, int variableId) {
		addTag(javaElement, tagParams[2], tagParams[3], variableId);
	}
	
	private static IMethod getAncestorMethod(IJavaElement je) {
		return (IMethod) je.getAncestor(IJavaElement.METHOD);
	}
	
}
