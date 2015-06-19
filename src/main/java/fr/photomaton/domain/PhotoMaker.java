package fr.photomaton.domain;

import fr.photomaton.domain.pictureprocessor.PictureProcessor;
import fr.photomaton.domain.pictureprocessor.PictureProcessorException;

import java.io.File;

import static fr.photomaton.domain.Format.IDENTITY;

public class PhotoMaker {

    private final PictureProcessor pictureProcessor;
    private final OrderToPictureProcessorProtocol orderToPictureProcessorProtocol;
    private final Validator identityValidator;
    private final Validator priceValidator;
    private final Validator pictureCombinationValidator;

    public PhotoMaker(PictureProcessor pictureProcessor, Validator identityValidator,
                      Validator priceValidator, Validator pictureCombinationValidator) {
        this.pictureProcessor = pictureProcessor;
        this.identityValidator = identityValidator;
        this.priceValidator = priceValidator;
        this.pictureCombinationValidator = pictureCombinationValidator;
        this.orderToPictureProcessorProtocol = new OrderToPictureProcessorProtocol();
    }

    public File make(Command command) throws MachineException, NotAValidIdentityPicture {
        try {
            validatePrice(command);
            validatePictureCombination(command);
            validateIdentityPicture(command);
            String pictureProcessorOrder = orderToPictureProcessorProtocol.convert(command.order);
            return pictureProcessor.process(pictureProcessorOrder, command.picture);
        } catch (PictureProcessorException e) {
            throw new MachineException("Unexpected error when processing picture", e);
        }
    }

    private void validatePictureCombination(Command command) {
        if(!pictureCombinationValidator.validate(command)){
            throw new IllegalArgumentException("the picture combination " + command.order.picture.format + " "
                    + command.order.picture.colorimetry + " is not allowed");
        }
    }

    private void validatePrice(Command command) {
        if(!priceValidator.validate(command)){
            throw new IllegalArgumentException("not enough money provided : " + command.order.money);
        }
    }

    private void validateIdentityPicture(Command command) throws NotAValidIdentityPicture {
        if (command.order.picture.format == IDENTITY) {
            if (!identityValidator.validate(command)) {
                throw new NotAValidIdentityPicture("This picture does not respect identity picture standard");
            }
        }
    }
}
