package com.codecool.shop.model;
import com.codecool.shop.enumeration.OrderStatus;

import java.util.List;

/**
 * Order class extends from the {@link BaseModel}.
 * The class extends the BaseModel variables with some more specific parameter.
 * <p>
 * This class responsible for the Order object storing and contain the necessary
 * getter and setter methods. Implement the {@link Orderable} interface pay method.
 *
 * @author gem
 * @version 1.8
 */


public class Order extends BaseModel implements Orderable {
    private int id;
    private String name;
    public Address address;
    private OrderStatus status;
    private static int counter;
    private List<LineItem> lineItemList;

    /**
     * Basic Constructor, which invoke the super class {@link BaseModel} constructor
     * with the name parameter
     *
     * @param name the order's name
     */
    public Order(String name) {
        super(name);
    }

    /**
     * Constructor, which invoke the super class {@link BaseModel} constructor and extend that
     * with the {@link LineItem} list.
     *
     * @param name the order's name
     * @param lineItemList the orders's selected {@link LineItem}
     */
    public Order(String name, List<LineItem> lineItemList) {
        super(name);
        this.status = OrderStatus.NEW;
        this.lineItemList = lineItemList;
    }

    /**
     * Constructor, which invoke the super class {@link BaseModel} constructor and extend that
     * with the {@link LineItem} list.
     *
     * @param name the order's name
     * @param lineItemList the orders's selected {@link LineItem}
     * @param address the order's address
     */
    public Order(String name, Address address, List<LineItem> lineItemList) {
        super(name);
        this.id = counter;
        counter++;
        this.address = address;
        this.status = OrderStatus.NEW;
        this.lineItemList = lineItemList;
    }

    /**
     * @return return the order's {@link Address}
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @return return the order's {@link OrderStatus}
     */
    public OrderStatus getStatus() {
        return this.status;
    }


    /**
     * Check the order's status and change it from {@link OrderStatus} NEW to
     * {@link OrderStatus} PAID.
     *
     * @return return <code>true</code> if the pay was success, else return with <code>false</code>
     */
    public boolean pay() {
        if (this.status == OrderStatus.NEW) {
            this.status = OrderStatus.PAID;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }

}
