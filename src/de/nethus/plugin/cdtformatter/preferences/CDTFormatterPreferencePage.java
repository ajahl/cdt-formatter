/*******************************************************************************
 * Copyright (c) 2014 Alex Jahl
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	   Alex Jahl
 *******************************************************************************/

package de.nethus.plugin.cdtformatter.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.nethus.plugin.cdtformatter.Activator;
import de.nethus.plugin.cdtformatter.CDTFormatter;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class CDTFormatterPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String AUTO_SAVE_ID = "autosaveID";
	private BooleanFieldEditor checkBox;
	
	// extentions org.eclipse.cdt.ui.preferences.CEditorPreferencePage
	// extentions org.eclipse.cdt.ui.preferences.CPluginPreferencePage

	public CDTFormatterPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("config auto format");
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		checkBox = new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN,
															"&auto format on save",
															getFieldEditorParent());
		
		checkBox.setPreferenceName(AUTO_SAVE_ID);
		checkBox.setPreferenceStore(Activator.getDefault().getPreferenceStore());
		checkBox.setPage(this);
		addField(checkBox);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
        setDescription("CDT Formatter Configuration");
	}
	
	@Override
	protected void performApply() {
		super.performApply();
	}
	
	@Override
	public boolean performOk() {
		boolean result = super.performOk();
		
		if (result) {
			boolean presentsDefaultValue = checkBox.getBooleanValue();
			CDTFormatter formatter = CDTFormatter.getInstance();
			if (presentsDefaultValue) {				
				formatter.addExexcuteListener();
			}
			else {
				formatter.removeExexcuteListener();
			}
		}
		return result;
	}
}
