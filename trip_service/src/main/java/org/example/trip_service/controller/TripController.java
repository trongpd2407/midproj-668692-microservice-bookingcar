package org.example.trip_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.trip_service.model.Trip;
import org.example.trip_service.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trip")
public class TripController {
    @Autowired
    private TripService tripService;
    @Operation(summary = "Get trips by departure and arrival", description = "Retrieve a list of trips based on the provided departure and arrival locations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of trips",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Trip.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Trips not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/list")
    public List<Trip> getByDonAndTra(@RequestParam("don") String don, @RequestParam("tra") String tra) {
        return tripService.getByDonAndTra(don, tra);
    }

    @Operation(summary = "Get trip by ID", description = "Retrieve a trip by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the trip",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Trip.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Trip not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get")
    public Trip getById(@RequestParam("id") int id) {
        return tripService.getById(id);
    }
}
