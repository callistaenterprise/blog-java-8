package se.callista.blog.java8.api;

import se.callista.blog.java8.model.Product;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by magnus on 23/12/14.
 */
public interface QueryApi {

    public List<Product> getProductsByDateAndCategoryOrderByWeight(LocalDate minDate, LocalDate maxDate, String category);

    public List<Product> getProductsByOrderValueAndWeightOrderByProductId(int minOrderValue, int maxOrderValue, int minProductWeight, int maxProductWeight);
}
