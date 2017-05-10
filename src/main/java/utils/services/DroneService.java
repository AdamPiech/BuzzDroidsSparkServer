package utils.services;

import com.google.gson.Gson;
import dataModel.DroneLocation;
import dataModel.DronePath;

import java.util.List;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class DroneService {

    public static List<DroneLocation> getDronesLocation() {
        return null;
    }

    public static List<DronePath> getDronesPaths() {
        return null;
    }

    public static DroneLocation saveDroneLocation(String body) {
        DroneLocation droneLocation = new Gson().fromJson(body, DroneLocation.class);
        return droneLocation;
    }

    public static DronePath saveDronePath(String body) {
        DronePath dronePath = new Gson().fromJson(body, DronePath.class);
        return dronePath;
    }

}
