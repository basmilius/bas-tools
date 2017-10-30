package com.basmilius.bastools.component

import com.basmilius.bastools.component.presenter.shortcuts.ShortcutPresenter
import com.basmilius.bastools.core.options.BasToolsOptions
import com.basmilius.bastools.core.options.BasToolsOptionsLoader
import com.intellij.AppTopics
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileDocumentManagerListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.vfs.VirtualFile

/**
 * Class BasToolsProjectComponent
 *
 * @author Bas Milius
 * @package com.basmilius.bastools.component
 */
class BasToolsProjectComponent(private val project: Project): FileDocumentManagerListener, ProjectComponent
{

	private val messageBus = project.messageBus
	private val connection = messageBus.connect()

	private var options: BasToolsOptions? = null
	private val presenterComponent: ShortcutPresenter = ApplicationManager.getApplication().getComponent(ShortcutPresenter::class.java)

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun projectClosed()
	{
		this.connection.disconnect()
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun projectOpened()
	{
		super.projectOpened()

		this.connection.subscribe(AppTopics.FILE_DOCUMENT_SYNC, this)

		this.loadBasToolsOptions()
	}

	/**
	 * Loads .bas-tools.json file.
	 *
	 * @author Bas Milius
	 */
	private fun loadBasToolsOptions(json: ByteArray? = null)
	{
		val basToolsDotJson = json ?: this.project.guessProjectDir().findChild(".bas-tools.json")?.contentsToByteArray()
		val options = BasToolsOptionsLoader.readFromFile(basToolsDotJson)

		if (options?.presenter?.enabled == false)
			this.presenterComponent.disable()
		else
			this.presenterComponent.enable()

		this.options = options
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun beforeDocumentSaving(document: Document)
	{
		val file = FileDocumentManager.getInstance().getFile(document) ?: return

		if (file.name == ".bas-tools.json")
		{
			this.loadBasToolsOptions(document.text.toByteArray())
		}
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun unsavedDocumentsDropped()
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun beforeAllDocumentsSaving()
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun fileWithNoDocumentChanged(p0: VirtualFile)
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun fileContentReloaded(p0: VirtualFile, p1: Document)
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun fileContentLoaded(p0: VirtualFile, p1: Document)
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius
	 */
	override fun beforeFileContentReload(p0: VirtualFile?, p1: Document)
	{
	}

}
