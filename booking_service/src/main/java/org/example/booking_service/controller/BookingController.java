package org.example.booking_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.example.booking_service.model.*;
import org.example.booking_service.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Get all trips by date and locations",
            description = "Retrieve a list of trips based on the provided date, departure, and arrival locations.")
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
    public List<Trip> getAllTripByDateAndDonAndTra(@RequestParam("date") String date,
                                                   @RequestParam("don") String don,
                                                   @RequestParam("tra") String tra, HttpSession session){
        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");*/
        Date sqlDate = Date.valueOf(date);
        session.setAttribute("date", sqlDate);

        List<Trip> list_trip = new ArrayList<>();
        list_trip = bookingService.getTripByDateAndDonAndTra(sqlDate, don, tra);
        session.setAttribute("list_trip", list_trip);
        return list_trip;
    }

    @Operation(summary = "Get route locations by trip ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved route locations", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Trip not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get-location")
    public List<List<Location>> getRouteByIdTrip(@RequestParam("id_trip") Integer id_trip, HttpSession session){
        return bookingService.getRouteByIdRouteAndType(id_trip);
    }

    @Operation(summary = "Save bill information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill saved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/save-bill")
    public Integer saveBill(@RequestBody Bill bill){
        try {
            bill.setCreateDate(Date.valueOf(LocalDate.now()));
            bill.setTime(Time.valueOf(LocalTime.now()));
            Bill billResult = bookingService.saveBill(bill);
            return billResult.getId();
        } catch (Exception e) {
            e.printStackTrace();
            // Return 500 Internal Server Error if there's an issue with saving bill
            throw new RuntimeException("Internal server error");
        }
    }

    @Operation(summary = "Save client information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client saved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/saveClient")
    public Integer saveClient(@RequestBody Client client){
        try {
            Client clientResult = bookingService.saveClient(client);
            return clientResult.getId();
        } catch (Exception e) {
            e.printStackTrace();
            // Return 500 Internal Server Error if there's an issue with saving client
            throw new RuntimeException("Internal server error");
        }

    }

    @Operation(summary = "Send confirmation email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email sent successfully"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/sendMail")
    public boolean sendMail(@RequestParam Integer billId){
        try {
            return bookingService.sendMail(billId);
        } catch (Exception e) {
            e.printStackTrace();
            // Return 500 Internal Server Error if there's an issue with saving client
            throw new RuntimeException("Internal server error");
        }

    }

}
