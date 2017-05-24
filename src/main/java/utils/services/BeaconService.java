package utils.services;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mongodb.*;
import dataModel.Beacon;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class BeaconService {

    public static Beacon getBeacon() {
        return null;
    }

    public static List<Beacon> getBeaconList(DB database) {
        DBCollection collection = database.getCollection("beacons");
        DBCursor cursor = collection.find();
        List<Beacon> beacons = new ArrayList<>();

        while (cursor.hasNext()) {
            beacons.add(new Gson().fromJson(cursor.next().toString(), Beacon.class));
        }

        return beacons;
    }

    public static Beacon saveBeacon(DB database, String body) {
        Beacon beacon = new Gson().fromJson(body, Beacon.class);
        DBCollection collection = database.getCollection("beacons");

        DBObject query = new BasicDBObject("_id", beacon.getName());
        if (collection.find(query).size() == 0) {
            collection.insert(beacon.getBeaconMongoBDObject());
        } else {
            collection.update(query, beacon.getBeaconMongoBDObject());
        }

        return beacon;
    }

    public static List<Beacon> saveBeaconList(DB database, String body) {
        Type listType = new TypeToken<ArrayList<Beacon>>(){}.getType();
        List<Beacon> beacons = new Gson().fromJson(body, listType);
        DBCollection collection = database.getCollection("beacons");

        for(Beacon beacon : beacons) {
            DBObject query = new BasicDBObject("_id", beacon.getName());
            if (collection.find(query).size() == 0) {
                collection.insert(beacon.getBeaconMongoBDObject());
            } else {
                collection.update(query, beacon.getBeaconMongoBDObject());
            }
        }

        return beacons;
    }

    public static List<Beacon> removeAllBeacons(DB database) {
        DBCollection collection = database.getCollection("beacons");
        DBCursor cursor = collection.find();
        List<Beacon> beacons = new ArrayList<>();

        while (cursor.hasNext()) {
            collection.remove(cursor.getQuery());
            beacons.add(new Gson().fromJson(cursor.next().toString(), Beacon.class));
        }

        return beacons;
    }

}
