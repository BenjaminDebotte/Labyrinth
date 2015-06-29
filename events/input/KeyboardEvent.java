package com.benjamindebotte.labyrinth.events.input;

/**
 * @author benjamindebotte
 * Événement indiquant qu'une touche du clavier a été pressée.
 */
public class KeyboardEvent extends InputEvent {
	private int keyCode;

	public KeyboardEvent(int keyCode) {
		this.setKeyCode(keyCode);
	}

	public int getKeyCode() {
		return this.keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

}
