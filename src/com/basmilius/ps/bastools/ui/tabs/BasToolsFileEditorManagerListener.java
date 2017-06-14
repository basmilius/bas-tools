package com.basmilius.ps.bastools.ui.tabs;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorTabbedContainer;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.EditorWithProviderComposite;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.FileColorManager;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BasToolsFileEditorManagerListener implements FileEditorManagerListener
{

	@Override
	public final void fileOpened (@NotNull FileEditorManager source, @NotNull VirtualFile file)
	{
	}

	@Override
	public final void fileClosed (@NotNull FileEditorManager source, @NotNull VirtualFile file)
	{
	}

	@Override
	public final void selectionChanged (@NotNull FileEditorManagerEvent event)
	{
		final Project project = event.getManager().getProject();
		final FileEditorManagerEx manager = FileEditorManagerEx.getInstanceEx(project);

		final VirtualFile oldFile = event.getOldFile();
		final VirtualFile newFile = event.getNewFile();

		for (final EditorWindow window : manager.getWindows())
		{
			this.processTab(oldFile, window);
			this.processTab(newFile, window);
		}
	}

	private int getEditorIndex (@NotNull EditorWindow window, final EditorWithProviderComposite composite)
	{
		int index = 0;

		for (final EditorWithProviderComposite sComposite : window.getEditors())
		{
			if (sComposite.equals(composite))
				break;

			index++;
		}

		return index;
	}

	private void processTab (final VirtualFile file, final EditorWindow window)
	{
		final EditorWithProviderComposite composite = window.findFileComposite(file);
		final int editorIndex = this.getEditorIndex(window, composite);

		if (editorIndex < 0)
			return;

		final EditorTabbedContainer tabs = window.getTabbedPane();

		if (tabs == null)
			return;

		if (tabs.getTabCount() <= editorIndex)
			return;

		tabs.getTabs().getPresentation().setActiveTabFillIn(BasToolsTabsPainterPatcherComponent.BackgroundColor);
	}

}
