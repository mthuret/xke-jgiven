package fr.photobooth.domain.jgiven.formatter;

import com.tngtech.jgiven.format.ArgumentFormatter;
import fr.photobooth.domain.Picture;

public class PictureFormatter implements ArgumentFormatter<Picture> {

    @Override
    public String format(Picture picture, String... args ) {
        return picture.colorimetry.name() + picture.format.name();
    }

}

