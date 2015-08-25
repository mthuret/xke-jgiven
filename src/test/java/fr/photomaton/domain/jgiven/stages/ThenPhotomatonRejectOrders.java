package fr.photomaton.domain.jgiven.stages;

import com.googlecode.catchexception.apis.CatchExceptionAssertJ;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.Quoted;

import static com.googlecode.catchexception.CatchException.caughtException;

public class ThenPhotomatonRejectOrders<SELF extends ThenPhotomatonRejectOrders<?>> extends Stage<SELF> {

    public SELF the_photomaton_should_reject_it_and_displayed_the_message(@Quoted String expectedMessage) {
        CatchExceptionAssertJ.then(caughtException()).hasMessage(expectedMessage);
        return self();
    }
}
