package fr.photobooth.domain.jgiven;

import com.tngtech.jgiven.junit.ScenarioTest;
import fr.photobooth.domain.jgiven.stages.GivenAPictureCommand;
import fr.photobooth.domain.jgiven.stages.ThenPhotoBoothRejectOrders;
import fr.photobooth.domain.jgiven.stages.WhenPictureIsMade;
import fr.photobooth.domain.jgiven.tags.colorimetry.Vintage;
import fr.photobooth.domain.jgiven.tags.format.Identity;
import org.junit.Test;

import static fr.photobooth.domain.Colorimetry.VINTAGE;
import static fr.photobooth.domain.Format.IDENTITY;

@Identity
public class ValidateIdentityPicturesTest extends ScenarioTest<GivenAPictureCommand<?>, WhenPictureIsMade<?>, ThenPhotoBoothRejectOrders<?>> {

    @Test
    public void do_not_take_pictures_not_respecting_the_identity_standard_format() throws Throwable {

        given().an_identity_picture_command()
                .and().the_picture_does_not_respect_identity_picture_standard();

        when().the_photo_booth_processes_the_picture_command();

        then().the_photo_booth_should_reject_it_and_display_the_message(
                "This picture does not respect identity picture standard"
        );
    }

    @Vintage
    @Test
    public void reject_vintage_identity_picture_orders() throws Throwable {

        given().a_$_$_picture_command(VINTAGE, IDENTITY);

        when().the_photo_booth_processes_the_picture_command();

        then().the_photo_booth_should_reject_it_and_display_the_message(
                "the picture combination IDENTITY VINTAGE is not allowed"
        );
    }
}