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

import org.eclipse.cdt.core.formatter.CodeFormatter;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;

import de.nethus.plugin.cdtformatter.CDTFormatter;


public class ISaveActionExecutionListener implements IExecutionListener {
	
	public static String SAVEACTION_ID = "org.eclipse.ui.file.save";
	private boolean isActiv;
	private CDTFormatter cdtFormatter;
	
	public ISaveActionExecutionListener(CDTFormatter cdtFormatter) {
		this.cdtFormatter = cdtFormatter;
	}

	@Override
	public void notHandled(String arg0, NotHandledException arg1) {
	}

	@Override
	public void postExecuteFailure(String arg0, ExecutionException arg1){
	}

	@Override
	public void postExecuteSuccess(String arg0, Object arg1) {
	}

	@Override
	public void preExecute(String string, ExecutionEvent event) {
		if (!isActiv)
			return;
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow workbenchWindow = workbench == null ? null : workbench
				.getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage = workbenchWindow == null ? null : workbenchWindow
				.getActivePage();
		IEditorPart editorPart = workbenchPage == null ? null : workbenchPage
				.getActiveEditor();
		IEditorInput input = editorPart == null ? null : editorPart.getEditorInput();
		System.out.println("code formatter: " + input.getName());
		
		TextEditor textEditor = editorPart instanceof TextEditor ? ( (TextEditor) editorPart ) : null;
		
		if (textEditor == null)
			return;
		
		IDocument document = textEditor.getDocumentProvider()
				.getDocument(input);

		String source = document.get();
		CodeFormatter codeFormatter = cdtFormatter.getOrCreateCodeFormatter();
		final TextEdit edit = codeFormatter.format(CodeFormatter.K_TRANSLATION_UNIT, source, 0, source.length(), 0, System.getProperty("line.separator"));

		try {
			edit.apply(document);

		} catch (MalformedTreeException | BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void setActiv(boolean isActiv) {
		this.isActiv = isActiv;	
	}
	
	public boolean isActiv() {
		return isActiv;
	}
}
