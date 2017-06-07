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
        points.add(new PointLocation("LAK_01", 1, new Coordinates(50.093, 20.192)));
        points.add(new PointLocation("LAK_02", 2, new Coordinates(50.093, 20.200)));
        points.add(new PointLocation("LAK_03", 3, new Coordinates(50.093, 20.207)));
        points.add(new PointLocation("LAK_04", 4, new Coordinates(50.090, 20.209)));
        points.add(new PointLocation("LAK_05", 5, new Coordinates(50.088, 20.209)));
        points.add(new PointLocation("LAK_06", 6, new Coordinates(50.088, 20.203)));
        points.add(new PointLocation("LAK_07", 7, new Coordinates(50.088, 20.200)));
        points.add(new PointLocation("LAK_08", 8, new Coordinates(50.088, 20.197)));
        points.add(new PointLocation("LAK_09", 9, new Coordinates(50.088, 20.193)));
        points.add(new PointLocation("LAK_10", 10, new Coordinates(50.091, 20.192)));
        points.add(new PointLocation("LAK_11", 11, new Coordinates(50.092, 20.192)));
        return points;
    }

}
