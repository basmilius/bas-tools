/*
 * Copyright © 2018 Bas Milius <bas@mili.us> - All rights reserved.
 *
 * This file is part of Bas Tools, a plugin for the IntelliJ Platform.
 *
 * For the full copyright and license information, please view the
 * LICENSE file that was distributed with this source code.
 */

package com.basmilius.bastools.component.codeMap

import com.basmilius.bastools.component.codeMap.editor.EditorPanelInjector
import com.basmilius.bastools.component.codeMap.renderer.TaskQueueRunner
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.Project

/**
 * Class CodeMapComponent
 *
 * @constructor
 * @param project Project
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.codeMap
 * @since 1.4.0
 */
class CodeMapComponent(private val project: Project): ProjectComponent
{

	private val queue = TaskQueueRunner()
	private val queueThread = Thread(this.queue)

	private val editorInjector: EditorPanelInjector = EditorPanelInjector(this.project, this.queue)

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun getComponentName() = "Bas Tools - Code Map"

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun disposeComponent()
	{
		this.queue.stop()
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun initComponent()
	{
		this.queueThread.start()
		this.project.messageBus
				.connect()
				.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this.editorInjector)
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun projectClosed()
	{
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.4.0
	 */
	override fun projectOpened()
	{
	}

}
