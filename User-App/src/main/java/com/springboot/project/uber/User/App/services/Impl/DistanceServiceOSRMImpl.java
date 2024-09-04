package com.springboot.project.uber.User.App.services.Impl;

import com.springboot.project.uber.User.App.services.DistanceService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistanceServiceOSRMImpl implements DistanceService {

    private final String OSRM_API_BASE_URL = "http://router.project-osrm.org/route/v1/driving/";

    @Override
    public double calculateDistance(Point src, Point dest) {
        //call the third party api called OSRM to fetch distance
        try{
            String uri = src.getX() + "," + src.getY()+";"+dest.getX()+","+dest.getY();
            OSRMResponseDto responseDto = RestClient.builder()
                    .baseUrl(OSRM_API_BASE_URL)
                    .build()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(OSRMResponseDto.class);

            return responseDto.getRoutes().get(0).getDistance() / 1000.0;
        }catch (Exception ex){
            throw new RuntimeException("Error getting data from OSRM "+ ex.getMessage());
        }
    }
}

@Data
class OSRMResponseDto{

    private List<OSRMRoute> routes;
}

@Data
class OSRMRoute{
    private Double distance;
}
