package fr.photobooth.domain;

public class PriceValidator implements Validator {
    @Override
    public Boolean validate(Command command) {
        return command.order.money >= command.order.picture.price();

    }
}
