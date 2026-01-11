package livraison.model;

import java.math.BigDecimal;

public class Dish {

    private String name;
    private BigDecimal price;
    private DishSize dishSize;

    public Dish(String name, BigDecimal price, DishSize dishSize) {
        this.name = name;
        this.price = price;
        this.dishSize = dishSize;
    }

    public Dish(String name, DishSize dishSize) {
        this(name, new BigDecimal("12"), dishSize);
    }

    public Dish() {
        this("default", new BigDecimal("12"), DishSize.MEDIUM);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public DishSize getDishSize() {
        return dishSize;
    }

    public void setDishSize(DishSize dishSize) {
        this.dishSize = dishSize;
    }

    @Override
    public String toString() {
        return "Dish [name=" + name + ", price=" + price + ", dishSize=" + dishSize +
                "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((dishSize == null) ? 0 : dishSize.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Dish other = (Dish) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (dishSize != other.dishSize)
            return false;
        return true;
    }

}