package com.codecool.shop.model;
import java.util.ArrayList;

/**
 * ProductCategory class extends from the {@link BaseModel}.
 * The class extends the BaseModel variables with some more specific parameter.
 * <p>
 * This class responsible for the product category object storing and contain the necessary
 * getter and setter methods.
 *
 * @author gem
 * @version 1.8
 */

public class ProductCategory extends BaseModel {
    private String department;
    private ArrayList<Product> products;

    /**
     * Constructor, which invoke the super class {@link BaseModel} constructor and extend that
     * with some more product specific parameter.
     *
     * @param name the product category's name
     * @param department the product category's department
     * @param description the product category's short description
     */
    public ProductCategory(String name, String department, String description) {
        super(name);
        this.department = department;
        this.products = new ArrayList<>();
    }

    /**
     * @return the product category department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the new department for the given product category object
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @param products set the given {@link Product} for the category object
     */
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * @return the all {@link Product} from the given category
     */
    public ArrayList getProducts() {
        return this.products;
    }

    /**
     * @return add {@link Product} for the given category object
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }

    @Override
    public String toString() {
        return String.format(
                "id: %1$d," +
                        "name: %2$s, " +
                        "department: %3$s, " +
                        "description: %4$s",
                this.id,
                this.name,
                this.department,
                this.description);
    }
}