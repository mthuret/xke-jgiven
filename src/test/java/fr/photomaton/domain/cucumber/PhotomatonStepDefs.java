package fr.photomaton.domain.cucumber;

import com.google.common.io.Resources;
import com.googlecode.catchexception.apis.CatchExceptionAssertJ;
import com.tngtech.jgiven.annotation.ScenarioRule;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import fr.photomaton.domain.*;
import fr.photomaton.domain.pictureprocessor.PictureProcessor;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static fr.photomaton.domain.Format.IDENTITY;
import static fr.photomaton.domain.PictureBuilder.aPicture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

public class PhotomatonStepDefs {

    @ScenarioRule
    public TemporaryFolder folder = new TemporaryFolder();

    private Order order;
    private OrderToPictureProcessorProtocol orderToPictureProcessorProtocol = new OrderToPictureProcessorProtocol();
    private String pictureProcessorOrder;
    public PictureProcessor pictureProcessor = mock(PictureProcessor.class);
    private Validator identityValidator = mock(Validator.class);
    public PhotoMaker photoMaker;
    private File pictureToProcess;
    private File expectedPictureProcessed;
    private Command command;
    private File pictureProcessed;
    private Validator priceValidator = new PriceValidator();
    private Validator pictureCombinationValidator = new PictureCombinationValidator();
    private Picture picture;
    private File processedPicture;
    private Scenario scenario;


    @org.junit.Before
    public void setupFolder() throws IOException {
        pictureToProcess = folder.newFile("xx");
        expectedPictureProcessed = folder.newFile("tt");
    }

    @Before
    public void setup(Scenario scenario) {
        this.photoMaker = new PhotoMaker(pictureProcessor, identityValidator, priceValidator, pictureCombinationValidator);
        this.scenario = scenario;
    }

    @Given("^an identity picture is taken by the photomaton$")
    public void an_identity_picture_is_taken_by_the_photomaton() throws Throwable {
        Order order = new OrderBuilder()
                .withPicture(new PictureBuilder()
                                .withFormat(IDENTITY)
                )
                .build();
        command = new Command(order, pictureToProcess);
    }

    @Given("^the picture does not respect identity picture standard$")
    public void the_picture_does_not_respect_identity_picture_standard() throws Throwable {
        Mockito.when(identityValidator.validate(eq(command))).thenReturn(false);
    }

    @When("^the picture is being processed by the picture processor$")
    public void the_picture_is_being_processed_by_the_picture_processor() throws Throwable {
        catchException(photoMaker).make(command);
    }

    @Then("^the photomaton should reject it and displayed \"(.*?)\"$")
    public void the_picture_should_be_rejected(String errorMessage) throws Throwable {
        CatchExceptionAssertJ.then(caughtException())
                .hasMessage(errorMessage);
    }

    @When("^an order is converted into picture processor protocol$")
    public void an_order_is_converted_into_picture_processor_protocol() throws Throwable {
        pictureProcessorOrder = orderToPictureProcessorProtocol.convert(order);
    }

    @Then("^the converted order should be \"(.*?)\"$")
    public void the_converted_order_should_be(String pictureProcessorOrder) throws Throwable {
        assertThat(this.pictureProcessorOrder).isEqualTo(pictureProcessorOrder);
    }

    @When("^the photomaton processed the picture$")
    public void the_photomaton_processed_the_picture() throws Throwable {
        the_picture_is_being_processed_by_the_picture_processor();
    }

    @When("^the picture processor processed the picture$")
    public void the_picture_processor_processed_the_picture() throws Throwable {
        processedPicture = new PictureProcessor().process(new OrderToPictureProcessorProtocol().convert(command.order),
                getPicture("dog.jpeg"));
    }

    private File getPicture(String resourceName) throws URISyntaxException {
        return new File(Resources.getResource(resourceName).toURI());
    }

    @Then("^a sepia effect should be apply to the picture$")
    public void a_sepia_effect_should_be_apply_to_the_picture() throws Throwable {
        File picture = getPicture("dog-sepia.jpeg");
        assertThat(Files.isSameFile(processedPicture.toPath(), picture.toPath()));
        scenario.embed(com.google.common.io.Files.toByteArray(picture), "image/png");
    }

    @Then("^the picture should be displayed four times$")
    public void the_picture_should_be_displayed_four_times() throws Throwable {
        File picture = getPicture("dog-identity.jpeg");
        assertThat(Files.isSameFile(processedPicture.toPath(), picture.toPath()));
        scenario.embed(com.google.common.io.Files.toByteArray(picture), "image/png");
    }

    @Then("^the picture should be displayed (\\d+) times$")
    public void the_picture_should_be_displayed_times(int arg1) throws Throwable {
        File picture = getPicture("dog-mini.jpeg");
        assertThat(Files.isSameFile(processedPicture.toPath(), picture.toPath()));
    }

    @Then("^a black and white effect should by apply to the picture$")
    public void a_black_and_white_effect_should_by_apply_to_the_picture() throws Throwable {
        File picture = getPicture("dog-mini.jpeg");
        assertThat(Files.isSameFile(processedPicture.toPath(), picture.toPath()));
        scenario.embed(com.google.common.io.Files.toByteArray(picture), "image/png");
    }


    @Given("^the price of a \"(.*?)\" \"(.*?)\" is \"(.*?)\" euros$")
    public void the_price_of_a_is_euros(Colorimetry colorimetry, Format format, String picturePrice) throws Throwable {
        picture = new PictureBuilder()
                .withColorimetry(colorimetry)
                .withFormat(format)
                .build();

    }

    @When("^more euros than the price of the wanted picture is given to the photomaton$")
    public void more_euros_than_the_price_of_the_wanted_picture_is_given_to_the_photomaton() throws Throwable {
        order = new OrderBuilder()
                .withMoney(picture.price() + 2)
                .withPicture(picture)
                .build();
        command = new Command(order, pictureToProcess);
        catchException(photoMaker).make(command);
    }

    @Then("^the photomaton should allow the photo taking$")
    public void the_photomaton_should_allow_the_photo_taking() throws Throwable {
        assertThat(pictureProcessed).isEqualTo(expectedPictureProcessed);
    }

    @Given("^a picture with a certain price$")
    public void a_picture_with_a_certain_price() throws Throwable {
        picture = aPicture();
    }

    @When("^not enough euros is given to the photomaton$")
    public void not_enough_euros_is_given_to_the_photomaton() throws Throwable {
        order = new OrderBuilder()
                .withMoney(picture.price() - 1)
                .withPicture(picture)
                .build();
        command = new Command(order, pictureToProcess);
        catchException(photoMaker).make(command);
    }

    @Given("^a \"([^\"]*)\" \"([^\"]*)\" picture order$")
    public void a_picture_order(Colorimetry colorimetry, Format format) throws Throwable {
        order = new OrderBuilder()
                .withPicture(new PictureBuilder()
                                .withColorimetry(colorimetry)
                                .withFormat(format)
                )
                .build();
        command = new Command(order, pictureToProcess);
    }
}
