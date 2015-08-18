package fr.photomaton.domain.jgiven.tags;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@PictureProcessor
@IsTag(  value = "Effects",
    description = "" )
@Retention( RetentionPolicy.RUNTIME )
public @interface Effects {

}
