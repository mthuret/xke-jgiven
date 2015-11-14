package fr.photobooth.domain.jgiven;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.jgiven.annotation.ExtendedDescription;
import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.junit.ScenarioTest;
import fr.photobooth.domain.jgiven.stages.*;
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

    @ScenarioStage
    private ThenPhotoBoothDisplaysPictures<?> $$;

    @ExtendedDescription("Price is depending on selected colorimetry and format.")
    @Test
    public void reject_picture_command_if_required_price_is_not_provided() throws Exception {

        given().a_$_$_picture_with_a_price_of_$_euros(
                BLACK_AND_WHITE,
                IDENTITY,
                BLACK_AND_WHITE.price + IDENTITY.price
        );

        when().$_euros_are_given_to_the_photo_booth(2);
        $.and().the_photo_booth_processes_the_picture_command();

        then().the_photo_booth_should_reject_it_and_display_the_message("not enough money provided : 2.0");
    }

    @Test
    public void allow_picture_command_if_required_price_is_provided() throws Exception {

        given().a_$_$_picture_with_a_price_of_$_euros(
            BLACK_AND_WHITE,
            IDENTITY,
            BLACK_AND_WHITE.price + IDENTITY.price
        );

        when().$_euros_are_given_to_the_photo_booth(3);
        $.and().the_photo_booth_processes_the_picture_command();

        $$.then().the_photo_booth_should_allow_the_photo_taking();
    }
}