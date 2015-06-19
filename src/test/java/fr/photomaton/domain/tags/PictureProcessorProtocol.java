package fr.photomaton.domain.tags;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IsTag( type = "Feature", value = "Picture Processor Protocol",
    description = "Photomaton use a third party to process the picture" )
@Retention( RetentionPolicy.RUNTIME )
public @interface PictureProcessorProtocol {

}
