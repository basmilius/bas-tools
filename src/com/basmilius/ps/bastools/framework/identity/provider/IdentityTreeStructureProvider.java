package com.basmilius.ps.bastools.framework.identity.provider;

import com.basmilius.ps.bastools.framework.identity.IdentityFramework;
import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiDirectoryNode;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IdentityTreeStructureProvider implements TreeStructureProvider
{

	@NotNull
	@Override
	public Collection<AbstractTreeNode> modify (@NotNull final AbstractTreeNode parent, @NotNull final Collection<AbstractTreeNode> children, final ViewSettings settings)
	{
		final List<AbstractTreeNode> nodes = new ArrayList<>();
		final Project project = parent.getProject();

		if (project == null)
		{
			return nodes;
		}

		if (IdentityFramework.isIdentityFrameworkProject(project))
		{
			for (final AbstractTreeNode child : children)
			{
				if (child instanceof PsiDirectoryNode)
				{
					final PsiDirectoryNode dir = (PsiDirectoryNode) child;
					final VirtualFile dirFile = dir.getVirtualFile();

					if (dirFile == null)
						continue;

					final String dirName = dirFile.getName();

					if (dirName.equals(".identity-deployer"))
						continue;
				}

				if (child instanceof PsiFileNode)
				{
					final PsiFileNode file = (PsiFileNode) child;
					final VirtualFile virtualFile = file.getVirtualFile();

					if (virtualFile == null)
						continue;

					if (virtualFile.getParent().getName().equals("wp-content") && virtualFile.getName().equals("install.lock"))
						continue;
				}

				nodes.add(child);
			}
		}
		else
		{
			nodes.addAll(children);
		}

		return nodes;
	}

	@Nullable
	@Override
	public Object getData (final Collection<AbstractTreeNode> selected, final String dataName)
	{
		return null;
	}

}
