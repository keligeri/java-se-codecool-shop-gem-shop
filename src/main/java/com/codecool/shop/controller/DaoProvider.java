package com.codecool.shop.controller;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;

/**
 *
 * The {@link DaoProvider} class responsible to decide, which object have to return.
 * <p>
 * If the only one public method <code>setupDaoJdbc</code> parameter is <code>true</code>,
 * the class setup the instances from DaoJdbc instances, else setup with DaoMem instances.
 *
 */
public class DaoProvider {
    public static ProductDao productDao;
    public static LineItemDao lineItemDao;
    public static OrderDao orderDao;
    public static ProductCategoryDao productCategoryDao;
    public static SupplierDao supplierDao;

    /**
     * Invoke the setup method, depend on the isDb parameter.
     * @param isDb if is <code>true</> setup variables to JDBC instances, else setup with Mem instances.
     */
    public static void setup(boolean isDb) {
        if (!isDb) setupDaoMem();
        else setupDaoJdbc();
    }

    /**
     * Assign the variables with DaoJdbc instances.
     */
    private static void setupDaoJdbc(){
        productDao = new ProductDaoImplJdbc();
        lineItemDao = new LineItemDaoImplJdbc();
        orderDao = new OrderDaoJdbc();
        productCategoryDao = new ProductCategoryDaoImplJdbc();
        supplierDao = new SupplierDaoJdbc();
    }

    /**
     * Assign the variables with DaoMem instances.
     */
    private static void setupDaoMem(){
        productDao = ProductDaoMem.getInstance();
        lineItemDao = LineItemDaoMem.getInstance();
        orderDao = OrderDaoMem.getInstance();
        productCategoryDao = ProductCategoryDaoMem.getInstance();
        supplierDao = SupplierDaoMem.getInstance();
    }
}
