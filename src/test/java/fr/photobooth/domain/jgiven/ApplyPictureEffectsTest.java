package fr.photobooth.domain.jgiven;

import com.tngtech.jgiven.junit.ScenarioTest;
import fr.photobooth.domain.jgiven.stages.GivenAPictureCommand;
import fr.photobooth.domain.jgiven.stages.ThenPhotoBoothDisplaysPictures;
import fr.photobooth.domain.jgiven.stages.WhenPictureIsBeingProcessed;
import fr.photobooth.domain.jgiven.tags.colorimetry.Color;
import fr.photobooth.domain.jgiven.tags.format.Identity;
import fr.photobooth.domain.jgiven.tags.pictureprocessor.Effects;
import org.junit.Test;

import static fr.photobooth.domain.Colorimetry.COLOR;
import static fr.photobooth.domain.Format.IDENTITY;

@Effects
public class ApplyPictureEffectsTest extends ScenarioTest<GivenAPictureCommand<?>, WhenPictureIsBeingProcessed<?>, ThenPhotoBoothDisplaysPictures<?>> {

    @Test
    @Identity
    @Color
    public void display_four_times_the_picture_when_ordering_an_identity_format() throws Throwable {

        given().a_$_$_picture_command(COLOR, IDENTITY);

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().the_picture_should_be_displayed_four_times();
    }
}