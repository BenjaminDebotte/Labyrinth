/**
 * 
 */
package com.benjamindebotte.labyrinth.gameplay;

import java.awt.event.KeyEvent;

import com.benjamindebotte.labyrinth.containers.*;
import com.benjamindebotte.labyrinth.entities.*;
import com.benjamindebotte.labyrinth.events.input.KeyboardEvent;

/**
 * @author benjamindebotte
 *
 */
public class MoveHandler {

	private Labyrinth laby;
	/**
	 * 
	 */
	public MoveHandler(Labyrinth lab) {

		laby = lab;
	}
	
	public void moveAll() {
		for(LabyObject obj : this.laby.getObjects()){
			if(!(obj instanceof Monster))
				continue;
			
			Monster m = (Monster)obj;
			Case c;
			switch((int)(Math.random()*4 + 1)) {
			case 1:
				c = laby.getMap().getSouth(m.getCase());
				break;
			case 2:
				c = laby.getMap().getWest(m.getCase());
				break;
			case 3:
				c = laby.getMap().getNorth(m.getCase());
				break;
			case 4:
			default:
				c = laby.getMap().getEast(m.getCase());
				break;
			}
			
			if(c != null){
				m.move(c);
			}
		}
	}
	
	public void processKeyboardEvent(KeyboardEvent evt) {
		Player player = laby.getPlayer();
		switch(evt.getKeyCode()){
		case KeyEvent.VK_LEFT:
			player.move(laby.getMap().getWest(player.getCase()));
		     break;
		case KeyEvent.VK_RIGHT:
			player.move(laby.getMap().getEast(player.getCase()));
			break;
		case KeyEvent.VK_UP:
			player.move(laby.getMap().getNorth(player.getCase()));
			break;
		case KeyEvent.VK_DOWN:
			player.move(laby.getMap().getSouth(player.getCase()));
			break;
		default:
			return;				
	    }
		
		evt.accept();
		
	}
}


