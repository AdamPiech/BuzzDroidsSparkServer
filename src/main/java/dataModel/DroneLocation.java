package dataModel;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class DroneLocation {

    private String droneName;
    private Coordinates coordinates;
    private FlightDirection flightDirection;

    public String getDroneName() {
        return droneName;
    }

    public void setDroneName(String droneName) {
        this.droneName = droneName;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public FlightDirection getFlightDirection() {
        return flightDirection;
    }

    public void setFlightDirection(FlightDirection flightDirection) {
        this.flightDirection = flightDirection;
    }

    public DBObject getDroneLocationMongoBDObject() {
        return new BasicDBObject("_id", droneName)
                .append("droneName", droneName)
                .append("coordinates", coordinates.getCoordinatesMongoBDObject())
                .append("flightDirection", flightDirection.getFlightDirectionMongoBDObject());
    }

}
