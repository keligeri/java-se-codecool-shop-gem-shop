package com.codecool.shop.model;
import java.util.ArrayList;

/**
 * Supplier class extends from the {@link BaseModel}.
 * The class extends the BaseModel variables with some more specific parameter.
 * <p>
 * This class responsible for the product category object storing and contain the necessary
 * getter and setter methods.
 *
 * @author gem
 * @version 1.8
 */
public class Supplier extends BaseModel {
    private ArrayList<Product> products;

    /**
     * Constructor, which invoke the super class {@link BaseModel} constructor and extend that
     * with some more product specific parameter.
     *
     * @param name the supplier's name
     * @param description the supplier's short description
     */
    public Supplier(String name, String description) {
        super(name,description);
        this.products = new ArrayList<>();
    }

    /**
     * @param products set the given {@link Product} for the {@link Supplier} object
     */
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * @return the all {@link Product} from the given supplier
     */
    public ArrayList getProducts() {
        return this.products;
    }


    /**
     * @return add {@link Product} for the given {@link Supplier} object
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description
        );
    }
}