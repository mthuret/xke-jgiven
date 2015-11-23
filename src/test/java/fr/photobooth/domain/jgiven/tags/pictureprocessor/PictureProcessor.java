package fr.photobooth.domain.jgiven.tags.pictureprocessor;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static fr.photobooth.domain.jgiven.tags.TagColor.PICTURE_PROCESSOR_COLOR;

@IsTag(
        value = "Picture Processor",
        description = "The photo booth embed a picture processor that is responsible for processing the pictures taken " +
                "by users.'",
        color = PICTURE_PROCESSOR_COLOR
)
@Retention(RetentionPolicy.RUNTIME)
public @interface PictureProcessor {
}
