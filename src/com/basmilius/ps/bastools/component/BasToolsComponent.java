package com.basmilius.ps.bastools.component;

import com.basmilius.ps.bastools.component.laf.BasToolsLaf;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.JBColor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class BasToolsComponent implements ApplicationComponent
{

	@Override
	public void initComponent ()
	{
		try
		{
			UIManager.setLookAndFeel(new BasToolsLaf());
			JBColor.setDark(true);
			IconLoader.setUseDarkIcons(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

//		try
//		{
//			final Properties properties = new Properties();
//			properties.load(this.getClass().getClassLoader().getResourceAsStream("laf/bastools.properties"));
//
//			for (final Map.Entry<Object, Object> entry : properties.entrySet())
//			{
//				if (UIManager.getDefaults().contains(entry.getKey()))
//				{
//					UIManager.getDefaults().remove(entry.getKey());
//				}
//
//				UIManager.getDefaults().put(entry.getKey(), entry.getValue());
//			}
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
	}

	@Override
	public void disposeComponent ()
	{

	}

	@NotNull
	@Override
	public String getComponentName ()
	{
		return "Bas Tools";
	}

}
