package com.codecool.shop.dao.implementation;
import java.io.IOException;
import java.sql.*;

import com.codecool.shop.controller.DatabaseConnectionData;
import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link LineItemDaoImplJdbc} class extends from {@link JdbcDao} and implements {@link LineItemDao} interface.
 * Extends these interface and class with basic database manipulation methods.
 * <p>
 * This class contain all the methods, which setup the query as String and execute it.
 *
 * @author gem
 * @version 1.8
 */
public class LineItemDaoImplJdbc extends JdbcDao implements LineItemDao {

    ProductDaoImplJdbc productJdbc = new ProductDaoImplJdbc();

    /**
     * Added the given {@link LineItem} to the database.
     *
     * @param lineItem the added {@link LineItem}
     */
    @Override
    public void add(LineItem lineItem) {
        String query = "INSERT INTO Line_items (product_id, quantity, price) " +
                "VALUES(?, ?, ?);";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, lineItem.getProduct().getId());
            stmt.setInt(2, lineItem.getQuantity());
            stmt.setFloat(3, lineItem.getPrice());
            executeQuery(stmt.toString());
        }
        catch (SQLException e) {
            System.out.println("LineItem could not be added to the database.");
        }
    }

    /**
     * Find the {@link LineItem} with the given id from the database.
     *
     * @param id the {@link LineItem}'s id
     */
    @Override
    public LineItem find(int id) {
        String query = "SELECT * FROM Line_items WHERE id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                LineItem lineItem = new LineItem(productJdbc.find(
                        resultSet.getInt("product_id")));
                return lineItem;
            }
            return null;
        }
        catch (SQLException e) {
            return null;
        }
    }

    /**
     * Remove the {@link LineItem} with the given id from the database.
     *
     * @param id the {@link LineItem}'s id
     */
    @Override
    public void remove(int id) {
        String query = "DELETE FROM Line_items WHERE id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            executeQuery(stmt.toString());
        }
        catch (SQLException e) {
            System.out.println("LineItem could not be removed.");
        }
    }

    /**
     * Get all {@link LineItem} int a List from the database.
     *
     * @return the all {@link LineItem} from the database
     */
    @Override
    public List<LineItem> getAll() {
        List<LineItem> results = new ArrayList<>();
        String query = "SELECT * FROM Line_items;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int dbId = resultSet.getInt("id");
                LineItem lineItem = new LineItem(productJdbc.find(
                        resultSet.getInt("product_id")));
                lineItem.setId(dbId);
                results.add(lineItem);
            }
            return results;
        }
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
        DatabaseConnectionData conn = new DatabaseConnectionData("connection.properties");
        return DriverManager.getConnection(
                conn.getDb(),
                conn.getDbUser(),
                conn.getDbPassword());
    }
}

