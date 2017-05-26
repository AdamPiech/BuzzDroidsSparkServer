package dataModel;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class FlightAreaPart {

    private String droneName;
    private List<PointLocation> flightArea;

    public String getDroneName() {
        return droneName;
    }

    public void setDroneName(String droneName) {
        this.droneName = droneName;
    }

    public List<PointLocation> getFlightArea() {
        return flightArea;
    }

    public void setFlightArea(List<PointLocation> flightArea) {
        this.flightArea = flightArea;
    }

    public DBObject getFlightAreaPartMongoBDObject() {
        return new BasicDBObject()
                .append("droneName", droneName)
                .append("flightArea", new BasicDBList()
                        .addAll(flightArea
                                .stream()
                                .map(p -> p.getPointLocationMongoBDObject())
                                .collect(Collectors.toList()))); //Możliwe że trzeba dodać typ
    }

}
