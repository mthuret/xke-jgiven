package fr.photobooth.domain;

import java.io.File;

public class Command {
    public final Order order;
    public final File picture;

    public Command(Order order, File picture) {
        this.order = order;
        this.picture = picture;
    }
}
