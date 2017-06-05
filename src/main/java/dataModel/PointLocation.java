package dataModel;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Created by Adam Piech on 2017-05-10.
 */

public class PointLocation {

    private String pointName;
    private int order;
    private Coordinates coordinates;

    public PointLocation(String pointName, int order, Coordinates coordinates) {
        this.pointName = pointName;
        this.order = order;
        this.coordinates = coordinates;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public DBObject getPointLocationMongoBDObject() {
        return new BasicDBObject()
                .append("pointName", pointName)
                .append("order", order)
                .append("coordinates", coordinates.getCoordinatesMongoBDObject());
    }

}
