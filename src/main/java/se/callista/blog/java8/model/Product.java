package se.callista.blog.java8.model;

/**
 * Created by magnus on 21/12/14.
 */
public class Product {
    private int id;
    private String name;
    private int weight;
    private String category;

    public Product(int id, String name, int weight, String category) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getWeight() {
        return weight;
    }

}
