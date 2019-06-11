package hello;

import static spark.Spark.port;
import static spark.Spark.staticFileLocation;



public class MainServer {

	final static Model model = new Model();

    public static void main(String[] args) {

		// Get port config of heroku on environment variable
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 8080;
        }
        port(port);

		
        
        initializeModel();
		
        
		
		staticFileLocation("/static");
		
	
    }
	
    public static void initializeModel(){
    	View view = new View(model);
    	
    	//Instituicao instituicao = new Instituicao("Habibs", "10.000, 10.0000");
    	//model.addInstituicao(instituicao);
    	view.receiveUsersMessages();
    	
    	
	}
	
}
