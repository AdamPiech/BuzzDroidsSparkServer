package utils.services;

import com.google.gson.Gson;
import com.mongodb.DB;
import dataModel.FlightArea;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class FlightService {

    public static FlightArea getFlightArea(DB database) {
        return null;
    }

    public static FlightArea saveFlightArea(DB database, String body) {
        FlightArea flightArea = new Gson().fromJson(body, FlightArea.class);
        return flightArea;
    }

}
