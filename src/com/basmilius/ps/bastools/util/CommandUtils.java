package com.basmilius.ps.bastools.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class CommandUtils
{

	/**
	 * Runs a command.
	 * @param path Path to the working directory.
	 * @param command Command that should be executed.
	 */
	public synchronized static void run(final String path, final String command)
	{
		final Thread thread = new Thread(() ->
		{
			final Runtime runtime = Runtime.getRuntime();

			try
			{
				final Process process = runtime.exec(command, null, new File(path));
				final BufferedInputStream in = new BufferedInputStream(process.getInputStream());
				final BufferedReader reader = new BufferedReader(new InputStreamReader(in));

				String lineStr;

				while ((lineStr = reader.readLine()) != null)
				{
					System.out.println(lineStr);
				}

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

}
