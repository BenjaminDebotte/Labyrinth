/**
 *
 */
package com.benjamindebotte.labyrinth.gameplay;

import com.benjamindebotte.labyrinth.entities.Bonus;
import com.benjamindebotte.labyrinth.entities.LabyObject;
import com.benjamindebotte.labyrinth.entities.Malus;
import com.benjamindebotte.labyrinth.events.game.BonusRetrievedEvent;
import com.benjamindebotte.labyrinth.events.game.GameEvent;
import com.benjamindebotte.labyrinth.events.game.GameOverEvent;
import com.benjamindebotte.labyrinth.events.game.MalusRetrievedEvent;
import com.benjamindebotte.labyrinth.events.game.MonsterEncounterEvent;


/**
 * @author benjamindebotte
 * Objet chargé de traiter les événements de type GameEvent. Intéragit avec l'objet Game en cours pour impacter la
 * partie en fonction des événements.
 */
public class GameplayHandler {

	private final Game currentGame;
	private int lives;
	private int score;

	public GameplayHandler(Game g) {
		this.currentGame = g;
		this.score = 0;
		this.lives = 3;
	}

	public int getLives() {
		return this.lives;
	}

	public int getScore() {
		return this.score;
	}

	private void playerHurted(GameEvent e, LabyObject encounteredObject) {
		if (--this.lives == 0) { /* On perd une vie */
			this.currentGame.addEvent(new GameOverEvent(e.getSender()));
			return;
		}
		this.currentGame.getLabyrinth().getObjects().remove(encounteredObject);
	}
	
	public void processGameEvent(GameEvent e) {
		if (e instanceof MonsterEncounterEvent) {
			playerHurted(e,((MonsterEncounterEvent)e).getEncounteredMonster());

		} else if (e instanceof BonusRetrievedEvent) {
			this.processScoreEvent(e);
			this.currentGame.getLabyrinth().getObjects().remove(((BonusRetrievedEvent) e).getSender());
		} else if (e instanceof MalusRetrievedEvent) {
			this.processScoreEvent(e);
			playerHurted(e,((MalusRetrievedEvent)e).getSender());
		}

	}

	private void processScoreEvent(GameEvent e) {
		if (e instanceof BonusRetrievedEvent) {
			this.score += ((Bonus)e.getSender()).getNbPoints();
		} else if (e instanceof MalusRetrievedEvent)
			this.score += ((Malus)e.getSender()).getNbPoints();
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

}
