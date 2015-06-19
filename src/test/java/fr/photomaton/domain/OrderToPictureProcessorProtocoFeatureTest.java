package fr.photomaton.domain;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import fr.photomaton.domain.tags.PictureProcessorProtocol;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
@PictureProcessorProtocol
public class OrderToPictureProcessorProtocoFeatureTest extends SimpleScenarioTest<OrderToPictureProcessorProtocoFeatureTest.OrderToPictureProcessorProtocolSteps> {

    @Test
    @DataProvider({"COLOR, PORTRAIT, C;P",
            "COLOR, IDENTITY, C;I",
            "BLACK_AND_WHITE, IDENTITY, BW;I",
            "VINTAGE, MINI, V;M"})
    public void should_convert_picture_order_into_picture_processor_protocol(Colorimetry colorimetry, Format format, String pictureProcessorOrder) {
        //given
        given().a_$_$_picture_order(colorimetry, format);

        //when
        when().order_is_converted_into_picture_processor_protocol();

        //then
        then().the_converted_order_should_be_$(pictureProcessorOrder);
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

        public void order_is_converted_into_picture_processor_protocol() {
            pictureProcessorOrder = orderToPictureProcessorProtocol.convert(order);
        }

        public void the_converted_order_should_be_$(String pictureProcessorOrder) {
            assertThat(this.pictureProcessorOrder).isEqualTo(pictureProcessorOrder);
        }
    }
}
