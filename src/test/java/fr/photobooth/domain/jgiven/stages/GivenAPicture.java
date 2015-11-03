package fr.photobooth.domain.jgiven.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import fr.photobooth.domain.Colorimetry;
import fr.photobooth.domain.Format;
import fr.photobooth.domain.Picture;

import static fr.photobooth.domain.PictureBuilder.aDefaultPicture;
import static fr.photobooth.domain.PictureBuilder.aPicture;

public class GivenAPicture<SELF extends GivenAPicture<?>> extends Stage<SELF> {

    @ProvidedScenarioState
    private Picture picture;

    public SELF a_picture_with_a_certain_price() {
        picture = aDefaultPicture();
        return self();
    }

    public SELF the_price_of_a_$_$_picture_is_$_euros(Colorimetry colorimetry, Format format, Double picturePrice) {
        picture = aPicture()
                .withColorimetry(colorimetry)
                .withFormat(format)
                .build();
        return self();
    }
}
