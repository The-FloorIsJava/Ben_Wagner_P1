package Models;
//This class will be the model for reimbursement tickets
public class Ticket {
    private int id;
    private double amount;
    private String description;
    private boolean status;
    private int submitterId;
    private int processorId;

    public Ticket(){}

    public Ticket(int id, double amount, String description, boolean status, int submitterId, int processorId){
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.status = status;
        this.submitterId = submitterId;
        this.processorId = processorId;
    }

    public int getId(){
        return this.id;
    }

    public double getAmount(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public boolean getStatus(){
        return this.status;
    }

    public int getSubmitter(){
        return this.submitterId;
    }

    public int getProcessor(){
        return this.processorId;
    }
}