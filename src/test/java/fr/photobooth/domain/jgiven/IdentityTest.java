package fr.photobooth.domain.jgiven;

import com.tngtech.jgiven.junit.ScenarioTest;
import fr.photobooth.domain.Format;
import fr.photobooth.domain.jgiven.stages.GivenAPictureOrder;
import fr.photobooth.domain.jgiven.stages.WhenPictureIsMade;
import fr.photobooth.domain.jgiven.tags.format.Identity;
import fr.photobooth.domain.jgiven.stages.ThenPhotoBoothRejectOrders;
import fr.photobooth.domain.jgiven.tags.colorimetry.Vintage;
import org.junit.Test;

import static fr.photobooth.domain.Colorimetry.VINTAGE;

@Identity
public class IdentityTest extends ScenarioTest<GivenAPictureOrder<?>, WhenPictureIsMade<?>, ThenPhotoBoothRejectOrders<?>> {

    @Test
    public void a_user_can_know_if_the_taken_picture_respect_the_standard_imposed_by_the_identity_format() throws Throwable {

        given().an_identity_picture_is_taken_by_the_photo_booth()
                .and().the_picture_does_not_respect_identity_picture_standard();

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().the_photo_booth_should_reject_it_and_displayed_the_message(
                "This picture does not respect identity picture standard"
        );
    }

    @Vintage
    @Test
    public void a_user_cant_choose_a_vintage_identity_picture() throws Throwable {

        given().a_$_$_picture_order(VINTAGE, Format.IDENTITY);

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().the_photo_booth_should_reject_it_and_displayed_the_message(
                "the picture combination IDENTITY VINTAGE is not allowed"
        );

    }

}
