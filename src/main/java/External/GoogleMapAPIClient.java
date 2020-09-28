package External;

import model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import utils.Constants;

public class GoogleMapAPIClient {

    private final String apiKey = "AIzaSyBjnVnvNgiL3PpPS5cNDghW85fqQM8jbsk";

    @Autowired
    private RestTemplate restTemplate;

    private DirectionsResponse[] getMapResponse(String from, String to, String station) {
        restTemplate = new RestTemplate();
        String[] response = new String[3];


        //from station to pickup location
        response[0] = restTemplate.getForObject(
                "https://maps.googleapis.com/maps/api/directions/json?"
                        + "origin=" + station
                        + "&destination=" + from
                        //+ "&waypoints=" + xxx
                        + "&key=" + apiKey,
                String.class
        );
        //from pickup location to delivery location
        response[1] = restTemplate.getForObject(
                "https://maps.googleapis.com/maps/api/directions/json?"
                        + "origin=" + from
                        + "&destination=" + to
                        //+ "&waypoints=" + xxx
                        + "&key=" + apiKey,
                String.class
        );
        //from delivery location to station
        response[2] = restTemplate.getForObject(
                "https://maps.googleapis.com/maps/api/directions/json?"
                        + "origin=" + to
                        + "&destination=" + station
                        //+ "&waypoints=" + xxx
                        + "&key=" + apiKey,
                String.class
        );

        DirectionsResponse[] directionsResponse = new DirectionsResponse[3];
        directionsResponse[0] = new DirectionsResponse(response[0]);
        directionsResponse[1] = new DirectionsResponse(response[1]);
        directionsResponse[2] = new DirectionsResponse(response[2]);

        return directionsResponse;
    }

    public Route getRobotRoute(String from, String to, String station) {
        DirectionsResponse[] directionsResponse = getMapResponse(from, to, station);
        double duration0 = directionsResponse[0].getRobotDuration();
        double duration1 = directionsResponse[1].getRobotDuration();
        double duration2 = directionsResponse[2].getRobotDuration();
        double duration = duration0 + duration1 + duration2;
        Route result = Route.builder()
                .startAddress(from)
                .endAddress(to)
                .station(station)
                .pickupDuration(duration0)
                .deliveryDuration(duration1)
                .backToStationDuration(duration2)
                .fee(duration * Constants.DEFAULT_ROBOT_COST)
//                .points(blabla)
                .build();
        return result;
    }

    public Route getDroneRoute(String from, String to, String station) {
        DirectionsResponse[] directionsResponse = getMapResponse(from, to, station);
        double duration0 = directionsResponse[0].getDroneDuration();
        double duration1 = directionsResponse[1].getDroneDuration();
        double duration2 = directionsResponse[2].getDroneDuration();
        double duration = duration0 + duration1 + duration2;
        Route result = Route.builder()
                .startAddress(from)
                .endAddress(to)
                .station(station)
                .pickupDuration(duration0)
                .deliveryDuration(duration1)
                .backToStationDuration(duration2)
                .fee(duration * Constants.DEFAULT_DRONE_COST)
                .build();
        return result;
    }
}