package fr.photomaton.domain;

import org.junit.Test;

import static fr.photomaton.domain.PictureBuilder.aPicture;
import static org.assertj.core.api.Assertions.assertThat;

public class PictureTest {

    @Test
    public void should_picture_price_obtained_from_colorimetry_and_format_prices(){
        //when
        Picture picture = aPicture();

        //then
        assertThat(picture.price()).isEqualTo(picture.colorimetry.price + picture.format.price);
    }


}
