package com.basmilius.bastools.component.presenter.shortcuts

import com.intellij.openapi.Disposable
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.actionSystem.ex.AnActionListener
import com.intellij.openapi.keymap.MacKeymapUtil
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.util.SystemInfo
import java.awt.Font
import javax.swing.KeyStroke

/**
 * Class Presenter
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.component.presenter.shortcuts
 * @since 1.1.0
 */
class Presenter: Disposable
{

	private val movingActions = setOf("EditorLeft", "EditorRight", "EditorDown", "EditorUp", "EditorLineStart", "EditorLineEnd", "EditorPageUp", "EditorPageDown", "EditorPreviousWord", "EditorNextWord", "EditorScrollUp", "EditorScrollDown", "EditorTextStart", "EditorTextEnd", "EditorDownWithSelection", "EditorUpWithSelection", "EditorRightWithSelection", "EditorLeftWithSelection", "EditorLineStartWithSelection", "EditorLineEndWithSelection", "EditorPageDownWithSelection", "EditorPageUpWithSelection")

	private val typingActions = setOf(IdeActions.ACTION_EDITOR_BACKSPACE, IdeActions.ACTION_EDITOR_ENTER, IdeActions.ACTION_EDITOR_NEXT_TEMPLATE_VARIABLE)

	private val parentGroupIds = setOf("CodeCompletionGroup", "FoldingGroup", "GoToMenu", "IntroduceActionsGroup")

	private var infoPanel: ActionInfoPanel? = null

	private val parentNames = HashMap<String, String>()

	/**
	 * Presenter Constructor.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	init
	{
		enable()
	}

	/**
	 * Fills parent names.
	 *
	 * @param group ActionGroup
	 * @param parentName String
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun fillParentNames(group: ActionGroup, parentName: String)
	{
		val actionManager = ActionManager.getInstance()

		for (item in group.getChildren(null))
		{
			when (item)
			{
				is ActionGroup ->
				{
					if (!item.isPopup) fillParentNames(item, parentName)
				}
				else ->
				{
					val id = actionManager.getId(item)
					if (id != null)
					{
						this.parentNames[id] = parentName
					}
				}
			}
		}
	}

	/**
	 * Enables the presenter.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun enable()
	{
		val actionManager = ActionManager.getInstance()

		this.parentGroupIds
				.map { actionManager.getAction(it) }
				.filterIsInstance<ActionGroup>()
				.forEach { fillParentNames(it, it.templatePresentation.text!!) }

		actionManager.addAnActionListener(object: AnActionListener
		{
			var currentAction: ActionData? = null

			override fun beforeActionPerformed(action: AnAction, dataContext: DataContext, event: AnActionEvent?)
			{
				currentAction = null

				val actionId = ActionManager.getInstance().getId(action) ?: return

				if (!movingActions.contains(actionId) && !typingActions.contains(actionId) && event != null)
				{
					val project = event.project
					val text = event.presentation.text

					currentAction = ActionData(actionId, project, text)
				}
			}

			override fun afterActionPerformed(action: AnAction, dataContext: DataContext, event: AnActionEvent?)
			{
				val actionData = currentAction
				val actionId = ActionManager.getInstance().getId(action)

				if (actionData != null && actionData.actionId === actionId)
				{
					showActionInfo(actionData)
				}
			}

			override fun beforeEditorTyping(c: Char, dataContext: DataContext?)
			{
			}

		}, this)
	}

	/**
	 * Adds text to a mutable list.
	 *
	 * @extension
	 *
	 * @param text String
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun MutableList<Pair<String, Font?>>.addText(text: String)
	{
		this.add(Pair(text, null))
	}

	/**
	 * Shows action info.
	 *
	 * @param actionData ActionData
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun showActionInfo(actionData: ActionData)
	{
		val actionId = actionData.actionId
		val parentGroupName = parentNames[actionId]
		val actionText = (if (parentGroupName != null) "$parentGroupName ${MacKeymapUtil.RIGHT}" else "") + (actionData.actionText ?: "").removeSuffix("...")

		val fragments = ArrayList<Pair<String, Font?>>()
		if (actionText.isNotEmpty())
			fragments.addText("<b>$actionText</b>")

		val mainKeymap = getShortcutPresenter().configuration.mainKeymap
		val shortcutTextFragments = shortcutTextFragments(mainKeymap, actionId, actionText)
		if (shortcutTextFragments.isNotEmpty())
		{
			if (fragments.isNotEmpty())
				fragments.addText(" via&nbsp;")

			fragments.addAll(shortcutTextFragments)
		}

		val alternativeKeymap = getShortcutPresenter().configuration.alternativeKeymap
		if (alternativeKeymap != null)
		{
			val mainShortcut = shortcutText(mainKeymap.getKeymap()?.getShortcuts(actionId), mainKeymap.getKind())
			val altShortcutTextFragments = shortcutTextFragments(alternativeKeymap, actionId, mainShortcut)
			if (altShortcutTextFragments.isNotEmpty())
			{
				fragments.addText("&nbsp;(")
				fragments.addAll(altShortcutTextFragments)
				fragments.addText(")")
			}
		}

		val realProject = actionData.project ?: ProjectManager.getInstance().openProjects.firstOrNull()
		if (realProject != null && !realProject.isDisposed && realProject.isOpen)
		{
			if (infoPanel == null || !infoPanel!!.canBeReused())
			{
				infoPanel = ActionInfoPanel(realProject, fragments)
			}
			else
			{
				infoPanel!!.updateText(realProject, fragments)
			}
		}
	}

	/**
	 * Gets the shortcut text fragments.
	 *
	 * @param keymap KeymapDescription
	 * @param actionId String
	 * @param shownShortcut String
	 *
	 * @return List<Pair<String, Font?>>
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun shortcutTextFragments(keymap: KeymapDescription, actionId: String, shownShortcut: String): List<Pair<String, Font?>>
	{
		val fragments = ArrayList<Pair<String, Font?>>()
		val shortcutText = shortcutText(keymap.getKeymap()?.getShortcuts(actionId), keymap.getKind())
		if (shortcutText.isEmpty() || shortcutText == shownShortcut) return fragments

		when
		{
			keymap.getKind() == KeymapKind.WIN || SystemInfo.isMac ->
			{
				fragments.addText(shortcutText)
			}
			macKeyStrokesFont != null && macKeyStrokesFont!!.canDisplayUpTo(shortcutText) == -1 ->
			{
				fragments.add(Pair(shortcutText, macKeyStrokesFont))
			}
			else ->
			{
				val altShortcutAsWin = shortcutText(keymap.getKeymap()?.getShortcuts(actionId), KeymapKind.WIN)
				if (altShortcutAsWin.isNotEmpty() && shownShortcut != altShortcutAsWin)
				{
					fragments.addText(altShortcutAsWin)
				}
			}
		}
		val keymapText = keymap.displayText
		if (keymapText.isNotEmpty())
		{
			fragments.addText("&nbsp;$keymapText")
		}
		return fragments
	}

	/**
	 * Gets the shortcut text.
	 *
	 * @param shortcuts Array<Shortcut>?
	 * @param keymapKind KeymapKind
	 *
	 * @return String
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun shortcutText(shortcuts: Array<Shortcut>?, keymapKind: KeymapKind): String
	{
		return when
		{
			shortcuts == null || shortcuts.isEmpty() -> ""
			else -> shortcutText(shortcuts[0], keymapKind)
		}
	}

	/**
	 * Gets the shortcut text.
	 *
	 * @param shortcut Shortcut
	 * @param keymapKind KeymapKind
	 *
	 * @return String
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun shortcutText(shortcut: Shortcut, keymapKind: KeymapKind): String
	{
		return when (shortcut)
		{
			is KeyboardShortcut -> arrayOf(shortcut.firstKeyStroke, shortcut.secondKeyStroke).filterNotNull().joinToString(separator = ", ") { shortcutText(it, keymapKind) }
			else -> ""
		}
	}

	/**
	 * Gets the shortcut text.
	 *
	 * @param keystroke KeyStroke
	 * @param keymapKind KeymapKind
	 *
	 * @return String
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	private fun shortcutText(keystroke: KeyStroke, keymapKind: KeymapKind): String
	{
		return when (keymapKind)
		{
			KeymapKind.MAC -> MacKeymapUtil.getKeyStrokeText(keystroke) ?: ""
			KeymapKind.WIN ->
			{
				val modifiers = keystroke.modifiers
				val tokens = arrayOf(if (modifiers > 0) getWinModifiersText(modifiers) else null, getWinKeyText(keystroke.keyCode))
				tokens.filterNotNull().filter { it.isNotEmpty() }.joinToString(separator = "+").trim()
			}
		}
	}

	/**
	 * Disables the presenter.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.1.0
	 */
	fun disable()
	{
		if (infoPanel != null)
		{
			infoPanel!!.close()
			infoPanel = null
		}

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
	}

}
