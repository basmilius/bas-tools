package com.basmilius.ps.bastools.ui.tabs;

import com.intellij.openapi.fileEditor.impl.EditorTabColorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class BasToolsEditorTabColorProvider implements EditorTabColorProvider
{

	@Nullable
	@Override
	public Color getEditorTabColor (@NotNull Project project, @NotNull VirtualFile virtualFile)
	{
		return BasToolsTabsPainterPatcherComponent.BackgroundColor;
	}

}
