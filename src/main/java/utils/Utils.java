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

    public static final String TIME_FORMAT = "yyyy-MM-dd_hh:mm:ss.SSS";
    public static final String LOG_PROPERTIES_FILE = "slf4j.properties";

    public static final String CONTENT_TYPE = "application/json";

    public static final double PATH_RESOLUTIONS = 5.0;

    public static List<PointLocation> getFlightArea() {
        List<PointLocation> points = new ArrayList<>();
        points.add(new PointLocation("LAK_01", 1, new Coordinates(20.192, 50.093)));
        points.add(new PointLocation("LAK_02", 2, new Coordinates(20.200, 50.093)));
        points.add(new PointLocation("LAK_03", 3, new Coordinates(20.207, 50.093)));
        points.add(new PointLocation("LAK_04", 4, new Coordinates(20.209, 50.090)));
        points.add(new PointLocation("LAK_05", 5, new Coordinates(20.209, 50.088)));
        points.add(new PointLocation("LAK_06", 6, new Coordinates(20.203, 50.088)));
        points.add(new PointLocation("LAK_07", 7, new Coordinates(20.200, 50.088)));
        points.add(new PointLocation("LAK_08", 8, new Coordinates(20.197, 50.088)));
        points.add(new PointLocation("LAK_09", 9, new Coordinates(20.193, 50.088)));
        points.add(new PointLocation("LAK_10", 10, new Coordinates(20.192, 50.091)));
        points.add(new PointLocation("LAK_11", 11, new Coordinates(20.192, 50.092)));
        return points;
    }

}
