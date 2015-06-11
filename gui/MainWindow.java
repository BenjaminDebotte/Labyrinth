package com.benjamindebotte.labyrinth.gui;

import java.awt.EventQueue;

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	private LabyFrame frame;

	/**
	 * Create the application.
	 */
	public MainWindow() {
		try {
			this.frame = new LabyFrame();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
