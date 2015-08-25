package fr.photomaton.domain.jgiven.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioRule;
import fr.photomaton.domain.*;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static fr.photomaton.domain.Format.IDENTITY;
import static fr.photomaton.domain.OrderBuilder.aDefaultOrder;
import static fr.photomaton.domain.PictureBuilder.aDefaultPicture;

public class GivenAPictureOrder<SELF extends GivenAPictureOrder<?>> extends Stage<SELF> {

    @ProvidedScenarioState
    private Command command;

    @ProvidedScenarioState
    private Validator validator;

    @ScenarioRule
    private TemporaryFolder folder = new TemporaryFolder();

    private File pictureToProcess;

    @BeforeStage
    public void setUp() throws IOException {
        pictureToProcess = folder.newFile("xx");
    }

    public SELF an_identity_picture_is_taken_by_the_photomaton() {
        Order order = aDefaultOrder()
                .withPicture(aDefaultPicture().withFormat(IDENTITY))
                .build();

        command = new Command(order, pictureToProcess);

        return self();
    }

    public SELF a_$_$_picture_order(Colorimetry colorimetry, Format format) {
        Order order = aDefaultOrder()
                .withPicture(aDefaultPicture()
                                .withColorimetry(colorimetry)
                                .withFormat(format)
                )
                .build();

        command = new Command(order, pictureToProcess);

        return self();
    }

    public SELF the_picture_does_not_respect_identity_picture_standard() {
        validator = Mockito.mock(Validator.class);
        Mockito.when(validator.validate(command)).thenReturn(false);
        return self();
    }
}
