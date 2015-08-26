package fr.photobooth.domain;

public class MachineException extends Exception {

	public MachineException(String msg, Exception e) {
		super(msg, e);
	}
}
