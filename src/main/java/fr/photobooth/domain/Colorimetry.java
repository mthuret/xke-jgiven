package fr.photobooth.domain;

public enum Colorimetry {
    COLOR(0.0) {
        public String accept(ColorimetryVisitor visitor) {
            return visitor.visitColor();
        }
    },

    BLACK_AND_WHITE(0.0) {
        public String accept(ColorimetryVisitor visitor) {
            return visitor.visitBlackAndWhite();
        }
    },

    VINTAGE(0.5) {
        public String accept(ColorimetryVisitor visitor) {
            return visitor.visitVintage();
        }
    };
    public Double price;

    Colorimetry(Double price) {
        this.price = price;
    }

    public abstract String accept(ColorimetryVisitor visitor);

    public interface ColorimetryVisitor {

        String visitColor();

        String visitBlackAndWhite();

        String visitVintage();
    }
}

