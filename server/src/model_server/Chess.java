package model_server;

import javax.sound.midi.Soundbank;

public class Chess {

	private String notation;



	public Chess(){
		setNotation("");
	}
	public void setNotation(String notation) {
		System.out.println("The notation was set...");
		this.notation = notation;
	}

	public String getNotation() {
		return notation;
	}

}
