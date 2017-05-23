package com.codecool.shop.dao.implementation;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * {@link JdbcDao} abstract class responsible for the db connection and the query execution.
 * <p>
 * This class contain all the methods, which need to can execute query int the db.
 *
 * @author gem
 * @version 1.8
 */
public abstract class JdbcDao {

    /**
     * @throws <code>SQLException</code> if the connection failed
     */
    abstract Connection getConnection() throws SQLException;

    /**
     * Execute the given query, but not return with the result.
     * <p>
     * Catch the <code>SQLException</code> exception.
     *
     * @param query the given query, which have to execute
     */
    void executeQuery(String query) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()
        ){
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}