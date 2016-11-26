package by.famcs.pasevinapolina.buyandsell.models;

import java.util.Date;

/**
 * Created by user on 27.11.2016.
 */

public class Order {

    private long id;
    private User user;
    private long cartId;

    private double sum;
    private Date orderDate;

    private boolean isPaid;
    private String address;
    private int delivery;

    public Order() {
    }

    public Order(long id, User user, long cartId, double sum,
                 Date orderDate, String address) {
        this.id = id;
        this.user = user;
        this.cartId = cartId;
        this.sum = sum;
        this.orderDate = orderDate;
        this.address = address;
    }

    public Order(int delivery, User user, long cartId, double sum,
                 Date orderDate, boolean isPaid, String address, long id) {
        this.delivery = delivery;
        this.user = user;
        this.cartId = cartId;
        this.sum = sum;
        this.orderDate = orderDate;
        this.isPaid = isPaid;
        this.address = address;
        this.id = id;
    }
}
