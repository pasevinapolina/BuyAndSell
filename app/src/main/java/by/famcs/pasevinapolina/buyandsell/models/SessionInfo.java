package by.famcs.pasevinapolina.buyandsell.models;

import java.util.Date;

/**
 * Created by user on 27.11.2016.
 */

public class SessionInfo {

    private long id;
    private User user;
    private long cartId;
    private Date startTime;

    public SessionInfo() {
    }

    public SessionInfo(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
