package fr.photomaton.domain.jgiven;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.junit.ScenarioTest;
import fr.photomaton.domain.Colorimetry;
import fr.photomaton.domain.Format;
import fr.photomaton.domain.jgiven.stages.*;
import fr.photomaton.domain.jgiven.tags.Price;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
@fr.photomaton.domain.jgiven.tags.colorimetry.Colorimetry
@fr.photomaton.domain.jgiven.tags.format.Format
@Price
public class PriceFeatureTest extends ScenarioTest<GivenAPicture<?>, WhenPaymentIsMade<?>, ThenPhotomatonRejectOrders<?>> {

    @ScenarioStage
    private WhenPictureIsMade<?> $;

    @ScenarioStage
    private ThenPhotomatonDisplaysPictures<?> $$;

    @Test
    @DataProvider({
            "BLACK_AND_WHITE, MINI, 4",
            "BLACK_AND_WHITE, PORTRAIT, 3",
            "COLOR, IDENTITY, 1.5",
            "COLOR, MINI, 4",
            "COLOR, PORTRAIT, 1",
            "VINTAGE, PORTRAIT, 3",
            "VINTAGE, MINI, 4.5"
    })
    public void each_type_of_picture_has_a_different_price_that_needs_to_be_paid_in_order_to_be_taken(Colorimetry colorimetry,
                                                                                                      Format format,
                                                                                                      Double picturePrice) throws Throwable {

        given().the_price_of_a_$_$_picture_is_$_euros(colorimetry, format, picturePrice);

        when().more_euros_than_the_price_of_the_wanted_picture_is_given_to_the_photomaton();
        $.and().the_picture_is_being_processed_by_the_picture_processor();

        $$.then().the_photomaton_should_allow_the_photo_taking();
    }

    @Test
    public void if_the_amount_of_money_given_to_the_photomaton_is_not_enough_comparing_the_picture_price_then_the_picture_cant_be_taken() throws Exception {

        given().a_picture_with_a_certain_price();

        when().not_enough_euros_is_given_to_the_photomaton();
        $.and().the_picture_is_being_processed_by_the_picture_processor();

        then().the_photomaton_should_reject_it_and_displayed_the_message("not enough money provided : 0.0");
    }

}
