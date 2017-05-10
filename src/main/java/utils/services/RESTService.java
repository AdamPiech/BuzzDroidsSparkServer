package utils.services;

import spark.Spark;
import utils.JsonTransformer;

import static spark.Spark.after;
import static utils.JsonTransformer.*;
import static utils.services.BeaconService.*;
import static utils.services.DroneService.*;
import static utils.services.FlightService.*;

/**
 * Created by Adam Piech on 2017-05-11.
 */

public class RESTService {

    public static void restMethods(String contentType) {

        after((req, res) -> res.type(contentType));

        Spark.get("/beacon/list", (req, res) -> getBeaconList(), toJson());
        Spark.get("/drones/location", (req, res) -> getDronesLocation(), toJson());
        Spark.get("/drones/path", (req, res) -> getDronesPaths(), toJson());
        Spark.get("/flight/area", (req, res) -> getFlightArea(), toJson());
        Spark.get("/help/area", (req, res) -> "/* TODO: algorytm wyznaczania ścieżki */", toJson());

        Spark.post("/beacon", (req, res) -> saveBeacon(req.body()), toJson());
        Spark.post("/beacon/list", (req, res) -> saveBeaconList(req.body()), toJson());
        Spark.post("/drone/location", (req, res) -> saveDroneLocation(req.body()), toJson());
        Spark.post("/drone/path", (req, res) -> saveDronePath(req.body()), toJson());
        Spark.post("/flight/area", (req, res) -> saveFlightArea(req.body()), toJson());

    }

}
