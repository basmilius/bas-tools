package com.basmilius.ps.bastools.ui.tabs

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ApplicationComponent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.util.messages.MessageBusConnection

class BasToolsTabHighlighterComponent : ApplicationComponent
{

	private var connection: MessageBusConnection? = null

	/**
	 * {@inheritdoc}
	 */
	override fun initComponent()
	{
		this.connection = ApplicationManager.getApplication().messageBus.connect()
		this.connection?.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, BasToolsFileEditorManagerListener())
	}

	/**
	 * {@inheritdoc}
	 */
	override fun disposeComponent()
	{
		this.connection?.disconnect()
	}

	/**
	 * {@inheritdoc}
	 */
	override fun getComponentName(): String
	{
		return "com.basmilius.ps.bastools.ui.tabs.BasToolsTabHighlighterComponent"
	}

}
