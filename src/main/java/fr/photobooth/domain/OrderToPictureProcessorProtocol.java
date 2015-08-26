package fr.photobooth.domain;

public class OrderToPictureProcessorProtocol {

    private final PictureProcessorColorimetry pictureProcessorColorimetry = new PictureProcessorColorimetry();
    private final PictureProcessorFormat pictureProcessorFormat = new PictureProcessorFormat();

    public String convert(Order order){
        Picture picture = order.picture;
        return picture.colorimetry.accept(pictureProcessorColorimetry) + ";" + picture.format.accept(pictureProcessorFormat);
    }
}
