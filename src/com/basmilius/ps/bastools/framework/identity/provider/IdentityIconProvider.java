package com.basmilius.ps.bastools.framework.identity.provider;

import com.basmilius.ps.bastools.framework.identity.IdentityFramework;
import com.basmilius.ps.bastools.resource.Icons;
import com.intellij.ide.FileIconProvider;
import com.intellij.ide.IconProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Iconable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class IdentityIconProvider extends IconProvider implements FileIconProvider
{

	@Nullable
	@Override
	public Icon getIcon (@NotNull final VirtualFile file, @Iconable.IconFlags final int flags, @Nullable final Project project)
	{
		if (project == null)
			return Icons.Default;

		if (file.getName().equals(".angular-cli.json"))
			return Icons.Angular;

		return Icons.Default;
	}

	@Nullable
	@Override
	public Icon getIcon (@NotNull final PsiElement psi, @Iconable.IconFlags final int flags)
	{
		final Project project = psi.getProject();

		if (IdentityFramework.isIdentityFrameworkProject(project))
		{
			if (psi instanceof PsiDirectory)
			{
				final PsiDirectory dir = (PsiDirectory) psi;
				final VirtualFile projectRoot = IdentityFramework.getSourcesRoot(project);

				if (dir.getVirtualFile().getPath().equals(projectRoot.getPath()))
					return Icons.Rhombus;

				if (dir.getParent() != null && (dir.getName().equals("plugins") || dir.getParent().getName().equals("plugins")))
					return Icons.Puzzle;

				if (dir.getParent() != null && (dir.getName().equals("themes") || dir.getParent().getName().equals("themes")))
					return Icons.Creation;
			}
		}

		return Icons.Default;
	}

}
