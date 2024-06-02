package org.example.location_service.service;

import org.example.location_service.model.Location;
import org.example.location_service.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;
    public List<Location> getByIdRouteAndType(Integer id_route, String type){
        return locationRepository.getByIdRouteAndType(id_route, type);
    }
}
