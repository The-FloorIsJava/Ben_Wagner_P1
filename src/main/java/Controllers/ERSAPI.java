package Controllers;

import Services.ERSService;
import io.javalin.Javalin;
import io.javalin.http.Context;

//This class will be my controller/api

public class ERSAPI {

    ERSService ersService = new ERSService();
    //javalin setup
    public void startAPI(){
        Javalin app = Javalin.create().start(8080);

        app.post("/login", this::loginHandler);
        app.post("/register", this::registerHandler);

        app.post("/submit", this::submitHandler);
        app.get("/submitted", this::getSubmittedHandler);

        app.get("/pending", this::getPendingHandler);
        app.post("/verdict", this::submitVerdictHandler);
    }

    //Handler to login
    private void loginHandler(Context context){

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

    }
    //Handler for managers to submit ticket approval or denial
    private void submitVerdictHandler(Context context){

    }
}