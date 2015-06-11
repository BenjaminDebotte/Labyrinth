package com.benjamindebotte.labyrinth.gui;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

public class MainWindow {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {

					MainWindow window = new MainWindow();
					window.frame.setVisible(true);

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	private final LabyFrame frame;

	/**
	 * Create the application.
	 */
	public MainWindow() {
		this.frame = new LabyFrame();
	}

}
