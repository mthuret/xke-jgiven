package fr.photobooth.domain.jgiven.tags.pictureprocessor;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static fr.photobooth.domain.jgiven.tags.TagColor.PICTURE_PROCESSOR_COLOR;

@PictureProcessor
@IsTag(value = "Effects", color = PICTURE_PROCESSOR_COLOR)
@Retention( RetentionPolicy.RUNTIME )
public @interface Effects {
}
