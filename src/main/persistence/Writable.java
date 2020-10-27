package persistence;

import org.json.JSONObject;

//SOURCE: JsonSerializationDemo - this interface was built based on JsonSerializationDemo
public interface Writable {
    //EFFECTS: returns this as JSON object
    JSONObject toJson();
}
