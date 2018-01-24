package com.basmilius.bastools.component.presenter.shortcuts

import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopup
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.JBPopupListener
import com.intellij.openapi.ui.popup.LightweightWindowEvent
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.IdeFrame
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.JBColor
import com.intellij.ui.awt.RelativePoint
import com.intellij.ui.components.panels.NonOpaquePanel
import com.intellij.ui.popup.ComponentPopupBuilderImpl
import com.intellij.util.Alarm
import com.intellij.util.ui.Animator
import com.intellij.util.ui.JBEmptyBorder
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import java.awt.*
import javax.swing.*

/**
 * Class ActionInfoPanel
 *
 * @constructor
 * @param project Project
 * @param textFragments List<Pait<String, Font?>>
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.presenter.shortcuts
 * @since 1.1.0
 */
class ActionInfoPanel(project: Project, textFragments: List<Pair<String, Font?>>): NonOpaquePanel(BorderLayout()), Disposable
{

	private val hint: JBPopup
	private val labelsPanel: JPanel
	private val hideAlarm = Alarm(this)
	private var animator: Animator
	private var phase = Phase.FADING_IN
	private val hintAlpha = if (UIUtil.isUnderDarcula()) 0.05.toFloat() else 0.1.toFloat()

	/**
	 * Enum Phase
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.component.presenter.shortcuts.ActionInfoPanel
	 * @since 1.1.0
	 */
	enum class Phase
	{

		FADING_IN,
		SHOWN,
		FADING_OUT,
		HIDDEN

	}

	/**
	 * ActionInfoPanel Constructor.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	init
	{
		val ideFrame = WindowManager.getInstance().getIdeFrame(project)
		labelsPanel = NonOpaquePanel(FlowLayout(FlowLayout.CENTER, 0, 0))

		val background = JBColor(Color(65, 105, 65), Color(65, 105, 65))
		updateLabelText(project, textFragments)
		setBackground(background)
		isOpaque = true

		add(labelsPanel, BorderLayout.CENTER)

		val emptyBorder = JBEmptyBorder(6, 12, 6, 12)
		border = emptyBorder

		hint = with(JBPopupFactory.getInstance().createComponentPopupBuilder(this, this) as ComponentPopupBuilderImpl) {
			setAlpha(1.0.toFloat())
			setFocusable(false)
			setBelongsToGlobalPopupStack(false)
			setCancelKeyEnabled(false)
			setCancelCallback { phase = Phase.HIDDEN; true }
			createPopup()
		}

		hint.addListener(object: JBPopupListener
		{
			override fun beforeShown(lightweightWindowEvent: LightweightWindowEvent?)
			{
			}

			override fun onClosed(lightweightWindowEvent: LightweightWindowEvent?)
			{
				phase = Phase.HIDDEN
			}
		})

		animator = FadeInOutAnimator(true)
		hint.show(computeLocation(ideFrame))
		animator.resume()
	}

	/**
	 * Fade Out.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun fadeOut()
	{
		if (phase != Phase.SHOWN) return
		phase = Phase.FADING_OUT
		Disposer.dispose(animator)
		animator = FadeInOutAnimator(false)
		animator.resume()
	}

	/**
	 * Gets the hint window.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun getHintWindow(): Window?
	{
		if (hint.isDisposed)
			return null

		val window = SwingUtilities.windowForComponent(hint.content)

		if (window != null && window.isShowing)
			return window

		return null
	}

	/**
	 * Sets the alpha
	 *
	 * @param alpha Alpha
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun setAlpha(alpha: Float)
	{
		val window = getHintWindow()
		if (window != null)
		{
			WindowManager.getInstance().setAlphaModeRatio(window, alpha)
		}
	}

	/**
	 * Shows the final panel.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun showFinal()
	{
		phase = Phase.SHOWN
		setAlpha(hintAlpha)
		hideAlarm.cancelAllRequests()
		hideAlarm.addRequest({ fadeOut() }, 4 * 1000)
	}

	/**
	 * Updates the text.
	 *
	 * @param project Project
	 * @param textFragments List<Pair<string, Font?>>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun updateText(project: Project, textFragments: List<Pair<String, Font?>>)
	{
		if (getHintWindow() == null) return
		labelsPanel.removeAll()
		updateLabelText(project, textFragments)
		hint.content.invalidate()

		val ideFrame = WindowManager.getInstance().getIdeFrame(project)
		hint.setLocation(computeLocation(ideFrame).screenPoint)
		hint.size = preferredSize
		hint.content.repaint()

		showFinal()
	}

	/**
	 * Computes the location.
	 *
	 * @param ideFrame IdeFrame
	 *
	 * @return RelativePoint
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun computeLocation(ideFrame: IdeFrame): RelativePoint
	{
		val statusBarHeight = ideFrame.statusBar.component.height
		val visibleRect = ideFrame.component.visibleRect
		val popupSize = preferredSize
		val point = Point(visibleRect.x + (visibleRect.width - popupSize.width) / 2, visibleRect.y + visibleRect.height - popupSize.height - statusBarHeight - 12)

		return RelativePoint(ideFrame.component, point)
	}

	/**
	 * Updates the label text.
	 *
	 * @param project Project
	 * @param textFragments List<Pair<String, Font?>>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun updateLabelText(project: Project, textFragments: List<Pair<String, Font?>>)
	{
		val ideFrame = WindowManager.getInstance().getIdeFrame(project)

		createLabels(textFragments, ideFrame).forEach {
			labelsPanel.add(it)
		}
	}

	/**
	 * Merges text fragments.
	 *
	 * @extension
	 *
	 * @return List<Pair<String, Font?>>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun List<Pair<String, Font?>>.mergeFragments(): List<Pair<String, Font?>>
	{
		val result = ArrayList<Pair<String, Font?>>()

		for (item in this)
		{
			val last = result.lastOrNull()
			if (last != null && last.second == item.second)
			{
				result.removeAt(result.lastIndex)
				result.add(Pair(last.first + item.first, last.second))
			}
			else
			{
				result.add(item)
			}
		}

		return result
	}

	/**
	 * Creates the labels.
	 *
	 * @param textFragments List<Pair<String, Font?>>
	 * @param ideFrame IdeFrame
	 *
	 * @return List<JLabel>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun createLabels(textFragments: List<Pair<String, Font?>>, ideFrame: IdeFrame): List<JLabel>
	{
		var fontSize = getShortcutPresenter().configuration.fontSize.toFloat()
		val labels = textFragments.mergeFragments().map {
			val label = JLabel("<html>${it.first}</html>", SwingConstants.CENTER)
			if (it.second != null) label.font = it.second
			label
		}

		fun setFontSize(size: Float)
		{
			for (label in labels)
				label.font = label.font.deriveFont(JBUI.scale(size))

			val maxAscent = labels.map { it.getFontMetrics(it.font).maxAscent }.max() ?: 0

			for (label in labels)
			{
				val ascent = label.getFontMetrics(label.font).maxAscent
				if (ascent < maxAscent)
				{
					label.border = JBEmptyBorder(maxAscent - ascent, 0, 0, 0)
				}
				else
				{
					label.border = null
				}
			}
		}

		setFontSize(fontSize)

		val frameWidth = ideFrame.component.width

		if (frameWidth > 100)
		{
			while (labels.map { it.preferredSize.width }.sum() > frameWidth - 10 && fontSize > 12)
			{
				setFontSize(--fontSize)
			}
		}

		return labels
	}

	/**
	 * Closes the panel.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun close()
	{
		Disposer.dispose(this)
	}

	/**
	 * {@inheritdoc}
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	override fun dispose()
	{
		phase = Phase.HIDDEN
		if (!hint.isDisposed)
		{
			hint.cancel()
		}
		Disposer.dispose(animator)
	}

	/**
	 * Returns TRUE if the  panel can be reused.
	 *
	 * @return Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun canBeReused(): Boolean = phase == Phase.FADING_IN || phase == Phase.SHOWN

	/**
	 * Class FadeInOutAnimator
	 *
	 * @constructor
	 * @param forward Boolean
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @package com.basmilius.bastools.component.presenter.shortcuts.ActionInfoPanel
	 * @since 1.1.0
	 */
	inner class FadeInOutAnimator(private val forward: Boolean): Animator("Action Hint Fade In/Out", 5, 100, false, forward)
	{

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.1.0
		 */
		override fun paintNow(frame: Int, totalFrames: Int, cycle: Int)
		{
			if (forward && phase != Phase.FADING_IN || !forward && phase != Phase.FADING_OUT)
				return

			setAlpha(hintAlpha + (1 - hintAlpha) * (totalFrames - frame) / totalFrames)
		}

		/**
		 * {@inheritdoc}
		 *
		 * @author Bas Milius <bas@mili.us>
		 * @since 1.1.0
		 */
		override fun paintCycleEnd()
		{
			if (forward)
			{
				showFinal()
			}
			else
			{
				close()
			}
		}
	}

}
