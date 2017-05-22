package com.codecool.shop.dao.implementation;
import java.sql.*;

import com.codecool.shop.controller.DatabaseConnectionData;
import com.codecool.shop.dao.LineItemDao;
import com.codecool.shop.model.LineItem;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eszti on 2017.05.16..
 */
public class LineItemDaoImplJdbc extends JdbcDao implements LineItemDao {
    private static final Logger logger = LoggerFactory.getLogger(LineItemDaoImplJdbc.class);
    ProductDaoImplJdbc productJdbc = new ProductDaoImplJdbc();

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
            logger.info("Added {} id's LineItem to the db", lineItem.getId());
        }
        catch (SQLException e) {
            logger.debug("Can't add LineItem to the db", e);
        }
    }

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
                logger.info("Get {} id's LineItem to the db", id);
                return lineItem;
            }
            return null;
        }
        catch (SQLException e) {
            logger.debug("Can't find LineItem from the db", e);
            return null;
        }
    }

    @Override
    public void remove(int id) {
        String query = "DELETE FROM Line_items WHERE id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            executeQuery(stmt.toString());
            logger.info("Remove {} id's LineItem from the db", id);
        }
        catch (SQLException e) {
            logger.debug("Can't remove LineItem from the db", e);
        }
    }

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
            logger.info("Get all LineItem from the db");
            return results;
        }
        catch (SQLException e) {
            logger.debug("Can't get all LineItem from the db", e);
            return null;
        }
    }

    @Override
    Connection getConnection() throws SQLException {
        DatabaseConnectionData conn = new DatabaseConnectionData("connection.properties");
        return DriverManager.getConnection(
                conn.getDb(),
                conn.getDbUser(),
                conn.getDbPassword());
    }
}

