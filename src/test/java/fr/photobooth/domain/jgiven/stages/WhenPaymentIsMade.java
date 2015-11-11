package fr.photobooth.domain.jgiven.stages;

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

import static fr.photobooth.domain.OrderBuilder.anOrder;

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
        pictureToProcess = folder.newFile("xxx");
    }

    public SELF $_euros_are_given_to_the_photo_booth(double amount) {
        final Order order = anOrder()
                .withMoney(amount)
                .withPicture(picture)
                .build();

        command = new Command(order, pictureToProcess);
        return self();
    }
}
