package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Builder
@Getter
@Setter
public class Route {
    private String startAddress;
    private String endAddress;
    private String station;
    private double fee;
    private double pickupDuration;
    private double deliveryDuration;
    private double backToStationDuration;

    // List of points
    private List<LatLng> points;
}
