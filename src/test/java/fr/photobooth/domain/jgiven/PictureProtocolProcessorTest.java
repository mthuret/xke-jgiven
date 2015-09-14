package fr.photobooth.domain.jgiven;


import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.jgiven.annotation.Table;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import fr.photobooth.domain.Colorimetry;
import fr.photobooth.domain.Format;
import fr.photobooth.domain.Order;
import fr.photobooth.domain.OrderBuilder;
import fr.photobooth.domain.OrderToPictureProcessorProtocol;
import fr.photobooth.domain.Picture;
import fr.photobooth.domain.PictureBuilder;
import fr.photobooth.domain.jgiven.tags.pictureprocessor.Protocol;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
@Protocol
public class PictureProtocolProcessorTest extends
    SimpleScenarioTest<PictureProtocolProcessorTest.ProtocolSteps> {

    @Test
    @DataProvider({
        "COLOR, IDENTITY, C;I",
        "BLACK_AND_WHITE, MINI, BW;M",
        "COLOR, PORTRAIT, C;P",
        "VINTAGE, PORTRAIT, V;P"
    })
    public void picture_orders_are_translated_into_a_specific_protocol_for_the_picture_processor(Colorimetry colorimetry,
                                                                                                 Format format,
                                                                                                 String instructions) {

        given().a_$_$_picture_order(colorimetry, format);

        when().an_order_is_converted_into_picture_processor_protocol();

        then().the_converted_order_should_be_$(instructions);
    }

    public static class ProtocolSteps {

        private Order order;
        private String instructions;

        public void an_order_is_converted_into_picture_processor_protocol() {
            instructions = new OrderToPictureProcessorProtocol().convert(order);
        }

        public void a_$_$_picture_order(Colorimetry colorimetry, Format format) {
            order = OrderBuilder.aDefaultOrder().withPicture(
                PictureBuilder.aDefaultPicture()
                    .withColorimetry(colorimetry)
                    .withFormat(format)
                    .build())
                .build();

        }

        public void the_converted_order_should_be_$(String instructions) {
            assertThat(this.instructions).isEqualTo(instructions);
        }

        public void a_$_$_picture_order(@Table Picture picture) {
            order = OrderBuilder.aDefaultOrder().withPicture(picture).build();
        }
    }
}
