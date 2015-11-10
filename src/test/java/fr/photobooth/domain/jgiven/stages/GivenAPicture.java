package fr.photobooth.domain.jgiven.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import fr.photobooth.domain.Colorimetry;
import fr.photobooth.domain.Format;
import fr.photobooth.domain.Picture;

import static fr.photobooth.domain.PictureBuilder.aPicture;

public class GivenAPicture<SELF extends GivenAPicture<?>> extends Stage<SELF> {

    @ProvidedScenarioState
    private Picture picture;

    public SELF a_$_$_picture_with_a_price_of_$_euros(Colorimetry colorimetry, Format format, Double picturePrice) {
        picture = aPicture()
                .withColorimetry(colorimetry)
                .withFormat(format)
                .build();
        return self();
    }
}
