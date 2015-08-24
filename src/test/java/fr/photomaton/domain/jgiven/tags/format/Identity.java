package fr.photomaton.domain.jgiven.tags.format;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Format
@IsTag( value = "Identity",
    description = "" )
@Retention( RetentionPolicy.RUNTIME )
public @interface Identity {

}
