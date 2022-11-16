import Models.Ticket;
import Models.User;
import Util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//This class will translate the model objects I have into database rows
public class ERSDAO {

    //create connection to access DB
    private Connection connection = ConnectionFactory.getConnectionFactory().getConnection();

    /**
     *  The purpose of this method is to retrieve a user from the database with a matching email
     * @param email takes a string email
     * @return User where email = email or null if no user found
     */
    public User getUser(String email){
        User user = null;
        try{
            String sql = "Select * from users where email = '" + email + "'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                User loadedUser = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4));
                user = loadedUser;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    /**
        Need to create a method to get all tickets made by one user
        @param user takes a user object
        @return List of tickets created by the user or null if none found
     */
    public List<Ticket> getTicketsByUser(User user) {
        List<Ticket> ticketList = new ArrayList<>();
        try {
            String sql = "Select * from tickets where submitter_id = '" + user.getId() + "'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int amount = rs.getInt(2);
                String description = rs.getString(3);
                Boolean status = rs.getBoolean(4);
                status = rs.wasNull() ? null : status; //rs.getBoolean returns false if null... so checked if rs.wasNull and reset to null if true
                int submitter = rs.getInt(5);
                int processor = rs.getInt(6);
                Ticket ticket = new Ticket(id, amount, description, status, submitter, processor);
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticketList;
    }
    /**
     * Need to create a method to get all pending tickets
     * @return all pending tickets
     */
    public List<Ticket> getPendingTickets(){
        List<Ticket> ticketList = new ArrayList<>();
        try{
            String sql = "Select * from tickets where status is null";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Ticket ticket = new Ticket(rs.getInt(1), rs.getInt(2), rs.getString(3), null, rs.getInt(5), rs.getInt(6));
                ticketList.add(ticket);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return ticketList;
    }

    /**
     * Need to create a method to add a user to the DB
     * @param user takes a user object
    */
    public void addUser(User user){
    }

    /**
     * Need a method to add a ticket to the DB
     * @param ticket takes a ticket object
     */
    public void addTicket(Ticket ticket){

    }


}
