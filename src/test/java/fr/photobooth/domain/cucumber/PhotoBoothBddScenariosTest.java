package fr.photobooth.domain.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/fr/photobooth/cucumber"},
        monochrome = true,
        plugin = {"pretty", "html:target/cucumber", "rerun:target/rerun.txt", "json:target/cucumber.json"}
)
public class PhotoBoothBddScenariosTest {
}

