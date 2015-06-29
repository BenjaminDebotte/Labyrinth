/**
 *
 */
package com.benjamindebotte.labyrinth.events;

/**
 * @author benjamindebotte
 * Classe de base d'un Événement. Un Event correspond à une action réalisée dans le labyrinthe qui a des conséquences sur le jeu ou d'autres
 * éléments du labyrinthe.
 */
public class Event {
	boolean accepted;

	/**
	 *
	 */
	public Event() {
		this.accepted = false;
	}

	public void accept() {
		this.accepted = true;
	}

	public void ignore() {
		this.accepted = false;
	}

	public boolean isAccepted() {
		return this.accepted;
	}

}
