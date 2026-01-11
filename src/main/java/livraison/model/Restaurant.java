package livraison.model;

import livraison.order.Order;
import livraison.order.OrderPreparationException;
import livraison.order.OrderStatus; 

public class Restaurant {

    private String name;

    public Restaurant(String name) {
        this.name = name;
    }

    public Restaurant() {
        this("Restaurant FoodFast");
    }

    /**
     * Simule la préparation d'une commande.
     * 20% de chances de lancer OrderPreparationException.
     */
    public void prepare(Order order) throws OrderPreparationException {
        int r = (int) (Math.random() * 100);
        
        if (r <= 20) {
            // L'exception sera rattrapée par le try-catch dans DeliveryPlatform
            throw new OrderPreparationException();
        }

        //Si la préparation réussit, on change le statut
        order.setStatus(OrderStatus.COMPLETED);
        System.out.println("Préparation terminée avec succès pour la commande : " + order.getId());
    }
}