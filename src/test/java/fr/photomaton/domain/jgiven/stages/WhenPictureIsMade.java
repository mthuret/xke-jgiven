package fr.photomaton.domain.jgiven.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import fr.photomaton.domain.*;
import fr.photomaton.domain.pictureprocessor.PictureProcessor;
import org.mockito.Mockito;

import java.io.File;

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

    private PhotoMaker photoMaker;

    @BeforeStage
    public void setUp() {
        if (identityValidator == null) {
            identityValidator = mock(Validator.class);
            Mockito.when(identityValidator.validate(any(Command.class))).thenReturn(true);
        }

        photoMaker = new PhotoMaker(
                new PictureProcessor(),
                identityValidator,
                new PriceValidator(),
                new PictureCombinationValidator()
        );
    }

    public SELF the_picture_is_being_processed_by_the_picture_processor() throws Exception {
        processedPicture = catchException(photoMaker).make(command);
        return self();
    }
}
