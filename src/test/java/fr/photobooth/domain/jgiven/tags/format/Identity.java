package fr.photobooth.domain.jgiven.tags.format;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Format
@IsTag(value = "Identity", description = "A photo booth can only take identity pictures that respect state standards.")
@Retention(RetentionPolicy.RUNTIME)
public @interface Identity {
}
