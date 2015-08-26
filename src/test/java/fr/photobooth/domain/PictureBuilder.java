package fr.photobooth.domain;

import static fr.photobooth.domain.Colorimetry.COLOR;
import static fr.photobooth.domain.Format.PORTRAIT;

public class PictureBuilder {

    private Colorimetry colorimetry = COLOR;
    private Format format = PORTRAIT;

    private PictureBuilder() {
    }

    public static PictureBuilder aDefaultPicture() {
        return new PictureBuilder();
    }

    public static Picture aPicture() {
        return aDefaultPicture().build();
    }

    public Picture build() {
        return new Picture(colorimetry, format);
    }

    public PictureBuilder withColorimetry(Colorimetry colorimetry) {
        this.colorimetry = colorimetry;
        return this;
    }

    public PictureBuilder withFormat(Format format) {
        this.format = format;
        return this;
    }
}
