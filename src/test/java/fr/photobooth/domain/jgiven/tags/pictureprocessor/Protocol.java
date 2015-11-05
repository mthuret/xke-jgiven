package fr.photobooth.domain.jgiven.tags.pictureprocessor;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static fr.photobooth.domain.jgiven.tags.TagColor.PICTURE_PROCESSOR_COLOR;

@PictureProcessor
@IsTag(
        value = "Protocol",
        description = "In order to communicate with the picture processor, a specific protocol needs to be used. This " +
                "protocol follows the format : <b>\"colorimetry of the picture\";\"format of the picture\"</b>",
        color = PICTURE_PROCESSOR_COLOR
)
@Retention(RetentionPolicy.RUNTIME)
public @interface Protocol {
}
