package fr.photobooth.domain.jgiven.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioRule;
import fr.photobooth.domain.*;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static fr.photobooth.domain.Format.IDENTITY;
import static fr.photobooth.domain.OrderBuilder.aDefaultOrder;
import static fr.photobooth.domain.PictureBuilder.aDefaultPicture;
import static org.mockito.Mockito.mock;

public class GivenAPicture<SELF extends GivenAPicture<?>> extends Stage<SELF> {

    @ScenarioRule
    private TemporaryFolder temporaryFolder = new TemporaryFolder();

    @ProvidedScenarioState
    private Command command;

    @ProvidedScenarioState
    private Validator validator;

    private File pictureToProcess;

    @BeforeStage
    public void setUp() throws IOException {
        pictureToProcess = temporaryFolder.newFile();
    }

    public SELF an_identity_picture_is_taken_by_the_photo_booth() {
        final Order order = aDefaultOrder()
                .withPicture(aDefaultPicture().withFormat(IDENTITY))
                .build();

        command = new Command(order, pictureToProcess);
        return self();
    }

    public SELF the_picture_does_not_respect_identity_picture_standard() {
        validator = mock(Validator.class);
        Mockito.when(validator.validate(command)).thenReturn(false);
        return self();
    }

    public SELF a_$_$_picture_order(Colorimetry colorimetry, Format format) {
        final Order order = aDefaultOrder()
                .withPicture(
                        aDefaultPicture()
                                .withColorimetry(colorimetry)
                                .withFormat(format)
                )
                .build();
        command = new Command(order, pictureToProcess);
        return self();
    }
}
