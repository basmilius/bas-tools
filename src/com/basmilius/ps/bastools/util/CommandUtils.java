package com.basmilius.ps.bastools.util;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandUtils
{

	/**
	 * Runs a command.
	 *
	 * @param project Project that the command should be executed on.
	 * @param command Command that should be executed.
	 * @param handler Handler.
	 */
	public synchronized static void run (final Project project, final String command, final ICommandResultHandler handler)
	{
		if (project == null)
			return;

		ProgressManager.getInstance().run(new Task.Backgroundable(project, "Executing command '" + command + "'")
		{

			@Override
			public void run (@NotNull final ProgressIndicator progress)
			{
				progress.setIndeterminate(true);

				final Runtime runtime = Runtime.getRuntime();

				try
				{
					final Process process = runtime.exec(command, null, new File(project.getBasePath()));
					final BufferedInputStream in = new BufferedInputStream(process.getInputStream());
					final BufferedReader reader = new BufferedReader(new InputStreamReader(in));

					String line;
					final List<String> lines = new ArrayList<>();

					while ((line = reader.readLine()) != null)
					{
						lines.add(line);
					}

					handler.onCommandResult(lines.toArray(new String[lines.size()]));

					process.waitFor();
					reader.close();
					in.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

		});
	}

	public interface ICommandResultHandler
	{

		void onCommandResult (final String[] lines);

	}

}
