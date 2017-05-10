import com.google.gson.Gson;
import dataModel.Beacon;
import dataModel.Coordinates;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;

import static spark.Spark.*;

/**
 * Created by Adam Piech on 2017-05-04.
 */

public class RESTService {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh:mm:ss.SSS");
    private static Logger log = LoggerFactory.getLogger(RESTService.class);

    public static void main(String[] args) {

        PropertyConfigurator.configure(RESTService.class.getResource("slf4j.properties"));
        port(9000);
        after((req, res) -> res.type("application/json"));

        get("/info/test/beacon", (req, res) -> createBeacon(), JsonTransformer.json());
        post("/info/test/beacon", (req, res) -> createBeacon(req.body()), JsonTransformer.json());

//        get("/beacon/list", (req, res) -> "", JsonTransformer.json());
//        get("/drones/location", (req, res) -> "", JsonTransformer.json());
//        get("/drones/path", (req, res) -> "", JsonTransformer.json());
//        get("/flight/area", (req, res) -> "", JsonTransformer.json());
//        get("/help/area", (req, res) -> "", JsonTransformer.json());
//        post("/beacon", (req, res) -> "", JsonTransformer.json());
//        post("/beacon/list", (req, res) -> "", JsonTransformer.json());
//        post("/drone/location", (req, res) -> "", JsonTransformer.json());
//        post("/drone/path", (req, res) -> "", JsonTransformer.json());
//        post("/flight/area", (req, res) -> "", JsonTransformer.json());

    }

    private static Beacon createBeacon() {
        Coordinates coordinates = new Coordinates(1.000, 2.000);
        Beacon beacon = new Beacon("beacon", "green", coordinates);
        log.info("GET: " + JsonTransformer.toJson(beacon));
        return beacon;
    }

    private static Beacon createBeacon(String body) {
        Gson gson = new Gson();
        Beacon beacon = gson.fromJson(body, Beacon.class);
        log.info("POST: " + JsonTransformer.toJson(beacon));
        return beacon;
    }

}
