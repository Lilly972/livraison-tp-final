package livraison.model;

public enum DishSize {

    SMALL("petite taille pour petit appetit"),
    MEDIUM("trql"),
    LARGE("gros lard");

    private final String message;

    DishSize(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}