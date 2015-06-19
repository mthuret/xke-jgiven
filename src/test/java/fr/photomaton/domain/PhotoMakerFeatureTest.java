package fr.photomaton.domain;

import com.googlecode.catchexception.apis.CatchExceptionAssertJ;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.*;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import fr.photomaton.domain.tags.Identity;
import fr.photomaton.domain.pictureprocessor.PictureProcessor;
import fr.photomaton.domain.pictureprocessor.PictureProcessorException;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static fr.photomaton.domain.Format.IDENTITY;
import static fr.photomaton.domain.OrderBuilder.anOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
@fr.photomaton.domain.tags.PhotoMaker
public class PhotoMakerFeatureTest extends SimpleScenarioTest<PhotoMakerFeatureTest.PhotoMakerSteps> {

    @Test
    public void should_process_picture() throws Exception {
        //given
        given().a_picture_is_taken_by_the_photomaton();

        //when
        when().the_photomaton_processed_the_picture();

        //then
        then().the_picture_is_the_expected_one();
    }

    @Test
    public void should_throw_machine_exception_when_picture_processor_fail() throws Exception {
        given().a_picture_is_taken_by_the_photomaton();

        when().the_picture_processor_cant_process_the_picture();

        then().an_error_is_displayed();
    }

    @Test
    @Identity
    public void should_validate_identity_picture_before_sending_it_to_picture_processor() throws Exception {
        given().an_identity_picture_is_taken_by_the_photomaton()
                .and().the_picture_does_not_respect_identity_picture_standard();

        when().the_picture_is_being_processed_by_the_picture_processor();

        then().an_not_valid_identity_picture_error_is_displayed();
    }

    public static class PhotoMakerSteps extends Stage<PhotoMakerSteps> {

        @ScenarioRule
        public TemporaryFolder folder = new TemporaryFolder();

        public PictureProcessor pictureProcessor = mock(PictureProcessor.class);
        private Validator identityValidator = mock(Validator.class);
        private Validator priceValidator = new PriceValidator();
        private Validator pictureCombinationValidator = new PictureCombinationValidator();

        public PhotoMaker photoMaker;
        private File pictureToProcess;
        private File expectedPictureProcessed;
        private Command command;
        private File pictureProcessed;

        @BeforeScenario
        public void before() throws IOException {
            pictureToProcess = folder.newFile("xx");
            expectedPictureProcessed = folder.newFile("tt");
            this.photoMaker = new PhotoMaker(pictureProcessor, identityValidator, priceValidator, pictureCombinationValidator);
        }

        public void a_picture_is_taken_by_the_photomaton() throws PictureProcessorException {
            command = new Command(anOrder(), pictureToProcess);
            Mockito.when(pictureProcessor.process(anyString(), eq(pictureToProcess))).thenReturn(expectedPictureProcessed);
        }

        public void the_photomaton_processed_the_picture() throws MachineException, NotAValidIdentityPicture {
            pictureProcessed = photoMaker.make(command);
        }

        public void the_picture_is_the_expected_one() {
            assertThat(pictureProcessed).isEqualTo(expectedPictureProcessed);
        }

        public void the_picture_processor_cant_process_the_picture() throws PictureProcessorException, MachineException, NotAValidIdentityPicture {
            Mockito.when(pictureProcessor.process(anyString(), eq(pictureToProcess))).thenThrow(new PictureProcessorException(null));
            catchException(photoMaker).make(command);
        }

        public void an_error_is_displayed() {
            CatchExceptionAssertJ.then(caughtException())
                    .isInstanceOf(MachineException.class)
                    .hasMessage("Unexpected error when processing picture");
        }

        public PhotoMakerSteps an_identity_picture_is_taken_by_the_photomaton() {
            Order order = new OrderBuilder()
                    .withPicture(new PictureBuilder()
                            .withFormat(IDENTITY)
                            .build())
                    .build();
            command = new Command(order, pictureToProcess);
            return self();
        }

        @ExtendedDescription( "Identity picture must respect certain requierements"
                + "such as no smile, no glasses, no hat in order to be accepted by" +
                "administration" )
        public void the_picture_does_not_respect_identity_picture_standard() {
            Mockito.when(identityValidator.validate(eq(command))).thenReturn(false);
        }

        public void the_picture_is_being_processed_by_the_picture_processor() throws MachineException, NotAValidIdentityPicture {
            catchException(photoMaker).make(command);
        }

        public void an_not_valid_identity_picture_error_is_displayed() {
            CatchExceptionAssertJ.then(caughtException())
                    .isInstanceOf(NotAValidIdentityPicture.class)
                    .hasMessage("This picture does not respect identity picture standard");
        }
    }
}
