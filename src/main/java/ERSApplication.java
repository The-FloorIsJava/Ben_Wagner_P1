import Controllers.ERSAPI;

//This class will be the entry point for my application
public class ERSApplication {
    public static void main(String[] args){
        ERSAPI ersapi = new ERSAPI();
        ersapi.startAPI();


    }
}