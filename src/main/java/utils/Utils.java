package utils;

import dataModel.Coordinates;
import dataModel.PointLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam Piech on 2017-05-05.
 */

public class Utils {

    public static final int SERVER_PORT = 9000;

    public static final String LOG_PROPERTIES_FILE = "slf4j.properties";

    public static final String CONTENT_TYPE = "application/json";

    public static final double PATH_RESOLUTIONS = 5.0;

    public static final String DB_ID = "_id";
    public static final String DB_RESOURCES_BEACONS = "beacons";
    public static final String DB_RESOURCES_DRONE_LOCATION = "drone_location";
    public static final String DB_RESOURCES_DRONE_PATH = "drone_path";
    public static final String DB_RESOURCES_FLIGHT_AREA = "flight_area";

    public static List<PointLocation> getFlightArea() {
        List<PointLocation> points = new ArrayList<>();
        points.add(new PointLocation("LAK_01", 9, new Coordinates(50.0931667, 20.1916000)));
//        points.add(new PointLocation("LAK_02", 8, new Coordinates(50.0931667, 20.2000000)));
        points.add(new PointLocation("LAK_03", 7, new Coordinates(50.0931667, 20.2066167)));
        points.add(new PointLocation("LAK_04", 6, new Coordinates(50.0903500, 20.2090667)));
        points.add(new PointLocation("LAK_05", 5, new Coordinates(50.0883333, 20.2090667)));
//        points.add(new PointLocation("LAK_06", 4, new Coordinates(50.0883333, 20.2029000)));
//        points.add(new PointLocation("LAK_07", 3, new Coordinates(50.0883333, 20.2000000)));
//        points.add(new PointLocation("LAK_08", 2, new Coordinates(50.0883333, 20.1970000)));
        points.add(new PointLocation("LAK_09", 1, new Coordinates(50.0883333, 20.1926833)));
        points.add(new PointLocation("LAK_10", 11, new Coordinates(50.0906167, 20.1919500)));
        points.add(new PointLocation("LAK_11", 10, new Coordinates(50.0916333, 20.1916000)));
        return points;
    }

}
