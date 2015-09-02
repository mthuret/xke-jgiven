package fr.photobooth.domain.jgiven;

import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.junit.ScenarioTest;
import fr.photobooth.domain.Colorimetry;
import fr.photobooth.domain.Format;
import fr.photobooth.domain.jgiven.stages.GivenAPictureOrder;
import fr.photobooth.domain.jgiven.stages.ThenPhotoBoothDisplaysPictures;
import fr.photobooth.domain.jgiven.stages.WhenThePictureIsBeingProcessed;
import fr.photobooth.domain.jgiven.tags.colorimetry.BlackAndWhite;
import fr.photobooth.domain.jgiven.tags.colorimetry.Color;
import fr.photobooth.domain.jgiven.tags.colorimetry.Vintage;
import fr.photobooth.domain.jgiven.tags.format.Identity;
import fr.photobooth.domain.jgiven.tags.format.Mini;
import fr.photobooth.domain.jgiven.tags.format.Portrait;
import fr.photobooth.domain.jgiven.tags.pictureprocessor.Effects;
import org.junit.Test;

import static fr.photobooth.domain.Colorimetry.COLOR;
import static fr.photobooth.domain.Colorimetry.VINTAGE;
import static fr.photobooth.domain.Format.IDENTITY;
import static fr.photobooth.domain.Format.PORTRAIT;

/*
@Effects
Feature: Picture Effects

  @Vintage, @Portrait
  Scenario: a vintage picture has a sepia effect

    Given a "VINTAGE" "PORTRAIT" picture order
    When the picture processor processed the picture
    Then a sepia effect should be apply to the picture

  @Color, @Identity
  Scenario: when ordering an identity picture, you'll get four of them

    Given a "COLOR" "IDENTITY" picture order
    When the picture processor processed the picture
    Then the picture should be displayed four times

  @BlackAndWhite, @Mini
  Scenario: when ordering a mini picture, you'll get 16 of them

    Given a "BLACK_AND_WHITE" "MINI" picture order
    When the picture processor processed the picture
    Then the picture should be displayed 16 times
    And a black and white effect should by apply to the picture
 */
@Effects
public class PictureEffectsTest extends ScenarioTest<GivenAPictureOrder<?>, WhenThePictureIsBeingProcessed<?>, ThenPhotoBoothDisplaysPictures<?>> {

    @Vintage
    @Portrait
    @Test
    public void a_vintage_picture_has_a_sepia_effect() throws Exception {
        given().a_$_$_picture_order(VINTAGE, PORTRAIT);

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().a_sepia_effect_should_be_apply_to_the_picture();
    }

    @As("when ordering an identity picture, you'll get four of them")
    @Color
    @Identity
    @Test
    public void when_ordering_an_identity_picture_you_ll_get_four_of_them() throws Exception {
        given().a_$_$_picture_order(COLOR, IDENTITY);

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().the_picture_should_be_displayed_four_times();
    }

    @As("when ordering a mini picture, you'll get 16 of them")
    @BlackAndWhite
    @Mini
    @Test
    public void when_ordering_a_mini_picture_you_ll_get_sixteen_of_them() throws Exception{
        given().a_$_$_picture_order(Colorimetry.BLACK_AND_WHITE, Format.MINI);

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().the_picture_should_be_displayed_sixteen_times();
    }
}
