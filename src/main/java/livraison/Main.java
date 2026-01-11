package livraison;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import livraison.model.Customer;
import livraison.model.Dish;
import livraison.model.DishSize;
import livraison.order.Order;
import livraison.platform.DeliveryPlatform;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== FOODFAST - SIMULATION MULTI-THREADS ===\n");

        //  Initialisation de la plateforme
        DeliveryPlatform platform = new DeliveryPlatform();

        // Création des données de base pour la simulation
        Dish pizza = new Dish("Pizza Margherita", new BigDecimal("15.50"), DishSize.LARGE);
        Dish burger = new Dish("Burger Deluxe", new BigDecimal("12.00"), DishSize.MEDIUM);
        
        Customer client1 = new Customer( "Jean Dupont", "123 Rue de la Paix, Paris");
        Customer client2 = new Customer( "Marie Martin", "456 Avenue des Fleurs, Lyon");

        //  Création du pool de threads 
        // On crée un pool de 4 threads pour simuler plusieurs restaurants
        ExecutorService executor = Executors.newFixedThreadPool(4);

        System.out.println("Début du traitement des commandes en parallèle...\n");

        // 4. Envoi de 10 commandes simultanées
        for (int i = 1; i <= 10; i++) {
            final int idSimulation = i;

            // Chaque tâche est soumise au pool de threads
            executor.submit(() -> {
                try {
                    // Création d'une commande spécifique 
                    Map<Dish, Integer> plats = new HashMap<>();
                    plats.put(pizza, 1);
                    plats.put(burger, 1);
                    
                    Customer client = (idSimulation % 2 == 0) ? client1 : client2;
                    Order commande = new Order(plats, client);

                    System.out.println("[Thread: " + Thread.currentThread().getName() + 
                                       "] Passage de la commande n°" + idSimulation);
                    
                    // La plateforme gère la concurrence et la persistance JDBC
                    platform.placeOrder(commande);

                } catch (Exception e) {
                    System.err.println("Erreur lors de la simulation : " + e.getMessage());
                }
            });
        }

        // 5. Arrêt propre de l'ExecutorService 

        executor.shutdown();
        
        try {
            // On attend que toutes les tâches se terminent 
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow(); // Forcer l'arrêt si c'est trop long
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt(); 
        }

        System.out.println("\n=== FIN DE LA SIMULATION : TOUTES LES COMMANDES ONT ETE TRAITEES ===");
    }
}

// ## Partie 4 : Concurrence et Persistance (Séance 4)

// *Objectif : Préparer l'application à un environnement multi-utilisateurs et
// persister les données.*

// ### Question 8 : "Montée en Charge (Concurrence)"

// 1. Rendez `DeliveryPlatform` *thread-safe*. Utilisez une `ConcurrentHashMap`
// pour stocker les commandes.
// 2. Dans votre `main`, créez un `ExecutorService` avec plusieurs threads pour
// simuler plusieurs restaurants qui passent des commandes en même temps.
// 3. Assurez-vous que les méthodes qui modifient l'état de la plateforme (comme
// `placeOrder`) sont correctement synchronisées pour éviter les *race
// conditions*.
// 4. **Discussion Sécurité :** Expliquez comment une *race condition* peut
// devenir une faille de sécurité de type **TOCTOU** (Time-of-check to
// Time-of-use).

// *Concepts-clés : Concurrence, `ExecutorService`, `ConcurrentHashMap`,
// `synchronized`.*

// ### Question 9 : "Persistance en Base de Données (JDBC)"

// 1. Configurez une connexion JDBC à une base de données PostgreSQL.
// 2. Créez une méthode dans `DeliveryPlatform` qui sauvegarde une commande dans
// une table `orders`.
// 3. **Utilisez systématiquement `PreparedStatement`** pour construire vos
// requêtes.
// 4. **Discussion Sécurité :** Expliquez, avec un exemple de code, comment
// `PreparedStatement` prévient les **injections SQL** par rapport à une
// concaténation de `String`.

// *Concepts-clés : JDBC, `Connection`, `PreparedStatement`,
// `try-with-resources`.*

// ---
