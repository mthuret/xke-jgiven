package fr.photomaton.domain.jgiven.stages;

import com.google.common.io.Resources;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import fr.photomaton.domain.Command;
import fr.photomaton.domain.OrderToPictureProcessorProtocol;
import fr.photomaton.domain.pictureprocessor.PictureProcessor;

import java.io.File;
import java.net.URISyntaxException;

public class WhenPictureIsBeingProcessed<SELF extends WhenPictureIsBeingProcessed<?>> extends Stage<SELF> {

    @ExpectedScenarioState
    private Command command;

    @ProvidedScenarioState
    private File processedPicture;

    public SELF the_picture_is_being_processed_by_the_picture_processor() throws Exception {
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
