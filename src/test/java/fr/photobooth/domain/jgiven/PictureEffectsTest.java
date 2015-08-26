package fr.photobooth.domain.jgiven;

import com.tngtech.jgiven.junit.ScenarioTest;
import fr.photobooth.domain.jgiven.stages.GivenAPictureOrder;
import fr.photobooth.domain.jgiven.stages.ThenPhotoBoothDisplaysPictures;
import fr.photobooth.domain.jgiven.stages.WhenPictureIsBeingProcessed;
import fr.photobooth.domain.jgiven.tags.colorimetry.BlackAndWhite;
import fr.photobooth.domain.jgiven.tags.colorimetry.Color;
import fr.photobooth.domain.jgiven.tags.colorimetry.Vintage;
import fr.photobooth.domain.jgiven.tags.format.Identity;
import fr.photobooth.domain.jgiven.tags.format.Mini;
import fr.photobooth.domain.jgiven.tags.format.Portrait;
import fr.photobooth.domain.jgiven.tags.pictureprocessor.Effects;
import org.junit.Test;

import static fr.photobooth.domain.Colorimetry.*;
import static fr.photobooth.domain.Format.*;

@Effects
public class PictureEffectsTest extends ScenarioTest<GivenAPictureOrder<?>, WhenPictureIsBeingProcessed<?>, ThenPhotoBoothDisplaysPictures<?>> {

    @Test
    @Portrait
    @Vintage
    public void a_vintage_picture_has_a_sepia_effect() throws Throwable {

        given().a_$_$_picture_order(VINTAGE, PORTRAIT);

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().a_sepia_effect_should_be_apply_to_the_picture();
    }

    @Test
    @Identity
    @Color
    public void when_ordering_an_identity_picture_you_will_get_four_of_them() throws Throwable {

        given().a_$_$_picture_order(COLOR, IDENTITY);

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().the_picture_should_be_displayed_four_times();
    }

    @Test
    @Mini
    @BlackAndWhite
    public void when_ordering_a_mini_picture_order_you_will_get_sixteen_of_them() throws Throwable {

        given().a_$_$_picture_order(BLACK_AND_WHITE, MINI);

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().a_black_and_white_effect_should_be_apply_to_the_picture()
                .and().the_picture_should_be_displayed_sixteen_times();
    }

}
