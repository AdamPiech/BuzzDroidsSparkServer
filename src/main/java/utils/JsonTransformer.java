package utils;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by Adam Piech on 2017-05-08.
 */

public class JsonTransformer {

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    public static ResponseTransformer toJson() {
        return JsonTransformer::toJson;
    }

}
