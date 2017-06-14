package com.basmilius.ps.bastools.ui.tabs;

import com.intellij.openapi.application.ex.ApplicationManagerEx;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.ui.JBColor;
import com.intellij.ui.tabs.JBTabsPosition;
import com.intellij.ui.tabs.impl.JBEditorTabs;
import com.intellij.ui.tabs.impl.JBEditorTabsPainter;
import com.intellij.util.ReflectionUtil;
import com.intellij.util.messages.MessageBusConnection;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class BasToolsTabsPainterPatcherComponent implements ApplicationComponent, FileEditorManagerListener
{

	public static final JBColor HighlightColor = new JBColor(new Color(105, 203, 156, 255), new Color(105, 203, 156, 255));
	public static final int HighlightThickness = 2;
	public static final JBColor BackgroundColor = new JBColor(new Color(45, 49, 53, 255), new Color(45, 49, 53, 255));

	private MessageBusConnection connection;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void initComponent ()
	{
		this.connection = ApplicationManagerEx.getApplicationEx().getMessageBus().connect();
		this.connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void disposeComponent ()
	{
		this.connection.disconnect();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void selectionChanged (@NotNull final FileEditorManagerEvent event)
	{
		final FileEditor editor = event.getNewEditor();

		if (editor == null)
			return;

		Component component = editor.getComponent();

		while (component != null)
		{
			if (component instanceof JBEditorTabs)
			{
				this.patchPainter((JBEditorTabs) component);
				return;
			}

			component = component.getParent();
		}
	}

	/**
	 * Patches the painter using reflection.
	 * @param component Tabs component.
	 */
	private void patchPainter (final JBEditorTabs component)
	{
		final JBEditorTabsPainter painter = ReflectionUtil.getField(JBEditorTabs.class, component, JBEditorTabsPainter.class, "myDarkPainter");

		if (painter instanceof BasToolsTabsPainter)
			return;

		final BasToolsTabsPainter tabsPainter = new BasToolsTabsPainter(component);
		final JBEditorTabsPainter proxy = (BasToolsTabsPainter) Enhancer.create(BasToolsTabsPainter.class, (MethodInterceptor) (o, method, objects, methodProxy) ->
		{
			final Object result = method.invoke(tabsPainter, objects);

			if (method.getName().equals("paintSelectionAndBorder"))
			{
				BasToolsTabsPainterPatcherComponent.this.paintSelectionAndBorder(objects, HighlightColor, HighlightThickness, tabsPainter);
			}

			return result;
		});

		ReflectionUtil.setField(JBEditorTabs.class, component, JBEditorTabsPainter.class, "myDarkPainter", proxy);
	}

	/**
	 * Paints the selection and border of a task.
	 *
	 * @param objects         Method parameters.
	 * @param borderColor     Highlight color.
	 * @param borderThickness Highlight thickness.
	 * @param painter         Tab painter.
	 * @throws ClassNotFoundException Exception.
	 * @throws NoSuchFieldException   Exception.
	 * @throws IllegalAccessException Exception.
	 */
	private void paintSelectionAndBorder (final Object[] objects, final Color borderColor, final int borderThickness, final BasToolsTabsPainter painter) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException
	{
		final Graphics2D g = (Graphics2D) objects[0];
		final Rectangle rect = (Rectangle) objects[1];
		final Color tabColor = (Color) objects[4];

		final JBEditorTabs tabsComponent = painter.getTabsComponent();
		final JBTabsPosition position = tabsComponent.getTabsPosition();

		tabsComponent.setBorder(null);
		tabsComponent.getJBTabs().getPresentation().setPaintBorder(0, 0, 0, 0);

		final int x = rect.x;
		final int y = rect.y;
		final int height = rect.height;

		painter.fillSelectionAndBorder(g, tabColor, x, y, rect.width, height);
		g.setColor(borderColor);
		g.setStroke(new BasicStroke(0));

		if (position == JBTabsPosition.bottom)
			g.fillRect(rect.x, rect.y, rect.width, borderThickness);

		else if (position == JBTabsPosition.top)
			g.fillRect(rect.x, rect.y + 1 + rect.height - borderThickness, rect.width, borderThickness);

		else if (position == JBTabsPosition.left)
			g.fillRect(rect.x, rect.y, borderThickness, rect.height);

		else if (position == JBTabsPosition.right)
			g.fillRect(rect.x + rect.width - borderThickness, rect.y, borderThickness, rect.height);
	}

}
