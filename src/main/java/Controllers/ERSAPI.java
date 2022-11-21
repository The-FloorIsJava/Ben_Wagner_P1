package Controllers;

import DAO.ERSDAO;
import Models.Ticket;
import Models.User;
import Services.ERSService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

//This class will be my controller/api

public class ERSAPI {

    ERSDAO ersdao = new ERSDAO();
    ERSService ersService = new ERSService(ersdao);
    //javalin setup
    public void startAPI(){
        Javalin app = Javalin.create().start(8080);

        app.post("/login", this::loginHandler);
        app.delete("/logout", this::logoutHandler);
        app.post("/register", this::registerHandler);

        app.post("/submit", this::submitHandler);
        app.get("/submitted", this::getSubmittedHandler);

        app.get("/pending", this::getPendingHandler);
        app.patch("/verdict", this::submitVerdictHandler);
    }

    //Handler to login
    private void loginHandler(Context context){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(context.body());
            ersService.login(root.get("email").asText(), root.get("password").asText());//need to get the id of the processor
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }
    //Handler to logout
    private void logoutHandler(Context context){
        ersService.logout();
        context.result("Logged out.");
    }
    //Handler to register
    private void registerHandler(Context context){
        try{
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(context.body(), User.class);
            context.result(ersService.registerUser(user));
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }
    //Handler to submit ticket
    private void submitHandler(Context context){
        try{
            ObjectMapper mapper = new ObjectMapper();
            Ticket ticket = mapper.readValue(context.body(), Ticket.class);
            context.result(ersService.addTicket(ticket));
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }
    //Handler for employee to view submitted tickets
    private void getSubmittedHandler(Context context){
        List<Ticket> submittedTickets = ersService.getSubmitted();
        if(submittedTickets != null){
            context.json(submittedTickets);
        }else{
            context.result("You are not logged in");
        }

    }
    //Handler for managers to view pending tickets
    private void getPendingHandler(Context context){
        List<Ticket> pendingTickets = ersService.getPendingTickets();
        if(pendingTickets != null) {
            context.json(pendingTickets);
        }else{
            context.result("You are not logged in as a manager");
        }

    }
    //Handler for managers to submit ticket approval or denial
    private void submitVerdictHandler(Context context){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(context.body());
            context.result(ersService.processTicket(root.get("id").asInt(), root.get("status").asBoolean()));
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }
}