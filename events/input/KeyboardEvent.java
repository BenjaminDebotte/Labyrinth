package com.benjamindebotte.labyrinth.events.input;

public class KeyboardEvent extends InputEvent {
	private int keyCode;
	
	public KeyboardEvent(int keyCode) {
		this.setKeyCode(keyCode);
	}
	
	public int getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

}
