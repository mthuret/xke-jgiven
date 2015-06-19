package fr.photomaton.domain.tags;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IsTag( type = "Feature", value = "Identity Picture",
    description = "The photomaton can take identity picture" )
@Retention( RetentionPolicy.RUNTIME )
public @interface Identity {

}
