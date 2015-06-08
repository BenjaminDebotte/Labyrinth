package com.benjamindebotte.labyrinth.gameplay;
/**
 * 
 */

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import com.benjamindebotte.labyrinth.containers.Labyrinth;
import com.benjamindebotte.labyrinth.entities.LabyObject;
import com.benjamindebotte.labyrinth.events.Event;
import com.benjamindebotte.labyrinth.events.game.GameEvent;
import com.benjamindebotte.labyrinth.events.input.InputEvent;
import com.benjamindebotte.labyrinth.events.input.KeyboardEvent;

/**
 * @author benjamindebotte
 *
 */
public class Game implements Observer {

	private Timer gameTimer, monstersTimer;
	
	private GameplayHandler gameplayHandler;
	private MoveHandler moveHandler;
	
	public int getScore() {
		return gameplayHandler.getScore();
	}

	private LinkedList<Event> events;
	
	private Labyrinth laby;
	
	private final static int MONSTER_MOVE_RATE = 1000; //ms
	private final static int GAME_RATE = 100;
	
	
	public int getGameRate() {
		return GAME_RATE;
	}


	/**
	 * @throws Exception 
	 * 
	 */
	public Game(int length, int width) throws Exception {
		laby = new Labyrinth(length, width);
		gameplayHandler =  new GameplayHandler(laby);
		moveHandler = new MoveHandler(laby);
		events = new LinkedList<Event>();
		
		
		for(LabyObject obj : laby.getObjects())
			obj.addObserver(this);
		
		
		/* ********* Timers *********/
		gameTimer = new Timer(true);
		monstersTimer = new Timer(true);
		
		gameTimer.scheduleAtFixedRate(new GameTask(), 0, GAME_RATE);
		monstersTimer.scheduleAtFixedRate(new MonstersTask(), 0, MONSTER_MOVE_RATE);

		/*	*	*	*	*	*	*/
		
	}
	
	public Game(Labyrinth laby){
		this.laby = laby;
		gameplayHandler =  new GameplayHandler(laby);
		moveHandler = new MoveHandler(laby);
		events = new LinkedList<Event>();
		
		
		for(LabyObject obj : laby.getObjects())
			obj.addObserver(this);
		
		
		/* ********* Timers *********/

		
		startTimers();

		/*	*	*	*	*	*	*/
	}
	
	public Labyrinth getLabyrinth() {
		return laby;
	}
	

	@Override
	public void update(Observable o, Object arg) {
		if(!(arg instanceof Event)) 
			return;
		events.add((Event)arg);
	}
	
	
	public void pauseTimers() {
		gameTimer.cancel();
		monstersTimer.cancel();
	}
	
	public void startTimers() {
		gameTimer = new Timer(true);
		monstersTimer = new Timer(true);
		gameTimer.scheduleAtFixedRate(new GameTask(), 0, GAME_RATE);
		monstersTimer.scheduleAtFixedRate(new MonstersTask(), 0, MONSTER_MOVE_RATE);
	}
	
	
	private class MonstersTask extends TimerTask {
		public void run() {
			moveHandler.moveAll();
		}
	}
	
	private class GameTask extends TimerTask {
		
		private void processEvent(Event e) {
			if(e instanceof GameEvent) {
				System.out.println("Game Event");
				gameplayHandler.processGameEvent((GameEvent)e);
			} else if(e instanceof InputEvent) {
				if(e instanceof KeyboardEvent)
					moveHandler.processKeyboardEvent((KeyboardEvent) e);
			} else {
				System.out.println("Wtf Event");
			}
		}
		
		private void processEvents() {
			while(!events.isEmpty()) {
				processEvent(events.removeFirst());
			}
		}

		public void run() {
			processEvents();
		}
	}
	
	


	

}
