package com.basmilius.ps.bastools.dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AboutDialog extends JDialog
{

	private JPanel contentPane;
	private JButton buttonOK;

	private AboutDialog ()
	{
		this.setLocationRelativeTo(null);
		this.setSize(new Dimension(360, 240));
		this.setMinimumSize(new Dimension(360, 240));
		this.setMaximumSize(new Dimension(360, 240));
		this.setResizable(false);
		this.setContentPane(contentPane);
		this.setModal(true);
		this.setTitle("About Bas Tools");
		this.getRootPane().setDefaultButton(buttonOK);

		this.buttonOK.addActionListener(e -> onOK());

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter()
		{

			public void windowClosing (WindowEvent e)
			{
				onOK();
			}

		});

		this.contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void onOK ()
	{
		this.dispose();
	}

	public static void showDialog ()
	{
		final AboutDialog dialog = new AboutDialog();
		dialog.pack();
		dialog.setVisible(true);
	}

}
