package fr.photobooth.domain.jgiven;


import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import fr.photobooth.domain.Colorimetry;
import fr.photobooth.domain.Format;
import fr.photobooth.domain.Order;
import fr.photobooth.domain.OrderToPictureProcessorProtocol;
import fr.photobooth.domain.jgiven.tags.pictureprocessor.Protocol;
import org.junit.Test;
import org.junit.runner.RunWith;

import static fr.photobooth.domain.OrderBuilder.anOrder;
import static fr.photobooth.domain.PictureBuilder.aPicture;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
@Protocol
public class ConvertOrderToPictureProcessorProtocolTest extends SimpleScenarioTest<ConvertOrderToPictureProcessorProtocolTest.ProtocolSteps> {

    @Test
    @DataProvider({
            "BLACK_AND_WHITE, MINI, BW;M",
            "COLOR, IDENTITY, C;I",
            "COLOR, PORTRAIT, C;P",
            "VINTAGE, PORTRAIT, V;P",
    })
    public void translate_picture_orders_into_a_specific_protocol_for_the_picture_processor(Colorimetry colorimetry,
                                                                                            Format format,
                                                                                            String expectedInstructions) {

        given().a_$_$_picture_order(colorimetry, format);

        when().an_order_is_converted_into_picture_processor_protocol();

        then().the_converted_order_should_be(expectedInstructions);
    }

    static class ProtocolSteps {

        private Order order;
        private String instructions;

        public void an_order_is_converted_into_picture_processor_protocol() {
            instructions = new OrderToPictureProcessorProtocol().convert(order);
        }

        public void a_$_$_picture_order(Colorimetry colorimetry, Format format) {
            order = anOrder()
                    .withPicture(
                            aPicture()
                                    .withColorimetry(colorimetry)
                                    .withFormat(format)
                    )
                    .build();
        }

        public void the_converted_order_should_be(String expectedInstructions) {
            assertThat(instructions).isEqualTo(expectedInstructions);
        }
    }
}
