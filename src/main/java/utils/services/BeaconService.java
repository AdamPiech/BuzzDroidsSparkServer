package utils.services;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import dataModel.Beacon;
import org.bson.Document;

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

    public static List<Beacon> getBeaconList(MongoDatabase database) {
        MongoCollection collection = database.getCollection("beacons");
        MongoCursor<Document> cursor = collection.find().iterator();
        List<Beacon> beacons = new ArrayList<>();

        while (cursor.hasNext()) {
            beacons.add(new Gson().fromJson(cursor.next().toString(), Beacon.class));
        }

        return beacons;
    }

    public static Beacon saveBeacon(MongoDatabase database, String body) {
        Beacon beacon = new Gson().fromJson(body, Beacon.class);
        MongoCollection collection = database.getCollection("beacons");
        collection.insertOne(beacon.getBeaconMongoBDObject());
        return beacon;
    }

    public static List<Beacon> saveBeaconList(MongoDatabase database, String body) {
        Type listType = new TypeToken<ArrayList<Beacon>>(){}.getType();
        List<Beacon> beacons = new Gson().fromJson(body, listType);
        MongoCollection collection = database.getCollection("beacons");

        for(Beacon beacon : beacons) {
            collection.insertOne(beacon.getBeaconMongoBDObject());
        }

        return beacons;
    }

}
