package com.codecool.shop.model;

import com.codecool.shop.enumeration.OrderStatus;

/**
 * {@link LineItem} object mean the selected items in the webshop.
 * <p>
 * This class contain all the methods, which need to access the {@link Product} all methods.
 * A user has one or more {@link LineItem}, but if increase the quantity,
 * won't create a new line item instance, instead increase the item's quantity.
 *
 * @author gem
 * @version 1.8
 */
public class LineItem {
    private int id;
    private int counter;
    private Integer quantity;
    private float price;
    private Product product;

    public LineItem(Product product) {
        counter ++;
        id = counter;
        quantity = 1;
        this.product = product;
        price = quantity * this.product.getDefaultPrice();
    }

    /**
     * @return return the line item's id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id set the id for the given instance
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return return the selected line item's quantities
     */
    public Integer getQuantity() {
        return this.quantity;
    }

    /**
     * @param quantity the quantity for the given instance
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        updatePrice();
    }

    /**
     * @return return the line item's price
     */
    public float getPrice() {
        return this.price;
    }

    /**
     * Update the line item's instance.
     * <p>
     * Update the item's instance with the multiplied quantity and {@link Product} defaultPrice.
     */
    private void updatePrice() {
        price = quantity * this.product.getDefaultPrice();
    }

    /**
     * @return return the {@link Product} instance
     */
    public Product getProduct() {
        return product;
    }
}
