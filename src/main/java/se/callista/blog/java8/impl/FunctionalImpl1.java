package se.callista.blog.java8.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.callista.blog.java8.api.QueryApi;
import se.callista.blog.java8.model.Order;
import se.callista.blog.java8.model.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by magnus on 21/12/14.
 */
public class FunctionalImpl1 implements QueryApi {

    private static final Logger LOG = LoggerFactory.getLogger(FunctionalImpl1.class);

    private final List<Order> orders;

    public FunctionalImpl1(List<Order> orders) {
        this.orders = orders;
    }

    public List<Product> getProductsByDateAndCategoryOrderByWeight(LocalDate minDate, LocalDate maxDate, String category) {

        return getOrders().stream()
            .filter(o -> o.getOrderDate().isAfter(minDate) && o.getOrderDate().isBefore(maxDate))
            .flatMap(o -> o.getOrderLines().stream())
            .map(ol -> ol.getProduct())
            .filter(p -> p.getCategory().equals(category))
            .distinct()
            .sorted((p1, p2) -> (p1.getWeight() < p2.getWeight()) ? -1 : ((p1.getWeight() == p2.getWeight()) ? 0 : 1))
            .collect(Collectors.toList());
    }

    public List<Product> getProductsByOrderValueAndWeightOrderByProductId(int minOrderValue, int maxOrderValue, int minProductWeight, int maxProductWeight) {

        return getOrders().stream()
            .filter(o -> minOrderValue <= o.getOrderValue() && o.getOrderValue() <= maxOrderValue)
            .flatMap(o -> o.getOrderLines().stream())
            .map(ol -> ol.getProduct())
            .filter(p -> minProductWeight <= p.getWeight() && p.getWeight() <= maxProductWeight)
            .distinct()
            .sorted((p1, p2) -> (p1.getId() < p2.getId()) ? -1 : ((p1.getId() == p2.getId()) ? 0 : 1))
            .collect(Collectors.toList());
    }

    private List<Order> getOrders() {
        return orders;
    }
}
