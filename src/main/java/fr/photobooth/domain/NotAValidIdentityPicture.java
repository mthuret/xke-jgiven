package fr.photobooth.domain;

public class NotAValidIdentityPicture extends Exception {

	public NotAValidIdentityPicture(String msg, Exception e) {
		super(msg, e);
	}

    public NotAValidIdentityPicture(String msg) {
        super(msg);
    }
}
