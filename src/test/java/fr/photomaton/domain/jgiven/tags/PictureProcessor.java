package fr.photomaton.domain.jgiven.tags;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IsTag( type = "Feature", value = "Picture Processor",
    description = "")
@Retention( RetentionPolicy.RUNTIME )
public @interface PictureProcessor {

}
