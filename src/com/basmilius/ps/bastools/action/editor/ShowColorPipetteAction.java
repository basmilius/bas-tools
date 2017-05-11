package com.basmilius.ps.bastools.action.editor;

import com.basmilius.ps.bastools.ui.DefaultColorPipette;
import com.basmilius.ps.bastools.util.EditorUtils;
import com.basmilius.ps.bastools.util.ExceptionUtils;
import com.basmilius.ps.bastools.util.JUtils;
import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.ui.ColorUtil;
import com.intellij.ui.picker.ColorListener;
import com.intellij.ui.picker.ColorPipette;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public final class ShowColorPipetteAction extends AnAction implements ColorListener, Disposable, WindowListener
{

	private Color currentColor;
	private Editor editor;
	private Project project;

	/**
	 * ShowColorPipetteAction Constructor.
	 */
	public ShowColorPipetteAction ()
	{
		super("Show Color Pipette");

		this.currentColor = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void actionPerformed (final AnActionEvent aae)
	{
		this.project = aae.getProject();
		final JComponent root = JUtils.getRootComponent(aae.getProject());

		if (project == null || root == null)
			return;

		this.editor = FileEditorManager.getInstance(this.project).getSelectedTextEditor();

		if (this.editor == null)
			return;

		final ColorPipette pipette = getPipetteIfAvailable(new DefaultColorPipette(root, this), this);

		if (pipette == null)
		{
			HintManager.getInstance().showErrorHint(this.editor, "Could not insert color, no pipette implementation found!");
			return;
		}

		pipette.setInitialColor(ColorUtil.fromHex("#000000"));
		pipette.show().addWindowListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void colorChanged (final Color color, final Object o)
	{
		this.currentColor = color;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void windowOpened (final WindowEvent e)
	{

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void windowClosing (final WindowEvent e)
	{

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void windowClosed (final WindowEvent e)
	{
		if (this.currentColor == null)
		{
			HintManager.getInstance().showInformationHint(this.editor, "Could not insert color, you didn't select a color!");
			return;
		}

		ExceptionUtils.executeIgnore(() -> EditorUtils.insertOrReplaceMultiCaret(this.editor, this.project, "#" + ColorUtil.toHex(this.currentColor)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void windowIconified (final WindowEvent e)
	{

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void windowDeiconified (final WindowEvent e)
	{

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void windowActivated (final WindowEvent e)
	{

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void windowDeactivated (final WindowEvent e)
	{
		e.getWindow().dispose();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void dispose ()
	{

	}

	/**
	 * Gets a color pipette implementation if available.
	 *
	 * @param pipette          Base instance.
	 * @param parentDisposable Parent disposable.
	 *
	 * @return ColorPipette
	 */
	@Nullable
	private static ColorPipette getPipetteIfAvailable (@NotNull ColorPipette pipette, @NotNull Disposable parentDisposable)
	{
		if (pipette.isAvailable())
		{
			Disposer.register(parentDisposable, pipette);
			return pipette;
		}
		else
		{
			Disposer.dispose(pipette);
			return null;
		}
	}

}
