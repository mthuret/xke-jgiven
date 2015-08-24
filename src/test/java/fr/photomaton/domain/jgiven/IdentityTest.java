package fr.photomaton.domain.jgiven;

import com.googlecode.catchexception.apis.CatchExceptionAssertJ;
import com.tngtech.jgiven.CurrentStep;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.annotation.ScenarioRule;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import fr.photomaton.domain.Colorimetry;
import fr.photomaton.domain.Command;
import fr.photomaton.domain.Format;
import fr.photomaton.domain.Order;
import fr.photomaton.domain.OrderBuilder;
import fr.photomaton.domain.PhotoMaker;
import fr.photomaton.domain.Picture;
import fr.photomaton.domain.PictureBuilder;
import fr.photomaton.domain.PictureCombinationValidator;
import fr.photomaton.domain.PriceValidator;
import fr.photomaton.domain.Validator;
import fr.photomaton.domain.jgiven.tags.format.Identity;
import fr.photomaton.domain.pictureprocessor.PictureProcessor;
import fr.photomaton.domain.pictureprocessor.PictureProcessorException;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static fr.photomaton.domain.Format.IDENTITY;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

@Identity
public class IdentityTest extends SimpleScenarioTest<IdentityTest.IdentitySteps> {

    @Test
    public void a_user_can_know_if_the_taken_picture_respect_the_standard_imposed_by_the_identity_format() throws Throwable {

        given().an_identity_picture_is_taken_by_the_photomaton()
            .and().the_picture_does_not_respect_identity_picture_standard();

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().the_photomaton_should_reject_it_and_displayed_the_message("This picture does not respect identity picture standard");
    }

    @Test
    public void a_user_cant_choose_a_vintage_identity_picture() throws Throwable {
        given().a_$_$_picture_order(Colorimetry.VINTAGE, Format.IDENTITY);

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().the_photomaton_should_reject_it_and_displayed_the_message("the picture combination IDENTITY VINTAGE is not allowed");

    }

    public static class IdentitySteps extends Stage<IdentitySteps> {

        Picture picture;
        Order order;
        Command command;

        private Validator priceValidator = new PriceValidator();
        private Validator pictureCombinationValidator = new PictureCombinationValidator();
        public PictureProcessor pictureProcessor = mock(PictureProcessor.class);
        private Validator identityValidator = mock(Validator.class);
        @ScenarioRule
        public TemporaryFolder folder = new TemporaryFolder();
        private File pictureToProcess;
        private File processedPicture;
        private PhotoMaker photoMaker;


        @ExpectedScenarioState
        CurrentStep currentStep;

        @BeforeStage
        public void before() throws IOException, PictureProcessorException {
            pictureToProcess = folder.newFile("xx");
            this.photoMaker = new PhotoMaker(pictureProcessor, identityValidator, priceValidator, pictureCombinationValidator);

        }

        public IdentitySteps an_identity_picture_is_taken_by_the_photomaton() {
            Order order = new OrderBuilder()
                .withPicture(new PictureBuilder()
                    .withFormat(IDENTITY)
                    .build())
                .build();
            command = new Command(order, pictureToProcess);
            return self();
        }

        public void the_picture_does_not_respect_identity_picture_standard() {

            Mockito.when(identityValidator.validate(eq(command))).thenReturn(false);

        }

        public void the_picture_is_being_processed_by_the_picture_processor() throws Throwable {
            catchException(photoMaker).make(command);

        }

        public void the_photomaton_should_reject_it_and_displayed_the_message(@Quoted String errorMessage) {
            CatchExceptionAssertJ.then(caughtException())
                .hasMessage(errorMessage);
            // currentStep.addAttachment(Attachment.plainText(errorMessage));
        }

        public void a_$_$_picture_order(Colorimetry colorimetry, Format format) {
            order = new OrderBuilder()
                .withPicture(new PictureBuilder()
                    .withColorimetry(colorimetry)
                    .withFormat(format)
                    .build())
                .build();
            command = new Command(order, pictureToProcess);
        }
    }
}
