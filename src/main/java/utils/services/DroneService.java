package utils.services;

import com.google.gson.Gson;
import com.mongodb.*;
import dataModel.DroneLocation;
import dataModel.DronePath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class DroneService {

    public static List<DroneLocation> getDronesLocations(DB database) {
        DBCollection collection = database.getCollection("drone_location");
        DBCursor cursor = collection.find();
        List<DroneLocation> droneLocations = new ArrayList<>();

        while (cursor.hasNext()) {
            droneLocations.add(new Gson().fromJson(cursor.next().toString(), DroneLocation.class));
        }

        return droneLocations;
    }

    public static DroneLocation saveDroneLocation(DB database, String body) {
        DroneLocation droneLocation = new Gson().fromJson(body, DroneLocation.class);
        DBCollection collection = database.getCollection("drone_location");

        DBObject query = new BasicDBObject("_id", droneLocation.getDroneName());
        if (collection.find(query).size() == 0) {
            collection.insert(droneLocation.getDroneLocationMongoBDObject());
        } else {
            collection.update(query, droneLocation.getDroneLocationMongoBDObject());
        }

        return droneLocation;
    }

    public static List<DroneLocation> removeDronesLocations(DB database) {
        DBCollection collection = database.getCollection("drone_location");
        DBCursor cursor = collection.find();
        List<DroneLocation> droneLocations = new ArrayList<>();

        while (cursor.hasNext()) {
            collection.remove(cursor.getQuery());
            droneLocations.add(new Gson().fromJson(cursor.next().toString(), DroneLocation.class));
        }

        return droneLocations;
    }

    public static List<DronePath> getDronesPaths(DB database) {
        DBCollection collection = database.getCollection("drone_path");
        DBCursor cursor = collection.find();
        List<DronePath> dronePaths = new ArrayList<>();

        while (cursor.hasNext()) {
            dronePaths.add(new Gson().fromJson(cursor.next().toString(), DronePath.class));
        }

        return dronePaths;
    }

    public static List<DronePath> removeDronesPaths(DB database) {
        DBCollection collection = database.getCollection("drone_path");
        DBCursor cursor = collection.find();
        List<DronePath> dronePaths = new ArrayList<>();

        while (cursor.hasNext()) {
            collection.remove(cursor.getQuery());
            dronePaths.add(new Gson().fromJson(cursor.next().toString(), DronePath.class));
        }

        return dronePaths;
    }

}
