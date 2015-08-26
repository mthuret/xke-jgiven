package fr.photobooth.domain;

public class Picture {

    public final Colorimetry colorimetry;
    public final Format format;

    public Picture(Colorimetry colorimetry, Format format) {
        this.colorimetry = colorimetry;
        this.format = format;
    }

    public double price(){
        return colorimetry.price + format.price;
    }
}
