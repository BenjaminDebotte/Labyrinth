package com.benjamindebotte.labyrinth.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;
import javax.swing.JLabel;

import com.benjamindebotte.labyrinth.containers.Labyrinth;
import com.benjamindebotte.labyrinth.entities.Entity;
import com.benjamindebotte.labyrinth.entities.LabyObject;
import com.benjamindebotte.labyrinth.entities.Monster;
import com.benjamindebotte.labyrinth.entities.Player;
import com.benjamindebotte.labyrinth.entities.Wall;
import com.benjamindebotte.labyrinth.events.input.KeyboardEvent;
import com.benjamindebotte.labyrinth.gameplay.Game;

public class LabyComponent extends JComponent {

private static final long serialVersionUID = 2337634060590226403L;
	
	private Timer refresh;
	private Game game;

	private JLabel[][] GUI;
	
	private LabyKeyListener listener;
	
	private int FRAME_RATE; //ms
	
	
	public LabyComponent(Game gameHandler) {
		if(gameHandler == null)
			throw new NullPointerException();
		
		this.game = gameHandler;
		
		initKeyListener();		
		initGUI();

		this.setFocusable(true);

		

		
		}
	
	private void initGUI() {
		if(game.getLabyrinth() == null)
			throw new NullPointerException();
		Labyrinth laby = game.getLabyrinth();
		this.setLayout(new GridLayout(laby.getMap().getLength(), laby.getMap().getWidth()));
		GUI = new JLabel[laby.getMap().getLength()][laby.getMap().getWidth()];
		for(int i = 0; i < laby.getMap().getLength(); i++) 
			for(int j = 0; j < laby.getMap().getWidth(); j++){
				GUI[i][j] = new JLabel();
				this.add(GUI[i][j]);
			}
	}
	
	private void initKeyListener() {
		listener = new LabyKeyListener();
		this.addKeyListener(listener);
		listener.addObserver(game);
	}
	
	
	
	@Override
	public void repaint() {
		this.requestFocusInWindow();

		Labyrinth laby = game.getLabyrinth();

			for(int i = 0; i < laby.getMap().getLength(); i++) 
				for(int j = 0; j < laby.getMap().getWidth(); j++) 
					objectToGraphics(GUI[i][j], laby.getMap().getCase(i, j).getObj());

		super.repaint();
	}
	
	
	public Game getGame() {
		return game;
	}
	
	

	public void objectToGraphics(JLabel graphObject, LabyObject obj) {
		graphObject.setOpaque(true);
		
		if(obj instanceof Wall){
			graphObject.setBackground(new Color(12,12,12));
		} else if (obj == null) {
			graphObject.setBackground(new Color(130,130,130));
		} else if(obj instanceof Entity) {
			if(obj instanceof Monster)
				graphObject.setBackground(new Color(255,40,20));
			else if(obj instanceof Player)
				graphObject.setBackground(new Color(255,160,20));
			else
				graphObject.setBackground(new Color(20,130,20));
		}
		
		
		
	}



	private class LabyKeyListener extends Observable implements KeyListener {
		@Override
		public void keyTyped(KeyEvent e) {
		}
	
	
	
		@Override
		public void keyPressed(KeyEvent evt) {	
			this.setChanged();
			notifyObservers(new KeyboardEvent(evt.getKeyCode()));
		}
	
	
	
		@Override
		public void keyReleased(KeyEvent e) {
		}
	}

}
