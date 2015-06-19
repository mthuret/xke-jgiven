package fr.photomaton.domain;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import fr.photomaton.domain.tags.Identity;
import org.junit.Test;

import static fr.photomaton.domain.Colorimetry.VINTAGE;
import static fr.photomaton.domain.Format.IDENTITY;
import static fr.photomaton.domain.PictureBuilder.aPicture;
import static org.assertj.core.api.Assertions.assertThat;

@fr.photomaton.domain.tags.Validation
public class ValidationFeatureTest extends SimpleScenarioTest<ValidationFeatureTest.ValidationSteps> {

    @Test
    public void should_validate_an_order_if_the_exact_picture_price_is_provided() {
        given().a_picture_order_with_exact_picture_price_provided(aPicture().price());

        when().order_is_validated();

        then().validation_should_be_in_sucess();
    }

    @Test
    public void should_validate_an_order_if_more_than_picture_price_is_provided() {
        given().a_picture_order_with_more_than_picture_price_provided(aPicture().price() + 0.5);

        when().order_is_validated();

        then().validation_should_be_in_sucess();
    }

    @Test
    public void should_not_validate_an_order_if_less_than_picture_price_is_provided() {
        given().a_picture_order_with_less_than_picture_price_provided(aPicture().price() - 0.5);

        when().order_is_validated();

        then().validation_should_be_in_failure();
    }

    @Test
    @Identity
    public void should_not_validate_an_order_if_it_is_for_identity_vintage_picture() {
        given().an_order_for_an_identity_vintage_picture();

        when().order_is_validated();

        then().validation_should_be_in_failure();
    }

    public static class ValidationSteps extends Stage<ValidationSteps> {
        private Order order;
        private Boolean orderValidated;

        public void a_picture_order_with_exact_picture_price_provided(double price) {
            order = buildOrder(price);
        }

        private Order buildOrder(double price) {
            return new OrderBuilder()
                    .withMoney(price)
                    .build();
        }

        public void order_is_validated() {
            orderValidated = Validation.INSTANCE.isValid(order);
        }

        public void validation_should_be_in_sucess() {
            assertThat(orderValidated).isTrue();
        }

        public void a_picture_order_with_more_than_picture_price_provided(double price) {
            order = buildOrder(price);
        }

        public void a_picture_order_with_less_than_picture_price_provided(double price) {
            order = buildOrder(price);
        }

        public void validation_should_be_in_failure() {
            assertThat(orderValidated).isFalse();
        }

        public void an_order_for_an_identity_vintage_picture() {
            Picture picture = new PictureBuilder()
                    .withColorimetry(VINTAGE)
                    .withFormat(IDENTITY)
                    .build();
            order = new OrderBuilder()
                    .withPicture(picture)
                    .withMoney(picture.price())
                    .build();
        }
    }
}
