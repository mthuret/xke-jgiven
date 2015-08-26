package fr.photobooth.domain;

public class PictureProcessorFormat implements Format.FormatVisitor {
    @Override
    public String visitPortrait() {
        return "P";
    }

    @Override
    public String visitIdentity() {
        return "I";
    }

    @Override
    public String visitMini() {
        return "M";
    }
}
