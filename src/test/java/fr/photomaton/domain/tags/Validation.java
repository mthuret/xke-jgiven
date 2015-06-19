package fr.photomaton.domain.tags;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IsTag( type = "Feature", value = "Validation",
    description = "In order to take picture, the photomaton <br>" +
            "needs to validate the order" )
@Retention( RetentionPolicy.RUNTIME )
public @interface Validation {

}
