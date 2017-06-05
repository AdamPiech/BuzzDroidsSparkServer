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

    public DBObject getFlightAreaMongoBDObject() {
        return new BasicDBObject("_id", "flight_area")
                .append("pathResolution", pathResolution)
                .append("fullFlightArea", new BasicDBList()
                        .addAll(fullFlightArea
                                .stream()
                                .map(p -> p.getPointLocationMongoBDObject())
                                .collect(Collectors.toList()))); //Możliwe że trzeba dodać typ
    }

}
