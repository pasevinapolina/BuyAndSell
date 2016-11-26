package by.famcs.pasevinapolina.buyandsell.models;

/**
 * Created by user on 27.11.2016.
 */

public class Cart {

    private long id;
    private long cartId;
    private Product product;
    private int amount;

    public Cart() {
    }

    public Cart(long id, long cartId, Product product, int amount) {
        this.id = id;
        this.cartId = cartId;
        this.product = product;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
