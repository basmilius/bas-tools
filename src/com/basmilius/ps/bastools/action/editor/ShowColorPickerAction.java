package com.basmilius.ps.bastools.action.editor;

import com.basmilius.ps.bastools.util.EditorUtils;
import com.basmilius.ps.bastools.util.ExceptionUtils;
import com.basmilius.ps.bastools.util.JUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.ui.ColorPicker;
import com.intellij.ui.ColorUtil;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;

public final class ShowColorPickerAction extends AnAction
{

	/**
	 * ShowColorPickerAction Constructor.
	 */
	public ShowColorPickerAction ()
	{
		super("Show Color Picker");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void actionPerformed (final AnActionEvent aae)
	{
		final Project project = aae.getProject();
		final JComponent root = JUtils.getRootComponent(aae.getProject());

		if (project == null || root == null)
			return;

		final Color color = ColorPicker.showDialog(root, "Pick a Color", JBColor.CYAN, false, null, false);

		if (color == null)
			return;

		final Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

		if (editor == null)
			return;

		ExceptionUtils.executeIgnore(() -> EditorUtils.insertOrReplaceMultiCaret(editor, project, "#" + ColorUtil.toHex(color)));
	}

}
