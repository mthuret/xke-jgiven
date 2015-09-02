package fr.photobooth.domain.jgiven.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import fr.photobooth.domain.Colorimetry;
import fr.photobooth.domain.Format;
import fr.photobooth.domain.Picture;
import fr.photobooth.domain.PictureBuilder;

import static fr.photobooth.domain.PictureBuilder.aDefaultPicture;

public class GivenAPicture<SELF extends GivenAPicture<?>> extends Stage<SELF> {

    @ProvidedScenarioState
    private Picture picture;

    public SELF a_picture_with_a_certain_price() {
        picture = PictureBuilder.aPicture();
        return self();
    }

    public SELF the_price_of_a_$_$_picture_is_$_euros(Colorimetry colorimetry, Format format, Double picturePrice) {
        picture = aDefaultPicture()
                .withColorimetry(colorimetry)
                .withFormat(format)
                .build();
        return self();
    }
}
