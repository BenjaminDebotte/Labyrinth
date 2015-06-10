package com.benjamindebotte.labyrinth.gui;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.benjamindebotte.labyrinth.filesystem.LabyrinthFileHandler;
import com.benjamindebotte.labyrinth.gameplay.Game;



public class LabyFrame extends JFrame   {

	/**
	 * 
	 */
	LabyComponent component;
	Game currentGame;
	int FRAME_RATE;
	Timer refresh;
	
	public LabyFrame() {
		super();
		component = null;
		setJMenuBar(new LabyMenuBar());
		currentGame = null;
		refresh = null;
		FRAME_RATE = 0;
		this.setResizable(false);
		
	}
	
	protected void frameInit() {
		super.frameInit();
		
		this.setTitle("Labyrinthe");
		int size = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
		this.setBounds(100, 100, size, size);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.getContentPane().add(new JLabel());

	}
	
	private void initTimers() {
		if(refresh != null)
			refresh.cancel();

		refresh = new Timer(true);
		refresh.scheduleAtFixedRate(new TimerTask() {
										public void run() {
											refreshComponent();
										}
									}, 0, FRAME_RATE);
	}

	public void setComponent(LabyComponent labyComponent) {
		
		if(this.component != null) 
			this.getContentPane().remove(this.component);
		this.getContentPane().add(labyComponent);

		this.component = labyComponent;
		this.currentGame = component.getGame();
		FRAME_RATE = currentGame.getGameRate() / 2;
		initTimers();
	}
	
	private void refreshComponent() {
		
		component.refreshGameStatus();
		
		if(this.getContentPane().getComponent(0) instanceof JLabel)
			((JLabel)this.getContentPane().getComponent(0)).setText("Score : " + currentGame.getScore());
		
		component.refreshGUI();
		
		revalidate();		
		repaint();
		

	}

	class LabyMenuBar extends JMenuBar {

		private static final long serialVersionUID = 1L;
		
		public LabyMenuBar() {
			super();
			JMenu FICHIER = new JMenu("Fichier");
			
			FICHIER.add(new JMenuItem(new AbstractAction("Nouveau Labyrinthe") {
		
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						setComponent(new LabyComponent(new Game(21,21))); //TODO : Modifier la taille
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}));
			FICHIER.add(new JMenuItem(new AbstractAction("Sauvegarder Labyrinthe") {
				

				@Override
				public void actionPerformed(ActionEvent e) {
					if(currentGame == null)
						return;
					JFileChooser fileChooser = new JFileChooser();
					currentGame.pauseTimers();
					
					fileChooser.setDialogTitle("Sauvegarder un labyrinthe");    

					int userSelection = fileChooser.showSaveDialog(getParent());

					if (userSelection == JFileChooser.APPROVE_OPTION) {
					    LabyrinthFileHandler fh = new LabyrinthFileHandler(fileChooser.getSelectedFile().getAbsolutePath());
					    try {
							fh.save(currentGame.getLabyrinth());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
			    	currentGame.startTimers();
					
				}
			}));
			FICHIER.add(new JMenuItem(new AbstractAction("Charger Labyrinthe") {
				

				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Charger un labyrinthe");    

					int userSelection = fileChooser.showOpenDialog(getParent());

					if (userSelection == JFileChooser.APPROVE_OPTION) {
					    LabyrinthFileHandler fh = new LabyrinthFileHandler(fileChooser.getSelectedFile().getAbsolutePath());
					    try {
							currentGame = new Game(fh.load());
							setComponent(new LabyComponent(currentGame));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				}
			}));
			
			this.add(FICHIER);
			
			this.add(new JMenu("Quitter").add(new QuitAction("Quitter")));
		}
		
		class QuitAction extends AbstractAction {

			/**
			 * 
			 */
			private static final long serialVersionUID = 7135211342181509137L;
			public QuitAction(String name) {
				super(name);
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
			
		}
		
	
	
}

	
}
