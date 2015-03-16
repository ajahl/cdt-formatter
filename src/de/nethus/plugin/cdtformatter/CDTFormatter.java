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

import org.eclipse.cdt.core.ToolFactory;
import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.core.commands.Command;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

import de.nethus.plugin.cdtformatter.preferences.ISaveActionExecutionListener;

public class CDTFormatter  {

	private static CDTFormatter instance;
	
	public static CDTFormatter getInstance() {
		instance = instance == null ? new CDTFormatter() : instance;
		return instance;
	}
	
	private Command command;
	private ISaveActionExecutionListener executionListener;
	
	private CDTFormatter() {
		addExexcuteListener();
	}
	
	public CodeFormatter getOrCreateCodeFormatter() {
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
