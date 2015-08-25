package fr.photomaton.domain.jgiven.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import fr.photomaton.domain.Colorimetry;
import fr.photomaton.domain.Format;
import fr.photomaton.domain.Picture;

import static fr.photomaton.domain.PictureBuilder.aDefaultPicture;
import static fr.photomaton.domain.PictureBuilder.aPicture;

public class GivenAPicture<SELF extends GivenAPicture<?>> extends Stage<SELF> {

    @ProvidedScenarioState
    private Picture picture;

    public SELF a_picture_with_a_certain_price() {
        picture = aPicture();
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
