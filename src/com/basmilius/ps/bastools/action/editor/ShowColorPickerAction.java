package com.basmilius.ps.bastools.action.editor;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
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
		super("Show Color Picker");
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
		{
			return;
		}

		final Color color = ColorPicker.showDialog(root, "Select a Color", JBColor.CYAN, true, null, true);

		if (color == null)
		{
			return;
		}

		final Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

		if (editor == null)
		{
			return;
		}

		final WriteCommandAction wca = new WriteCommandAction.Simple(project)
		{

			@Override
			protected void run () throws Throwable
			{
				final Document document = editor.getDocument();
				final CaretModel caret = editor.getCaretModel();
				final SelectionModel selection = editor.getSelectionModel();

				if (selection.hasSelection())
				{
					final int[] starts = selection.getBlockSelectionStarts();
					final int[] ends = selection.getBlockSelectionEnds();
					final int length = starts.length - 1;

					for (int i = length; i >= 0; i--)
					{
						document.replaceString(starts[i], ends[i], "#" + ColorUtil.toHex(color));
					}
				}
				else if (caret.getCaretCount() > 0)
				{
					for (final Caret c : caret.getAllCarets())
					{
						document.insertString(c.getOffset(), "#" + ColorUtil.toHex(color));
						c.moveToOffset(c.getOffset() + 7);
					}
				}
				else
				{
					HintManager.getInstance().showInformationHint(editor, "Could not insert color due an unknown error.");
				}
			}

		};
		wca.execute();
	}

	/**
	 * Gets the root component for the project.
	 *
	 * @param project Project.
	 *
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
