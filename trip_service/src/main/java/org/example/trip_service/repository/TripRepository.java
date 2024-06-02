package org.example.trip_service.repository;

import org.example.trip_service.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> getByDonAndTra(String don, String tra);
}