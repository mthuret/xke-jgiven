package fr.photobooth.domain.jgiven.stages;

import com.googlecode.catchexception.apis.CatchExceptionAssertJ;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.Quoted;

import static com.googlecode.catchexception.CatchException.caughtException;

public class ThenPhotoBoothRejectOrders<SELF extends ThenPhotoBoothRejectOrders<?>> extends Stage<SELF> {

    public SELF the_photo_booth_should_reject_it_and_display_the_message(@Quoted String expectedMessage) {
        CatchExceptionAssertJ.then(caughtException()).hasMessage(expectedMessage);
        return self();
    }
}
