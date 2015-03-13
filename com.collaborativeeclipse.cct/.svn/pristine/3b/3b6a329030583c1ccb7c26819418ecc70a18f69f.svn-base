package com.collaborativeeclipse.cct;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.collaborativeeclipse.cct";

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IExtension[] extensions = registry
				.getExtensionPoint("com.collaborativeeclipse.cct.Initialize").getExtensions();

		/**
		 * arrange the handlers and exceptionhandlers
		 */
		IConfigurationElement[] ces = null;
		Runnable task = null;
		for (int i = 0; i < extensions.length; i++) {
			ces = extensions[i].getConfigurationElements();
			for (int j = 0; j < ces.length; j++) {

				if ("handler".equals(ces[j].getName())) {
					try {
						task = (Runnable) ces[j]
						.createExecutableExtension("class");
					} catch (CoreException e) {
						e.printStackTrace();
					}
						task.run();
				}
				
			}
		}
		System.out.println("CCT core started");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
