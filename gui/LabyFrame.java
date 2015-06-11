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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

import com.benjamindebotte.labyrinth.filesystem.LabyrinthFileHandler;
import com.benjamindebotte.labyrinth.gameplay.Game;
import com.benjamindebotte.labyrinth.gameplay.Game.GAME_STATE;

public class LabyFrame extends JFrame {

	class LabyMenuBar extends JMenuBar {

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
		
		class LabySpinner extends JSpinner {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5822061973675736557L;

			@Override
			public Object getValue() {
				return (int)super.getValue();
			}

			@Override
			public Object getNextValue() {
				return (int)getValue() + 2 > 0 ? (int)getValue() + 2 : getValue();			}

			@Override
			public Object getPreviousValue() {
				return (int)getValue() - 2 > 0 ? (int)getValue() - 2 : getValue();
			}
			
			@Override
			public void setValue(Object obj) {
				if((int)obj % 2 == 0)
					throw new IllegalArgumentException("Les nombres pairs ne sont pas acceptés.");
				super.setValue(obj);
			}
			
		}
		
		private static final long serialVersionUID = 1L;

		public LabyMenuBar() {
			super();
			JMenu FICHIER = new JMenu("Fichier");

			FICHIER.add(new JMenuItem(new AbstractAction("Nouveau Labyrinthe") {

				/**
				 *
				 */
				private static final long serialVersionUID = 6183079068969025429L;

				@Override
				public void actionPerformed(ActionEvent e) {
						try {
							JPanel panel = new JPanel(new GridLayout(0, 2));
							JSpinner spinnerL = new LabySpinner();
							spinnerL.setValue((int)31);
							
							panel.add(new JLabel("Taille des côtés : "));
							panel.add(spinnerL);

						    int result = JOptionPane.showConfirmDialog(null, panel, "Veuillez saisir la dimension de votre labyrinthe", JOptionPane.OK_CANCEL_OPTION);
						    if (result == JOptionPane.OK_OPTION) {
						    	int length = (int) spinnerL.getValue();
						    	int width = length;
						    	
						    	
						    	
						    		LabyFrame.this.setComponent(new LabyComponent(new Game(
									length,width ))); 
						    }
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getClass().toString(), JOptionPane.ERROR_MESSAGE);
						} 

				}
			}));
			FICHIER.add(new JMenuItem(new AbstractAction("Sauvegarder Labyrinthe") {

				/**
				 *
				 */
				private static final long serialVersionUID = -8241691375123168364L;

				@Override
				public void actionPerformed(ActionEvent e) {
					if (LabyFrame.this.currentGame == null)
						return;
					JFileChooser fileChooser = new JFileChooser();
					LabyFrame.this.currentGame.pauseTimers();

					fileChooser.setDialogTitle("Sauvegarder un labyrinthe");

					int userSelection = fileChooser
							.showSaveDialog(LabyMenuBar.this.getParent());

					if (userSelection == JFileChooser.APPROVE_OPTION) {
						LabyrinthFileHandler fh = new LabyrinthFileHandler(
								fileChooser.getSelectedFile().getAbsolutePath());
						try {
							fh.save(LabyFrame.this.currentGame.getLabyrinth());
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getClass().toString(), JOptionPane.ERROR_MESSAGE);
						}
					}
					LabyFrame.this.currentGame.startTimers();

				}
			}));
			FICHIER.add(new JMenuItem(new AbstractAction("Charger Labyrinthe") {

				/**
				 *
				 */
				private static final long serialVersionUID = 5195378510431215349L;

				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Charger un labyrinthe");

					int userSelection = fileChooser
							.showOpenDialog(LabyMenuBar.this.getParent());

					if (userSelection == JFileChooser.APPROVE_OPTION) {
						LabyrinthFileHandler fh = new LabyrinthFileHandler(
								fileChooser.getSelectedFile().getAbsolutePath());
						try {
							LabyFrame.this.currentGame = new Game(fh.load());
							LabyFrame.this.setComponent(new LabyComponent(
									LabyFrame.this.currentGame));
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), e1.getClass().toString(), JOptionPane.ERROR_MESSAGE);

						}
					}

				}
			}));

			this.add(FICHIER);

			this.add(new JMenu("Quitter").add(new QuitAction("Quitter")));
		}

	}

	/**
	 *
	 */
	private static final long serialVersionUID = -8213964782201944325L;
	/**
	 *
	 */
	LabyComponent component;
	Game currentGame;
	int FRAME_RATE;

	Timer refresh;

	public LabyFrame() {
		super();
		this.component = null;
		this.setJMenuBar(new LabyMenuBar());
		this.currentGame = null;
		this.refresh = null;
		this.FRAME_RATE = 0;
		this.setResizable(false);

	}

	@Override
	protected void frameInit() {
		super.frameInit();

		this.setTitle("Labyrinthe");
		int size = Toolkit.getDefaultToolkit().getScreenSize().height - 200;
		this.setBounds(100, 100, size - 50, size);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		this.getContentPane().add(new JLabel("", SwingConstants.CENTER)); // Score
		// &
		// Vie

	}

	private void initTimers() {
		if (this.refresh != null) {
			this.refresh.cancel();
		}

		this.refresh = new Timer(true);
		this.refresh.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				LabyFrame.this.refreshComponent();
			}
		}, 0, this.FRAME_RATE);
	}

	private void refreshComponent() {

		this.refreshGameStatus();
		this.component.refreshGUI();

		this.revalidate();
		this.repaint();

	}

	private void refreshGameStatus() {
		/* Premier component : Texte de Score + Vies */
		((JLabel) this.getContentPane().getComponent(0)).setText("Score : "
				+ this.currentGame.getScore() + "     Vie(s) : "
				+ this.currentGame.getLives());

		if (this.currentGame.isGameEnded()) {
			this.refresh.cancel();

			if (this.currentGame.getGameState() == GAME_STATE.VICTORY) {
				JOptionPane.showMessageDialog(this,
						"Félicitations ! Vous avez gagné !");
			} else if (this.currentGame.getGameState() == GAME_STATE.LOST) {
				JOptionPane.showMessageDialog(this, "Perdu ! :(");
			}

		}

	}

	public void setComponent(LabyComponent labyComponent) {

		if (this.component != null) {
			this.getContentPane().remove(this.component);
		}
		this.getContentPane().add(labyComponent);

		this.component = labyComponent;
		this.currentGame = this.component.getGame();
		this.FRAME_RATE = this.currentGame.getGameRate() / 2;
		this.initTimers();
	}

}
