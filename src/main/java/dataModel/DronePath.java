package dataModel;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class DronePath {

    private String droneName;
    private List<PointLocation> path;

    public String getDroneName() {
        return droneName;
    }

    public void setDroneName(String droneName) {
        this.droneName = droneName;
    }

    public List<PointLocation> getPath() {
        return path;
    }

    public void setPath(List<PointLocation> path) {
        this.path = path;
    }

    public DBObject getDronePathMongoBDObject() {
        return new BasicDBObject("_id", droneName)
                .append("droneName", droneName)
                .append("flightDirection", new BasicDBList()
                        .addAll(path
                                .stream()
                                .map(p -> p.getPointLocationMongoBDObject())
                                .collect(Collectors.toList()))); //Możliwe że trzeba dodać typ BasicDBObject
    }

}
