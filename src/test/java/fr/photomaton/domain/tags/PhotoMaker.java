package fr.photomaton.domain.tags;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IsTag( type = "Feature", value = "Photo Maker",
    description = "Take beautiful picture" )
@Retention( RetentionPolicy.RUNTIME )
public @interface PhotoMaker {

}
