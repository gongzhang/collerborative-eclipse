package com.collaborativeeclipse.cct;


import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.collaborativeeclipse.cct.exception.IExceptionHandler;
import com.collaborativeeclipse.cct.exception.TagCoreException;
import com.collaborativeeclipse.cct.extension.ITagAddListener;
import com.collaborativeeclipse.cct.extension.ITagDataProvider;
import com.collaborativeeclipse.cct.views.ITagShowListener;
import com.collaborativeeclipse.cct.views.ITagsData;

public class CodeTagCore{
	public static String EXTENSION_TAGADDLISTENER = "com.collaborativeeclipse.cct.TagAddListener";
	public static String EXTENSION_TAGDATAPROVIDER = "com.collaborativeeclipse.cct.TagDataProvider";
	public static String EXTENSION_EXCEPTIONHANDLER = "com.collaborativeeclipse.cct.ExceptionHandler";
	
	private static CodeTagCore codeTagCore = null;
	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 5, 3,
			TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10),
			new ThreadPoolExecutor.DiscardOldestPolicy());
	
	private List<IExceptionHandler> exceptionHandlers = new LinkedList<IExceptionHandler>();

	public List<IExceptionHandler> getExceptionHandlers() {
		if(exceptionHandlers.size() == 0){
			IExtension[] extensions = getExtensions(EXTENSION_EXCEPTIONHANDLER);
			
			IConfigurationElement[] ces = null;
			for (int i = 0; i < extensions.length; i++) {
				ces = extensions[i].getConfigurationElements();
				for (int j = 0; j < ces.length; j++) {

					if ("handler".equals(ces[j].getName()))
						try {
							exceptionHandlers.add((IExceptionHandler) ces[j].createExecutableExtension("class"));
						} catch (CoreException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					
				}
			}
		}
		return exceptionHandlers;
	}


	public static CodeTagCore getInstance() {
		if (codeTagCore == null)
			codeTagCore = new CodeTagCore();

		return codeTagCore;
	}

	CodeTagCore() {
	};

	public void saveChangePersistent() {

		return;
	}

	/**
	 * This method will simply add the tag to tagdatasystem and
	 *  notify all the listeners,
	 *  if the caller itself is a listener,careful not to cause dead loop
	 */
	public void addTag(ITag tag) {
		/**
		 * store the exceptionHandlers
		 */
		final LinkedList<ThreadPoolTask> tasklist = new LinkedList<ThreadPoolTask>();
		tasklist.clear();
		/**
		 * read extensionpoints
		 */
		IExtension[] extensions = getExtensions(EXTENSION_TAGADDLISTENER);

		/**
		 * arrange the handlers and exceptionhandlers
		 */
		IConfigurationElement[] ces = null;
		for (int i = 0; i < extensions.length; i++) {
			ces = extensions[i].getConfigurationElements();
			for (int j = 0; j < ces.length; j++) {

				if ("handler".equals(ces[j].getName()))
					try {
						dealAddListenerNode(ces[j],tag,tasklist);
					} catch (TagCoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
		}
		runTasks(tasklist);
	}


	private IExtension[] getExtensions(String extensionID) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IExtensionPoint point = registry
				.getExtensionPoint(extensionID);
		
		if(point == null)
			return null;
		else
			return point.getExtensions();
	}

	private void dealAddListenerNode(IConfigurationElement ce, ITag tag, LinkedList<ThreadPoolTask> tasklist) throws TagCoreException {

		ITagAddListener tagAddListener = null;
		
		try {
			// 判断是否为目标对象
			Class clazz = getTargetClass(ce);
			if (clazz.isInstance(tag)) {
				if (tagAddListener == null)
					tagAddListener = (ITagAddListener) ce
							.createExecutableExtension("class");
				

			}
//			getTagAccess().addTag(tag); //TagAccess也成为扩展点
			tasklist.add(new ThreadPoolTask(
					new Object[] { tagAddListener, tag }) {

				@Override
				public void run() {
					try {
						((ITagAddListener) getData()[0])
						.addTag((ITag) getData()[1]);
					} catch (Exception e) {
						//if exception happenned,publish it
						publishException(getExceptionHandlers(),getData()[0], e);
					}

				}

			});

		} catch (CoreException e1) {
			e1.printStackTrace();
			throw new TagCoreException("Tag Add Failed");
		}
	
		
	}


	private void runTasks(LinkedList<ThreadPoolTask> tasklist) {
		for(ThreadPoolTask task : tasklist){
			threadPool.execute(task);
		}
		
	}
	protected void publishException(
			List<IExceptionHandler> exceptionHandlers, Object object,
			Exception e) {
		for (IExceptionHandler handler:exceptionHandlers){
			handler.handleException(object, e);
		}
		
	}

	private IExceptionHandler getExceptionHandler(
			IConfigurationElement ce) throws CoreException {
		if(ce.getAttribute("exceptionHandler") != null)
			return (IExceptionHandler) ce.createExecutableExtension("exceptionHandler");
		return null;
	}

	private Class getTargetClass(
			IConfigurationElement ce) {
		try {
			return Class.forName(ce.getAttribute("target"));
		} catch (Exception e) {
		}
		return Object.class;
	}

	
	public void showTag(ITagsData data) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry
				.getExtensionPoint("com.collaborativeeclipse.cct.TagShower");
		if (point == null)
			return;
		IExtension[] extensions = point.getExtensions();

		IConfigurationElement ce = null;
		for (int i = 0; i < extensions.length; i++) {
			ce = extensions[i].getConfigurationElements()[0];

			if ("reciever".equals(ce.getName())) {

				try {
					ITagShowListener tagShowListener = (ITagShowListener) ce
							.createExecutableExtension("class");
					
					threadPool.execute(new ThreadPoolTask(new Object[] {
							tagShowListener, data }) {

						@Override
						public void run() {
							((ITagShowListener) getData()[0])
									.dataRecived((ITagsData) getData()[1]);
						}
					});

				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public List<ITag> getTagsFromTarget(ITarget target) {
		/**
		 * read extensionpoints
		 */
		IExtension[] extensions = getExtensions(EXTENSION_TAGDATAPROVIDER);

		List<ITag> res = null;

		/**
		 * arrange the handlers and exceptionhandlers
		 */
		IConfigurationElement[] ces = null;
		ITagDataProvider dataProvider = null;
		for (int i = 0; i < extensions.length; i++) {
			ces = extensions[i].getConfigurationElements();
			for (int j = 0; j < ces.length; j++) {

				if ("handler".equals(ces[j].getName())) {
					try {
						dataProvider = (ITagDataProvider) ces[j]
						.createExecutableExtension("class");
					} catch (CoreException e) {
						e.printStackTrace();
					}
					List<ITag> data = dataProvider.getTagsFromTarget(target);
					if(res == null) 
						res = data;
					else 
						res.addAll(data);
				}
				
			}
		}
		return res;
	}

	public void removeTag(ITag tag) {
		// TODO Auto-generated method stub

	}
	public void removeTarget(ITarget target) {
		// TODO Auto-generated method stub

	}
	
}

abstract class ThreadPoolTask implements Runnable {

	private Object[] data;

	public ThreadPoolTask(Object data[]) {
		this.data = data;
	}

	public Object[] getData() {
		return data;
	}

}
