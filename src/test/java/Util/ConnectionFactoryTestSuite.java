package Util;

import org.junit.Test;
import org.junit.Assert;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactoryTestSuite {
    
    @Test
    public void test_getConnection_returnValidConnection_givenProvidedCredentialsAreCorrect(){
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()){
            System.out.println(connection);
            Assert.assertNotNull(connection);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
