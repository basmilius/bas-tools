package com.basmilius.bastools.component

import com.basmilius.bastools.core.util.invokeLater
import com.intellij.AppTopics
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileDocumentManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFileManager

class BTSafeSyncFixComponent(project: Project) : FileDocumentManagerListener, ProjectComponent
{

	private val messageBus = project.messageBus
	private val connection = messageBus.connect()

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun projectClosed()
	{
		super.projectClosed()

		this.connection.disconnect()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 */
	override fun projectOpened()
	{
		super.projectOpened()

		this.connection.subscribe(AppTopics.FILE_DOCUMENT_SYNC, this)
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.0.0
	 *
	 * TODO(Bas): Find a way to get the current project and show an textEditor hint on error.
	 */
	override fun beforeDocumentSaving(document: Document)
	{
		val file = FileDocumentManager.getInstance().getFile(document) ?: return

		invokeLater (timeout = 1000) {
			VirtualFileManager.getInstance().asyncRefresh {
				System.out.println(String.format("File saved and Virtual File System synced! (%s)", file.name))
			}
		}
	}

}