/**
 *
 */
package com.benjamindebotte.labyrinth.gameplay;

import com.benjamindebotte.labyrinth.entities.Bonus;
import com.benjamindebotte.labyrinth.events.game.BonusRetrievedEvent;
import com.benjamindebotte.labyrinth.events.game.GameEvent;
import com.benjamindebotte.labyrinth.events.game.GameOverEvent;
import com.benjamindebotte.labyrinth.events.game.MonsterEncounterEvent;

/**
 * @author benjamindebotte
 *
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

	public void processGameEvent(GameEvent e) {
		if (e instanceof MonsterEncounterEvent) {
			if (--this.lives == 0) { /* On perd une vie */
				this.currentGame.addEvent(new GameOverEvent(e.getSender()));
				return;
			}
			/* On supprime le monstre. */
			this.currentGame
			.getLabyrinth()
			.getObjects()
			.remove(((MonsterEncounterEvent) e).getEncounteredMonster());

		} else if (e instanceof BonusRetrievedEvent) {
			this.processScoreEvent((BonusRetrievedEvent) e);
		}
	}

	private void processScoreEvent(BonusRetrievedEvent e) {
		this.score += ((Bonus) e.getSender()).getNbPoints();
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

}
