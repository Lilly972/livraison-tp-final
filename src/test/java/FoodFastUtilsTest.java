import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import resources.FoodFastUtils;

public class FoodFastUtilsTest {

    @Test
    void testDeliveryPlanner() {
        assertEquals("fizz", FoodFastUtils.deliveryPlanner(3));
        assertEquals("buzz", FoodFastUtils.deliveryPlanner(5));
        assertEquals("fizzbuzz", FoodFastUtils.deliveryPlanner(15));
        assertEquals("", FoodFastUtils.deliveryPlanner(7));
    }

    @Test
    void testTypicalLeapYears() {
        assertTrue(FoodFastUtils.isLeapYear(2020), "2020 doit être bissextile");
        assertTrue(FoodFastUtils.isLeapYear(2000), "2000 doit être bissextile");
    }

    @Test
    void testTypicalNonLeapYears() {
        assertFalse(FoodFastUtils.isLeapYear(2019), "2019 n'est pas bissextile");
        assertFalse(FoodFastUtils.isLeapYear(1900), "1900 n'est pas bissextile");
    }

    @Test
    void testEdgeCases() {
        assertTrue(FoodFastUtils.isLeapYear(2400), "2400 doit être bissextile");
        assertFalse(FoodFastUtils.isLeapYear(1800), "1800 n'est pas bissextile");
    }
}
