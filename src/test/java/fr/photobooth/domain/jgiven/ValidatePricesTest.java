package fr.photobooth.domain.jgiven;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.jgiven.annotation.ExtendedDescription;
import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.junit.ScenarioTest;
import fr.photobooth.domain.jgiven.stages.GivenAPicture;
import fr.photobooth.domain.jgiven.stages.ThenPhotoBoothRejectOrders;
import fr.photobooth.domain.jgiven.stages.WhenPaymentIsMade;
import fr.photobooth.domain.jgiven.stages.WhenPictureIsMade;
import fr.photobooth.domain.jgiven.tags.Price;
import fr.photobooth.domain.jgiven.tags.colorimetry.Colorimetry;
import fr.photobooth.domain.jgiven.tags.format.Format;
import org.junit.Test;
import org.junit.runner.RunWith;

import static fr.photobooth.domain.Colorimetry.BLACK_AND_WHITE;
import static fr.photobooth.domain.Format.IDENTITY;

@RunWith(DataProviderRunner.class)
@Colorimetry
@Format
@Price
public class ValidatePricesTest extends ScenarioTest<GivenAPicture<?>, WhenPaymentIsMade<?>, ThenPhotoBoothRejectOrders<?>> {

    @ScenarioStage
    private WhenPictureIsMade<?> $;

    @ExtendedDescription("Price is depending on selected colorimetry and format.")
    @Test
    public void reject_picture_orders_if_required_price_is_not_provided() throws Exception {

        given().a_$_$_picture_with_a_price_of_$_euros(
                BLACK_AND_WHITE,
                IDENTITY,
                BLACK_AND_WHITE.price + IDENTITY.price
        );

        when().$_euros_are_given_to_the_photo_booth(2);
        $.and().the_picture_is_being_processed_by_the_picture_processor();

        then().the_photo_booth_should_reject_it_and_display_the_message("not enough money provided : 2.0");
    }
}