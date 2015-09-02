package fr.photobooth.domain.jgiven;

import com.tngtech.jgiven.junit.ScenarioTest;
import fr.photobooth.domain.jgiven.stages.GivenAPictureOrder;
import fr.photobooth.domain.jgiven.stages.ThenPhotoBoothRejectsPictures;
import fr.photobooth.domain.jgiven.stages.WhenThePictureIsMade;
import fr.photobooth.domain.jgiven.tags.colorimetry.Vintage;
import fr.photobooth.domain.jgiven.tags.format.Identity;
import org.junit.Test;

import static fr.photobooth.domain.Colorimetry.VINTAGE;
import static fr.photobooth.domain.Format.IDENTITY;

@Identity
public class IdentityTest extends ScenarioTest<GivenAPictureOrder<?>, WhenThePictureIsMade<?>, ThenPhotoBoothRejectsPictures<?>> {

    @Test
    public void a_user_can_know_if_the_taken_picture_respects_the_standard_imposed_by_the_identity_format() throws Exception {
        given().an_identity_picture_is_taken_by_the_photo_booth()
                .and().the_picture_does_not_respect_identity_picture_standard();

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().the_photo_booth_rejects_it_and_displays_$("This picture does not respect identity picture standard");
    }

    @Vintage
    @Test
    public void a_user_can_not_choose_a_vintage_identity_picture() throws Exception {
        given().a_$_$_picture_order(VINTAGE, IDENTITY);

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().the_photo_booth_rejects_it_and_displays_$("the picture combination IDENTITY VINTAGE is not allowed");
    }
}
