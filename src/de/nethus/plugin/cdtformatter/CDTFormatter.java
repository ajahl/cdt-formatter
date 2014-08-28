package de.nethus.plugin.cdtformatter;
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
import org.eclipse.cdt.core.ToolFactory;
import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.core.commands.Command;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

import de.nethus.plugin.cdtformatter.preferences.ISaveActionExecutionListener;

public class CDTFormatter  {

	private static CDTFormatter instance;
	
	public static CDTFormatter getInstance() {
		instance = instance == null ? new CDTFormatter() : instance;
		return instance;
	}
	
	private CodeFormatter codeFormatter;
	private Command command;
	private ISaveActionExecutionListener executionListener;
	private IWorkbench workbench;
	
	private CDTFormatter() {
		codeFormatter = getOrCreateCodeFormatter();
		addExexcuteListener();
	}
	
	public CodeFormatter getOrCreateCodeFormatter() {
//		HashMap<String, String> pluginOptions = CCorePlugin.getOptions();
//		String formatterID = (String) pluginOptions
//				.get(CCorePreferenceConstants.CODE_FORMATTER);
//		String extID = CCorePlugin.FORMATTER_EXTPOINT_ID;
//		IExtensionPoint extension = Platform.getExtensionRegistry()
//				.getExtensionPoint(CCorePlugin.PLUGIN_ID, extID);
//		if (extension != null) {
//			IExtension[] extensions = extension.getExtensions();
//			for (int i = 0; i < extensions.length; i++) {
//				IConfigurationElement[] configElements = extensions[i]
//						.getConfigurationElements();
//				
//				for (int j = 0; j < configElements.length; j++) {
//					String initializerID = configElements[j].getAttribute("id"); //$NON-NLS-1$
//					
//					if (initializerID != null
//							&& initializerID.equals(formatterID)) {
//						try {
//							Object execExt = configElements[j]
//									.createExecutableExtension("class"); //$NON-NLS-1$
//							if (execExt instanceof CodeFormatter) {
//								CodeFormatter formatter = (CodeFormatter) execExt;
//								formatter.setOptions(pluginOptions);
//								return formatter;
//							}
//						} catch (CoreException e) {
//							CCorePlugin.log(e.getStatus());
//							break;
//						}
//					}
//				}
//			}
//		}
//		Map<String, ?> options = DefaultCodeFormatterConstants .getDefaultSettings();
		
		CodeFormatter codeFormatter = ToolFactory.createCodeFormatter(null);
		return codeFormatter;
	}
	
	public void addExexcuteListener() {
		
		if (command == null) {
			ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
			command = service.getCommand(ISaveActionExecutionListener.SAVEACTION_ID);
		}
		
		if (executionListener == null) {
			executionListener = new ISaveActionExecutionListener(this);
		}
		executionListener.setActiv(true);
		command.addExecutionListener(executionListener);
	}
	
	public void removeExexcuteListener() {
		
		if (command != null && executionListener != null) {
			executionListener.setActiv(false);
			command.removeExecutionListener(executionListener);
		}
	}

	public void setAutoSaveState(boolean autoSaveState) {
		executionListener.setActiv(autoSaveState);
		if (!autoSaveState) {
			removeExexcuteListener();
		}
	}
	
}
