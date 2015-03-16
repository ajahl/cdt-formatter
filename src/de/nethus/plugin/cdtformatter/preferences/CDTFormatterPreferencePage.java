package de.nethus.plugin.cdtformatter.preferences;

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

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import de.nethus.plugin.cdtformatter.Activator;
import de.nethus.plugin.cdtformatter.CDTFormatter;

public class CDTFormatterPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String AUTO_SAVE_ID = "autosaveID";
	private BooleanFieldEditor checkBox;
	
	public CDTFormatterPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("config auto format");
	}
	
	public void createFieldEditors() {
		checkBox = new BooleanFieldEditor(PreferenceConstants.P_BOOLEAN,
															"&auto format on save",
															getFieldEditorParent());
		
		checkBox.setPreferenceName(AUTO_SAVE_ID);
		checkBox.setPreferenceStore(Activator.getDefault().getPreferenceStore());
		checkBox.setPage(this);
		addField(checkBox);
	}

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
