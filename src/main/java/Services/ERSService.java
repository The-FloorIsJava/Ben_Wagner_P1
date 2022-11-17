package Services;

import DAO.ERSDAO;
import Models.Ticket;
import Models.User;

import java.util.List;

//This class will use the DAO.ERSDAO to retrieve or add information to the database
public class ERSService{
    private final ERSDAO ersdao;

    public ERSService(ERSDAO ersdao){
        this.ersdao = ersdao;
    }

    public User getUser(String email) {
        return ersdao.getUser(email);
    }

    public List<Ticket> getTicketsByUser(User user) {
        return ersdao.getTicketsByUser(user);
    }

    public List<Ticket> getPendingTickets() {
        return ersdao.getPendingTickets();
    }

    public void addUser(User user) {
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
    public String processTicket(int id, boolean status, int processor) {
        Ticket ticket = ersdao.getTicket(id);
        if(ticket.getStatus() != null){
            return "Cannot process previously processed ticket.";
        }else{
            ticket.setStatus(status);
            ersdao.processTicket(ticket);
            return "Success the ticket has been processed!";
        }
    }
}