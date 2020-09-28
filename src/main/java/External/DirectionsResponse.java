package External;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import utils.Constants;


public class DirectionsResponse {

    private JsonElement jsonElement;
    private JsonObject jsonObject;
    private JsonArray jsonObjectResult;

    public DirectionsResponse(String json) {
        jsonElement = new JsonParser().parse(json);
        jsonObject = jsonElement.getAsJsonObject();

        // Get route array and pick up the first array by default
        jsonObjectResult = jsonObject.getAsJsonArray("routes").get(0).getAsJsonArray("legs").get(0).getAsJsonObject();
    }

    // Method for getting start_address
    public String getStartAddress() {
        String start_address = jsonObjectResult.get("start_address").getAsString();
        return start_address;
    }

    // Method for getting end_address
    public String getEndAddress() {
        String end_address = jsonObjectResult.get("end_address").getAsString();
        return end_address;
    }

    // Method for getting robotDuration
    public double getRobotDuration() {
        String duration = jsonObjectResult.get("duration").getAsObject().get("value").getAsString();
        double durationValue = Long.parseLong(duration);
        return durationValue;
    }

    // Method for getting robotDistance
    public long getRobotDistance() {
        String robotDistance = jsonObjectResult.get("distance").getAsObject().get("value").getAsString();
        long robotDistanceValue = Long.parseLong(robotDistance);
        return robotDistanceValue;
    }

    // Method for getting droneDuration
    public double getDroneDuration() {
        long droneDistanceValue = getDroneDistance();
        return droneDistanceValue / Constants.DEFAULT_DRONE_SPEED;
    }

    // Method for getting droneDistance
    public long getDroneDistance() {
        String droneDistance = jsonObjectResult.get("distance").getAsObject().get("value").getAsString();
        long droneDistanceValue = Long.parseLong(droneDistance);
        return droneDistanceValue;
    }
}

