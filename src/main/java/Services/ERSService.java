package Services;

import DAO.ERSDAO;
import Models.Ticket;
import Models.User;

import java.util.List;

//This class will use the DAO.ERSDAO to retrieve or add information to the database
public class ERSService{
    private final ERSDAO ersdao;
    private User sessionUser = null;
    public ERSService(ERSDAO ersdao){
        this.ersdao = ersdao;
    }

    public User getUser(String email) {
        return ersdao.getUser(email);
    }

    public void login(String email, String password){
        sessionUser = ersdao.getUserLogin(email, password);
    }

    public void logout(){
        sessionUser = null;
    }

    public List<Ticket> getSubmitted() {
        if(sessionUser != null) {
            return ersdao.getTicketsByUser(sessionUser);
        }
        return null;
    }

    public List<Ticket> getPendingTickets() {
        return ersdao.getPendingTickets();
    }

    public void registerUser(User user) { //need to add verification that email is unique and a response if it isn't
        ersdao.addUser(user);
    }

    public void addTicket(Ticket ticket) {
        ersdao.addTicket(ticket);
    }

    /**
     * Process ticket should verify that
     * 1. The user who is updating the ticket is a manager
     * 2. The ticket has not yet been approved or denied (tickets approved or denied should be unalterable)
     * @param id the id of the ticket to be updates
     * @param status the given status
     */
    public String processTicket(int id, boolean status) {
        if(sessionUser == null || !sessionUser.getIsManager()){
            return "Must be a manager to process tickets";
        }
        Ticket ticket = ersdao.getTicket(id);
        if(ticket.getStatus() != null){
            return "Cannot process previously processed ticket.";
        }else{
            ticket.setStatus(status);
            ersdao.processTicket(ticket);
            return "Success! The ticket has been processed.";
        }
    }
}