package utils.services;

import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.client.MongoDatabase;
import dataModel.DroneLocation;
import dataModel.DronePath;

import java.util.List;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class DroneService {

    public static List<DroneLocation> getDronesLocation(DB database) {
        return null;
    }

    public static List<DronePath> getDronesPaths(DB database) {
        return null;
    }

    public static DroneLocation saveDroneLocation(DB database, String body) {
        DroneLocation droneLocation = new Gson().fromJson(body, DroneLocation.class);
        return droneLocation;
    }

    public static DronePath saveDronePath(DB database, String body) {
        DronePath dronePath = new Gson().fromJson(body, DronePath.class);
        return dronePath;
    }

}
