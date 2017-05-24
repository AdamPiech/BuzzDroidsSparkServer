package utils.services;

import com.google.gson.Gson;
import com.mongodb.*;
import dataModel.Beacon;
import dataModel.DroneLocation;
import dataModel.DronePath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class DroneService {

    public static List<DroneLocation> getDronesLocations(DB database) {
        DBCollection collection = database.getCollection("drones");
        DBCursor cursor = collection.find();
        List<DroneLocation> droneLocations = new ArrayList<>();

        while (cursor.hasNext()) {
            droneLocations.add(new Gson().fromJson(cursor.next().toString(), DroneLocation.class));
        }

        return droneLocations;
    }

    public static List<DronePath> getDronesPaths(DB database) {
        return null;
    }

    public static DroneLocation saveDroneLocation(DB database, String body) {
        DroneLocation droneLocation = new Gson().fromJson(body, DroneLocation.class);
        DBCollection collection = database.getCollection("drones");

        DBObject query = new BasicDBObject("_id", droneLocation.getDroneName());
        if (collection.find(query).size() == 0) {
            collection.insert(droneLocation.getDroneLocationMongoBDObject());
        } else {
            collection.update(query, droneLocation.getDroneLocationMongoBDObject());
        }

        return droneLocation;
    }

    public static DronePath saveDronePath(DB database, String body) {
        DronePath dronePath = new Gson().fromJson(body, DronePath.class);
        return dronePath;
    }

    public static List<DroneLocation> removeDronesLocations(DB database) {
        DBCollection collection = database.getCollection("drones");
        DBCursor cursor = collection.find();
        List<DroneLocation> droneLocations = new ArrayList<>();

        while (cursor.hasNext()) {
            collection.remove(cursor.getQuery());
            droneLocations.add(new Gson().fromJson(cursor.next().toString(), DroneLocation.class));
        }

        return droneLocations;
    }

}
