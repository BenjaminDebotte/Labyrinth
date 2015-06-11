/**
 *
 */
package com.benjamindebotte.labyrinth.gameplay;

import java.awt.event.KeyEvent;

import com.benjamindebotte.labyrinth.containers.Case;
import com.benjamindebotte.labyrinth.containers.Labyrinth;
import com.benjamindebotte.labyrinth.entities.LabyObject;
import com.benjamindebotte.labyrinth.entities.Monster;
import com.benjamindebotte.labyrinth.entities.Player;
import com.benjamindebotte.labyrinth.events.input.KeyboardEvent;

/**
 * @author benjamindebotte
 *
 */
public class MoveHandler {

	private final Labyrinth laby;

	/**
	 *
	 */
	public MoveHandler(Labyrinth lab) {

		this.laby = lab;
	}

	public void moveAll() {
		for (LabyObject obj : this.laby.getObjects()) {
			if (!(obj instanceof Monster)) {
				continue;
			}

			Monster m = (Monster) obj;
			Case c;
			switch ((int) (Math.random() * 4 + 1)) {
			case 1:
				c = this.laby.getMap().getSouth(m.getCase());
				break;
			case 2:
				c = this.laby.getMap().getWest(m.getCase());
				break;
			case 3:
				c = this.laby.getMap().getNorth(m.getCase());
				break;
			default:
				c = this.laby.getMap().getEast(m.getCase());
				break;
			}

			if (c != null) {
				m.move(c);
			}
		}
	}

	public void processKeyboardEvent(KeyboardEvent evt) {
		Player player = this.laby.getPlayer();
		switch (evt.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			player.move(this.laby.getMap().getWest(player.getCase()));
			break;
		case KeyEvent.VK_RIGHT:
			player.move(this.laby.getMap().getEast(player.getCase()));
			break;
		case KeyEvent.VK_UP:
			player.move(this.laby.getMap().getNorth(player.getCase()));
			break;
		case KeyEvent.VK_DOWN:
			player.move(this.laby.getMap().getSouth(player.getCase()));
			break;
		default:
			return;
		}

		evt.accept();

	}
}
