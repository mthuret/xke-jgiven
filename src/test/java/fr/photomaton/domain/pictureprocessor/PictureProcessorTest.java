package fr.photomaton.domain.pictureprocessor;

import com.google.common.io.Resources;
import fr.photomaton.domain.pictureprocessor.PictureProcessor;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;


public class PictureProcessorTest {

    PictureProcessor pictureProcessor = new PictureProcessor();

    @Test
    public void should_apply_classic_potrait_sepia() throws Exception {
        File processedPicture = pictureProcessor.process("V;P", getPicture("dog.jpeg"));
        assertThat(Files.isSameFile(processedPicture.toPath(), getPicture("dog-sepia.jpeg").toPath()));
    }

    @Test
    public void should_apply_official_identity() throws Exception {
        File processedPicture = pictureProcessor.process("C;I", getPicture("dog.jpeg"));
        assertThat(Files.isSameFile(processedPicture.toPath(), getPicture("dog-identity.jpeg").toPath()));
    }

    @Test
    public void should_apply_classic_mini_nb() throws Exception {
        File processedPicture = pictureProcessor.process("BW;M", getPicture("dog.jpeg"));
        assertThat(Files.isSameFile(processedPicture.toPath(), getPicture("dog-mini.jpeg").toPath()));
    }

    private File getPicture(String resourceName) throws URISyntaxException {
        return new File(Resources.getResource(resourceName).toURI());
    }

}
