package fr.photomaton.domain;

import static fr.photomaton.domain.Colorimetry.VINTAGE;
import static fr.photomaton.domain.Format.IDENTITY;

public class PictureCombinationValidator implements Validator {
    @Override
    public Boolean validate(Command command) {
        return !(command.order.picture.colorimetry == VINTAGE &&
                command.order.picture.format == IDENTITY);
    }
}
