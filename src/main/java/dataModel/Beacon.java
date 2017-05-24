package dataModel;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Created by Adam Piech on 2017-05-08.
 */
public class Beacon {

    private String name;
    private String color;
    private Coordinates coordinates;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public DBObject getBeaconMongoBDObject() {
        return new BasicDBObject()
                .append("_id", name)
                .append("name", name)
                .append("color", color)
                .append("coordinates", coordinates.getCoordinatesMongoBDObject());
    }

}
