package fr.photobooth.domain;

public enum Format {

    PORTRAIT(1.0) {
        public String accept(FormatVisitor visitor) {
            return visitor.visitPortrait();
        }
    },

    IDENTITY(3.0) {
        public String accept(FormatVisitor visitor) {
            return visitor.visitIdentity();
        }
    },

    MINI(4.0) {
        public String accept(FormatVisitor visitor) {
            return visitor.visitMini();
        }
    };

    public final Double price;

    private Format(Double price) {
        this.price = price;
    }

    public abstract String accept(FormatVisitor visitor);

    public interface FormatVisitor {

        String visitPortrait();

        String visitIdentity();

        String visitMini();
    }
}
