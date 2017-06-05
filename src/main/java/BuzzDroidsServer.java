import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.apache.log4j.PropertyConfigurator;
import utils.services.RESTService;

import static spark.Spark.*;
import static utils.Utils.*;

/**
 * Created by Adam Piech on 2017-05-04.
 */

public class BuzzDroidsServer {

//    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        PropertyConfigurator.configure(BuzzDroidsServer.class.getResource(LOG_PROPERTIES_FILE));

        MongoClient mongoClient = new MongoClient("172.17.0.2", 27017);
        DB database = mongoClient.getDB("mydb");

        port(SERVER_PORT);
        RESTService.restMethods(CONTENT_TYPE, database);
    }

}