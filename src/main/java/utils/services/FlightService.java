package utils.services;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import dataModel.FlightArea;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class FlightService {


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

}
