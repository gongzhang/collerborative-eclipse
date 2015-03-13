package com.collaborativeeclipse.im;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;


public class DisplayManager {
	private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 5, 3,
			TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10),
			new ThreadPoolExecutor.DiscardOldestPolicy());;
	private static DisplayManager displayManager;

	private String lastMessage;

	private Text area;

	private Composite composite;

	private DisplayManager() {
	}

	/**
	 * get Default displayManager.
	 * 
	 * @return displaymanager.
	 * 
	 */
	public static DisplayManager getDisplayManager() {
		if (displayManager == null) {
			displayManager = new DisplayManager();
		}

		return displayManager;
	}

	public void saveLast() {
		if (lastMessage != null) {
			lastMessage=area.getText();
		}
	}

	public void setArea(Text area) {
		this.area = area;

	}

	public void input(String str) {

		IMCore core = IMCore.getCore();
		String user = core.getUser();
		String buddy = core.getBuddy();

		if (user != null) {
			area.append(user + ":" + str);
			if (buddy != null) {
				IMCore.getCore().sendMessage(buddy, str);
			}
		} else {
			area.append(str);
		}
	}

	public void recive(final String buddy, final String content) {

		//给扩展点发送信息
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint point = registry
				.getExtensionPoint("com.collaborativeeclipse.im.MessageRecievedListener");
		if (point == null)
			return;
		IExtension[] extensions = point.getExtensions();

		IConfigurationElement[] ces = null;
		for (int i = 0; i < extensions.length; i++) {
			ces = extensions[i].getConfigurationElements();
			for (int j = 0; j < ces.length; j++) {

				if ("handler".equals(ces[j].getName())) {

					IMessageRecieveHandler handler = null;
					try {

						handler = (IMessageRecieveHandler) ces[j]
									.createExecutableExtension("class");

						threadPool.execute(new ThreadPoolTask(
								new Object[] { handler, buddy,content }) {

							@Override
							public void run() {
								((IMessageRecieveHandler) getData()[0])
								.messageRecieved((String) getData()[1],(String)getData()[2]);

							}
						});

					} catch (CoreException e1) {
						e1.printStackTrace();
					}
				}
			}
		}

		
		area.getDisplay().syncExec(new Runnable() {

			public void run() {
				String str = content;
				if(content.contains(IMCore.MESSAGESTARTSIGNAL)){
					
					str = content.substring(content.indexOf(IMCore.MESSAGESTARTSIGNAL)+ IMCore.MESSAGESTARTSIGNAL.length());

				}else
					area.append(buddy + ":" + str + "\n");
			}

		});
	}

	public void setComposite(Composite parent) {
		this.composite = parent;
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
