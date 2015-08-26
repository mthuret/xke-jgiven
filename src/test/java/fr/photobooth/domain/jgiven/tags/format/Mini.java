package fr.photobooth.domain.jgiven.tags.format;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Format
@IsTag( value = "Mini",
    description = "" )
@Retention( RetentionPolicy.RUNTIME )
public @interface Mini {

}
