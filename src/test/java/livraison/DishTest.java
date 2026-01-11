package livraison;

import org.junit.jupiter.api.Test;

import livraison.model.Dish;
import livraison.model.DishSize;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class DishTest {

    private Dish dish1;
    private Dish dish2;
    private Dish dish3;

    @BeforeEach
    void setUp() {
        dish1 = new Dish("Pizza", new BigDecimal("15"), DishSize.LARGE);
        dish2 = new Dish("Pasta", new BigDecimal("12"), DishSize.MEDIUM);
        dish3 = new Dish("Pizza", new BigDecimal("15"), DishSize.LARGE); // Same as dish1
    }

    @Test
    void testConstructorWithAllParameters() {
        assertEquals("Pizza", dish1.getName());
        assertEquals(new BigDecimal("15"), dish1.getPrice());
        assertEquals(DishSize.LARGE, dish1.getDishSize());
    }

    @Test
    void testConstructorWithNameAndSize() {
        Dish dish = new Dish("Salad", DishSize.SMALL);
        assertEquals("Salad", dish.getName());
        assertEquals(new BigDecimal("12"), dish.getPrice()); // Default price
        assertEquals(DishSize.SMALL, dish.getDishSize());
    }

    @Test
    void testDefaultConstructor() {
        Dish dish = new Dish();
        assertEquals("default", dish.getName());
        assertEquals(new BigDecimal("12"), dish.getPrice());
        assertEquals(DishSize.MEDIUM, dish.getDishSize());
    }

    @Test
    void testGetName() {
        assertEquals("Pizza", dish1.getName());
        assertEquals("Pasta", dish2.getName());
    }

    @Test
    void testSetName() {
        dish1.setName("Burger");
        assertEquals("Burger", dish1.getName());
    }

    @Test
    void testSetNameNull() {
        dish1.setName(null);
        assertNull(dish1.getName());
    }

    @Test
    void testGetPrice() {
        assertEquals(new BigDecimal("15"), dish1.getPrice());
        assertEquals(new BigDecimal("12"), dish2.getPrice());
    }

    @Test
    void testSetPrice() {
        dish1.setPrice(new BigDecimal("20"));
        assertEquals(new BigDecimal("20"), dish1.getPrice());
    }

    @Test
    void testSetPriceZero() {
        dish1.setPrice(new BigDecimal("0"));
        assertEquals(new BigDecimal("0"), dish1.getPrice());
    }

    @Test
    void testSetPriceNegative() {
        dish1.setPrice(new BigDecimal("-5"));
        assertEquals(new BigDecimal("-5"), dish1.getPrice());
    }

    @Test
    void testGetDishSize() {
        assertEquals(DishSize.LARGE, dish1.getDishSize());
        assertEquals(DishSize.MEDIUM, dish2.getDishSize());
    }

    @Test
    void testSetDishSize() {
        dish1.setDishSize(DishSize.SMALL);
        assertEquals(DishSize.SMALL, dish1.getDishSize());
    }

    @Test
    void testSetDishSizeNull() {
        dish1.setDishSize(null);
        assertNull(dish1.getDishSize());
    }

    @Test
    void testToString() {
        String expected = "Dish [name=Pizza, price=15, dishSize=LARGE]";
        assertEquals(expected, dish1.toString());
    }

    @Test
    void testToStringWithNullValues() {
        Dish dish = new Dish();
        dish.setName(null);
        dish.setDishSize(null);
        String expected = "Dish [name=null, price=12, dishSize=null]";
        assertEquals(expected, dish.toString());
    }

    @Test
    void testEqualsReflexive() {
        // Un objet doit être égal à lui-même
        assertTrue(dish1.equals(dish1));
    }

    @Test
    void testEqualsSymmetric() {
        // Si a.equals(b) alors b.equals(a)
        assertTrue(dish1.equals(dish3));
        assertTrue(dish3.equals(dish1));
    }

    @Test
    void testEqualsTransitive() {
        // Si a.equals(b) et b.equals(c) alors a.equals(c)
        Dish dish4 = new Dish("Pizza", new BigDecimal("15"), DishSize.LARGE);
        assertTrue(dish1.equals(dish3));
        assertTrue(dish3.equals(dish4));
        assertTrue(dish1.equals(dish4));
    }

    @Test
    void testEqualsNull() {
        assertFalse(dish1.equals(null));
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(dish1.equals("Not a Dish"));
    }

    @Test
    void testEqualsDifferentName() {
        assertFalse(dish1.equals(dish2));
    }

    @Test
    void testEqualsDifferentPrice() {
        Dish dish = new Dish("Pizza", new BigDecimal("20"), DishSize.LARGE);
        assertFalse(dish1.equals(dish));
    }

    @Test
    void testEqualsDifferentSize() {
        Dish dish = new Dish("Pizza", new BigDecimal("15"), DishSize.SMALL);
        assertFalse(dish1.equals(dish));
    }

    @Test
    void testEqualsWithNullName() {
        Dish dishNull1 = new Dish(null, new BigDecimal("15"), DishSize.LARGE);
        Dish dishNull2 = new Dish(null, new BigDecimal("15"), DishSize.LARGE);
        Dish dishNonNull = new Dish("Pizza", new BigDecimal("15"), DishSize.LARGE);

        assertTrue(dishNull1.equals(dishNull2));
        assertFalse(dishNull1.equals(dishNonNull));
        assertFalse(dishNonNull.equals(dishNull1));
    }

    @Test
    void testEqualsWithNullSize() {
        Dish dishNull1 = new Dish("Pizza", new BigDecimal("15"), null);
        Dish dishNull2 = new Dish("Pizza", new BigDecimal("15"), null);
        Dish dishNonNull = new Dish("Pizza", new BigDecimal("15"), DishSize.LARGE);

        assertTrue(dishNull1.equals(dishNull2));
        assertFalse(dishNull1.equals(dishNonNull));
        assertFalse(dishNonNull.equals(dishNull1));
    }

    @Test
    void testHashCodeConsistency() {
        // Le hashCode d'un objet ne doit pas changer
        int hash1 = dish1.hashCode();
        int hash2 = dish1.hashCode();
        assertEquals(hash1, hash2);
    }

    @Test
    void testHashCodeEquality() {
        // Si deux objets sont égaux, ils doivent avoir le même hashCode
        assertEquals(dish1.hashCode(), dish3.hashCode());
    }

    @Test
    void testHashCodeDifferentObjects() {
        // Des objets différents devraient avoir des hashCodes différents (pas
        // obligatoire mais souhaitable)
        assertNotEquals(dish1.hashCode(), dish2.hashCode());
    }

    @Test
    void testHashCodeWithNullValues() {
        Dish dishWithNulls = new Dish(null, new BigDecimal("15"), null);
        // Doit pouvoir calculer un hashCode même avec des valeurs null
        assertDoesNotThrow(() -> dishWithNulls.hashCode());
    }

    @Test
    void testHashCodeWithSameValues() {
        Dish dish4 = new Dish("Pizza", new BigDecimal("15"), DishSize.LARGE);
        Dish dish5 = new Dish("Pizza", new BigDecimal("15"), DishSize.LARGE);
        assertEquals(dish4.hashCode(), dish5.hashCode());
    }
}