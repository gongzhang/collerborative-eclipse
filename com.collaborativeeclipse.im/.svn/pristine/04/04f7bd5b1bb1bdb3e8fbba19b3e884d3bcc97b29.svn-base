package com.collaborativeeclipse.im.views;


import com.collaborativeeclipse.im.DisplayManager;
import com.collaborativeeclipse.im.IMCore;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class ChatWindow extends ViewPart {

	private Action loginAction;

//	 private Action chatAction;

	 private Action logoutAction;

//	 private Action saveAction;

//	private Action addListenerAction;
//
	private Action testAction;

	private Composite parent = null;

	private Text area;

	private Text input;

	private Label label;

	private ISelectionListener selectionListener;

	/**
	 * The constructor.
	 */
	public ChatWindow() {

		selectionListener = new ISelectionListener() {
			public void selectionChanged(IWorkbenchPart part,
					ISelection selection) {

				// skip wrong type selection
				if (!part.getTitle().equalsIgnoreCase("OutLine")) {
					return;
				}
				if (!(selection instanceof TreeSelection)) {
					return;
				}


			}
		};

		IWorkbenchWindow[] workbenchWindows = PlatformUI.getWorkbench()
				.getWorkbenchWindows();
		for (int i = 0; i < workbenchWindows.length; i++) {
			IWorkbenchWindow workbenchWindow = workbenchWindows[i];
			IWorkbenchPage[] pages = workbenchWindow.getPages();

			for (int j = 0; j < pages.length; j++) {
				pages[j].addSelectionListener(selectionListener);
			}
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		this.parent = parent;
		DisplayManager.getDisplayManager().setComposite(parent);

		parent.setLayout(new FillLayout(SWT.VERTICAL));

		label = new Label(parent, SWT.BORDER);
		area = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
				| SWT.BORDER);
		input = new Text(parent, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL
				| SWT.BORDER);

		Console.setConsole(area);
		makeActions();
		hookContextMenu();
		hookKeyListener();
		hookLabel();
		contributeToActionBars();
	}

	private void hookLabel() {
		LabelInfo.getLabelInfo().setLabel(label);
		DisplayManager.getDisplayManager().setArea(area);
	}

	private void hookKeyListener() {
		input.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if (e.keyCode == SWT.CR) {

					String str = input.getText();
					if (str.length() == 0) {
						return;
					}

					DisplayManager.getDisplayManager().input(str);
					input.setText("");
				}
			}

		});
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ChatWindow.this.fillContextMenu(manager);
			}
		});
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(loginAction);
//		 manager.add(chatAction);
		 manager.add(logoutAction);
//		 manager.add(saveAction);
//		manager.add(addListenerAction);
		manager.add(testAction);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(loginAction);
//		 manager.add(chatAction);
		 manager.add(logoutAction);
//		 manager.add(saveAction);
//		manager.add(addListenerAction);
		manager.add(testAction);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(loginAction);
//		 manager.add(chatAction);
		 manager.add(logoutAction);
//		 manager.add(saveAction);
//		manager.add(addListenerAction);
		manager.add(testAction);
	}

	private void makeActions() {

		loginAction = new Action() {
			public void run() {
				login();
			}
		};
		loginAction.setText("Login");
		loginAction.setToolTipText("Login to XMPP server.");
		loginAction.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_OBJ_FILE));
//
//		 chatAction = new Action() {
//		 public void run() {
////		 DisplayManager.getDisplayManager().setElement(null);
//		 }
//		 };
//		 chatAction.setText("Chat");
//		 chatAction.setToolTipText("Chat.");
//		 chatAction.setImageDescriptor(PlatformUI.getWorkbench()
//		 .getSharedImages().getImageDescriptor(
//		 ISharedImages.IMG_OBJ_FILE));

		 logoutAction = new Action() {
		 public void run() {
		 IMCore.getCore().logout();
		 }
		 };
	 logoutAction.setText("Logout");
		 logoutAction.setToolTipText("Logout.");
		 logoutAction.setImageDescriptor(PlatformUI.getWorkbench()
		 .getSharedImages().getImageDescriptor(
	 ISharedImages.IMG_OBJ_FILE));
//
//		 saveAction = new Action() {
//		 public void run() {
//		 DisplayManager.getDisplayManager().saveLast();
//		 }
//		 };
//		 saveAction.setText("Save");
//		 saveAction.setToolTipText("Save.");
//		 saveAction.setImageDescriptor(PlatformUI.getWorkbench()
//		 .getSharedImages().getImageDescriptor(
//		 ISharedImages.IMG_OBJ_FILE));

//		addListenerAction = new Action() {
//			public void run() {
//				IWorkbenchWindow[] workbenchWindows = PlatformUI.getWorkbench()
//						.getWorkbenchWindows();
//				for (int i = 0; i < workbenchWindows.length; i++) {
//					IWorkbenchWindow workbenchWindow = workbenchWindows[i];
//					IWorkbenchPage[] pages = workbenchWindow.getPages();
//
//					for (int j = 0; j < pages.length; j++) {
//						pages[j].addSelectionListener(selectionListener);
//					}
//				}
//			}
//		};
//		addListenerAction.setText("Add listener");
//		addListenerAction.setToolTipText("Add listener.");
//		addListenerAction.setImageDescriptor(PlatformUI.getWorkbench()
//				.getSharedImages().getImageDescriptor(
//						ISharedImages.IMG_OBJ_FILE));

		testAction = new Action() {
			public void run() {
				test();
			}
		};
		testAction.setText("Select user");
		testAction.setToolTipText("select.");
		testAction.setImageDescriptor(PlatformUI.getWorkbench()
				.getSharedImages().getImageDescriptor(
						ISharedImages.IMG_OBJS_INFO_TSK));
	}

	private void test() {
		final String[] names = IMCore.getCore().getBuddies();
		if (names == null || names.length == 0) {
			MessageBox box = new MessageBox(parent.getShell());
			box.setMessage("Can't find valid buddy");
			box.open();
		} else {
			// StringBuffer buffer = new StringBuffer("");
			// for (int i = 0; i < names.length; i++) {
			// buffer.append(names[i] + '\n');
			// }
			// InputDialog input = new InputDialog(parent.getShell(),
			// "Select buddy", "Vaild buddies:\n" + buffer.toString(), "",
			// null);
			// if (input.open() != Window.OK) {
			// return;
			// }
			//
			// String buddy = input.getValue();
			// IMCore.getCore().selectBuddy(buddy);

			// IStructuredContentProvider sp = new IStructuredContentProvider()
			// {
			//
			// public Object[] getElements(Object inputElement) {
			// return names;
			// }
			//
			// public void dispose() {
			//
			// }
			//
			// public void inputChanged(Viewer viewer, Object oldInput,
			// Object newInput) {
			//
			// }
			//
			// };
			//
			// ListDialog dialog = new ListDialog(parent.getShell());
			// dialog.setTitle("title");
			// dialog.setInput(null);
			// dialog.setContentProvider(sp);
			// dialog.open();
			// String[] selects = (String[]) dialog.getResult();
			// if (selects.length == 1) {
			// IMCore.getCore().selectBuddy(selects[0]);
			// }

			TitleAreaDialog dialog = new TitleAreaDialog(parent.getShell()) {
				String user = null;

				Combo list;

				public Control createDialogArea(Composite composite) {
					this.setTitle("Select user");
					Label label;
					GridData gridData;

					Composite area = (Composite) super
							.createDialogArea(composite);

					GridLayout layout = new GridLayout();
					layout.numColumns = 2;
					area.setLayout(layout);

					label = new Label(area, SWT.LEFT);
					label
							.setText("Select the buddy you talk to in the combo box");
					gridData = new GridData();
					gridData.horizontalSpan = 2;
					label.setLayoutData(gridData);

					label = new Label(area, SWT.LEFT);
					label.setText("name:");
					list = new Combo(area, SWT.READ_ONLY);
					list.setItems(names);
					gridData = new GridData();
					gridData.horizontalAlignment = GridData.FILL;
					gridData.grabExcessHorizontalSpace = true;
					list.setLayoutData(gridData);

					list.addModifyListener(new ModifyListener() {

						public void modifyText(ModifyEvent e) {
							user = list.getText();
						}

					});

					return area;
				}

				public void okPressed() {
					if (user != null && user.trim().length() > 0) {
						IMCore.getCore().selectBuddy(user);
					}

					super.okPressed();
				}
			};
			dialog.open();
		}

	}

	// private void logout() {
	// IMCore.getCore().logout();
	// }

	private void login() {
		// String user = IMCore.getCore().getUser();
		// if (user != null) {
		// MessageBox box = new MessageBox(parent.getShell());
		// box.setText("logined");
		// box.setMessage("Logined as " + user);
		// box.open();
		// return;
		// }
		//
		// InputDialog input = new InputDialog(parent.getShell(),
		// "Input username", "Please input username:",
		// "mudertest@gmail.com", null);
		// if (input.open() != Window.OK) {
		// return;
		// }
		// String username = input.getValue();
		// input = new InputDialog(parent.getShell(), "Input password",
		// "Please input password:", "mudertest123", null);
		// if (input.open() != Window.OK) {
		// return;
		// }
		// String password = input.getValue();
		//
		// IMCore.getCore().login(username, password);

		TitleAreaDialog dialog = new TitleAreaDialog(parent.getShell()) {

			Text text;

			Text passwordText;

			public Control createDialogArea(Composite composite) {
				this.setTitle("Input account");

				Composite area = (Composite) super.createDialogArea(composite);

				GridLayout layout = new GridLayout();
				layout.numColumns = 2;
				area.setLayout(layout);

				Label label = new Label(area, SWT.LEFT);
				label.setText("Enter your username and password");
				GridData gridData = new GridData();
				gridData.horizontalSpan = 2;
				label.setLayoutData(gridData);

				label = new Label(area, SWT.LEFT);
				label.setText("Username:");
				text = new Text(area, SWT.SINGLE | SWT.BORDER);
				text.setText("mudertest@gmail.com");
				gridData = new GridData();
				gridData.horizontalAlignment = GridData.FILL;
				gridData.grabExcessHorizontalSpace = true;
				text.setLayoutData(gridData);

				label = new Label(area, SWT.LEFT);
				label.setText("Password:");
				passwordText = new Text(area, SWT.SINGLE | SWT.BORDER
						| SWT.PASSWORD);
				passwordText.setText("mudertest123");
				gridData = new GridData();
				gridData.horizontalAlignment = GridData.FILL;
				gridData.grabExcessHorizontalSpace = true;
				passwordText.setLayoutData(gridData);

				return area;
			}

			public void okPressed() {
				String username = text.getText();
				String password = passwordText.getText();

				if (username == null || username.trim().length() == 0) {
					return;
				} else if (password == null || password.trim().length() == 0) {
					return;
				}

				Controler.getControler().setUsername(username);
				Controler.getControler().setPassword(password);

				super.okPressed();
			}

		};
		dialog.open();

		Controler.getControler().login();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
}