package fr.photomaton.domain.jgiven.tags;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IsTag(type = "Feature", value = "Price", description = "In order to let a user take a picture, the correct amount of " +
        "money is given to the photomaton.")
@Retention(RetentionPolicy.RUNTIME)
public @interface Price {
}
