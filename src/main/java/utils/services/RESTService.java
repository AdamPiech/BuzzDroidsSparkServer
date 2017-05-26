package utils.services;

import com.mongodb.DB;
import spark.Spark;

import static spark.Spark.after;
import static utils.JsonTransformer.*;
import static utils.services.BeaconService.*;
import static utils.services.DroneService.*;
import static utils.services.FlightService.*;

/**
 * Created by Adam Piech on 2017-05-11.
 */

public class RESTService {

    public static void restMethods(String contentType, DB database) {

        after((req, res) -> res.type(contentType));

        Spark.get("/beacon/list", (req, res) -> getBeaconList(database), toJson()); // +
        Spark.get("/drone/location", (req, res) -> getDronesLocations(database), toJson()); // +
        Spark.get("/drone/path", (req, res) -> getDronesPaths(database), toJson());
//        Spark.get("/flight/area", (req, res) -> getFlightArea(database), toJson());
        Spark.get("/help/area", (req, res) -> "/* TODO: algorytm wyznaczania ścieżki */", toJson());

        Spark.post("/beacon", (req, res) -> saveBeacon(database, req.body()), toJson()); // +
        Spark.post("/beacon/list", (req, res) -> saveBeaconList(database, req.body()), toJson()); // +
        Spark.post("/drone/location", (req, res) -> saveDroneLocation(database, req.body()), toJson()); // +
//        Spark.post("/drone/path", (req, res) -> saveDronePath(database, req.body()), toJson());
        Spark.post("/flight/area", (req, res) -> saveFlightArea(database, req.body()), toJson());

        Spark.delete("/beacon/reset", (req, res) -> removeAllBeacons(database), toJson()); // +
        Spark.delete("/drone/location/reset", (req, res) -> removeDronesLocations(database), toJson()); // +
//        Spark.delete("/drone/path/reset", (req, res) -> , toJson());
//        Spark.delete("/drone/flight/area", (req, res) -> , toJson());
    }

}
