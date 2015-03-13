package com.collaborativeeclipse.cct.internal.views;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.part.ViewPart;

import com.collaborativeeclipse.cct.ITag;
import com.collaborativeeclipse.cct.views.ITagShowListener;
import com.collaborativeeclipse.cct.views.ITagsData;

/**
 * 标签视图。
 * <p>
 * 将当前语法单元的相关标签分类显示在列表中。
 * 当前编辑器中光标位置改变时，{@code TagShow}视图将刷新显示内容。
 * <p>
 * 视图每次刷新取得所有相关标签后，按{@link #getTagKind(ITag)}方法取得标签的类别，并按此类别分类显示。
 * 标签的内容由{@link #getTagComment(ITag)}方法解析取得。
 * 另外，视图标题将显示当前语法单元的信息，由{@link #getTargetName(ITag)}方法取得。
 * <p>
 * 以上三个方法拥有默认实现，但应在具体的语言环境中进行重写。
 * @author 张弓
 * @version 1.00
 */
public class TagShower extends ViewPart implements ITagShowListener, Runnable {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.collaborativeeclipse.cct.views.MyTagShower";

	/**
	 * debug output switch
	 */
	private static final boolean DEBUG = false;

	private static ITag[] currentTags = null; // work in refreshing thread
	
	private Tree tree = null;
	private TreeColumn title = null;

	/**
	 * 若当期语法单元没有相关标签时，在视图标题中显示此文字。
	 */
	protected static String NO_TAGS_CURRENTLY = "No tags currently.";

	@Override
	public void createPartControl(Composite parent) {
		print("createPartControl");

		// start the refreshing thread
		new Thread(this).start();

		// init UI
		tree = new Tree(parent, SWT.H_SCROLL);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);
		title = new TreeColumn(tree, SWT.NONE);
		title.setText(NO_TAGS_CURRENTLY);
		title.setWidth(tree.getClientArea().width);
	}

	@SuppressWarnings("unchecked")
	private void refreshTree(ITag[] tags) {
		
		// title
		if (tags.length > 0) {
			String strTitle = getTargetName(tags[0]);
			title.setText(strTitle + "(Total " + tags.length + " tags)");
		} else {
			title.setText(NO_TAGS_CURRENTLY);
		}
		title.setWidth(tree.getClientArea().width);

		// clear the tree
		tree.removeAll();
		TreeItem tmp_item = null;

		print("Tree shows " + tags.length + " items.");

		// get tags' kinds
		LinkedList kinds = new LinkedList();
		for (int i = 0; i < tags.length; i++) {

			// find the kind
			int kind_index = -1;
			for (int j = 0; j < kinds.size(); j++) {
				Object[] data = (Object[]) kinds.get(j);
				if (getTagKind(tags[i]).equals(data[0])) {
					kind_index = j;
					break;
				}
			}

			// create new root
			if (kind_index == -1) {
				tmp_item = new TreeItem(tree, SWT.NONE);
				kinds.addLast(new Object[] { getTagKind(tags[i]),
						new Integer(0), tmp_item });
				kind_index = kinds.size() - 1;
			}

			// add item
			Object[] data = (Object[]) kinds.get(kind_index);
			data[1] = ((Integer) data[1]) + 1;
			tmp_item = new TreeItem((TreeItem) data[2], SWT.NONE);
			tmp_item.setText(getTagComment(tags[i]));
		}

		// set kinds' caption
		for (int i = 0; i < tags.length; i++) {
			Object[] data = (Object[]) kinds.get(i);
			tmp_item = (TreeItem) data[2];
			String str = String.valueOf(((String) data[0]) + "("
					+ ((Integer) data[1]) + " items)");
			tmp_item.setText(str);
		}

		// append other kinds or tags
		refreshRelatedTags(tags, tree);
	}

	/**
	 * 刷新显示相关标签。
	 * <p>
	 * 默认情况下此方法为空实现，子类重写后可以增加当前显示的内容。
	 * 此方法将在此视图刷新显示内容后被系统回调。
	 * @param tags 当前已经显示的标签
	 * @param tree 显示标签的控件实例
	 */
	protected void refreshRelatedTags(ITag[] tags, Tree tree) {

	}

	@Override
	public void setFocus() {
		print("setFocus");
		// if (viewer != null)
		// viewer.getControl().setFocus();
		if (tree != null)
			tree.setFocus();
	}

	public void dataRecived(ITagsData data) {
		print("dataRecived");
		if (data != null) {
			List<ITag> tags = data.getTags();
			currentTags = new ITag[tags.size()];
			for (int i = 0; i < tags.size(); i++)
				currentTags[i] = tags.get(i);
			print("Recived " + currentTags.length + " items.");
		} else
			currentTags = null;
	}

	static void print(Object info) {
		if (DEBUG)
			System.out.println("##" + info);
	}

	@Override
	final public void run() {
		try {
			while (true) {
				if (currentTags != null) {
					final ITag[] tags = currentTags.clone();
					currentTags = null;
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							// viewer.setInput(tags);
							refreshTree(tags);
						}
					});
				}
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			new Thread(this).start();
		}
	}

	/**
	 * 取得标签所依附的目标名称。
	 * <p>
	 * 此方法决定视图中标题行显示的内容。
	 * @param tag 指定的标签
	 * @return 目标名称
	 */
	protected String getTargetName(ITag tag) {
		return tag.getTarget().toString();
	}

	/**
	 * 取得标签的类别。
	 * <p>
	 * 侧方法决定视图中的标签分类名称。
	 * @param tag 指定的标签
	 * @return 标签的类别
	 */
	protected String getTagKind(ITag tag) {
		return tag.getData();
	}

	/**
	 * 取得标签的注释内容。
	 * <p>
	 * 侧方法决定视图中的标签内容显示。
	 * @param tag 指定的标签
	 * @return 标签的注释内容
	 */
	protected String getTagComment(ITag tag) {
		return tag.getData();
	}

}

// class ViewContentProvider implements IStructuredContentProvider {
//	
// private ITag[] currentTags = new ITag[0];
//	
// public void inputChanged(Viewer v, Object oldInput, Object newInput) {
// if (newInput instanceof ITag[])
// currentTags = (ITag[]) newInput;
// }
//
// public void dispose() {
//		
// }
//
// public Object[] getElements(Object parent) {
// return currentTags;
// }
//	
//	
// }
//
// class ViewLabelProvider extends LabelProvider implements ITableLabelProvider
// {
//	
// public String getColumnText(Object obj, int index) {
// return getText(obj);
// }
//
// public Image getColumnImage(Object obj, int index) {
// return getImage(obj);
// }
//	
// public String getText(Object obj) {
// if (obj instanceof ITag)
// return ((ITag) obj).getData();
// return obj.toString();
// }
//
// public Image getImage(Object obj) {
// return PlatformUI.getWorkbench().getSharedImages().getImage(
// ISharedImages.IMG_OBJ_ELEMENT);
// }
// }

// package com.collaborativeeclipse.cct.internal.views;
//
//
// import org.eclipse.swt.widgets.Composite;
// import org.eclipse.ui.part.*;
// import org.eclipse.jface.viewers.*;
// import org.eclipse.swt.graphics.Image;
// import org.eclipse.jface.action.*;
// import org.eclipse.jface.dialogs.MessageDialog;
// import org.eclipse.ui.*;
// import org.eclipse.swt.widgets.Menu;
// import org.eclipse.swt.SWT;
//
// import com.collaborativeeclipse.cct.views.ITagShowListener;
// import com.collaborativeeclipse.cct.views.ITagsData;
//
//
// /**
// * This sample class demonstrates how to plug-in a new
// * workbench view. The view shows data obtained from the
// * model. The sample creates a dummy model on the fly,
// * but a real implementation would connect to the model
// * available either in this or another plug-in (e.g. the workspace).
// * The view is connected to the model using a content provider.
// * <p>
// * The view uses a label provider to define how model
// * objects should be presented in the view. Each
// * view can present the same model objects using
// * different labels and icons, if needed. Alternatively,
// * a single label provider can be shared between views
// * in order to ensure that objects of the same type are
// * presented in the same way everywhere.
// * <p>
// */
//
// public class TagShower extends ViewPart implements ITagShowListener{
//
// /**
// * The ID of the view as specified by the extension.
// */
// public static final String ID =
// "com.collaborativeeclipse.cct.views.TagShower";
//
// private TableViewer viewer;
// private Action action1;
// private Action action2;
// private Action doubleClickAction;
//
// /*
// * The content provider class is responsible for
// * providing objects to the view. It can wrap
// * existing objects in adapters or simply return
// * objects as-is. These objects may be sensitive
// * to the current input of the view, or ignore
// * it and always show the same content
// * (like Task List, for example).
// */
//	 
// class ViewContentProvider implements IStructuredContentProvider {
// public void inputChanged(Viewer v, Object oldInput, Object newInput) {
// }
// public void dispose() {
// }
// public Object[] getElements(Object parent) {
// return new String[] { "One", "Two", "Three" };
// }
// }
// class ViewLabelProvider extends LabelProvider implements ITableLabelProvider
// {
// public String getColumnText(Object obj, int index) {
// return getText(obj);
// }
// public Image getColumnImage(Object obj, int index) {
// return getImage(obj);
// }
// public Image getImage(Object obj) {
// return PlatformUI.getWorkbench().
// getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
// }
// }
// class NameSorter extends ViewerSorter {
// }
//
// /**
// * The constructor.
// */
// public TagShower() {
// }
//
// /**
// * This is a callback that will allow us
// * to create the viewer and initialize it.
// */
// public void createPartControl(Composite parent) {
// viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
// viewer.setContentProvider(new ViewContentProvider());
// viewer.setLabelProvider(new ViewLabelProvider());
// viewer.setSorter(new NameSorter());
// viewer.setInput(getViewSite());
//
// // Create the help context id for the viewer's control
// PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(),
// "com.collaborativeeclipse.cct.viewer");
// makeActions();
// hookContextMenu();
// hookDoubleClickAction();
// contributeToActionBars();
// }
//
// private void hookContextMenu() {
// MenuManager menuMgr = new MenuManager("#PopupMenu");
// menuMgr.setRemoveAllWhenShown(true);
// menuMgr.addMenuListener(new IMenuListener() {
// public void menuAboutToShow(IMenuManager manager) {
// TagShower.this.fillContextMenu(manager);
// }
// });
// Menu menu = menuMgr.createContextMenu(viewer.getControl());
// viewer.getControl().setMenu(menu);
// getSite().registerContextMenu(menuMgr, viewer);
// }
//
// private void contributeToActionBars() {
// IActionBars bars = getViewSite().getActionBars();
// fillLocalPullDown(bars.getMenuManager());
// fillLocalToolBar(bars.getToolBarManager());
// }
//
// private void fillLocalPullDown(IMenuManager manager) {
// manager.add(action1);
// manager.add(new Separator());
// manager.add(action2);
// }
//
// private void fillContextMenu(IMenuManager manager) {
// manager.add(action1);
// manager.add(action2);
// // Other plug-ins can contribute there actions here
// manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
// }
//	
// private void fillLocalToolBar(IToolBarManager manager) {
// manager.add(action1);
// manager.add(action2);
// }
//
// private void makeActions() {
// action1 = new Action() {
// public void run() {
// showMessage("Action 1 executed");
// }
// };
// action1.setText("Action 1");
// action1.setToolTipText("Action 1 tooltip");
// action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
// getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
//		
// action2 = new Action() {
// public void run() {
// showMessage("Action 2 executed");
// }
// };
// action2.setText("Action 2");
// action2.setToolTipText("Action 2 tooltip");
// action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
// getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
// doubleClickAction = new Action() {
// public void run() {
// ISelection selection = viewer.getSelection();
// Object obj = ((IStructuredSelection)selection).getFirstElement();
// showMessage("Double-click detected on "+obj.toString());
// }
// };
// }
//
// private void hookDoubleClickAction() {
// viewer.addDoubleClickListener(new IDoubleClickListener() {
// public void doubleClick(DoubleClickEvent event) {
// doubleClickAction.run();
// }
// });
// }
// private void showMessage(String message) {
// MessageDialog.openInformation(
// viewer.getControl().getShell(),
// "TagShower",
// message);
// }
//
// /**
// * Passing the focus request to the viewer's control.
// */
// public void setFocus() {
// viewer.getControl().setFocus();
// }
//
// @Override
// public void dataRecived(ITagsData data) {
// //TODO change the data in the viewer
//		
// }
// }