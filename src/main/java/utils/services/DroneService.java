package utils.services;

import com.google.gson.Gson;
import com.mongodb.client.MongoDatabase;
import dataModel.DroneLocation;
import dataModel.DronePath;

import java.util.List;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class DroneService {

    public static List<DroneLocation> getDronesLocation(MongoDatabase database) {
        return null;
    }

    public static List<DronePath> getDronesPaths(MongoDatabase database) {
        return null;
    }

    public static DroneLocation saveDroneLocation(MongoDatabase database, String body) {
        DroneLocation droneLocation = new Gson().fromJson(body, DroneLocation.class);
        return droneLocation;
    }

    public static DronePath saveDronePath(MongoDatabase database, String body) {
        DronePath dronePath = new Gson().fromJson(body, DronePath.class);
        return dronePath;
    }

}
