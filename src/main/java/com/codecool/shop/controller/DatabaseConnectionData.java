package com.codecool.shop.controller;
import com.codecool.shop.dao.implementation.JdbcDao;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * {@link DatabaseConnectionData} class responsible for config file reading
 * and setup the required params for the connection.
 * <p
 * Read the necessary parameter from the config gile (resources/connection.properties)
 * and setup the parsed parameter for the correct variables.
 *
 * @author gem
 * @version 1.8
 */

public class DatabaseConnectionData {

    private String filePath = "src/main/resources/";
    private String DB_URL;
    private String DB_NAME;
    private String DB_USER;
    private String DB_PASSWORD;

    /**
     * Parse the config file and invoke the <code>setupUserAndPasswordFromFile(String fileName)</code> method.
     * Catch the <code>IOException</code> exception.
     *
     * @param filePath the given path, where the files is found
     */
    public DatabaseConnectionData(String filePath){
        try {
            setupUserAndPasswordFromFile(filePath);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public String getDb() {
        return "jdbc:postgresql://" + DB_URL + "/" + DB_NAME;
    }

    public String getDbUser(){
        return DB_USER;
    }

    public String getDbPassword(){
        return DB_PASSWORD;
    }

    /**
     * Parse the config file and invoke the and setup the private variables without return anything.
     *
     * @param fileName the given path, where the files is found
     * @throws IOException so have to handle it
     */
    private void setupUserAndPasswordFromFile(String fileName) throws IOException{
        List<String> allLinesList = Files.readAllLines(Paths.get(filePath + fileName));
        DB_URL = allLinesList.get(0);
        DB_NAME = allLinesList.get(1);
        DB_USER = allLinesList.get(2);
        DB_PASSWORD = allLinesList.get(3);
    }
}

