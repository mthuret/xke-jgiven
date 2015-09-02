package fr.photobooth.domain.jgiven.stages;

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

public class ThenPhotoBoothDisplaysPictures<SELF extends ThenPhotoBoothDisplaysPictures<?>> extends Stage<SELF> {

    @ExpectedScenarioState
    private CurrentStep currentStep;

    @ExpectedScenarioState
    private File processedPicture;

    public SELF a_sepia_effect_should_be_apply_to_the_picture() throws Exception {
        assertExpectedFile("dog-sepia.jpeg", "Sepia effect");
        return self();
    }

    public SELF the_picture_should_be_displayed_four_times() throws Exception {
        assertExpectedFile("dog-identity.jpeg", "Identity");
        return self();
    }

    public SELF the_picture_should_be_displayed_sixteen_times() throws Exception{
        assertExpectedFile("dog-mini.jpeg", "Mini picture");
        return self();
    }

    private File getPicture(String resourceName) throws URISyntaxException {
        return new File(Resources.getResource(resourceName).toURI());
    }

    private void assertExpectedFile(String expectedFileName, String expectedTitle) throws URISyntaxException, IOException {
        final File expectedPicture = getPicture(expectedFileName);
        assertThat(isSameFile(processedPicture.toPath(), expectedPicture.toPath()));

        currentStep.addAttachment(Attachment.
                fromBinaryBytes(toByteArray(processedPicture), PNG)
                .withTitle(expectedTitle));
    }

    public SELF the_photo_booth_should_allow_the_photo_taking() {
        assertThat(processedPicture.isFile()).isTrue();
        assertThat(processedPicture.exists()).isTrue();
        return self();
    }
}
