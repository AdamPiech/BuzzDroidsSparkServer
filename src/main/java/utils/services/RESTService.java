package utils.services;

import com.mongodb.DB;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import spark.Request;
import spark.Response;

import java.io.File;
import java.util.List;

import static spark.Spark.*;
import static spark.Spark.after;
import static spark.Spark.halt;
import static utils.Utils.*;

import static utils.JsonTransformer.*;
import static utils.services.BeaconService.*;
import static utils.services.DroneService.*;
import static utils.services.FlightService.*;

/**
 * Created by Adam Piech on 2017-05-11.
 */
public class RESTService {

    public static void restMethods(String contentType, DB database) {

        after((req, res) -> res.type(contentType));

        get("/beacon/list", (req, res) -> getBeaconList(database), toJson());
        get("/drone/location", (req, res) -> getDronesLocations(database), toJson());
        get("/drone/path", (req, res) -> getDronePath(database), toJson());

        post("/beacon", (req, res) -> saveBeacon(database, req.body()), toJson());
        post("/beacon/list", (req, res) -> saveBeaconList(database, req.body()), toJson());
        post("/drone/location", (req, res) -> saveDroneLocation(database, req.body()), toJson());
        post("/flight/area", (req, res) -> saveFlightArea(database, req.body()), toJson());


//        post("/uploadImage", UPLOAD_CONTENT_TYPE, (req, res) -> saveImageFile(req, res));

        delete("/beacon/reset", (req, res) -> removeAllBeacons(database), toJson());
        delete("/drone/location/reset", (req, res) -> removeDronesLocations(database), toJson());
        delete("/flight/area/reset", (req, res) -> removeFlightArea(database), toJson());
//        Spark.delete("/drone/path/reset", (req, res) -> removeDronesPaths(database), toJson());

        post("/uploadImage", (req, res) -> {
            File upload = new File("uploadImage");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setRepository(upload);
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            List<FileItem> items = null;
            try {
                items = fileUpload.parseRequest(req.raw());
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            FileItem item = items.stream().findFirst().get();
            try {
                item.write(new File(LINUX_PATH, item.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            halt(200);
            return null;
        });
    }


//    private static Object saveImageFile(Request req, Response res) {
//        File upload = new File("uploadImage");
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        factory.setRepository(upload);
//        ServletFileUpload fileUpload = new ServletFileUpload(factory);
//        List<FileItem> items = null;
//        try {
//            items = fileUpload.parseRequest(req.raw());
//        } catch (FileUploadException e) {
//            e.printStackTrace();
//        }
//        FileItem item = items.stream().findFirst().get();
//        try {
//            item.write(new File(LINUX_PATH, item.getName()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        halt(200);
//        return null;
//    }

}
