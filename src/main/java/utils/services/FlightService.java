package utils.services;

import com.google.gson.Gson;
import com.mongodb.*;
import dataModel.DroneLocation;
import dataModel.DronePath;
import dataModel.FlightArea;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class FlightService {

    public static FlightArea getDeafaultFlightArea() {
        FlightArea flightArea = new FlightArea();
        flightArea.setPathResolution(Utils.PATH_RESOLUTIONS);
        flightArea.setFullFlightArea(Utils.getFlightArea());
        return flightArea;
    }

    public static FlightArea getFlightArea(DB database) {
        DBCollection collection = database.getCollection("flight_area");
        DBCursor cursor = collection.find();
        return new Gson().fromJson(cursor.next().toString(), FlightArea.class);
    }

    public static FlightArea saveFlightArea(DB database, String body) {
        FlightArea flightArea = new Gson().fromJson(body, FlightArea.class);
        DBCollection collection = database.getCollection("flight_area");

        DBObject query = new BasicDBObject("_id", "flight_area");
        if (collection.find(query).size() == 0) {
            collection.insert(flightArea.getFlightAreaMongoBDObject());
        } else {
            collection.update(query, flightArea.getFlightAreaMongoBDObject());
        }

        return flightArea;
    }

    public static List<FlightArea> removeFlightArea(DB database) {
        DBCollection collection = database.getCollection("flight_area");
        DBCursor cursor = collection.find();
        List<FlightArea> flightAreas = new ArrayList<>();

        while (cursor.hasNext()) {
            collection.remove(cursor.getQuery());
            flightAreas.add(new Gson().fromJson(cursor.next().toString(), FlightArea.class));
        }

        return flightAreas;
    }

}
