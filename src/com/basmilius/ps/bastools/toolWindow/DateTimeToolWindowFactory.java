package com.basmilius.ps.bastools.toolWindow;

import com.basmilius.ps.bastools.util.strtotime.DateTimeUtilsKt;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowType;
import com.intellij.openapi.wm.WindowManager;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class DateTimeToolWindowFactory extends BaseToolWindowFactory
{

	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private JPanel rootPane;

	private JButton currentTimestampCopy;
	private JTextField currentTimestamp;
	private JButton currentDateCopy;
	private JTextField currentDate;
	private JTextField convertField;
	private JButton convertButton;
	private JTextField convertResultField;

	@NotNull
	@Override
	protected final JComponent createWindowContent ()
	{
		final Frame frame = WindowManager.getInstance().getFrame(this.getProject());
		final ToolWindow toolWindow = this.getToolWindow();

		if (frame != null && toolWindow != null)
			this.getToolWindow().setDefaultState(ToolWindowAnchor.RIGHT, ToolWindowType.FLOATING, new Rectangle((frame.getBounds().width - (303 + 30)) + frame.getBounds().x, (frame.getBounds().y + 120), 303, 233));

		this.currentDateCopy.addActionListener(this::onCurrentDateCopyClick);
		this.currentTimestampCopy.addActionListener(this::onCurrentTimestampCopyClick);
		this.convertButton.addActionListener(this::onConvertButtonClick);
		this.convertField.addKeyListener(new ConvertFieldKeyListener());

		return this.rootPane;
	}

	@Override
	public final void init (final ToolWindow window)
	{
		super.init(window);

		final Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new Update(), 100, 100L);
	}

	private void onConvertButtonClick (final ActionEvent e)
	{
		if (this.getProject() == null)
			return;

		final String input = this.convertField.getText();

		if (StringUtils.isEmpty(input))
			return;

		try
		{
			final long l = Long.parseLong(input);
			this.convertResultField.setText(this.format.format(new Timestamp(l * 1000)));
		}
		catch (Exception err)
		{
			final Long result = DateTimeUtilsKt.strtotime(input);

			if (result != null)
			{
				this.convertResultField.setText(result.toString());
			}
			else
			{
				this.convertResultField.setText("Could not convert!");
			}
		}
	}

	private void onCurrentDateCopyClick (final ActionEvent e)
	{
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		final StringSelection selection = new StringSelection(this.currentDate.getText());

		clipboard.setContents(selection, selection);
	}

	private void onCurrentTimestampCopyClick (final ActionEvent e)
	{
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		final StringSelection selection = new StringSelection(this.currentTimestamp.getText());

		clipboard.setContents(selection, selection);
	}

	private class Update extends TimerTask
	{

		@Override
		public final void run ()
		{
			DateTimeToolWindowFactory.this.currentDate.setText(DateTimeToolWindowFactory.this.format.format(new Timestamp(System.currentTimeMillis())));
			DateTimeToolWindowFactory.this.currentTimestamp.setText(Long.toString(System.currentTimeMillis() / 1000L));
		}

	}

	private class ConvertFieldKeyListener implements KeyListener
	{

		@Override
		public void keyTyped (KeyEvent e)
		{
		}

		@Override
		public void keyPressed (KeyEvent e)
		{
			if (e.getKeyCode() != KeyEvent.VK_ENTER)
				return;

			DateTimeToolWindowFactory.this.onConvertButtonClick(null);
		}

		@Override
		public void keyReleased (KeyEvent e)
		{
		}

	}

}
