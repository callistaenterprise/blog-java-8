package se.callista.blog.java8.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by magnus on 21/12/14.
 */
public class Order {
    private int orderNo;
    private LocalDate orderDate;
    private int orderValue;
    private ArrayList<OrderLine> orderLines = new ArrayList<>();

    public Order(int orderNo, LocalDate orderDate, int orderValue) {
        this.orderDate = orderDate;
        this.orderValue = orderValue;
    }

    public int getOrderNo() {
        return orderNo;
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

    @Override
    public int hashCode() {
        return orderNo;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Order)) return false;
        if (obj == this) return true;
        return orderNo == ((Order)obj).getOrderNo();
    }
}
