package com.basmilius.bastools.ui.tabs

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.util.messages.MessageBusConnection

/**
 * Class BasToolsTabHighighterComponent
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.ui.tabs
 */
class BasToolsTabHighlighterComponent: ApplicationComponent
{

	private var connection: MessageBusConnection? = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun initComponent()
	{
		this.connection = ApplicationManager.getApplication().messageBus.connect()
		this.connection?.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, BasToolsFileEditorManagerListener())
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun disposeComponent()
	{
		this.connection?.disconnect()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 */
	override fun getComponentName(): String
	{
		return "com.basmilius.bastools.ui.tabs.BasToolsTabHighlighterComponent"
	}

}
