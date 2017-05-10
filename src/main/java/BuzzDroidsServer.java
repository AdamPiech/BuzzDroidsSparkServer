import org.apache.log4j.PropertyConfigurator;
import utils.services.RESTService;

import java.time.format.DateTimeFormatter;

import static spark.Spark.*;
import static utils.States.*;

/**
 * Created by Adam Piech on 2017-05-04.
 */

public class BuzzDroidsServer {

//    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);

    public static void main(String[] args) {
        PropertyConfigurator.configure(BuzzDroidsServer.class.getResource(LOG_PROPERTIES_FILE));
        port(SERVER_PORT);
        RESTService.restMethods(CONTENT_TYPE);
    }

}