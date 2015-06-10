package com.benjamindebotte.labyrinth.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.benjamindebotte.labyrinth.containers.Labyrinth;
import com.benjamindebotte.labyrinth.filesystem.LabyrinthFileHandler;
import com.benjamindebotte.labyrinth.gameplay.Game;

public class MainWindow {

	private LabyFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) { 
		EventQueue.invokeLater(new Runnable() {
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

	/**
	 * Create the application.
	 */
	public MainWindow() {
		try {
			frame = new LabyFrame();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
		

	
	

}
