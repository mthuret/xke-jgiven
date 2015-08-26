package fr.photobooth.domain.jgiven.tags.pictureprocessor;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@PictureProcessor
@IsTag(value = "Protocol",
        description = "The photo booth embed a picture processor that is responsible for processing the pictures taken by users. In order to communicate with it, a specific protocol needs to be used. <br>" +
                "This protocol follows the format : <b>\"colorimetry of the picture\";\"format of the picture\"</b>")
@Retention(RetentionPolicy.RUNTIME)
public @interface Protocol {

}
