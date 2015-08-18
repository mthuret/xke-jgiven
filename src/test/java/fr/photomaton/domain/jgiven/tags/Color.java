package fr.photomaton.domain.jgiven.tags;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Colorimetry
@IsTag( value = "Color",
    description = "" )
@Retention( RetentionPolicy.RUNTIME )
public @interface Color {

}
