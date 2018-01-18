package com.basmilius.bastools.watcher

import com.basmilius.bastools.core.util.ApplicationUtils
import com.intellij.AppTopics
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileDocumentManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager

/**
 * Class SassWatcherProjectComponent
 *
 * @constructor
 * @param project Project
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.watcher
 */
class SassWatcherProjectComponent(project: Project): FileDocumentManagerListener, ProjectComponent
{

	private val messageBus = project.messageBus
	private val connection = messageBus.connect()

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun projectClosed()
	{
		super.projectClosed()

		this.connection.disconnect()
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun projectOpened()
	{
		super.projectOpened()

		this.connection.subscribe(AppTopics.FILE_DOCUMENT_SYNC, this)
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 *
	 * TODO(Bas): Find a way to get the current project and show an editor hint on error.
	 */
	override fun beforeDocumentSaving(document: Document)
	{
		val file = FileDocumentManager.getInstance().getFile(document) ?: return

		ApplicationUtils.deferRun(timeout = 1000) {
			VirtualFileManager.getInstance().asyncRefresh(null)
			System.out.println(String.format("File saved and Virtual File System synced! (%s)", file?.name))
		}
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun fileWithNoDocumentChanged(file: VirtualFile)
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun fileContentReloaded(file: VirtualFile, document: Document)
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun fileContentLoaded(file: VirtualFile, document: Document)
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun beforeFileContentReload(file: VirtualFile?, document: Document)
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun unsavedDocumentsDropped()
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun beforeAllDocumentsSaving()
	{
	}

}
