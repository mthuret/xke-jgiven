package fr.photomaton.domain.jgiven;

import com.google.common.io.Resources;
import com.tngtech.jgiven.CurrentStep;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioRule;
import com.tngtech.jgiven.attachment.Attachment;
import com.tngtech.jgiven.attachment.MediaType;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import fr.photomaton.domain.Colorimetry;
import fr.photomaton.domain.Command;
import fr.photomaton.domain.Format;
import fr.photomaton.domain.Order;
import fr.photomaton.domain.OrderBuilder;
import fr.photomaton.domain.OrderToPictureProcessorProtocol;
import fr.photomaton.domain.Picture;
import fr.photomaton.domain.PictureBuilder;
import fr.photomaton.domain.Validator;
import fr.photomaton.domain.jgiven.tags.colorimetry.BlackAndWhite;
import fr.photomaton.domain.jgiven.tags.colorimetry.Color;
import fr.photomaton.domain.jgiven.tags.pictureprocessor.Effects;
import fr.photomaton.domain.jgiven.tags.format.Identity;
import fr.photomaton.domain.jgiven.tags.format.Mini;
import fr.photomaton.domain.jgiven.tags.format.Portrait;
import fr.photomaton.domain.jgiven.tags.colorimetry.Vintage;
import fr.photomaton.domain.pictureprocessor.PictureProcessor;
import fr.photomaton.domain.pictureprocessor.PictureProcessorException;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

import static fr.photomaton.domain.Colorimetry.BLACK_AND_WHITE;
import static fr.photomaton.domain.Colorimetry.COLOR;
import static fr.photomaton.domain.Colorimetry.VINTAGE;
import static fr.photomaton.domain.Format.IDENTITY;
import static fr.photomaton.domain.Format.MINI;
import static fr.photomaton.domain.Format.PORTRAIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

@Effects
public class PictureEffectsTest extends SimpleScenarioTest<PictureEffectsTest.PictureEffectsSteps> {

    @Test
    @Portrait
    @Vintage
    public void a_vintage_picture_has_a_sepia_effect() throws Throwable {

        given().a_$_$_picture_order(VINTAGE, PORTRAIT);

        when().the_picture_processor_processed_the_picture();

        then().a_sepia_effect_should_be_apply_to_the_picture();
    }

    @Test
    @Identity
    @Color
    public void when_ordering_an_identity_picture_you_will_get_four_of_them() throws Throwable {

        given().a_$_$_picture_order(COLOR, IDENTITY);

        when().the_picture_processor_processed_the_picture();

        then().the_picture_should_be_displayed_four_times();
    }


    @Test
    @Mini
    @BlackAndWhite
    public void when_ordering_a_mini_picture_order_you_will_get_sixteen_of_them() throws Throwable {

        given().a_$_$_picture_order(BLACK_AND_WHITE, MINI);

        when().the_picture_processor_processed_the_picture();

        then().a_black_and_white_effect_should_be_apply_to_the_picture()
            .and().the_picture_should_be_displayed_sixteen_times();
    }

    public static class PictureEffectsSteps extends Stage<PictureEffectsSteps> {

        Picture picture;
        Order order;
        Command command;

        private Validator identityValidator = mock(Validator.class);

        @ScenarioRule
        public TemporaryFolder folder = new TemporaryFolder();
        private File pictureToProcess;
        private File processedPicture;

        @ExpectedScenarioState
        CurrentStep currentStep;


        @BeforeStage
        public void before() throws IOException, PictureProcessorException {
            pictureToProcess = folder.newFile("xx");
            Mockito.when(identityValidator.validate(any())).thenReturn(true);
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

        public void the_picture_processor_processed_the_picture() throws Throwable  {
            processedPicture = new PictureProcessor().process(new OrderToPictureProcessorProtocol().convert(command.order),
                getPicture("dog.jpeg"));
        }

        public void the_picture_should_be_displayed_four_times() throws Throwable {
            File picture = getPicture("dog-identity.jpeg");
            assertThat(Files.isSameFile(processedPicture.toPath(), picture.toPath()));
            Attachment attachment = Attachment
                .fromBinaryBytes(com.google.common.io.Files.toByteArray(picture), MediaType.PNG)
                .withTitle("Identity pictures");
            currentStep.addAttachment(attachment);
        }

        public void a_sepia_effect_should_be_apply_to_the_picture() throws Throwable {
            File picture = getPicture("dog-sepia.jpeg");
            assertThat(Files.isSameFile(processedPicture.toPath(), picture.toPath()));
            Attachment attachment = Attachment
                .fromBinaryBytes(com.google.common.io.Files.toByteArray(picture), MediaType.PNG)
                .withTitle("Sepia Effect");
            currentStep.addAttachment(attachment);
        }

        public PictureEffectsSteps a_black_and_white_effect_should_be_apply_to_the_picture() throws Throwable {
            File picture = getPicture("dog-mini.jpeg");
            assertThat(Files.isSameFile(processedPicture.toPath(), picture.toPath()));
            Attachment attachment = Attachment
                .fromBinaryBytes(com.google.common.io.Files.toByteArray(picture), MediaType.PNG)
                .withTitle("Mini pictures");
            currentStep.addAttachment(attachment);
            return self();
        }

        public void the_picture_should_be_displayed_sixteen_times() throws Throwable {
            File picture = getPicture("dog-identity.jpeg");
            assertThat(Files.isSameFile(processedPicture.toPath(), picture.toPath()));
            Attachment attachment = Attachment
                .fromBinaryBytes(com.google.common.io.Files.toByteArray(picture), MediaType.PNG)
                .withTitle("Identity pictures");
            currentStep.addAttachment(attachment);
        }

        private File getPicture(String resourceName) throws URISyntaxException {
            return new File(Resources.getResource(resourceName).toURI());
        }
    }
}
