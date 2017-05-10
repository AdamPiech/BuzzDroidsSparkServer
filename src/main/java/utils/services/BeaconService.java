package utils.services;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
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

    public static Beacon getBeaconList() {
        return null;
    }

    public static Beacon saveBeacon(String body) {
        Beacon beacon = new Gson().fromJson(body, Beacon.class);
        return beacon;
    }

    public static List<Beacon> saveBeaconList(String body) {
        Type listType = new TypeToken<ArrayList<Beacon>>(){}.getType();
        List<Beacon> beacons = new Gson().fromJson(body, listType);
        return beacons;
    }

}
