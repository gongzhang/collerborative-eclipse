package com.collaborativeeclipse.cct.java.actions;

import org.eclipse.jdt.core.ICodeAssist;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.IWorkingCopyManager;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.text.java.hover.IJavaEditorTextHover;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
/**
 * hover激活机制十分令人费解...
 * javadoc的显示也不是利用hover实现的
 * @deprecated
 */
public class ShowTagTextHover implements IJavaEditorTextHover {

	private IEditorPart currentEditor = null;
	
	@Override
	public void setEditor(IEditorPart editor) {
		// TODO Auto-generated method stub
		currentEditor = editor;
	}

	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		// TODO Auto-generated method stub
		//System.out.println("get!");
//		ICodeAssist resolve = getCodeAssist();
//		if (resolve != null) {
//			try {
//				IJavaElement[] result= null;
//				
//				synchronized (resolve) {
//					result= resolve.codeSelect(hoverRegion.getOffset(), hoverRegion.getLength());
//				}
//				
//				if (result == null)
//					return null;
//				
//				int nResults= result.length;	
//				if (nResults == 0)
//					return null;
//				System.out.println(result[0]);
//				return result[0].getElementName();
//				
//			} catch (JavaModelException x) {
//				return null;
//			}
//		}
//		return null;
		return null;
	}

	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	protected ICodeAssist getCodeAssist() {
//		if (currentEditor != null) {
//			IEditorInput input= currentEditor.getEditorInput();
////			if (input instanceof IClassFileEditorInput) {
////				IClassFileEditorInput cfeInput= (IClassFileEditorInput) input;
////				return cfeInput.getClassFile();
////			}
//			
//			IWorkingCopyManager manager= JavaUI.getWorkingCopyManager();				
//			return manager.getWorkingCopy(input);
//		}
//		
//		return null;
//	}

}
