package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DatabaseConnectionData;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Created by keli on 2017.05.15..
 */
public class ProductDaoImplJdbc extends JdbcDao implements ProductDao{
    private static final Logger logger = LoggerFactory.getLogger(ProductDaoImplJdbc.class);

    @Override
    public void add(Product product){
        String query = "INSERT INTO products (product_name, product_description, default_price, currency_id, " +
                "category_id, supplier_id) VALUES(?, ?, ?, ?, ?, ?);";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setFloat(3, product.getDefaultPrice());
            stmt.setString(4, product.getDefaultCurrency().toString());
            stmt.setInt(5, product.getProductCategory().getId());
            stmt.setInt(6, product.getSupplier().getId());
            executeQuery(stmt.toString());
            connection.close();
            logger.info("Added {} id's to the Product table", product.getId());
        } catch (SQLException e){
            logger.info("Couldn't added {} id's to the Product table", product.getId(), e);
        }

    }

    @Override
    public Product find(int id) {
        ProductCategoryDaoImplJdbc productCategoryDaoImplJdbc = new ProductCategoryDaoImplJdbc();
        SupplierDaoJdbc supplierDaoJdbc = new SupplierDaoJdbc();
        String query = "SELECT * FROM products WHERE id = ?;";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()){
                Product product = new Product(resultSet.getString("product_name"),
                        resultSet.getFloat("default_price"),
                        Currency.getInstance(resultSet.getString("currency_id")).getCurrencyCode(),
                        resultSet.getString("product_description"),
                        productCategoryDaoImplJdbc.find(resultSet.getInt("category_id")),
                        supplierDaoJdbc.find(resultSet.getInt("supplier_id")));
                product.setId(resultSet.getInt("id"));
                logger.info("Find {} id's Product from the db", id);
                return product;
            }
            connection.close();
        } catch (SQLException e){
            logger.debug("Couldn't find {} id's from the db", id,  e);
        }
        return null;
    }

    @Override
    public void remove(int id){
        String query = "DELETE FROM products WHERE id = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            executeQuery(stmt.toString());
            connection.close();
            logger.info("Remove {} id's Product from the db", id);
        } catch (SQLException e){
            logger.debug("Couldn't remove the product", e);
        }
    }

    public List<Product> getAll() {
        ProductCategoryDaoImplJdbc productCategoryDaoImplJdbc = new ProductCategoryDaoImplJdbc();
        List<Product> productList = new ArrayList<>();
        SupplierDaoJdbc supplierDaoJdbc = new SupplierDaoJdbc();

        String query = "SELECT * from products;";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                Product product = new Product(resultSet.getString("product_name"),
                        resultSet.getFloat("default_price"),
                        Currency.getInstance(resultSet.getString("currency_id")).getCurrencyCode(),
                        resultSet.getString("product_description"),
                        productCategoryDaoImplJdbc.find(resultSet.getInt("category_id")),
                        supplierDaoJdbc.find(resultSet.getInt("supplier_id")));
                product.setId(resultSet.getInt("id"));
                productList.add(product);
            }
            connection.close();
            logger.info("Get all Product from the db");
        } catch (SQLException exception){
            logger.debug("Can't get all Products from the db", exception);
        }
        return productList;
    }

    @Override
    public List<Product> getBy(Supplier supplier) {
        ProductCategoryDaoImplJdbc productCategoryDaoImplJdbc = new ProductCategoryDaoImplJdbc();
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM products WHERE supplier_id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, supplier.getId());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                Product product = new Product(resultSet.getString("product_name"),
                        resultSet.getFloat("default_price"),
                        Currency.getInstance(resultSet.getString("currency_id")).getCurrencyCode(),
                        resultSet.getString("product_description"),
                        productCategoryDaoImplJdbc.find(resultSet.getInt("category_id")),
                        supplier);
                product.setId(resultSet.getInt("id"));
                productList.add(product);
            }
            connection.close();
            logger.info("Get Product from {} supplier's id", supplier.getId());
        } catch (SQLException e){
            logger.debug("Can't find ProductCategory from {} id's supplier", supplier.getId() , e);
        }
        return productList;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) {
        List<Product> productListByCategory = new ArrayList<>();
        SupplierDaoJdbc supplierDaoJdbc = new SupplierDaoJdbc();
        String query = "SELECT * FROM products WHERE category_id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, productCategory.getId());
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                Product product = new Product(resultSet.getString("product_name"),
                        resultSet.getFloat("default_price"),
                        Currency.getInstance(resultSet.getString("currency_id")).getCurrencyCode(),
                        resultSet.getString("product_description"),
                        productCategory,
                        supplierDaoJdbc.find(resultSet.getInt("supplier_id")));
                product.setId(resultSet.getInt("id"));
                productListByCategory.add(product);
            }
            connection.close();
            logger.info("Get Product from {} productsCategory's id", productCategory.getId());
        } catch (SQLException e){
            logger.debug("Can't find ProductCategory from {} id's category", productCategory.getId() , e);
        }
        return productListByCategory;
    }

    @Override
    Connection getConnection() throws SQLException {
        DatabaseConnectionData dbConn = new DatabaseConnectionData("connection.properties");
        return DriverManager.getConnection(
                dbConn.getDb(),
                dbConn.getDbUser(),
                dbConn.getDbPassword());
    }
}
