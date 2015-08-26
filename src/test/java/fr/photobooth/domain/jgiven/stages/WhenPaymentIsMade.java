package fr.photobooth.domain.jgiven.stages;

import com.google.common.io.Resources;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.annotation.ScenarioRule;
import fr.photobooth.domain.Command;
import fr.photobooth.domain.Order;
import fr.photobooth.domain.Picture;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static fr.photobooth.domain.OrderBuilder.aDefaultOrder;

public class WhenPaymentIsMade<SELF extends WhenPaymentIsMade<?>> extends Stage<SELF> {

    @ExpectedScenarioState
    private Picture picture;

    @ProvidedScenarioState
    private Command command;

    @ScenarioRule
    public TemporaryFolder folder = new TemporaryFolder();

    private File pictureToProcess;

    @BeforeStage
    public void before() throws IOException {
        pictureToProcess = folder.newFile("xx");
    }

    public SELF not_enough_euros_is_given_to_the_photo_booth() {
        final Order order = aDefaultOrder()
                .withMoney(picture.price() - 1)
                .withPicture(picture)
                .build();

        command = new Command(order, pictureToProcess);
        return self();
    }

    public SELF more_euros_than_the_price_of_the_wanted_picture_is_given_to_the_photo_booth() throws URISyntaxException {
        final Order order = aDefaultOrder()
                .withMoney(picture.price() + 2)
                .withPicture(picture)
                .build();

        command = new Command(order, getPicture("dog.jpeg"));
        return self();
    }

    private File getPicture(String resourceName) throws URISyntaxException {
        return new File(Resources.getResource(resourceName).toURI());
    }
}
