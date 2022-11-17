package Controllers;

import DAO.ERSDAO;
import Models.Ticket;
import Services.ERSService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.eclipse.jetty.util.DateCache;

import java.util.HashMap;
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

    }
    //Handler to logout
    private void logoutHandler(Context context){

    }
    //Handler to register
    private void registerHandler(Context context){

    }
    //Handler to submit ticket
    private void submitHandler(Context context){

    }
    //Handler for employee to view submitted tickets
    private void getSubmittedHandler(Context context){

    }
    //Handler for managers to view pending tickets
    private void getPendingHandler(Context context){
        List<Ticket> pendingTickets = ersService.getPendingTickets();
        context.json(pendingTickets);
    }
    //Handler for managers to submit ticket approval or denial
    private void submitVerdictHandler(Context context){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(context.body());
            context.result(ersService.processTicket(root.get("id").asInt(), root.get("status").asBoolean(), 8));//need to get the id of the processor
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
    }
}