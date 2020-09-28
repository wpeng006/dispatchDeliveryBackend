package model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    private List<LatLng> points;

    //List of points

}
