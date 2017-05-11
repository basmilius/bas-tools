package com.basmilius.ps.bastools.util;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.project.Project;

public final class EditorUtils
{

	/**
	 * Inserts or replaces text in the editor.
	 *
	 * @param editor  Editor instance.
	 * @param project Project instance.
	 * @param str     String you want to add/replace.
	 *
	 * @throws Exception Thrown if inserting fails.
	 */
	public static void insertOrReplaceMultiCaret (final Editor editor, final Project project, final String str) throws Exception
	{
		final WriteCommandAction wca = new WriteCommandAction.Simple(project)
		{

			@Override
			protected final void run () throws Throwable
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
						document.replaceString(starts[i], ends[i], str);
					}
				}
				else if (caret.getCaretCount() > 0)
				{
					for (final Caret c : caret.getAllCarets())
					{
						document.insertString(c.getOffset(), str);
						c.moveToOffset(c.getOffset() + 7);
					}
				}
				else
				{
					throw new Exception("No carets found!");
				}
			}

		};
		wca.execute();
	}

}
