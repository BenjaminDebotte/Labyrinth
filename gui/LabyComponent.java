package com.benjamindebotte.labyrinth.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

import com.benjamindebotte.labyrinth.containers.Labyrinth;
import com.benjamindebotte.labyrinth.entities.Bonus;
import com.benjamindebotte.labyrinth.entities.Entity;
import com.benjamindebotte.labyrinth.entities.FinishLine;
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
	private boolean iconsRescaled;
	
	private LabyKeyListener listener;
	private int FRAME_RATE; //ms
	
	/* Icônes pré-chargés */
	private ImageIcon iconPlayer;
	private ImageIcon iconMonster;
	private ImageIcon iconWall;
	private ImageIcon iconItem;
	private ImageIcon iconFloor;
	private ImageIcon iconFinish;
	
	
	public LabyComponent(Game gameHandler) {
		if(gameHandler == null)
			throw new NullPointerException();
		
		this.game = gameHandler;
		
		initKeyListener();	
		
		loadIcons();

		initGUI();
		

		this.setFocusable(true);

		

		
	}
	
	private void loadIcons(){
		/* On charge les icônes */
		iconPlayer = new ImageIcon("./img/Player.png");
		iconWall = new ImageIcon("./img/Wall.png");
		iconItem = new ImageIcon("./img/Item.jpg");
		iconFloor = new ImageIcon("./img/Floor.png");
		iconMonster = new ImageIcon("./img/Monster.png");
		iconFinish = new ImageIcon("./img/FinishLine.png");
		iconsRescaled = false;
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
	
	
	public void refreshGameStatus() {
		
	}
	
	
	public void refreshGUI() {
		
		this.requestFocusInWindow();

		if(!iconsRescaled){
			if(GUI[0][0].getWidth() != 0 && GUI[0][0].getHeight() != 0) {
				iconPlayer.setImage(iconPlayer.getImage().getScaledInstance(GUI[0][0].getWidth(), GUI[0][0].getWidth(), Image.SCALE_FAST));
				iconWall.setImage(iconWall.getImage().getScaledInstance(GUI[0][0].getWidth(), GUI[0][0].getWidth(), Image.SCALE_FAST));
				iconItem.setImage(iconItem.getImage().getScaledInstance(GUI[0][0].getWidth(), GUI[0][0].getWidth(), Image.SCALE_FAST));
				iconFloor.setImage(iconFloor.getImage().getScaledInstance(GUI[0][0].getWidth(), GUI[0][0].getWidth(), Image.SCALE_FAST));
				iconMonster.setImage(iconMonster.getImage().getScaledInstance(GUI[0][0].getWidth(), GUI[0][0].getWidth(), Image.SCALE_FAST));
				iconFinish.setImage(iconFinish.getImage().getScaledInstance(GUI[0][0].getWidth(), GUI[0][0].getWidth(), Image.SCALE_FAST));
				iconsRescaled = true;
			}
		}
		
		
		Labyrinth laby = game.getLabyrinth();

		for(int i = 0; i < laby.getMap().getLength(); i++) 
			for(int j = 0; j < laby.getMap().getWidth(); j++) 
				objectToGraphics(GUI[i][j], laby.getMap().getCase(i, j).getObj());
			
		repaint();
		
	}
	
	@Override
	public void repaint() {
		super.repaint();
	}
	
	
	public Game getGame() {
		return game;
	}
	
	

	public void objectToGraphics(JLabel graphObject, LabyObject obj) {
		graphObject.setOpaque(true);
		ImageIcon icon = null;
		if(obj instanceof Wall){
			icon = iconWall;
		} else if (obj == null) {
			icon = (iconFloor);
		} else if(obj instanceof Entity) {
			if(obj instanceof Monster)
				icon = (iconMonster);
			else if(obj instanceof Player)
				icon = (iconPlayer);
		} else if(obj instanceof Bonus) {
			icon = (iconItem);
		} else if(obj instanceof FinishLine) {
			icon = iconFinish;
		}
		if(icon == null)
			return;
		
		graphObject.setIcon(icon);
		
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
