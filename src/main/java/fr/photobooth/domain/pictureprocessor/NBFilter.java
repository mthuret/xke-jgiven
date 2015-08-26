package fr.photobooth.domain.pictureprocessor;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class NBFilter {

    public static Image applyFilter(BufferedImage img ) {
        ColorConvertOp op = new ColorConvertOp(
                ColorSpace.getInstance(ColorSpace.CS_GRAY),
                null);
        return op.filter(img, null);
    }
}
