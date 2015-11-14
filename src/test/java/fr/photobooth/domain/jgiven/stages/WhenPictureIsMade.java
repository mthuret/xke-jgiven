package fr.photobooth.domain.jgiven.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioRule;
import fr.photobooth.domain.*;
import fr.photobooth.domain.pictureprocessor.PictureProcessor;
import fr.photobooth.domain.pictureprocessor.PictureProcessorException;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class WhenPictureIsMade<SELF extends WhenPictureIsMade<?>> extends Stage<SELF> {

    @ExpectedScenarioState
    private Command command;

    @ProvidedScenarioState
    private File processedPicture;

    @ProvidedScenarioState
    private Validator identityValidator;

    @ScenarioRule
    public TemporaryFolder folder = new TemporaryFolder();

    private PhotoMaker photoMaker;

    @BeforeStage
    public void setUp() throws PictureProcessorException, IOException {
        if (identityValidator == null) {
            identityValidator = mock(Validator.class);
            Mockito.when(identityValidator.validate(any(Command.class))).thenReturn(true);
        }

        PictureProcessor pictureProcessor = mock(PictureProcessor.class);
        Mockito.when(pictureProcessor.process(any(String.class), any(File.class))).thenReturn(folder.newFile("xx-processed"));

        photoMaker = new PhotoMaker(
            pictureProcessor,
                identityValidator,
                new PriceValidator(),
                new PictureCombinationValidator()
        );
    }

    public SELF the_photo_booth_processes_the_picture_command() throws Exception {
        processedPicture = catchException(photoMaker).make(command);
        return self();
    }
}
