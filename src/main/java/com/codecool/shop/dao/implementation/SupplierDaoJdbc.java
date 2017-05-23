package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DatabaseConnectionData;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marti on 2017.05.15..
 */
public class SupplierDaoJdbc extends JdbcDao implements SupplierDao {

    /**
     * Added the given {@link Supplier} to the database.
     *
     * @param supplier the added {@link Supplier}
     */
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
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Supplier could not be added to the database.");
        }

    }

    /**
     * Find the {@link Supplier} with the given id from the database.
     *
     * @param id the {@link Supplier}'s id
     */
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
        return null;
        }
        catch (SQLException e) {
            return null;
        }
    }

    /**
     * Remove the {@link Supplier} with the given id from the database.
     *
     * @param id the {@link Supplier}'s id
     */
    @Override
    public void remove(int id) {

        try {
        String query = "DELETE FROM suppliers WHERE id = ?";

        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);
        executeQuery(stmt.toString());
        connection.close();}
        catch (SQLException e) {
            System.out.println("Could not remove supplier from database.");
        }

    }

    /**
     * Get all {@link Supplier} int a List from the database.
     *
     * @return the all {@link Supplier} from the database
     */
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
        return results;}
        catch (SQLException e) {
            return null;
        }
    }

    /**
     * Create a {@link DatabaseConnectionData} instance invoke the setup
     * and add the returned value to the {@link DriverManager}.
     *
     * @throws SQLException so have to handle it
     */
    @Override
    Connection getConnection() throws SQLException {
        DatabaseConnectionData dbConn = new DatabaseConnectionData("connection.properties");
        return DriverManager.getConnection(
                dbConn.getDb(),
                dbConn.getDbUser(),
                dbConn.getDbPassword());
    }

}


