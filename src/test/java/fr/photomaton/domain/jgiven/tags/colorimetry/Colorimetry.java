package fr.photomaton.domain.jgiven.tags.colorimetry;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IsTag( type = "Feature", value = "Colorimetry",
    description = "" )
@Retention( RetentionPolicy.RUNTIME )
public @interface Colorimetry {

}