package com.collaborativeeclipse.cct.java.dialogs;

import java.awt.Color;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.collaborativeeclipse.cct.java.actions.AddTagAction;
import com.collaborativeeclipse.cct.java.analyzer.JavaTagCore;
import com.collaborativeeclipse.cct.java.listeners.TransmissionSelectionListener;

public class AddTagDialog extends Dialog {

	private Object result;
	private IJavaElement targetJavaElement = null;
	private ASTNode targetNode = null;
	protected Text txtComment = null;
	protected Text txtKind = null;
	protected Shell shell;
	private AddTagAction owner = null;

	private AddTagDialog(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	private AddTagDialog(Shell parent, int style) {
		super(parent, style);
	}

	public AddTagDialog(Shell parent, AddTagAction action) {
		this(parent);
		owner = action;
	}

	public AddTagDialog(Shell parent, int style, AddTagAction action) {
		this(parent, style);
		owner = action;
	}

	public Object open() {
		// TODO if target is null, throw exception
		Shell parent = getParent();
		shell = new Shell(parent, SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		// TODO this is only a test.
		shell.setText("CCT");
		Button btn = new Button(shell, SWT.NULL);
		btn.setBounds(160, 290, 70, 30);
		btn.setText("OK");
		btn.addSelectionListener(new TransmissionSelectionListener(this) {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				AddTagDialog owner = (AddTagDialog) this.getOwner();
				owner.transmitTag(true);
				closeWindow();
			}

		});
		
		Button btn2 = new Button(shell, SWT.NULL);
		btn2.setBounds(240, 290, 70, 30);
		btn2.setText("Cancel");
		btn2.addSelectionListener(new TransmissionSelectionListener(this){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				AddTagDialog owner = (AddTagDialog) this.getOwner();
				owner.transmitTag(false);
				closeWindow();
			}
			
		});
		Label targetLabel = new Label(shell, SWT.LEFT);
		targetLabel.setText("Target:");
		targetLabel.setBounds(10, 10, 300, 25);
		
		Label lbl = new Label(shell, SWT.LEFT);
		lbl.setBounds(10, 35, 300, 25);
		if (targetJavaElement != null)
			lbl.setText("["
					+ JavaTagCore.getTargetType(targetJavaElement
							.getElementType()) + "] "
					+ targetJavaElement.getElementName());
		else
			lbl.setText("Oh!..NO!!! I'm NULL!");
		
		Label kindLabel = new Label(shell, SWT.LEFT);
		kindLabel.setText("Tag Kind:");
		kindLabel.setBounds(10, 60, 300, 25);
		
		txtKind = new Text(shell, SWT.NONE | SWT.BORDER);
		txtKind.setBounds(10, 85, 300, 25);
		
		Label commentLable = new Label(shell, SWT.LEFT);
		commentLable.setText("Tag Comment:");
		commentLable.setBounds(10, 110, 300, 25);
		txtComment = new Text(shell, SWT.NONE | SWT.BORDER);
		txtComment.setBounds(10, 135, 300, 150);
		
		shell.setBounds(500, 200, 325, 355);
		
		// Your code goes here (widget creation, set result, etc).
		shell.open();
		Display display = parent.getDisplay();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	public void setTargetJavaElement(IJavaElement element) {
		targetJavaElement = element;
	}

	public void setTargetASTNode(ASTNode node) {
		targetNode = node;
	}

	public String getTagContent() {
		if (txtComment != null)
			return txtComment.getText();
		else
			return null;
	}

	public void transmitTag(boolean transmitInfo) {
		if (transmitInfo) {
			this.owner.setTagContent(txtComment.getText());
			this.owner.setTagKind(txtKind.getText());
		}
		else 
		{
			this.owner.setTagContent(null);
			this.owner.setTagKind(null);
		}
	}
	
	private void closeWindow() {
		shell.dispose();
	}
}
