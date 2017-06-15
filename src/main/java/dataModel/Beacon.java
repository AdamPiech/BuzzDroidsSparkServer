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
    private String imageContent;

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

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public DBObject getBeaconMongoBDObject() {
        return new BasicDBObject("_id", name)
                .append("name", name)
                .append("color", color)
                .append("coordinates", coordinates.getCoordinatesMongoBDObject());
    }

}
