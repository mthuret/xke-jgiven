package fr.photobooth.domain.pictureprocessor;

import com.google.common.io.Files;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class PictureProcessor {

    public File process(String order, File takenPicture) throws PictureProcessorException {
        File processedPicture = new File(takenPicture.toPath().getParent().toString() + "/" + constructProcessedPictureFilename(takenPicture));
        try {
            BufferedImage image = ImageIO.read(takenPicture);
            ImageIO.write((RenderedImage) applyOrder(order, image),
                    Files.getFileExtension(takenPicture.getName()),
                    processedPicture);
        } catch (Exception e) {
            throw new PictureProcessorException("picture processor error", e);
        }
        return processedPicture;
    }

    private String constructProcessedPictureFilename(File takenPicture) {
        return Files.getNameWithoutExtension(takenPicture.getPath()) + "-processed." + Files.getFileExtension(takenPicture.getName());
    }

    private Image applyOrder(String order, BufferedImage image) throws Exception {
        String[] splittedOrder = order.split(";");
        BufferedImage filteredImage = (BufferedImage) applyColorimetry(image, splittedOrder[0]);
        Image formattedImage = applyFormat(filteredImage, splittedOrder[1]);
        return formattedImage;
    }

    private Image applyFormat(BufferedImage buffered, String format) throws IOException {
        Image formatted;
        switch (format) {
            case "I":
                formatted = Formatter.format(2, 2, buffered);
                break;
            case "M":
                formatted = Formatter.format(4, 4, buffered);
                break;
            case "P":
                formatted = buffered;
                break;
            default:
                throw new IllegalArgumentException("The format " + format + " is not known");
        }
        return formatted;
    }

    private Image applyColorimetry(BufferedImage pictureToModify, String colorimetry) throws Exception {
        Image filtered;
        switch (colorimetry) {
            case "BW":
                filtered = NBFilter.applyFilter(pictureToModify);
                break;
            case "V":
                filtered = SepiaFilter.applyFilter(pictureToModify, 15);
                break;
            case "C":
                filtered = pictureToModify;
                break;
            default:
                throw new IllegalArgumentException("The colorimetry " + colorimetry + " is not known");
        }
        return filtered;
    }


}
