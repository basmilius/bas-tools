package com.basmilius.ps.bastools.ui.tabs;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.util.messages.MessageBusConnection;
import org.jetbrains.annotations.NotNull;

public class BasToolsTabHighlighterComponent implements ApplicationComponent
{

	private MessageBusConnection connection;

	@Override
	public void initComponent ()
	{
		this.connection = ApplicationManager.getApplication().getMessageBus().connect();
		this.connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new BasToolsFileEditorManagerListener());
	}

	@Override
	public void disposeComponent ()
	{
		this.connection.disconnect();
	}

	@NotNull
	@Override
	public String getComponentName ()
	{
		return "com.basmilius.ps.bastools.ui.tabs.BasToolsTabHighlighterComponent";
	}

}
