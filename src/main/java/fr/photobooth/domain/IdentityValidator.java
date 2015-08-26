package fr.photobooth.domain;

import java.util.Random;

public class IdentityValidator implements Validator {

    private Random random;

    public IdentityValidator() {
        this.random = new Random();
    }

    @Override
    public Boolean validate(Command picture) {
        return random.nextBoolean();
    }
}
