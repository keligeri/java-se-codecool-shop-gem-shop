package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DatabaseConnectionData;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marti on 2017.05.16..
 */
public class OrderDaoJdbc extends JdbcDao implements OrderDao {

    ProductDaoImplJdbc productJdbc = new ProductDaoImplJdbc();
    LineItemDaoImplJdbc lineItemJdbc = new LineItemDaoImplJdbc();

    /**
     * Added the given {@link Order} to the database.
     *
     * @param order the added {@link Order}
     */
    @Override
    public void add(Order order) {
        String query = "INSERT INTO orders (status_name" +
                ") " +
                "VALUES(?);";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, order.getStatus().getValue());
            stmt.executeQuery();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Find the {@link LineItem} with the given id from the database.
     *
     * @param order_id the {@link Order}'s id
     */
    public List<LineItem> findLineItems(int order_id) {
        List<LineItem> results = new ArrayList<>();
        String query = "SELECT * FROM Line_items WHERE order_id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, order_id);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int lineId = resultSet.getInt("id");
                LineItem lineItem = new LineItem(productJdbc.find(
                        resultSet.getInt("product_id")));
                lineItem.setId(lineId);
                results.add(lineItem);
            }
            connection.close();
            return results;
        }
        catch (SQLException e) {
            return null;
        }
    }

    /**
     * Find the {@link Order} with the given id from the database.
     *
     * @param id the {@link Order}'s id
     */
    @Override
    public Order find(int id) {
        String query = "SELECT * FROM orders WHERE id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Order order = new Order(resultSet.getString("order_name"),
                        findLineItems(id));
                connection.close();
                return order;
            }
            connection.close();
            return null;
        }
        catch (SQLException e) {
            return null;
        }
    }

    /**
     * Remove the {@link Order} with the given id from the database.
     *
     * @param id the {@link Order}'s id
     */
    @Override
    public void remove(int id) {
        String query = "DELETE FROM orders WHERE id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            List<LineItem> lineItems = findLineItems(id);
            for(LineItem lineItem : lineItems) {
            lineItemJdbc.remove(lineItem.getId());
            }
            stmt.executeUpdate();
            connection.close();

        }
        catch (SQLException e) {
            System.out.println("Order could not be removed.");
        }
    }

    /**
     * Get all {@link Order} int a List from the database.
     *
     * @return the all {@link Order} from the database
     */
    @Override
    public List<Order> getAll() {
        List<Order> results = new ArrayList<>();
        String query = "SELECT * FROM orders;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int dbId = resultSet.getInt("id");
                Order order = new Order(resultSet.getString("order_name"),
                        findLineItems(dbId));
                order.setId(dbId);
                results.add(order);
            }
            connection.close();
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
        DatabaseConnectionData dbConn = new DatabaseConnectionData("connection.properties");
        return DriverManager.getConnection(
                dbConn.getDb(),
                dbConn.getDbUser(),
                dbConn.getDbPassword());
    }

}
