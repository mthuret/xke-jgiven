package fr.photomaton.domain;

import static fr.photomaton.domain.Colorimetry.COLOR;
import static fr.photomaton.domain.Format.PORTRAIT;

public class OrderBuilder {

    private boolean moneyOverrided = false;
    private Picture picture = new Picture(COLOR, PORTRAIT);
    private Double money = 1.0;

    private OrderBuilder() {
    }

    public static OrderBuilder aDefaultOrder() {
        return new OrderBuilder();
    }

    public static Order anOrder() {
        return aDefaultOrder().build();
    }

    public Order build() {
        if (!moneyOverrided) money = picture.price();
        return new Order(picture, money);
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
}
