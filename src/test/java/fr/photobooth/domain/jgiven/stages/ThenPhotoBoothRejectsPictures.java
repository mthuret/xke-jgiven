package fr.photobooth.domain.jgiven.stages;

import com.googlecode.catchexception.apis.CatchExceptionAssertJ;
import com.tngtech.jgiven.Stage;

import static com.googlecode.catchexception.CatchException.caughtException;

public class ThenPhotoBoothRejectsPictures<SELF extends ThenPhotoBoothRejectsPictures<?>> extends Stage<SELF> {

    public SELF the_photo_booth_rejects_it_and_displays_$(String expectedError) {
        CatchExceptionAssertJ.then(caughtException()).hasMessage(expectedError);
        return self();
    }
}
