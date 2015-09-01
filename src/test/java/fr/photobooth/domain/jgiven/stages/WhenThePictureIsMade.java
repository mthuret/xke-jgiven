package fr.photobooth.domain.jgiven.stages;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import fr.photobooth.domain.*;
import fr.photobooth.domain.pictureprocessor.PictureProcessor;

import static com.googlecode.catchexception.CatchException.catchException;

public class WhenThePictureIsMade<SELF extends WhenThePictureIsMade<?>> extends Stage<SELF> {

    @ExpectedScenarioState
    private Command command;

    @ExpectedScenarioState
    private Validator validator;

    private PhotoMaker photoMaker;

    @BeforeStage
    public void setUp() {
        photoMaker = new PhotoMaker(
                new PictureProcessor(),
                validator,
                new PriceValidator(),
                new PictureCombinationValidator()
        );
    }

    public SELF the_picture_is_being_processed_by_the_picture_processor() throws Exception {
        catchException(photoMaker).make(command);
        return self();
    }
}
