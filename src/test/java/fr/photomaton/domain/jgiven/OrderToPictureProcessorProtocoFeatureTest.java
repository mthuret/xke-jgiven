package fr.photomaton.domain.jgiven;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import fr.photomaton.domain.Colorimetry;
import fr.photomaton.domain.Format;
import fr.photomaton.domain.Order;
import fr.photomaton.domain.OrderBuilder;
import fr.photomaton.domain.OrderToPictureProcessorProtocol;
import fr.photomaton.domain.PictureBuilder;
import fr.photomaton.domain.jgiven.tags.Protocol;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/*
* Feature: Picture Processor Protocol

  The photomaton embed a picture processor that is responsible for processing the pictures taken by users. In order to communicate with it, a specific protocol needs to be used.

  This protocol follows the format : "colorimetry of the picture";"format of the picture"
      */
@RunWith(DataProviderRunner.class)
@Protocol
public class OrderToPictureProcessorProtocoFeatureTest extends SimpleScenarioTest<OrderToPictureProcessorProtocoFeatureTest.OrderToPictureProcessorProtocolSteps> {

    @Test
    @DataProvider({"COLOR, IDENTITY, C;I",
            "COLOR, PORTRAIT, C;P",
            "BLACK_AND_WHITE, MINI, BW;M",
            "VINTAGE, PORTRAIT, V;P"})
    public void should_convert_a_picture_order_into_a_picture_processor_protocol(Colorimetry colorimetry, Format format, String pictureProcessorInstruction) {
        //given
        given().a_$_$_picture_order(colorimetry, format);

        //when
        when().an_order_is_converted_into_picture_processor_protocol();

        //then
        then().the_converted_order_should_be_$(pictureProcessorInstruction);
    }

    public static class OrderToPictureProcessorProtocolSteps {

        private Order order;
        private OrderToPictureProcessorProtocol orderToPictureProcessorProtocol = new OrderToPictureProcessorProtocol();
        private String pictureProcessorOrder;

        public void a_$_$_picture_order(Colorimetry colorimetry, Format format) {
            order = new OrderBuilder()
                    .withPicture(new PictureBuilder()
                            .withColorimetry(colorimetry)
                            .withFormat(format)
                            .build())
                    .build();
        }

        public void an_order_is_converted_into_picture_processor_protocol() {
            pictureProcessorOrder = orderToPictureProcessorProtocol.convert(order);
        }

        public void the_converted_order_should_be_$(String pictureProcessorOrder) {
            assertThat(this.pictureProcessorOrder).isEqualTo(pictureProcessorOrder);
        }
    }
}
