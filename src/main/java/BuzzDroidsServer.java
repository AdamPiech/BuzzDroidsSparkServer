import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.apache.log4j.PropertyConfigurator;
import utils.services.RESTService;

import static spark.Spark.port;
import static utils.Utils.*;

/**
 * Created by Adam Piech on 2017-05-04.
 */

public class BuzzDroidsServer {

    public static void main(String[] args) {
        PropertyConfigurator.configure(BuzzDroidsServer.class.getResource(LOG_PROPERTIES_FILE));

        MongoClient mongoClient = new MongoClient(DB_ADDRESS, DB_PORT);
        DB database = mongoClient.getDB(DB_NAME);

        port(SERVER_PORT);
        RESTService.restMethods(CONTENT_TYPE, database);
    }

}