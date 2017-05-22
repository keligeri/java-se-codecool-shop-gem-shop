package com.codecool.shop.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by eszti on 2017.04.27..
 */
public class CheckoutProcess extends AbstractProcess {
    private static final Logger logger = LoggerFactory.getLogger(CheckoutProcess.class);

    protected void action(Orderable item) {
        logger.info("Checkout process done.");
    }
}
