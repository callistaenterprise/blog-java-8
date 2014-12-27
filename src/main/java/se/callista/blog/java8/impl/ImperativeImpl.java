package se.callista.blog.java8.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.callista.blog.java8.api.QueryApi;
import se.callista.blog.java8.model.Order;
import se.callista.blog.java8.model.OrderLine;
import se.callista.blog.java8.model.Product;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by magnus on 21/12/14.
 */
public class ImperativeImpl implements QueryApi {

    private static final Logger LOG = LoggerFactory.getLogger(ImperativeImpl.class);

    private final List<Order> orders;

    public ImperativeImpl(List<Order> orders) {
        this.orders = orders;
    }

    public List<Product> getProductsByDateAndCategoryOrderByWeight(LocalDate minDate, LocalDate maxDate, String category) {

        List<Order> orders = getOrders();

        List<Product> products = new ArrayList<>();
        for (Order order : orders) {

            // Filter on order date
            LocalDate date = order.getOrderDate();
            if (date.isAfter(minDate) && date.isBefore(maxDate)) {

                List<OrderLine> orderLines = order.getOrderLines();
                for (OrderLine orderLine : orderLines) {

                    // Filter on product category
                    Product product = orderLine.getProduct();
                    if (product.getCategory().equals(category)) {
                        products.add(product);
                    }
                }

            }
        }

        // Remove any duplicates form the list of selected products
        products = new ArrayList<>(new HashSet<>(products));

        // Sort on product weight
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return (p1.getWeight() < p2.getWeight()) ? -1 : ((p1.getWeight() == p2.getWeight()) ? 0 : 1);
            }
        });

        return products;
    }

    public List<Product> getProductsByOrderValueAndWeightOrderByProductId(int minOrderValue, int maxOrderValue, int minProductWeight, int maxProductWeight) {

        List<Order> orders = getOrders();

        List<Product> products = new ArrayList<>();
        for (Order order : orders) {

            // Filter on order value
            int orderValue = order.getOrderValue();
            if (minOrderValue <= orderValue && orderValue <= maxOrderValue) {

                List<OrderLine> orderLines = order.getOrderLines();
                for (OrderLine orderLine : orderLines) {

                    // Filter on product weight
                    Product product = orderLine.getProduct();
                    int productWeight = product.getWeight();
                    if (minProductWeight <= productWeight && productWeight <= maxProductWeight) {
                        products.add(product);
                    }
                }
            }
        }

        // Remove any duplicates form the list of selected products
        products = new ArrayList<>(new HashSet<>(products));

        // Sort on product Id
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return (p1.getId() < p2.getId()) ? -1 : ((p1.getId() == p2.getId()) ? 0 : 1);
            }
        });

        return products;
    }

    private List<Order> getOrders() {
        return orders;
    }
}
