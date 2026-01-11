package livraison.platform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import livraison.model.Customer;
import livraison.model.Restaurant;
import livraison.order.Order;
import livraison.order.OrderStatus;

/**
 * Classe gérant la plateforme de livraison.
 * Question 8 : Mise en place de la thread-safety (ConcurrentHashMap et AtomicInteger)
 * Question 9 : Persistance JDBC avec PreparedStatement 
 */
public class DeliveryPlatform {

    // Utilisation de ConcurrentHashMap pour la sécurité multi-thread (Question 8.1) 
    private Map<String, Order> orders = new ConcurrentHashMap<>();
    
    // AtomicInteger pour garantir des IDs de traitement uniques sans race condition (Question 8.3) 
    private AtomicInteger cpt = new AtomicInteger(0);

    /**
     * Enregistre une commande de manière synchronisée pour éviter les conflits (Question 8.3)
     */
    public synchronized void placeOrder(Order order) {
        // Incrémentation atomique sûre pour l'ID de session 
        int currentNumber = cpt.incrementAndGet();
        String orderKey = "Commande" + currentNumber;
        
        orders.put(orderKey, order);
        System.out.println("Traitement de la " + orderKey);

        Restaurant restaurant = new Restaurant();
        try {
            // Tentative de préparation simulée (Question 7)
            restaurant.prepare(order);
            
            // Si la préparation réussit, on persiste en base de données (Question 9)
            saveOrderToDb(order);
            
        } catch (Exception e) {
            // En cas d'exception (ex: OrderPreparationException), on annule la commande 
            order.setStatus(OrderStatus.CANCELLED);
            System.err.println("Erreur de préparation : " + e.getMessage());
        }
    }

    /**
     * Sauvegarde une commande dans la base PostgreSQL (Question 9).
     * Utilise PreparedStatement pour prévenir les injections SQL
     */
    private void saveOrderToDb(Order order) {
        // Configuration de la connexion 
        String url = "jdbc:postgresql://localhost:5432/foodfast";
        String user = "postgres";
        String password = "secret"; 

       
        String query = "INSERT INTO orders (id, customer_name, total_price, status, order_date) VALUES (?, ?, ?, ?, ?)";

        // Try-with-resources pour la fermeture automatique des ressources 
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            // Liaison des paramètres 
            pstmt.setString(1, order.getId()); 
            pstmt.setString(2, order.getCustomer().getName()); 
            pstmt.setBigDecimal(3, order.calculateTotalPrice()); 
            pstmt.setString(4, order.getStatus().toString()); 
            pstmt.setTimestamp(5, Timestamp.valueOf(order.getOrderDate())); 

            // Exécution de la mise à jour 
            pstmt.executeUpdate();
            System.out.println("Commande sauvegardée avec succès en base de données.");

        } catch (SQLException e) {
            // Gestion des erreurs de connexion 
            System.err.println("Erreur de persistance JDBC : " + e.getMessage());
        }
    }

    

    public Optional<Order> findOrderById(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    public List<Order> findOrdersByCustomer(Customer customer) {
        return orders.values().stream()
                .filter(order -> order.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }

    public List<Order> findOrdersByStatus(OrderStatus status) {
        return orders.values().stream()
                .filter(order -> order.getStatus().equals(status))
                .collect(Collectors.toList());
    }
}

    // Dans `DeliveryPlatform`, lors de l'ajout d'une commande, appelez cette
    // méthode et utilisez un bloc `try-catch` pour gérer l'erreur (par exemple, en
    // passant la commande au statut `CANCELLED` et en affichant un message).

