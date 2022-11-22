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
        if(sessionUser == null || !sessionUser.getIsManager()){
            return null;
        }
        return ersdao.getPendingTickets();
    }

    public String registerUser(User user) {
        User tempUser = ersdao.getUser(user.getEmail());
        String pass = user.getPassword();
        if(pass.length() < 5 || pass == null){
            return "Must have password with 5 or more characters";
        }
        if(user.getEmail().length() < 0 || user.getEmail() == null){
            return "Email is required";
        }
        if(tempUser == null){
            ersdao.addUser(user);
            return "Successfully registered!";
        }else{
            return "Email is already in use.";
        }
    }

    public String addTicket(Ticket ticket) {
        if(sessionUser == null){
            return "Must be logged in to submit a ticket";
        }
        ticket.setSubmitterId(sessionUser.getId());
        if(ticket.getAmount() <= 0){
            return "amount cannot be less than or equal to 0";
        } else if (ticket.getDescription().length() == 0 || ticket.getDescription() == null) {
            return "Description cannot be empty";
        }
        ersdao.addTicket(ticket);
        return "Successfully submitted ticket!";
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
            ticket.setProcessorId(sessionUser.getId());
            ersdao.processTicket(ticket);
            return "Success! The ticket has been processed.";
        }
    }
}