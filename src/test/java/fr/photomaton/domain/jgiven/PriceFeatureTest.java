package fr.photomaton.domain.jgiven;

import com.googlecode.catchexception.apis.CatchExceptionAssertJ;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ScenarioRule;
import com.tngtech.jgiven.annotation.ScenarioStage;
import com.tngtech.jgiven.junit.ScenarioReportRule;
import com.tngtech.jgiven.junit.StandaloneScenarioRule;
import fr.photomaton.domain.Colorimetry;
import fr.photomaton.domain.Command;
import fr.photomaton.domain.Format;
import fr.photomaton.domain.MachineException;
import fr.photomaton.domain.NotAValidIdentityPicture;
import fr.photomaton.domain.Order;
import fr.photomaton.domain.OrderBuilder;
import fr.photomaton.domain.PhotoMaker;
import fr.photomaton.domain.Picture;
import fr.photomaton.domain.PictureBuilder;
import fr.photomaton.domain.PictureCombinationValidator;
import fr.photomaton.domain.PriceValidator;
import fr.photomaton.domain.Validator;
import fr.photomaton.domain.jgiven.tags.Price;
import fr.photomaton.domain.pictureprocessor.PictureProcessor;
import fr.photomaton.domain.pictureprocessor.PictureProcessorException;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static fr.photomaton.domain.PictureBuilder.aPicture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

@RunWith(DataProviderRunner.class)
@fr.photomaton.domain.jgiven.tags.colorimetry.Colorimetry
@fr.photomaton.domain.jgiven.tags.format.Format
@Price
public class PriceFeatureTest {

    @Rule
    public StandaloneScenarioRule scenarioRule = new StandaloneScenarioRule();

    @ClassRule
    public static ScenarioReportRule reportRule = new ScenarioReportRule();

    @ScenarioStage
    PriceSteps priceSteps;

    @Test
    @DataProvider({
        "COLOR, PORTRAIT, 1",
        "BLACK_AND_WHITE, PORTRAIT, 3",
        "VINTAGE, PORTRAIT, 3",
        "COLOR, IDENTITY, 1.5",
        "VINTAGE, MINI, 4.5",
        "BLACK_AND_WHITE, MINI, 4",
        "COLOR, MINI, 4",
    })
    public void each_type_of_picture_has_a_different_price_that_needs_to_be_paid_in_order_to_be_taken(Colorimetry colorimetry, Format format, Double picturePrice) throws Throwable {

        priceSteps.
            given().the_price_of_a_$_$_is_$_euros(colorimetry, format, picturePrice);

        priceSteps.
            when().more_euros_than_the_price_of_the_wanted_picture_is_given_to_the_photomaton();

        priceSteps.
            then().the_photomation_should_allow_the_photo_taking();
    }

    @Test
    public void if_the_amount_of_money_given_to_the_photomation_is_not_enough_comparing_the_picture_price_then_the_picture_cant_be_taken() throws MachineException, NotAValidIdentityPicture {
        priceSteps.
            given().a_picture_with_a_certain_price();

        priceSteps.
            when().not_enough_euros_is_given_to_the_photomation();

        priceSteps.
            then().the_photomation_should_reject_it_and_displayed_the_message_$("not enough money provided :");
    }

    public static class PriceSteps extends Stage<PriceSteps> {

        Picture picture;
        Order order;
        Command command;
        PhotoMaker photoMaker;

        private Validator priceValidator = new PriceValidator();
        private Validator pictureCombinationValidator = new PictureCombinationValidator();
        public PictureProcessor pictureProcessor = mock(PictureProcessor.class);
        private Validator identityValidator = mock(Validator.class);

        @ScenarioRule
        public TemporaryFolder folder = new TemporaryFolder();
        private File pictureToProcess;
        private File expectedPictureProcessed;
        private File pictureProcessed;
        private double priceGiven;


        @BeforeStage
        public void before() throws IOException, PictureProcessorException {
            pictureToProcess = folder.newFile("xx");
            expectedPictureProcessed = folder.newFile("tt");
            this.photoMaker = new PhotoMaker(pictureProcessor, identityValidator, priceValidator, pictureCombinationValidator);
            Mockito.when(pictureProcessor.process(any(), any())).thenReturn(expectedPictureProcessed);
            Mockito.when(identityValidator.validate(any())).thenReturn(true);
        }

        public void the_price_of_a_$_$_is_$_euros(Colorimetry colorimetry, Format format, Double picturePrice) {
            picture = new PictureBuilder()
                .withColorimetry(colorimetry)
                .withFormat(format)
                .build();
        }

        public void more_euros_than_the_price_of_the_wanted_picture_is_given_to_the_photomaton() throws Throwable {
            order = new OrderBuilder()
                .withMoney(picture.price() + 2)
                .withPicture(picture)
                .build();
            command = new Command(order, pictureToProcess);
            pictureProcessed = photoMaker.make(command);
        }

        public void the_photomation_should_allow_the_photo_taking() {
            assertThat(pictureProcessed).isEqualTo(expectedPictureProcessed);
        }

        public void a_picture_with_a_certain_price() {
            picture = aPicture();
        }

        public void not_enough_euros_is_given_to_the_photomation() throws MachineException, NotAValidIdentityPicture {
            priceGiven = picture.price() - 1;
            order = new OrderBuilder()
                .withMoney(priceGiven)
                .withPicture(picture)
                .build();
            command = new Command(order, pictureToProcess);
            catchException(photoMaker).make(command);
        }

        public void the_photomation_should_reject_it_and_displayed_the_message_$(String errorMessage) {
            CatchExceptionAssertJ.then(caughtException())
                .hasMessage(errorMessage + " " + priceGiven);
        }
    }

}
