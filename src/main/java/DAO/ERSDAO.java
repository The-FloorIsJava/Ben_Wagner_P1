package DAO;

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


    /**
     *  The purpose of this method is to retrieve a user from the database with a matching email
     * @param email takes a string email
     * @return User where email = email or null if no user found
     */
    public User getUser(String email){
        User user = null;
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()){
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
        The purpose of this method is to retrieve a list of tickets which were submitted by the user
        @param user takes a user object
        @return List of tickets created by the user or null if none found
     */
    public List<Ticket> getTicketsByUser(User user) {
        List<Ticket> ticketList = new ArrayList<>();
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()){
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
     * The purpose of this method is to retrieve all pending tickets from the database
     * @return all pending tickets
     */
    public List<Ticket> getPendingTickets(){
        List<Ticket> ticketList = new ArrayList<>();
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()){
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
     * Inserts a user into the database
     * @param user takes a user object
    */
    public void addUser(User user){
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "Insert into users (email, password) values (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());

            int checkInsert = ps.executeUpdate();

            if(checkInsert == 0){
                throw new RuntimeException("User was not added to the database");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Inserts a ticket into the database
     * @param ticket takes a ticket object
     */
    public void addTicket(Ticket ticket){
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "Insert into tickets (amount, description, submitter_id) values (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setDouble(1, ticket.getAmount());
            ps.setString(2, ticket.getDescription());
            ps.setInt(3, ticket.getSubmitter());


            int checkInsert = ps.executeUpdate();

            if(checkInsert == 0){
                throw new RuntimeException("Ticket was not added to the database");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * The purpose of this method is to update the status of an existing ticket in the database
     * @param ticket takes a ticket object to update
     */
    public void processTicket(Ticket ticket){
        try(Connection connection = ConnectionFactory.getConnectionFactory().getConnection()){
            String sql = "Update tickets set status = " + ticket.getStatus() + " where id =" + ticket.getId();
            PreparedStatement ps = connection.prepareStatement(sql);

            int checkInsert = ps.executeUpdate();

            if(checkInsert == 0){
                throw new RuntimeException("Ticket was not processed");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Ticket getTicket(int id) {
        Ticket ticket = null;
        try (Connection connection = ConnectionFactory.getConnectionFactory().getConnection()) {
            String sql = "Select * from tickets where id =" + id;
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ticketId = rs.getInt(1);
                int amount = rs.getInt(2);
                String description = rs.getString(3);
                Boolean status = rs.getBoolean(4);
                status = rs.wasNull() ? null : status; //rs.getBoolean returns false if null... so checked if rs.wasNull and reset to null if true
                int submitter = rs.getInt(5);
                int processor = rs.getInt(6);
                ticket = new Ticket(ticketId, amount, description, status, submitter, processor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

}
