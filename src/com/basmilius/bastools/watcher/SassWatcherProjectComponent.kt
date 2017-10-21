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

class SassWatcherProjectComponent(project: Project): FileDocumentManagerListener, ProjectComponent
{

	private val messageBus = project.messageBus
	private val connection = messageBus.connect()

	override fun projectClosed()
	{
		super.projectClosed()

		this.connection.disconnect()
	}

	override fun projectOpened()
	{
		super.projectOpened()

		this.connection.subscribe(AppTopics.FILE_DOCUMENT_SYNC, this)
	}

	override fun beforeDocumentSaving(document: Document)
	{
		val file = FileDocumentManager.getInstance().getFile(document)

		ApplicationUtils.deferRun(timeout = 1000) {
			VirtualFileManager.getInstance().asyncRefresh(null)
			System.out.println(String.format("File saved and Virtual File System synced! (%s)", file?.name))
		}
	}

	override fun fileWithNoDocumentChanged(file: VirtualFile)
	{
	}

	override fun fileContentReloaded(file: VirtualFile, document: Document)
	{
	}

	override fun fileContentLoaded(file: VirtualFile, document: Document)
	{
	}

	override fun beforeFileContentReload(file: VirtualFile?, document: Document)
	{
	}

	override fun unsavedDocumentsDropped()
	{
	}

	override fun beforeAllDocumentsSaving()
	{
	}

}
