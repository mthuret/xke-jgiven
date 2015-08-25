package fr.photomaton.domain.jgiven.stages;

import com.google.common.io.Resources;
import com.tngtech.jgiven.CurrentStep;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.attachment.Attachment;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.google.common.io.Files.toByteArray;
import static com.tngtech.jgiven.attachment.MediaType.PNG;
import static java.nio.file.Files.isSameFile;
import static org.assertj.core.api.Assertions.assertThat;

public class ThenPhotomatonDisplaysPictures<SELF extends ThenPhotomatonDisplaysPictures<?>> extends Stage<SELF> {

    @ExpectedScenarioState
    private CurrentStep currentStep;

    @ExpectedScenarioState
    private File processedPicture;

    public SELF the_picture_should_be_displayed_four_times() throws Exception {
        assertExpectedPicture("Identity picture", "dog-identity.jpeg");
        return self();
    }

    public SELF a_sepia_effect_should_be_apply_to_the_picture() throws Exception {
        assertExpectedPicture("Sepia Effect", "dog-sepia.jpeg");
        return self();
    }

    public SELF a_black_and_white_effect_should_be_apply_to_the_picture() throws Exception {
        assertExpectedPicture("Mini pictures", "dog-mini.jpeg");
        return self();
    }

    public SELF the_picture_should_be_displayed_sixteen_times() {
        return self();
    }

    public SELF the_photomaton_should_allow_the_photo_taking() {
        assertThat(processedPicture.isFile()).isTrue();
        assertThat(processedPicture.exists()).isTrue();
        return self();
    }

    private void assertExpectedPicture(String title, String resourceName) throws URISyntaxException, IOException {
        File picture = getPicture(resourceName);
        assertThat(isSameFile(processedPicture.toPath(), picture.toPath()));

        currentStep.addAttachment(Attachment
                        .fromBinaryBytes(toByteArray(picture), PNG)
                        .withTitle(title)
        );
    }

    private File getPicture(String resourceName) throws URISyntaxException {
        return new File(Resources.getResource(resourceName).toURI());
    }
}
