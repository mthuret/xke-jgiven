package fr.photobooth.domain;

public class Order {
    public final Picture picture;
    public final Double money;

    public Order(Picture picture, Double money) {
        this.money = money;
        this.picture = picture;
    }

}
