package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DatabaseConnectionData;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marti on 2017.05.15..
 */
public class SupplierDaoJdbc extends JdbcDao implements SupplierDao {
    private static final Logger logger = LoggerFactory.getLogger(SupplierDaoJdbc.class);

    @Override
    public void add(Supplier supplier) {
        try {
            String query = "INSERT INTO suppliers (supplier_name, supplier_description) VALUES(?,?);";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getDescription());
            executeQuery(stmt.toString());
            connection.close();
            logger.info("Added {} id's Supplier to the db", supplier.getId());
        }
        catch (SQLException e) {
            logger.debug("Can't add Supplier to the db", e);
        }

    }

    @Override
    public Supplier find(int id)  {
        String query = "SELECT * FROM suppliers WHERE id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()){
            Supplier supplier = new Supplier(resultSet.getString("supplier_name"),
                resultSet.getString("supplier_description"));
                supplier.setId(id);
            return supplier;
        }
            connection.close();
            logger.info("Find {} id's Supplier from the db", id);
            return null;
        } catch (SQLException e) {
            logger.debug("Can't find ProductCategory in the db", e);
            return null;
        }
    }

    @Override
    public void remove(int id) {

        try {
            String query = "DELETE FROM suppliers WHERE id = ?";

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            executeQuery(stmt.toString());
            connection.close();
            logger.info("Remove {} id's Supplier from the db", id);

        }
        catch (SQLException e) {
            logger.debug("Can't remove Supplier from the db", e);
        }

    }

    @Override
    public List<Supplier> getAll() {

        try {
            List<Supplier> results = new ArrayList<>();
            String query = "SELECT * FROM suppliers;";

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int dbId = resultSet.getInt("id");
                Supplier supplier = new Supplier(resultSet.getString("supplier_name"),
                        resultSet.getString("supplier_description"));
                supplier.setId(dbId);
                results.add(supplier);
            }
            connection.close();
            logger.info("Get all Supplier from the db");
            return results;
        }
        catch (SQLException e) {
            logger.debug("Can't get all Supplier from the db", e);
            return null;
        }
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


