package fr.photobooth.domain.jgiven.stages;

import com.tngtech.jgiven.CurrentStep;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.google.common.io.Resources.getResource;
import static com.tngtech.jgiven.attachment.Attachment.fromBinaryFile;
import static com.tngtech.jgiven.attachment.MediaType.PNG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;

public class ThenPhotoBoothDisplaysPictures<SELF extends ThenPhotoBoothDisplaysPictures<?>> extends Stage<SELF> {

    @ExpectedScenarioState
    private CurrentStep currentStep;

    @ExpectedScenarioState
    private File processedPicture;

    public SELF the_picture_should_be_displayed_four_times() throws Exception {
        assertExpectedPicture("dog-identity.jpeg", "Identity picture");
        return self();
    }

    public SELF the_photo_booth_should_allow_the_photo_taking() {
        assertThat(processedPicture.isFile()).isTrue();
        assertThat(processedPicture.exists()).isTrue();
        return self();
    }

    public SELF a_sepia_effect_should_be_apply_to_the_picture() throws IOException, URISyntaxException {
        assertExpectedPicture("dog-sepia.jpeg", "Sepia picture");
        return self();
    }


    public SELF the_picture_should_be_displayed_sixteen_times() throws IOException, URISyntaxException {
        assertExpectedPicture("dog-mini.jpeg", "Mini picture");
        return self();
    }

    private void assertExpectedPicture(String pictureName, String tooltipTitle) throws URISyntaxException, IOException {
        final File expectedPicture = getPicture(pictureName);
        assertThat(contentOf(processedPicture)).isEqualTo(contentOf(expectedPicture));

        currentStep.addAttachment(
                fromBinaryFile(expectedPicture, PNG)
                        .withTitle(tooltipTitle)
                        .showDirectly()
        );
    }

    private File getPicture(String resourceName) throws URISyntaxException {
        return new File(getResource(resourceName).toURI());
    }
}
