package dataModel;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class FlightArea {

    private double pathResolution;
    private List<PointLocation> fullFlightArea;
    private List<FlightAreaPart> flightAreaParts;

    public double getPathResolution() {
        return pathResolution;
    }

    public void setPathResolution(double pathResolution) {
        this.pathResolution = pathResolution;
    }

    public List<PointLocation> getFullFlightArea() {
        return fullFlightArea;
    }

    public void setFullFlightArea(List<PointLocation> fullFlightArea) {
        this.fullFlightArea = fullFlightArea;
    }

    public List<FlightAreaPart> getFlightAreaParts() {
        return flightAreaParts;
    }

    public void setFlightAreaParts(List<FlightAreaPart> flightAreaParts) {
        this.flightAreaParts = flightAreaParts;
    }

    public DBObject getFlightAreaMongoBDObject() {
        return new BasicDBObject("_id", "flight_area")
                .append("pathResolution", pathResolution)
                .append("fullFlightArea", new BasicDBList()
                        .addAll(fullFlightArea
                                .stream()
                                .map(p -> p.getPointLocationMongoBDObject())
                                .collect(Collectors.toList()))) //Możliwe że trzeba dodać typ
                .append("flightAreaParts", new BasicDBList()
                        .addAll(flightAreaParts
                                .stream()
                                .map(p -> p.getFlightAreaPartMongoBDObject())
                                .collect(Collectors.toList()))); //Możliwe że trzeba dodać typ
    }

}
