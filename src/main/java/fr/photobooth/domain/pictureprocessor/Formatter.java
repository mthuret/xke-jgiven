package fr.photobooth.domain.pictureprocessor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Formatter {

    public static Image format(int rows, int cols, BufferedImage image) throws IOException {
        int chunks = rows * cols;

        int chunkWidth, chunkHeight;
        int type;
        //creating a bufferd image array from image files
        BufferedImage[] buffImages = new BufferedImage[chunks];
        for (int i = 0; i < chunks; i++) {
            buffImages[i] = Resizer.resize(image, image.getWidth() / rows, image.getHeight() / cols);
        }

        type = buffImages[0].getType();
        chunkWidth = buffImages[0].getWidth();
        chunkHeight = buffImages[0].getHeight();

        //Initializing the final image
        BufferedImage finalImg = new BufferedImage(chunkWidth*cols, chunkHeight*rows, type);

        int num = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                finalImg.createGraphics().drawImage(buffImages[num], chunkWidth * j, chunkHeight * i, null);
                num++;
            }
        }
        return finalImg;
    }


}
