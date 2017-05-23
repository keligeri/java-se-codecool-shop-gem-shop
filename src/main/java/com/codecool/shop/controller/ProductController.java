package com.codecool.shop.controller;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductDaoImplJdbc;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.ShoppingCart;
import spark.Request;
import spark.Response;
import spark.ModelAndView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@link ProductController} class responsible to transmit parameters
 * to Thymeleaf {@link ModelAndView} template.
 */
public class ProductController {
    private static ProductDao productDataStore;
    private static ProductCategoryDao productCategoryDataStore;
    private static SupplierDao supplierDataStore;

    /**
     * Check the {@link DaoProvider} instances and if the
     *  <ul>
     *      <li>productDataStore</li>
     *      <li>productCategoryDataStore</li>
     *      <li>supplierDataStore</li>
     *  </ul>
     *   If the above variables values is null setup with the DaoProvider instances.
     */
    private static void checkAndSetupStaticVar(){
        if (productDataStore == null) productDataStore = DaoProvider.productDao;
        if (productCategoryDataStore == null) productCategoryDataStore = DaoProvider.productCategoryDao;
        if (supplierDataStore == null) supplierDataStore = DaoProvider.supplierDao;
    }

    /**
     * Render all products from the database
     * <p>
     * Render all product from the product table. Store the product's information in in a HashMap,
     * with the following key
     * <ul>
     *     <li>categories ({@link ProductDaoImplJdbc})</li>
     *     <li>suppliers ({@link SupplierDaoJdbc})</li>
     *     <li>products ({@link ProductDaoImplJdbc})</li>
     *     <li>shoppingListSize ({@link ShoppingCart})</li>
     * </ul>
     *
     * @param req {@link Request} object from the request
     * @param res {@link Response} object from the response
     * @return a new {@link ModelAndView} instance, with product/index viewName
     */
    public static ModelAndView renderProducts(Request req, Response res) {
        checkAndSetupStaticVar();
        Map params = new HashMap<>();
        ShoppingCart currentSession = req.session().attribute("shoppingCart");
        int shoppingListSize = currentSession.getShoppingList().size();
        params.put("categories", productCategoryDataStore.getAll());
        params.put("suppliers", supplierDataStore.getAll());
        params.put("products", productDataStore.getAll());
        params.put("shoppingListSize", shoppingListSize);
        return new ModelAndView(params, "product/index");
    }

    /**
     * Render all products by the given category
     * <p>
     * Render all product from the product table by the given category.
     * Store the product's information in in a HashMap, with the following key
     * <ul>
     *     <li>categories ({@link ProductDaoImplJdbc})</li>
     *     <li>suppliers ({@link SupplierDaoJdbc})</li>
     *     <li>products ({@link ProductDaoImplJdbc})</li>
     *     <li>shoppingListSize ({@link ShoppingCart})</li>
     * </ul>
     *
     * @param req {@link Request} object from the request
     * @param res {@link Response} object from the response
     * @return a new {@link ModelAndView} instance, with product/index viewName
     */
    public static ModelAndView renderByCategory(Request req, Response res) {
        checkAndSetupStaticVar();
        int searchedId = Integer.parseInt(req.params(":id"));
        ShoppingCart currentSession = req.session().attribute("shoppingCart");
        int shoppingListSize = currentSession.getShoppingList().size();
        Map params = new HashMap<>();
        params.put("categories", productCategoryDataStore.getAll());
        params.put("suppliers", supplierDataStore.getAll());
        params.put("products", productDataStore.getBy(productCategoryDataStore.find(searchedId)));
        params.put("shoppingListSize", shoppingListSize);
        return new ModelAndView(params, "product/index");
    }

    /**
     * Render all products by the given supplier
     * <p>
     * Render all product from the product table by the given supplier.
     * Store the product's information in in a HashMap, with the following key
     * <ul>
     *     <li>categories ({@link ProductDaoImplJdbc})</li>
     *     <li>suppliers ({@link SupplierDaoJdbc})</li>
     *     <li>products ({@link ProductDaoImplJdbc})</li>
     *     <li>shoppingListSize ({@link ShoppingCart})</li>
     * </ul>
     *
     * @param req {@link Request} object from the request
     * @param res {@link Response} object from the response
     * @return a new {@link ModelAndView} instance, with product/index viewName
     */
    public static ModelAndView renderBySupplier(Request req, Response res) {
        checkAndSetupStaticVar();
        int searchedId = Integer.parseInt(req.params(":id"));
        Map params = new HashMap<>();
        ShoppingCart currentSession = req.session().attribute("shoppingCart");
        int shoppingListSize = currentSession.getShoppingList().size();
        params.put("suppliers", supplierDataStore.getAll());
        params.put("categories", productCategoryDataStore.getAll());
        params.put("products", productDataStore.getBy(supplierDataStore.find(searchedId)));
        params.put("shoppingListSize", shoppingListSize);
        return new ModelAndView(params, "product/index");
    }

    /**
     * Render all line item ({@link com.codecool.shop.model.LineItem}) with a new route
     * <p>
     * Render all line item from the database.
     * Store the items's information in in a HashMap, with the following key
     * <ul>
     *     <li>totalprice ({@link ProductDaoImplJdbc})</li>
     *     <li>currency ({@link SupplierDaoJdbc})</li>
     * </ul>
     *
     * @param req {@link Request} object from the request
     * @param res {@link Response} object from the response
     * @param shoppingCart the current ShoppingCart {@link ShoppingCart} instance
     * @return a new {@link ModelAndView} instance, with product/cart viewName
     */
    public static ModelAndView renderToCart(Request req, Response res, ShoppingCart shoppingCart) {
        checkAndSetupStaticVar();
        Map params = new HashMap<>();
        params.put("lineitems", shoppingCart.getShoppingList());
        if (0 < shoppingCart.getShoppingList().size()) {
            params.put("totalprice", shoppingCart.getTotalPrice());
            params.put("currency", shoppingCart.getShoppingList().get(0).getProduct().getDefaultCurrency());
        } else {
            params.put("totalprice", 0);
            params.put("currency", "USD");
        }
        return new ModelAndView(params, "product/cart");
    }


    /**
     * Render to the checkout route, without data.
     *
     * @param req {@link Request} object from the request
     * @param res {@link Response} object from the response
     * @return a new {@link ModelAndView} instance, with product/cart viewName
     */
    public static ModelAndView renderToCheckout(Request req, Response res) {
        checkAndSetupStaticVar();
        Map params = new HashMap<>();
        return new ModelAndView(params, "product/checkout");
    }

}
