package se.callista.blog.java8.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by magnus on 21/12/14.
 */
public class Order {
    private LocalDate orderDate;
    private int orderValue;
    private ArrayList<OrderLine> orderLines = new ArrayList<>();

    public Order(LocalDate orderDate, int orderValue) {
        this.orderDate = orderDate;
        this.orderValue = orderValue;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public int getOrderValue() {
        return orderValue;
    }
}
