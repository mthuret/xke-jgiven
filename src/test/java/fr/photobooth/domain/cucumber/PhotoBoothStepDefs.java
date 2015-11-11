package fr.photobooth.domain.cucumber;

import com.google.common.io.Resources;
import com.googlecode.catchexception.apis.CatchExceptionAssertJ;
import com.tngtech.jgiven.annotation.ScenarioRule;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import fr.photobooth.domain.*;
import fr.photobooth.domain.pictureprocessor.PictureProcessor;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static fr.photobooth.domain.OrderBuilder.anOrder;
import static fr.photobooth.domain.PictureBuilder.aDefaultPicture;
import static fr.photobooth.domain.PictureBuilder.aPicture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;

public class PhotoBoothStepDefs {

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

    @Given("^an identity picture command$")
    public void an_identity_picture_command() throws Throwable {
        Order order = anOrder()
                .withPicture(aPicture().withFormat(Format.IDENTITY))
                .build();
        command = new Command(order, pictureToProcess);
    }

    @Given("^the picture does not respect identity picture standard$")
    public void the_picture_does_not_respect_identity_picture_standard() throws Throwable {
        Mockito.when(identityValidator.validate(eq(command))).thenReturn(false);
    }

    @When("^the photobooth processed the picture command$")
    public void the_photobooth_processed_the_picture_command() throws Throwable {
        catchException(photoMaker).make(command);
    }

    @Then("^the photo booth should reject it and displayed \"(.*?)\"$")
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

    @When("^the photo booth processed the picture$")
    public void the_photo_booth_processed_the_picture() throws Throwable {
        the_photobooth_processed_the_picture_command();
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
        final File expectedPicture = getPicture("dog-sepia.jpeg");
        assertThat(contentOf(processedPicture)).isEqualTo(contentOf(expectedPicture));
        scenario.embed(com.google.common.io.Files.toByteArray(expectedPicture), "image/png");
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
        picture = aPicture()
                .withColorimetry(colorimetry)
                .withFormat(format)
                .build();

    }

    @When("^more euros than the price of the wanted picture is given to the photo booth")
    public void more_euros_than_the_price_of_the_wanted_picture_is_given_to_the_photo_booth() throws Throwable {
        order = anOrder()
                .withMoney(picture.price() + 2)
                .withPicture(picture)
                .build();
        command = new Command(order, pictureToProcess);
        catchException(photoMaker).make(command);
    }

    @Then("^the photo booth should allow the photo taking$")
    public void the_photo_booth_should_allow_the_photo_taking() throws Throwable {
        assertThat(pictureProcessed).isEqualTo(expectedPictureProcessed);
    }

    @Given("^a picture with a certain price$")
    public void a_picture_with_a_certain_price() throws Throwable {
        picture = aDefaultPicture();
    }

    @When("^not enough euros is given to the photo booth")
    public void not_enough_euros_is_given_to_the_photo_booth() throws Throwable {
        order = anOrder()
                .withMoney(picture.price() - 1)
                .withPicture(picture)
                .build();
        command = new Command(order, pictureToProcess);
        catchException(photoMaker).make(command);
    }

    @Given("^a \"([^\"]*)\" \"([^\"]*)\" picture command$")
    public void a_picture_command(Colorimetry colorimetry, Format format) throws Throwable {
        order = anOrder()
                .withPicture(aPicture()
                                .withColorimetry(colorimetry)
                                .withFormat(format)
                )
                .build();
        command = new Command(order, pictureToProcess);
    }

    @Given("^a \"([^\"]*)\" \"([^\"]*)\" picture order")
    public void a_picture_order(Colorimetry colorimetry, Format format) throws Throwable {
        a_picture_command(colorimetry, format);
    }

    @When("^(\\d) euros are given to the photo booth$")
    public void eurosAreGivenToThePhotoBooth(double amount) throws Throwable {
        final Order order = anOrder()
                .withMoney(amount)
                .withPicture(picture)
                .build();
        command = new Command(order, pictureToProcess);
    }

    @Given("^a \"([^\"]*)\" \"([^\"]*)\" picture with a price of (\\d) euros$")
    public void aPictureWithAPriceOfEuros(Colorimetry colorimetry, Format format, double price) throws Throwable {
        picture = aPicture()
                .withColorimetry(colorimetry)
                .withFormat(format)
                .build();
    }
}
