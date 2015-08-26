package fr.photobooth.domain;

public class PictureProcessorColorimetry implements Colorimetry.ColorimetryVisitor {
    @Override
    public String visitColor() {
        return "C";
    }

    @Override
    public String visitBlackAndWhite() {
        return "BW";
    }

    @Override
    public String visitVintage() {
        return "V";
    }
}
