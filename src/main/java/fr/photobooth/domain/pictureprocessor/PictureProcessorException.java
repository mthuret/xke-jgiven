package fr.photobooth.domain.pictureprocessor;

public class PictureProcessorException extends Exception {

	public PictureProcessorException(String msg, Exception e) {
		super(msg, e);
	}

    public PictureProcessorException(String msg) {
        super(msg);
    }
}
