import Models.Ticket;
import Models.User;
import org.junit.Test;
import org.junit.Assert;

import java.util.List;

public class ERSDAOTestSuite {

    ERSDAO ersdao = new ERSDAO();
    @Test
    public void test_getUser_returnValidUser_givenMatchingEmail(){
        //arrange
        User user = new User(4, "test@example.com", "password", false);
        //act
        User testUser = ersdao.getUser(user.getEmail());
        //assert
        Assert.assertNotNull(testUser);
        Assert.assertEquals(testUser.getEmail(), user.getEmail());
        Assert.assertEquals(testUser.getId(), user.getId());
        Assert.assertEquals(testUser.getPassword(), user.getPassword());
        Assert.assertEquals(testUser.getIsManager(), user.getIsManager());
    }

    @Test
    public void test_getTicketsByUser_returnValidList_givenValidUser(){
        User user = new User(4, "test@example.com", "password", false);

        List<Ticket> ticketList = ersdao.getTicketsByUser(user);

        Assert.assertNotNull(ticketList);

    }

    @Test
    public void test_getPendingTickets_returnValidList(){
        List<Ticket> ticketList = ersdao.getPendingTickets();

        Assert.assertNotNull(ticketList);
    }
}
