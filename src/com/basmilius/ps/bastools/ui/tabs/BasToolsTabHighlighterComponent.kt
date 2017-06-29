package com.basmilius.ps.bastools.ui.tabs

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.util.messages.MessageBusConnection

/**
 * Class BasToolsTabHighighterComponent
 *
 * @author Bas Milius
 * @package com.basmilius.ps.bastools.ui.tabs
 */
class BasToolsTabHighlighterComponent : ApplicationComponent
{

	private var connection : MessageBusConnection? = null

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun initComponent()
	{
		this.connection = ApplicationManager.getApplication().messageBus.connect()
		this.connection?.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, BasToolsFileEditorManagerListener())
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun disposeComponent()
	{
		this.connection?.disconnect()
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius
	 */
	override fun getComponentName() : String
	{
		return "com.basmilius.ps.bastools.ui.tabs.BasToolsTabHighlighterComponent"
	}

}
