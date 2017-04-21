package com.basmilius.ps.bastools.action.editor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.IdeFrame;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.ColorPicker;
import com.intellij.ui.ColorUtil;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;

public class ShowColorPickerAction extends AnAction
{

	/**
	 * ShowColorPickerAction Constructor.
	 */
	public ShowColorPickerAction ()
	{
		super ("Show Color Picker");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void actionPerformed (final AnActionEvent aae)
	{
		final Project project = aae.getProject();
		final JComponent root = this.getRootComponent(aae.getProject());

		if (project == null || root == null)
			return;

		final Color color = ColorPicker.showDialog(root, "Select a Color", JBColor.CYAN, true, null, true);

		if (color == null)
			return;

		final Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

		if (editor == null)
			return;

		final WriteCommandAction wca = new WriteCommandAction.Simple(project)
		{

			@Override
			protected void run () throws Throwable
			{
				final int cursorOffset = editor.getCaretModel().getOffset();
				final Document document = editor.getDocument();

				document.insertString(cursorOffset, "#" + ColorUtil.toHex(color));
			}

		};
		wca.execute();
	}

	/**
	 * Gets the root component for the project.
	 * @param project Project.
	 * @return JComponent
	 */
	private JComponent getRootComponent (final Project project)
	{
		if (project != null)
		{
			final IdeFrame frame = WindowManager.getInstance().getIdeFrame(project);

			if (frame != null)
			{
				return frame.getComponent();
			}
		}

		final JFrame frame = WindowManager.getInstance().findVisibleFrame();
		return frame != null ? frame.getRootPane() : null;
	}

}
