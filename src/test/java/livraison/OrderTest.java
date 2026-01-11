package livraison;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import livraison.model.Customer;
import livraison.model.Dish;
import livraison.model.DishSize;
import livraison.order.Order;
import livraison.order.OrderStatus;

public class OrderTest {

    private Customer customer;
    private Dish dish1;
    private Dish dish2;
    private Map<Dish, Integer> dishes;

    @BeforeEach
    void setUp() {
        customer = new Customer("John Doe", "123 Main St");
        dish1 = new Dish("Pizza", new BigDecimal("15.50"), DishSize.LARGE);
        dish2 = new Dish("Burger", new BigDecimal("12.00"), DishSize.MEDIUM);

        dishes = new HashMap<>();
        dishes.put(dish1, 2);
        dishes.put(dish2, 1);
    }

    @Test
    void testOrderCreation() {
        Order order = new Order(dishes, customer);

        assertNotNull(order.getId());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(customer, order.getCustomer());
        assertEquals(dishes, order.getDishes());
        assertNotNull(order.getOrderDate());
        assertTrue(order.getOrderDate().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void testOrderIdIsUnique() {
        Order order1 = new Order(dishes, customer);
        Order order2 = new Order(dishes, customer);

        assertNotEquals(order1.getId(), order2.getId());
    }

    @Test
    void testCalculateTotalPrice() {
        Order order = new Order(dishes, customer);

        // 2 * 15.50 + 1 * 12.00 = 43.00
        BigDecimal expectedTotal = new BigDecimal("43.00");
        assertEquals(0, expectedTotal.compareTo(order.calculateTotalPrice()));
    }

    @Test
    void testCalculateTotalPriceEmptyOrder() {
        Map<Dish, Integer> emptyDishes = new HashMap<>();
        Order order = new Order(emptyDishes, customer);

        BigDecimal expectedTotal = new BigDecimal("0.00");
        assertEquals(0, expectedTotal.compareTo(order.calculateTotalPrice()));
    }

    @Test
    void testCalculateTotalPriceSingleDish() {
        Map<Dish, Integer> singleDish = new HashMap<>();
        singleDish.put(dish1, 3);
        Order order = new Order(singleDish, customer);

        // 3 * 15.50 = 46.50
        BigDecimal expectedTotal = new BigDecimal("46.50");
        assertEquals(0, expectedTotal.compareTo(order.calculateTotalPrice()));
    }

    @Test
    void testSetStatus() {
        Order order = new Order(dishes, customer);

        assertEquals(OrderStatus.PENDING, order.getStatus());

        order.setStatus(OrderStatus.IN_PREPARATION);
        assertEquals(OrderStatus.IN_PREPARATION, order.getStatus());

        order.setStatus(OrderStatus.COMPLETED);
        assertEquals(OrderStatus.COMPLETED, order.getStatus());

        order.setStatus(OrderStatus.CANCELLED);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void testSetCustomer() {
        Order order = new Order(dishes, customer);
        Customer newCustomer = new Customer("Jane Smith", "456 Oak Ave");

        order.setCustomer(newCustomer);
        assertEquals(newCustomer, order.getCustomer());
    }

    @Test
    void testSetDishes() {
        Order order = new Order(dishes, customer);

        Map<Dish, Integer> newDishes = new HashMap<>();
        Dish newDish = new Dish("Salad", new BigDecimal("8.00"), DishSize.SMALL);
        newDishes.put(newDish, 1);

        order.setDishes(newDishes);
        assertEquals(newDishes, order.getDishes());
    }

    @Test
    void testSetOrderDate() {
        Order order = new Order(dishes, customer);
        LocalDateTime newDate = LocalDateTime.of(2023, 12, 25, 12, 0, 0);

        order.setOrderDate(newDate);
        assertEquals(newDate, order.getOrderDate());
    }

    @Test
    void testToString() {
        Order order = new Order(dishes, customer);
        String orderString = order.toString();

        assertTrue(orderString.contains("Order"));
        assertTrue(orderString.contains("id="));
        assertTrue(orderString.contains("status=" + OrderStatus.PENDING));
        assertTrue(orderString.contains("customer=" + customer.toString()));
        assertTrue(orderString.contains("dishes="));
        assertTrue(orderString.contains("orderDate="));
    }

    @Test
    void testOrderWithNullCustomer() {
        Order order = new Order(dishes, null);
        assertNull(order.getCustomer());
    }

    @Test
    void testCalculateTotalPriceWithZeroQuantity() {
        Map<Dish, Integer> dishesWithZero = new HashMap<>();
        dishesWithZero.put(dish1, 0);
        dishesWithZero.put(dish2, 2);

        Order order = new Order(dishesWithZero, customer);

        // 0 * 15.50 + 2 * 12.00 = 24.00
        BigDecimal expectedTotal = new BigDecimal("24.00");
        assertEquals(0, expectedTotal.compareTo(order.calculateTotalPrice()));
    }
}
