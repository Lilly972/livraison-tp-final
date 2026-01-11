package resources;

public class FoodFastUtils {

    public static String deliveryPlanner(int n) {
        if (n % 3 == 0 && n % 5 == 0) {
            return "fizzbuzz";
        } else if (n % 3 == 0) {
            return "fizz";
        } else if (n % 5 == 0) {
            return "buzz";
        } else
            return "";
    }

    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static int sumUpTo(int n) {
        int res = 0;
        for (int i = 1; i < n; i++) {
            res += i;
        }
        return res;
    }

    public static String anonymize(String text) {
        return new StringBuilder(text).reverse().toString();
    }

    public static void main(String[] args) {
        String all = args.length > 0 ? String.join(" ", args) : "";
        System.out.println("Bienvenue chez FoodFast ! " + all);
    }

}
