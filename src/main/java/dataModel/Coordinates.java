package dataModel;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Created by Adam Piech on 2017-05-08.
 */

public class Coordinates {

    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public DBObject getCoordinatesMongoBDObject() {
        return new BasicDBObject()
                .append("latitude", latitude)
                .append("longitude", longitude);
    }

}
