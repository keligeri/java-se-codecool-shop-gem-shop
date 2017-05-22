package com.codecool.shop.controller;

import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by keli on 2017.05.18..
 */
public class DaoProvider {
    private static final Logger logger = LoggerFactory.getLogger(DaoProvider.class);

    public static ProductDao productDao;
    public static LineItemDao lineItemDao;
    public static OrderDao orderDao;
    public static ProductCategoryDao productCategoryDao;
    public static SupplierDao supplierDao;

    public static void setup(boolean isDb) {
        if (!isDb) setupDaoMem();
        else setupDaoJdbc();
    }

    private static void setupDaoJdbc(){
        productDao = new ProductDaoImplJdbc();
        lineItemDao = new LineItemDaoImplJdbc();
        orderDao = new OrderDaoJdbc();
        productCategoryDao = new ProductCategoryDaoImplJdbc();
        supplierDao = new SupplierDaoJdbc();
        logger.info("Setup instances to DaoJdbc");
    }

    private static void setupDaoMem(){
        productDao = ProductDaoMem.getInstance();
        lineItemDao = LineItemDaoMem.getInstance();
        orderDao = OrderDaoMem.getInstance();
        productCategoryDao = ProductCategoryDaoMem.getInstance();
        supplierDao = SupplierDaoMem.getInstance();
        logger.info("Setup instances to DaoMem");
    }
}
