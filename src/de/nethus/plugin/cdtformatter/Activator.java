package de.nethus.plugin.cdtformatter;

/*******************************************************************************
 * Copyright (c) 2014 Alex Jahl
 * All rights reserved.
 * 
 * Licensed under the EUPL, Version 1.1 or (as soon they
 * will be approved by the European Commission) subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl5
 * 
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 *
 * Contributors:
 * 	   Alex Jahl
 *******************************************************************************/

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.nethus.plugin.cdtformatter.preferences.CDTFormatterPreferencePage;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin implements IStartup {

	public static final String PLUGIN_ID = "CDT_Formatter"; //$NON-NLS-1$
	public static final String DEFAULT_BAD_WORDS = "bug;bogus;hack;";

	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

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

	@Override
	public void earlyStartup() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		boolean autoSaveState = store.getBoolean(CDTFormatterPreferencePage.AUTO_SAVE_ID);
		CDTFormatter formatter = CDTFormatter.getInstance();
		formatter.setAutoSaveState(autoSaveState);
	}
	
	/** 
	 * Initializes a preference store with default preference values 
	 * for this plug-in.
	 */
	protected void initializeDefaultPreferences(IPreferenceStore store) {
	}
}
