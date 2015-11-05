package fr.photobooth.domain.jgiven.stages;

import com.tngtech.jgiven.CurrentStep;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import java.io.File;
import java.net.URISyntaxException;

import static com.google.common.io.Resources.getResource;
import static com.tngtech.jgiven.attachment.Attachment.fromBinaryFile;
import static com.tngtech.jgiven.attachment.MediaType.PNG;
import static java.nio.file.Files.isSameFile;
import static org.assertj.core.api.Assertions.assertThat;

public class ThenPhotoBoothDisplaysPictures<SELF extends ThenPhotoBoothDisplaysPictures<?>> extends Stage<SELF> {

    @ExpectedScenarioState
    private CurrentStep currentStep;

    @ExpectedScenarioState
    private File processedPicture;

    public SELF the_picture_should_be_displayed_four_times() throws Exception {
        File picture = getPicture("dog-identity.jpeg");
        assertThat(isSameFile(processedPicture.toPath(), picture.toPath()));

        currentStep.addAttachment(
                fromBinaryFile(picture, PNG)
                        .withTitle("Identity picture")
                        .showDirectly()
        );
        return self();
    }

    public SELF the_photo_booth_should_allow_the_photo_taking() {
        assertThat(processedPicture.isFile()).isTrue();
        assertThat(processedPicture.exists()).isTrue();
        return self();
    }

    private File getPicture(String resourceName) throws URISyntaxException {
        return new File(getResource(resourceName).toURI());
    }
}
