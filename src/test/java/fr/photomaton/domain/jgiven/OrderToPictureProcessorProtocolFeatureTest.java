package fr.photomaton.domain.jgiven;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import fr.photomaton.domain.*;
import fr.photomaton.domain.jgiven.tags.pictureprocessor.Protocol;
import org.junit.Test;
import org.junit.runner.RunWith;

import static fr.photomaton.domain.OrderBuilder.aDefaultOrder;
import static fr.photomaton.domain.PictureBuilder.aDefaultPicture;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
@Protocol
public class OrderToPictureProcessorProtocolFeatureTest
        extends SimpleScenarioTest<OrderToPictureProcessorProtocolFeatureTest.OrderToPictureProcessorProtocolSteps> {

    @Test
    @DataProvider({
            "BLACK_AND_WHITE, MINI, BW;M",
            "COLOR, IDENTITY, C;I",
            "COLOR, PORTRAIT, C;P",
            "VINTAGE, PORTRAIT, V;P"
    })
    public void should_convert_a_picture_order_into_a_picture_processor_protocol(Colorimetry colorimetry,
                                                                                 Format format,
                                                                                 String pictureProcessorInstruction) {
        // Given
        given().a_$_$_picture_order(colorimetry, format);

        // When
        when().an_order_is_converted_into_picture_processor_protocol();

        // Then
        then().the_converted_order_should_be_$(pictureProcessorInstruction);
    }

    public static class OrderToPictureProcessorProtocolSteps {


        private Order order;
        private OrderToPictureProcessorProtocol protocol = new OrderToPictureProcessorProtocol();
        private String pictureProcessorOrder;

        public void a_$_$_picture_order(Colorimetry colorimetry, Format format) {
            order = aDefaultOrder()
                    .withPicture(aDefaultPicture()
                                    .withColorimetry(colorimetry)
                                    .withFormat(format)
                    )
                    .build();
        }

        public void an_order_is_converted_into_picture_processor_protocol() {
            pictureProcessorOrder = protocol.convert(order);
        }

        public void the_converted_order_should_be_$(String pictureProcessorOrder) {
            assertThat(this.pictureProcessorOrder).isEqualTo(pictureProcessorOrder);
        }
    }
}
