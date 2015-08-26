package fr.photobooth.domain.jgiven.tags.format;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static fr.photobooth.domain.jgiven.tags.TagColor.FORMAT_COLOR;

@Format
@IsTag(value = "Mini", color = FORMAT_COLOR)
@Retention( RetentionPolicy.RUNTIME )
public @interface Mini {

}
