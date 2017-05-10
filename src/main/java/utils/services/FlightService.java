package utils.services;

import com.google.gson.Gson;
import dataModel.FlightArea;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class FlightService {

    public static FlightArea getFlightArea() {
        return null;
    }

    public static FlightArea saveFlightArea(String body) {
        FlightArea flightArea = new Gson().fromJson(body, FlightArea.class);
        return flightArea;
    }

}
