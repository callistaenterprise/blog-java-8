package se.callista.blog.java8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.callista.blog.java8.api.QueryApi;
import se.callista.blog.java8.impl.FunctionalImpl1;
import se.callista.blog.java8.impl.FunctionalImpl2;
import se.callista.blog.java8.impl.ImperativeImpl;
import se.callista.blog.java8.model.Order;
import se.callista.blog.java8.model.OrderLine;
import se.callista.blog.java8.model.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by magnus on 21/12/14.
 */
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {
        new Application().execute();
    }

    private void execute() {
        List<Order>   orders   = getOrders();

        logInfo(orders);

        testImpl(new ImperativeImpl(orders));
        testImpl(new FunctionalImpl1(orders));
        testImpl(new FunctionalImpl2(orders));
    }

    private void testImpl(QueryApi impl) {

        LOG.info("Testing {}", impl.getClass().getSimpleName());

        LocalDate today = LocalDate.now();

        String d;
        List<Product> products = impl.getProductsByDateAndCategoryOrderByWeight(today.minusDays(30), today, "C1");

        LOG.info("Count #1: {}, categories: {} - {}",
            products.size(),
            products.stream().map(p -> p.getCategory()).min(String.CASE_INSENSITIVE_ORDER).get(),
            products.stream().map(p -> p.getCategory()).max(String.CASE_INSENSITIVE_ORDER).get());

        products = impl.getProductsByOrderValueAndWeightOrderByProductId(1000, 2000, 100, 200);
        LOG.info("Count #2: {}, weights: {} - {}",
            products.size(),
            products.stream().map(p -> p.getWeight()).min(Integer::compare).get(),
            products.stream().map(p -> p.getWeight()).max(Integer::compare).get());
    }

    private List<Order> getOrders() {

        LocalDate today = LocalDate.now();
        List<Order> orders = new ArrayList<>();

        // Create orders
        IntStream
            .range(0, 1000)
            .forEach(i -> orders.add(getRandomOrder(today)));

        // Create orderLines and Products
        orders.forEach(o ->
            IntStream
                .range(1, getRandomNoOfOrderLines())
                .forEach(i -> o.getOrderLines().add(new OrderLine(i, getRandomProduct()))));

        return orders;
    };

    private Order getRandomOrder(LocalDate today) {
        int daysSinceOrderCreated = getRandomValue(1,100);
        int orderValue = getRandomValue(500, 5000);
        return new Order(today.minusDays(daysSinceOrderCreated), orderValue);
    }

    private int getRandomNoOfOrderLines() {
        return getRandomValue(2,6);
    }

    private Product getRandomProduct() {
        int productId = getRandomValue(10000, 20000);
        int weight = getRandomValue(50, 500);
        return new Product(productId, "ID-" + productId, weight, "C" + getRandomValue(1,9));
    }

    private int getRandomValue(int min, int max) {
        int value = min + (int) (Math.random() * (max - min));
        return value;
    }

    private void logInfo(List<Order> orders) {
        List<Product> products = orders.stream()
            .flatMap(o -> o.getOrderLines().stream())
            .map(ol -> ol.getProduct())
            .collect(Collectors.toList());

        LOG.info("Test with {} orders and {} products, product categories: {} - {}, product weights: {} - {}",
            orders.size(),
            products.size(),
            products.stream().map(p -> p.getCategory()).min(String.CASE_INSENSITIVE_ORDER).get(),
            products.stream().map(p -> p.getCategory()).max(String.CASE_INSENSITIVE_ORDER).get(),
            products.stream().map(p -> p.getWeight()).min(Integer::compare).get(),
            products.stream().map(p -> p.getWeight()).max(Integer::compare).get());
    }
}