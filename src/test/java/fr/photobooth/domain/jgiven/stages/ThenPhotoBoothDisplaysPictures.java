package fr.photobooth.domain.jgiven.stages;

import com.tngtech.jgiven.CurrentStep;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import java.io.File;
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
        final File expectedPicture = getPicture("dog-identity.jpeg");
        assertThat(contentOf(processedPicture)).isEqualTo(contentOf(expectedPicture));

        currentStep.addAttachment(
                fromBinaryFile(expectedPicture, PNG)
                        .withTitle("Identity picture")
                        .showDirectly()
        );
        return self();
    }

    private File getPicture(String resourceName) throws URISyntaxException {
        return new File(getResource(resourceName).toURI());
    }
}
