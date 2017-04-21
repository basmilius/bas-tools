package com.basmilius.ps.bastools.util;

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
	 * @param path    Path to the working directory.
	 * @param command Command that should be executed.
	 * @param handler Handler.
	 */
	public synchronized static void run (final String path, final String command, final ICommandResultHandler handler)
	{
		final Thread thread = new Thread(() ->
		{
			final Runtime runtime = Runtime.getRuntime();

			try
			{
				final Process process = runtime.exec(command, null, new File(path));
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
		});

		thread.start();
	}

	public interface ICommandResultHandler
	{

		void onCommandResult (final String[] lines);

	}

}
