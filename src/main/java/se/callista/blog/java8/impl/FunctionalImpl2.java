package se.callista.blog.java8.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.callista.blog.java8.api.QueryApi;
import se.callista.blog.java8.model.Order;
import se.callista.blog.java8.model.Product;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by magnus on 21/12/14.
 */
public class FunctionalImpl2 implements QueryApi {

    private static final Logger LOG = LoggerFactory.getLogger(FunctionalImpl2.class);

    private final List<Order> orders;

    public FunctionalImpl2(List<Order> orders) {
        this.orders = orders;
    }

    public List<Product> getProductsByDateAndCategoryOrderByWeight(LocalDate minDate, LocalDate maxDate, String category) {

        return getProducts(
                o -> o.getOrderDate().isAfter(minDate) && o.getOrderDate().isBefore(maxDate),
                p -> p.getCategory().equals(category),
                (p1, p2) -> ((p1.getWeight() < p2.getWeight()) ? -1 : ((p1.getWeight() == p2.getWeight()) ? 0 : 1)));
    }

    public List<Product> getProductsByOrderValueAndWeightOrderByProductId(int minOrderValue, int maxOrderValue, int minProductWeight, int maxProductWeight) {

        return getProducts(
            o -> minOrderValue <= o.getOrderValue() && o.getOrderValue() <= maxOrderValue,
            p -> minProductWeight <= p.getWeight() && p.getWeight() <= maxProductWeight,
            (p1, p2) -> ((p1.getId() < p2.getId()) ? -1 : ((p1.getId() == p2.getId()) ? 0 : 1)));
    }

    private List<Product> getProducts(Predicate<Order> orderFilter, Predicate<Product> productFilter, Comparator<Product> productComparator) {

        return getOrders().stream()
            .filter(orderFilter)
            .flatMap(o -> o.getOrderLines().stream())
            .map(ol -> ol.getProduct())
            .filter(productFilter)
            .distinct()
            .sorted(productComparator)
            .collect(Collectors.toList());
    }

    private List<Order> getOrders() {
        return orders;
    }
}
