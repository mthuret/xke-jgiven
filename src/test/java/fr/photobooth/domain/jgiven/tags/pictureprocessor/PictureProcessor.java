package fr.photobooth.domain.jgiven.tags.pictureprocessor;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IsTag(type = "Feature", value = "Picture Processor", description = "The photo booth embed a picture processor that is " +
        "responsible for processing the pictures taken by users. In order to communicate with it, a specific protocol " +
        "needs to be used. This protocol follows the format : 'colorimetry of the picture';'format of the picture'")
@Retention(RetentionPolicy.RUNTIME)
public @interface PictureProcessor {
}
