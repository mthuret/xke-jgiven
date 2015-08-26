package fr.photobooth.domain;

import static fr.photobooth.domain.Colorimetry.VINTAGE;
import static fr.photobooth.domain.Format.IDENTITY;

public class PictureCombinationValidator implements Validator {
    @Override
    public Boolean validate(Command command) {
        return !(command.order.picture.colorimetry == VINTAGE &&
                command.order.picture.format == IDENTITY);
    }
}
