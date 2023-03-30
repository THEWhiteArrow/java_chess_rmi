package model_server;

import javax.sound.midi.Soundbank;
import java.util.logging.Logger;

public class Chess {

	private String notation;



	public Chess(){
		setNotation("");
	}
	public void setNotation(String notation) {
		this.notation = notation;
	}

	public String getNotation() {
		return notation;
	}

}
