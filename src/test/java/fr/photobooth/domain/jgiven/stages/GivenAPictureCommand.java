package fr.photobooth.domain.jgiven.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioRule;
import com.tngtech.jgiven.annotation.Table;
import fr.photobooth.domain.Colorimetry;
import fr.photobooth.domain.Command;
import fr.photobooth.domain.Format;
import fr.photobooth.domain.Order;
import fr.photobooth.domain.Picture;
import fr.photobooth.domain.Validator;
import fr.photobooth.domain.jgiven.formatter.PictureFormatter;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static com.tngtech.jgiven.annotation.Table.HeaderType.VERTICAL;
import static fr.photobooth.domain.Format.IDENTITY;
import static fr.photobooth.domain.OrderBuilder.anOrder;
import static fr.photobooth.domain.PictureBuilder.aPicture;
import static org.mockito.Mockito.mock;

public class GivenAPictureCommand<SELF extends GivenAPictureCommand<?>> extends Stage<SELF> {

    @ProvidedScenarioState
    private Command command;

    @ProvidedScenarioState
    private Validator validator = mock(Validator.class);

    @ScenarioRule
    private TemporaryFolder folder = new TemporaryFolder();

    private File pictureToProcess;

    @BeforeStage
    public void setUp() throws IOException {
        pictureToProcess = folder.newFile("xx");
    }

    public SELF an_identity_picture_command() {
        Order order = anOrder()
            .withPicture(aPicture().withFormat(IDENTITY))
            .build();

        command = new Command(order, pictureToProcess);
        return self();
    }

    public SELF a_$_$_picture_command(Colorimetry colorimetry, Format format) {
        Order order = anOrder()
            .withPicture(
                aPicture()
                    .withColorimetry(colorimetry)
                    .withFormat(format)
            ).build();
        command = new Command(order, pictureToProcess);
        return self();
    }

    public SELF a_picture_command(@Table(header = VERTICAL) Picture picture) {
        Order order = anOrder()
            .withPicture(picture).build();

        command = new Command(order, pictureToProcess);
        return self();
    }

    public SELF a_$_picture_command(@com.tngtech.jgiven.annotation.Format(value = PictureFormatter.class) Picture picture) {
        Order order = anOrder()
            .withPicture(picture).build();

        command = new Command(order, pictureToProcess);
        return self();
    }

    public SELF the_picture_does_not_respect_identity_picture_standard() {
        Mockito.when(validator.validate(command)).thenReturn(false);
        return self();
    }
}
