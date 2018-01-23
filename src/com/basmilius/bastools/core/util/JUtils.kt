package com.basmilius.bastools.core.util

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager
import javax.swing.JComponent
import javax.swing.SwingUtilities
import javax.swing.event.ChangeEvent
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.Document
import javax.swing.text.JTextComponent

/**
 * Object JUtils
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.util
 * @since 1.1.0
 */
object JUtils
{

	/**
	 * Adds a simple change listener to a JTextComponent.
	 *
	 * @param field JTextComponent
	 * @param listener ChangeListener
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun addChangeListener(field: JTextComponent, listener: (event: ChangeEvent) -> Unit)
	{
		val dl = object: DocumentListener
		{

			private var lastChange = 0
			private var lastNotifiedChange = 0

			/**
			 * {@inheritdoc}
			 * @author Bas Milius <bas@mili.us>
			 */
			override fun changedUpdate(e: DocumentEvent?)
			{
				this.lastChange++

				SwingUtilities.invokeLater {
					if (this.lastNotifiedChange != this.lastChange)
					{
						this.lastNotifiedChange = this.lastChange
						listener(ChangeEvent(field))
					}
				}
			}

			/**
			 * {@inheritdoc}
			 * @author Bas Milius <bas@mili.us>
			 */
			override fun insertUpdate(e: DocumentEvent?)
			{
				this.changedUpdate(e)
			}

			/**
			 * {@inheritdoc}
			 * @author Bas Milius <bas@mili.us>
			 */
			override fun removeUpdate(e: DocumentEvent?)
			{
				this.changedUpdate(e)
			}

		}

		field.addPropertyChangeListener("document") {
			(it.oldValue as? Document?)?.removeDocumentListener(dl)
			(it.newValue as? Document?)?.addDocumentListener(dl)

			dl.changedUpdate(null)
		}

		val document = field.document ?: return
		document.addDocumentListener(dl)
	}

	/**
	 * Gets the root component for the project.
	 *
	 * @param project Project.
	 *
	 * @return JComponent
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun getRootComponent(project: Project?): JComponent?
	{
		if (project != null)
		{
			val frame = WindowManager.getInstance().getIdeFrame(project)

			if (frame != null)
			{
				return frame.component
			}
		}

		val frame = WindowManager.getInstance().findVisibleFrame()
		return frame?.rootPane
	}

}
