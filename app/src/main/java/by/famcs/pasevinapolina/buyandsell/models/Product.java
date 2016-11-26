package by.famcs.pasevinapolina.buyandsell.models;

import java.util.List;

/**
 * Created by user on 27.11.2016.
 */

public class Product {

    private long id;

    private User owner;
    private String name;
    private String category;

    private double price;
    private double onSalePrice;

    private String description;
    private String brand;

    //private byte[] photo;
    private List<String> colors;
    private List<Integer> sizes;


    public Product() {
    }

    public Product(double price, long id, User owner, String name, String category) {
        this.price = price;
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.category = category;
    }

    public Product(long id, User owner, String name, String category,
                   double price, double onSalePrice, String description, String brand) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.category = category;
        this.price = price;
        this.onSalePrice = onSalePrice;
        this.description = description;
        this.brand = brand;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOnSalePrice() {
        return onSalePrice;
    }

    public void setOnSalePrice(double onSalePrice) {
        this.onSalePrice = onSalePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<Integer> getSizes() {
        return sizes;
    }

    public void setSizes(List<Integer> sizes) {
        this.sizes = sizes;
    }
}
