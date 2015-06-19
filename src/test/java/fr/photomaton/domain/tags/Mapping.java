package fr.photomaton.domain.tags;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IsTag( type = "Feature", value = "Mapping",
    description = "Should map something" )
@Retention( RetentionPolicy.RUNTIME )
public @interface Mapping {

}
