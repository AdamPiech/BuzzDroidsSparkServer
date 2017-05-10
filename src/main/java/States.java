import static java.io.File.separator;

/**
 * Created by Adam Piech on 2017-05-05.
 */

public class States {

    public static final String FTP_DIRECTORY_PATH =
            separator + "home" + separator + "buzzdroids" + separator + "ftp";
    public static final String WINDOWS_TEST_DIRECTORY_PATH =
            "C:" + separator + "Users" + separator + "Adam Piech" + separator + "Desktop";

    public static final String TIME_FORMAT = "yyyy-MM-dd_hh:mm:ss.SSS";

    public static final String JSON_FILE_EXTENSION = "json";

    public static final String BEACON_JSON_FILE_PATTERN = "^.*beacon_.*$";
    public static final String BEACON_LIST_JSON_FILE_PATTERN = "^.*beaconlist_.*$";
    public static final String DRON_LOCATION_JSON_FILE_PATTERN = "^.*dronlocation_.*$";
    public static final String DRON_PATH_JSON_FILE_PATTERN = "^.*dronpath_.*$";
    public static final String HELP_PLAN_JSON_FILE_PATTERN = "^.*helpplan_.*$";

}
