package livraison.order;

public class OrderPreparationException extends Exception {

    // Constructeur par défaut avec un message générique
    public OrderPreparationException() {
        super("Erreur lors de la préparation de la commande.");
    }

    // Constructeur permettant de passer un message spécifique
    public OrderPreparationException(String message) {
        super(message);
    }
}