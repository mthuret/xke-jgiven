package fr.photobooth.domain.jgiven.stages;

import com.google.common.io.Resources;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import fr.photobooth.domain.Command;
import fr.photobooth.domain.OrderToPictureProcessorProtocol;
import fr.photobooth.domain.pictureprocessor.PictureProcessor;
import fr.photobooth.domain.pictureprocessor.PictureProcessorException;

import java.io.File;
import java.net.URISyntaxException;

public class WhenThePictureIsBeingProcessed<SELF extends WhenThePictureIsBeingProcessed<?>> extends Stage<SELF> {

    @ExpectedScenarioState
    private Command command;

    @ProvidedScenarioState
    private File processedPicture;

    public SELF the_picture_is_being_processed_by_the_picture_processor() throws URISyntaxException, PictureProcessorException {
        processedPicture = new PictureProcessor().process(
                new OrderToPictureProcessorProtocol().convert(command.order),
                getPicture("dog.jpeg")
        );
        return self();
    }

    private File getPicture(String resourceName) throws URISyntaxException {
        return new File(Resources.getResource(resourceName).toURI());
    }
}
