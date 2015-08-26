package fr.photobooth.domain.pictureprocessor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class SepiaFilter {
    public static Image applyFilter(BufferedImage img, int
            sepiaIntensity) throws Exception
    {
        // Play around with this.  20 works well and was recommended
        //   by another developer. 0 produces black/white image
        int sepiaDepth = 20;

        int w = img.getWidth();
        int h = img.getHeight();

        WritableRaster raster = img.getRaster();

        // We need 3 integers (for R,G,B color values) per pixel.
        int[] pixels = new int[w*h*3];
        raster.getPixels(0, 0, w, h, pixels);

        //  Process 3 ints at a time for each pixel.  Each pixel has 3 RGB
        //colors in array
        for (int i=0;i<pixels.length; i+=3)
        {
            int r = pixels[i];
            int g = pixels[i+1];
            int b = pixels[i+2];

            int gry = (r + g + b) / 3;
            r = g = b = gry;
            r = r + (sepiaDepth * 2);
            g = g + sepiaDepth;

            if (r>255) r=255;
            if (g>255) g=255;
            if (b>255) b=255;

            // Darken blue color to increase sepia effect
            b-= sepiaIntensity;

            // normalize if out of bounds
            if (b<0) b=0;
            if (b>255) b=255;

            pixels[i] = r;
            pixels[i+1]= g;
            pixels[i+2] = b;
        }
        raster.setPixels(0, 0, w, h, pixels);
        return img;
    }
}
