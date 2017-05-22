package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DatabaseConnectionData;
import com.codecool.shop.dao.OrderDao;
import com.codecool.shop.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marti on 2017.05.16..
 */
public class OrderDaoJdbc extends JdbcDao implements OrderDao {
    private static final Logger logger = LoggerFactory.getLogger(OrderDaoJdbc.class);
    ProductDaoImplJdbc productJdbc = new ProductDaoImplJdbc();
    LineItemDaoImplJdbc lineItemJdbc = new LineItemDaoImplJdbc();

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
            logger.info("Added {} id's Order to the db", order.getId());
        }
        catch (SQLException e) {
            logger.debug("Can't add Orders to the db", e);
            e.printStackTrace();
        }
    }

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
            logger.debug("Can't find LineItem from the db", e);
            return null;
        }
    }

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
                logger.info("Find {} id's Order to the db", id);
                return order;
            }
            connection.close();
            return null;
        }
        catch (SQLException e) {
            logger.debug("Can't find Order from the db", e);
            return null;
        }
    }

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
            logger.info("Remove {} id's Order from the db", id);
        }
        catch (SQLException e) {
            logger.debug("Can't remove Order from the db", e);
        }
    }

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
            logger.info("Get all Orders from the db");
            return results;
        }
        catch (SQLException e) {
            logger.debug("Can't get all Order from the db", e);
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
