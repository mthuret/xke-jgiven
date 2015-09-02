package fr.photobooth.domain.jgiven.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import fr.photobooth.domain.*;
import fr.photobooth.domain.pictureprocessor.PictureProcessor;
import org.mockito.Mockito;

import java.io.File;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.mockito.Mockito.mock;

public class WhenThePictureIsMade<SELF extends WhenThePictureIsMade<?>> extends Stage<SELF> {

    @ExpectedScenarioState
    private Command command;

    @ExpectedScenarioState
    private Validator validator;

    @ProvidedScenarioState
    private File processedPicture;

    private PhotoMaker photoMaker;

    @BeforeStage
    public void setUp() {
        if (validator == null) {
            validator = mock(Validator.class);
            Mockito.when(validator.validate(command)).thenReturn(true);
        }

        photoMaker = new PhotoMaker(
                new PictureProcessor(),
                validator,
                new PriceValidator(),
                new PictureCombinationValidator()
        );
    }

    public SELF the_picture_is_being_processed_by_the_picture_processor() throws Exception {
        processedPicture = catchException(photoMaker).make(command);
        return self();
    }
}
