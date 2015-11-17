package fr.photobooth.domain.jgiven;

import com.tngtech.jgiven.annotation.ExtendedDescription;
import com.tngtech.jgiven.junit.ScenarioTest;
import fr.photobooth.domain.jgiven.stages.GivenAPicture;
import fr.photobooth.domain.jgiven.stages.ThenPhotoBoothRejectOrders;
import fr.photobooth.domain.jgiven.stages.WhenPaymentIsMade;
import fr.photobooth.domain.jgiven.tags.Price;
import fr.photobooth.domain.jgiven.tags.colorimetry.Colorimetry;
import fr.photobooth.domain.jgiven.tags.format.Format;
import org.junit.Test;

import static fr.photobooth.domain.Colorimetry.BLACK_AND_WHITE;
import static fr.photobooth.domain.Format.IDENTITY;

@Colorimetry
@Format
@Price
public class ValidatePricesTest extends ScenarioTest<GivenAPicture<?>, WhenPaymentIsMade<?>, ThenPhotoBoothRejectOrders<?>> {

    /*
    Scenario: Reject picture command if required price is not provided

    Given a "BLACK_AND_WHITE" "IDENTITY" picture with a price of 3 euros
    When 2 euros are given to the photo booth
    And the photobooth processed the picture command
    Then the photo booth should reject it and displayed "not enough money provided : 2.0"
    */

    @Test
    public void reject_picture_command_if_required_price_is_not_provided() throws Exception {

        given().a_$_$_picture_with_a_price_of_$_euros(
            BLACK_AND_WHITE,
            IDENTITY,
            BLACK_AND_WHITE.price + IDENTITY.price
        );

        when().$_euros_are_given_to_the_photo_booth(2);

        then().the_photo_booth_should_reject_it_and_display_the_message("not enough money provided : 2.0");
    }

}