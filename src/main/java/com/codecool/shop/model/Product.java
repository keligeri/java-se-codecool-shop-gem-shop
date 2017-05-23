package com.codecool.shop.model;
import java.util.Currency;

/**
 * Product class extends from the {@link BaseModel}.
 * The class has extends the BaseModel variables with some more specific parameter.
 * <p>
 * This class responsible for the product object storing and contain the necessary
 * getter and setter methods.
 *
 * @author gem
 * @version 1.8
 */

public class Product extends BaseModel {

    private float defaultPrice;
    private Currency defaultCurrency;
    private ProductCategory productCategory;
    private Supplier supplier;

    /**
     * Constructor, which invoke the super class (BaseModel) constructor and extend that
     * with some more product specific parameter.
     *
     * @param name the product name
     * @param defaultPrice the product default price
     * @param currencyString the product default currency in String
     * @param description the product short description
     * @param productCategory the product category object
     * @param supplier the product supplier object
     */
    public Product(String name, float defaultPrice, String currencyString, String description, ProductCategory productCategory, Supplier supplier) {
        super(name, description);
        this.setPrice(defaultPrice, currencyString);
        this.setSupplier(supplier);
        this.setProductCategory(productCategory);
    }

    /**
     * @return the product default price
     */
    public float getDefaultPrice() {
        return defaultPrice;
    }

    /**
     * @param defaultPrice the new default price for the given product object
     */
    public void setDefaultPrice(float defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    /**
     * @return get the product default currency
     */
    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    /**
     * @param defaultCurrency new currency for the given product object
     */
    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    /**
     * @return the product's default price and the product's currency
     */
    public String getPrice() {
        return String.valueOf(this.defaultPrice) + " " + this.defaultCurrency.toString();
    }

    /**
     * @return the product's default price in int
     */
    public float gerPriceInFloat(){return defaultPrice;}

    /**
     * @param price set the price for the given product object
     * @param currency set the currency for the given product object
     */
    public void setPrice(float price, String currency) {
        this.defaultPrice = price;
        this.defaultCurrency = Currency.getInstance(currency);
    }

    /**
     * @return the product's category
     */
    public ProductCategory getProductCategory() {
        return productCategory;
    }

    /**
     * @param productCategory set the category for the given product object
     */
    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        this.productCategory.addProduct(this);
    }

    /**
     * @return the product's supplier
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * @param supplier set the supplier for the given product object
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
        this.supplier.addProduct(this);
    }

    @Override
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "defaultPrice: %3$f, " +
                        "defaultCurrency: %4$s, " +
                        "productCategory: %5$s, " +
                        "supplier: %6$s",
                this.id,
                this.name,
                this.defaultPrice,
                this.defaultCurrency.toString(),
                this.productCategory.getName(),
                this.supplier.getName());
    }
}
