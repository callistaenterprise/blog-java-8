package se.callista.blog.java8.model;

/**
 * Created by magnus on 21/12/14.
 */
public class OrderLine {
    private int lineNo;
    private Product product;

    public OrderLine(int lineNo, Product product) {
        this.lineNo = lineNo;
        this.product = product;
    }

    public int getLineNo() {
        return lineNo;
    }

    public Product getProduct() {
        return product;
    }
}
