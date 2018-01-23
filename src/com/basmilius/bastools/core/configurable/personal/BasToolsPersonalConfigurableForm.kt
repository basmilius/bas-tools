package com.basmilius.bastools.core.configurable.personal

import com.basmilius.bastools.core.configurable.AbstractForm
import com.basmilius.bastools.core.util.JUtils
import com.intellij.ui.border.IdeaTitledBorder
import com.intellij.uiDesigner.core.GridConstraints
import com.intellij.uiDesigner.core.GridLayoutManager
import java.awt.*
import javax.swing.*

/**
 * Class BasToolsPersonalConfigurableForm
 *
 * @constructor
 * @param configurable BasToolsPersonalConfigurable
 *
 * @author Bas Milius <bas@mili.us>
 * @package com.basmilius.bastools.core.configurable.personal
 * @since 1.3.0
 */
class BasToolsPersonalConfigurableForm(private val configurable: BasToolsPersonalConfigurable): AbstractForm<BasToolsPersonalConfigurable.PersonalConfigurationState>()
{

	private val detailsNameField: JTextField = JTextField()
	private val detailsMailField: JTextField = JTextField()

	/**
	 * BasToolsPersonalConfigurableForm Initializer.
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	init
	{
		JUtils.addChangeListener(this.detailsNameField) { this.modified = true }
		JUtils.addChangeListener(this.detailsMailField) { this.modified = true }
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun applyChanges()
	{
		val state = BasToolsPersonalConfigurable.PersonalConfigurationState.getInstance()

		state.fullName = this.detailsNameField.text
		state.eMail = this.detailsMailField.text

		this.modified = false
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun build()
	{
		this.layout = GridLayoutManager(1, 1)
		this.maximumSize = Dimension(505, Int.MAX_VALUE)

		this.add(this.createDetailsPanel(), this.constraints(fill = GridConstraints.FILL_NONE))

		this.load()
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun load()
	{
		this.update(BasToolsPersonalConfigurable.PersonalConfigurationState.getInstance())

		this.modified = false
	}

	/**
	 * {@inheritdoc}
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	override fun update(state: BasToolsPersonalConfigurable.PersonalConfigurationState)
	{
		this.detailsNameField.text = state.fullName
		this.detailsMailField.text = state.eMail
	}

	/**
	 * Creates the personal details panel.
	 *
	 * @return JComponent
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	private fun createDetailsPanel(): JComponent
	{
		val detailsPanel = JPanel()

		this.detailsNameField.preferredSize = Dimension(350, -1)
		this.detailsMailField.preferredSize = Dimension(350, -1)

		detailsPanel.border = IdeaTitledBorder("Personal Details", 0, Insets(12, 0, 6, 0))
		detailsPanel.layout = GridLayoutManager(2, 2)
		detailsPanel.maximumSize = this.maximumSize
		detailsPanel.minimumSize = Dimension(this.maximumSize.width, 0)

		detailsPanel.add(JLabel("Full Name"), this.constraints(column = 0, row = 0))
		detailsPanel.add(this.detailsNameField, this.constraints(column = 1, row = 0, xgrow = GridConstraints.SIZEPOLICY_CAN_GROW and GridConstraints.SIZEPOLICY_WANT_GROW))

		detailsPanel.add(JLabel("E-Mail"), this.constraints(column = 0, row = 1))
		detailsPanel.add(this.detailsMailField, this.constraints(column = 1, row = 1, xgrow = GridConstraints.SIZEPOLICY_CAN_GROW and GridConstraints.SIZEPOLICY_WANT_GROW))

		return detailsPanel
	}

	/**
	 * Creates Grid Constraints.
	 *
	 * @return GridConstraints
	 *
	 * @author Bas Milius <bas@mili.us>
	 * @since 1.3.0
	 */
	private fun constraints(row: Int = 0, column: Int = 0, fill: Int = GridConstraints.FILL_HORIZONTAL, xgrow: Int = GridConstraints.SIZEPOLICY_FIXED, ygrow: Int = GridConstraints.SIZEPOLICY_FIXED): GridConstraints
	{
		val constraints = GridConstraints()

		constraints.anchor = GridConstraints.ANCHOR_NORTHWEST
		constraints.fill = fill

		constraints.column = column
		constraints.row = row

		constraints.hSizePolicy = xgrow
		constraints.vSizePolicy = ygrow

		return constraints
	}

}
