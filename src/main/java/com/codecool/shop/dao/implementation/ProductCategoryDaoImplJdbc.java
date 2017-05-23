package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.DatabaseConnectionData;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.ProductCategory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eszti on 2017.05.16..
 */


public class ProductCategoryDaoImplJdbc extends JdbcDao implements ProductCategoryDao {

    /**
     * Added the given {@link ProductCategory} to the database.
     *
     * @param category the added {@link ProductCategory}
     */
    @Override
    public void add(ProductCategory category) {
        try {
            String query = "INSERT INTO Categories " +
                    "(category_name, category_department, category_description)" +
                    " VALUES(?,?,?);";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDepartment());
            stmt.setString(3, category.getDescription());
            executeQuery(stmt.toString());
            connection.close();
        }
        catch (SQLException e) {
            System.out.println("Category could not be added to the database.");
        }

    }

    /**
     * Find the {@link ProductCategory} with the given id from the database.
     *
     * @param id the {@link ProductCategory}'s id
     */
    @Override
    public ProductCategory find(int id)  {
        String query = "SELECT * FROM categories WHERE id = ?;";

        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()){
                ProductCategory category = new ProductCategory(
                        resultSet.getString("category_name"),
                        resultSet.getString("category_department"),
                        resultSet.getString("category_description"));
                category.setId(resultSet.getInt("id"));
                connection.close();
                return category;
            }
            connection.close();
            return null;
        }
        catch (SQLException e) {
            return null;
        }
    }

    /**
     * Remove the {@link ProductCategory} with the given id from the database.
     *
     * @param id the {@link ProductCategory}'s id
     */
    @Override
    public void remove(int id) {

        try {
            String query = "DELETE FROM categories WHERE id = ?";

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            executeQuery(stmt.toString());
            connection.close();}
        catch (SQLException e) {
            System.out.println("Could not remove category from database.");
        }

    }

    /**
     * Get all {@link Order} int a List from the database.
     *
     * @return the all {@link Order} from the database
     */
    @Override
    public List<ProductCategory> getAll() {

        try {
            List<ProductCategory> results = new ArrayList<>();
            String query = "SELECT * FROM categories;";

            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                int dbId = resultSet.getInt("id");
                ProductCategory category = new ProductCategory(
                        resultSet.getString("category_name"),
                        resultSet.getString("category_department"),
                        resultSet.getString("category_description"));
                category.setId(dbId);
                results.add(category);
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

