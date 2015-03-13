package com.collaborativeeclipse.cct.java.actions;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ITypeRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

import com.collaborativeeclipse.cct.java.LocalVariableTarget;
import com.collaborativeeclipse.cct.java.analyzer.JavaTagCore;
import com.collaborativeeclipse.cct.java.dialogs.AddTagDialog;

public class AddTagAction implements IEditorActionDelegate {

	private ITextEditor fTextEditor = null;
	private ASTNode fTargetNode = null;
	private IJavaElement fTargetJavaElement = null;
	private String fTagContent = "";
	private String fTagKind = "default";
	private IDocument fDocument = null;
	private ITypeRoot fTypeRoot = null;
	private StyledText fStyledText = null;
	private ScannerCaretListener fCaretListener = null;
	private int fLocalVariableId = -1;
	private ListenerSyncBlock fListenerSyncBlock = null;

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		IEditorPart currentEditor = targetEditor;
		IEditorInput input = currentEditor.getEditorInput();
		fTypeRoot = JavaUI.getEditorInputTypeRoot(input);
		if (currentEditor instanceof ITextEditor && fTypeRoot != null) {
			fTextEditor = (ITextEditor) currentEditor;
			fDocument = fTextEditor.getDocumentProvider().getDocument(input);
			fStyledText = (StyledText) fTextEditor.getAdapter(Control.class);
			fCaretListener = new ScannerCaretListener(fTypeRoot, this);
			fListenerSyncBlock = new ListenerSyncBlock();
			Display.getDefault().asyncExec(fListenerSyncBlock);
		}
	}

	@Override
	public void run(IAction action) {
		Display.getDefault().asyncExec(fListenerSyncBlock);
		int offset = ((ITextSelection) fTextEditor.getSelectionProvider().getSelection()).getOffset();
		CompilationUnit compilationUnit = (CompilationUnit) createASTParser(
				fTypeRoot).createAST(null);
		compilationUnit.recordModifications();
		JavaTagCore.getTargetByCursorOffset(compilationUnit, offset, this);

		AddTagDialog dialog = new AddTagDialog(fTextEditor.getSite()
				.getShell(), this);
		dialog.setTargetJavaElement(fTargetJavaElement);
		dialog.setTargetASTNode(fTargetNode);
		dialog.open();
		// TODO modify the abstract symbol tree

		if (!"".equals(fTagContent) && fTagContent != null) {
			JavaTagCore.addTag(fTargetJavaElement, fTagKind, fTagContent, fLocalVariableId);
			addTagToJavadoc(compilationUnit);
		} else
			System.out.println("null!");
		
		rewriteToBuffer(compilationUnit);
		Display.getDefault().asyncExec(fListenerSyncBlock);
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

	public void setTargetNode(ASTNode node) {
		fTargetNode = node;
	}

	public void setTargetJavaElement(IJavaElement element) {
		fTargetJavaElement = element;
	}

	public void setTagContent(String content) {
		fTagContent = content;
	}

	public void setTagKind(String kind) {
		fTagKind = kind;
	}

	public IJavaElement getTargetJavaElement() {
		return fTargetJavaElement;
	}

	public void setLocalVariableId(int id) {
		if (id < 0)
			fLocalVariableId = -1;
		else
			fLocalVariableId = id;
	}
	
	public int getLocalVariableId() {
		return fLocalVariableId;
	}
	
	private ASTParser createASTParser(ITypeRoot typeRoot) {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(typeRoot);
		parser.setResolveBindings(true);
		return parser;
	}

	private BodyDeclaration getBodyDeclaration(ASTNode node) {
		ASTNode parentNode = fTargetNode.getParent();
		while (!(parentNode instanceof BodyDeclaration))
			parentNode = parentNode.getParent();
		if (parentNode instanceof BodyDeclaration) {
			return (BodyDeclaration) parentNode;
		} else
			return null;
	}

	private void addTagToJavadoc(CompilationUnit compilationUnit) {
		BodyDeclaration body = getBodyDeclaration(fTargetNode);
		if (body != null) {
			Javadoc jdoc = body.getJavadoc();
			TagElement tag = compilationUnit.getAST().newTagElement();
			tag.setTagName(JavaTagCore.TAG_TAG);
			TextElement text = tag.getAST().newTextElement();
			text.setText(JavaTagCore.getFormatTagContent(
					fTargetJavaElement, fTagKind, fTagContent));
			tag.fragments().add(text);
			if (jdoc != null) {
				jdoc.tags().add(tag);
			} else {
				jdoc = compilationUnit.getAST().newJavadoc();
				jdoc.tags().add(tag);
				body.setJavadoc(jdoc);
			}
		}
	}
	
	private void rewriteToBuffer(CompilationUnit compilationUnit) {
		ICompilationUnit cu = (ICompilationUnit) compilationUnit
				.getJavaElement();
		TextEdit edit = compilationUnit.rewrite(fDocument, fTypeRoot
				.getJavaProject().getOptions(true));
		try {
			edit.apply(fDocument);
		} catch (MalformedTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String newSource = fDocument.get();
		try {
			cu.getBuffer().setContents(newSource);
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class ScannerCaretListener implements CaretListener {
		ITypeRoot typeRoot = null;
		AddTagAction action = null;

		public ScannerCaretListener(ITypeRoot root, AddTagAction owner) {
			typeRoot = root;
			action = owner;
		}

		@Override
		public void caretMoved(CaretEvent event) {
			Display.getDefault().asyncExec(new ScannerSyncBlock());
		}
		private class ScannerSyncBlock implements Runnable {
			@Override
			public void run() {
				CompilationUnit compilationUnit = (CompilationUnit) createASTParser(
						typeRoot).createAST(null);
				int offset = ((ITextSelection) fTextEditor.getSelectionProvider().getSelection()).getOffset();
				JavaTagCore.getTargetByCursorOffset(compilationUnit, offset, action);
				JavaTagCore.showTagInfo(action.getTargetJavaElement(), action
						.getLocalVariableId());
			}
		}
	}
	
	private class ListenerSyncBlock implements Runnable {
		private boolean fHasAddListener = false;
		@Override
		public void run() {
			if (fCaretListener != null) {
				if (!fHasAddListener) {
					fStyledText.addCaretListener(fCaretListener);
					fHasAddListener = true;
				}
				else {
					fStyledText.removeCaretListener(fCaretListener);
					fHasAddListener = false;
				}
			}
		}
	}
}
