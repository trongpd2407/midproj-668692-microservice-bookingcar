package org.example.trip_service.service;

import org.example.trip_service.model.Trip;
import org.example.trip_service.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {
    @Autowired
    TripRepository tripRepository;

    public List<Trip> getByDonAndTra(String don, String tra){
        return tripRepository.getByDonAndTra(don, tra);
    }
    public Trip getById(Integer id){
        return tripRepository.findById(id).get();
    }
}
