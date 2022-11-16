package Util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    
    private static final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final Properties properties = new Properties();

    private ConnectionFactory(){
        try{
            properties.load(new FileReader("src/main/Resources/db.properties"));
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The purpose for this method is for the program to get the factory
     * @return ConnectionFactory Object
     */
    public static ConnectionFactory getConnectionFactory(){
        return connectionFactory;
    }

    public Connection getConnection(){
        try{
            return DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
        }catch(SQLException exception){
            exception.printStackTrace();
            return null;
        }
    }
}
