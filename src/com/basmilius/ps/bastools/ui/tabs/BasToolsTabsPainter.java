package com.basmilius.ps.bastools.ui.tabs;

import com.intellij.ui.tabs.impl.DefaultEditorTabsPainter;
import com.intellij.ui.tabs.impl.JBEditorTabs;

import java.awt.*;

public class BasToolsTabsPainter extends DefaultEditorTabsPainter
{

	public BasToolsTabsPainter ()
	{
		super(null);
	}

	public BasToolsTabsPainter (JBEditorTabs tabs)
	{
		super(tabs);
	}

	@Override
	public void doPaintInactive (Graphics2D g, Rectangle effectiveBounds, int x, int y, int w, int h, Color color, int row, int column, boolean vertical)
	{
		g.setColor(color != null ? color : this.getDefaultTabColor());
		g.fillRect(x - 1, y - 1, w + 1, h + 1);
	}

	@Override
	public void doPaintBackground (Graphics2D g, Rectangle clip, boolean vertical, Rectangle rectangle)
	{
		g.setColor(this.getDefaultTabColor());
		g.fillRect(rectangle.x - 1, rectangle.y - 1, rectangle.width + 1, rectangle.height + 1);
	}

	public final void fillSelectionAndBorder (final Graphics2D g, final Color color, final int x, final int y, final int width, final int height)
	{
		g.setColor(color != null ? color : this.getDefaultTabColor());
		g.fillRect(x - 1, y - 1, width + 1, height + 1);
	}

	@Override
	public final Color getBackgroundColor ()
	{
		return BasToolsTabsPainterPatcherComponent.BackgroundColor;
	}

	@Override
	public final Color getEmptySpaceColor ()
	{
		return BasToolsTabsPainterPatcherComponent.BackgroundColor;
	}

	@Override
	public final Color getDefaultTabColor ()
	{
		return this.getBackgroundColor();
	}

	@Override
	protected Color getInactiveMaskColor ()
	{
		return this.getBackgroundColor();
	}

	public final JBEditorTabs getTabsComponent ()
	{
		return this.myTabs;
	}

}
