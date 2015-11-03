package fr.photobooth.domain;

import static fr.photobooth.domain.Colorimetry.COLOR;
import static fr.photobooth.domain.Format.PORTRAIT;

public class OrderBuilder {

    private boolean moneyOverrided = false;
    private Double money = 1.0;
    private Picture picture = new Picture(COLOR, PORTRAIT);

    private OrderBuilder() {
    }

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder withMoney(Double money) {
        moneyOverrided = true;
        this.money = money;
        return this;
    }

    public OrderBuilder withPicture(Picture picture) {
        this.picture = picture;
        return this;
    }

    public OrderBuilder withPicture(PictureBuilder pictureBuilder) {
        return withPicture(pictureBuilder.build());
    }

    public Order build() {
        if (!moneyOverrided) money = picture.price();
        return new Order(picture, money);
    }
}
