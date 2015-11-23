package fr.photobooth.domain.jgiven.tags;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static fr.photobooth.domain.jgiven.tags.TagColor.PRICE_COLOR;

@IsTag(
        value = "Price",
        description = "In order to let a user take a picture, the correct amount of money is given to the photo booth.",
        color = PRICE_COLOR
)
@Retention(RetentionPolicy.RUNTIME)
public @interface Price {
}
