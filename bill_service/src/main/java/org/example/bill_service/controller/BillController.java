package org.example.bill_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.bill_service.model.Bill;
import org.example.bill_service.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @Operation(summary = "Get quantity by trip ID and date", description = "Retrieve the quantity of bills for a specific trip and date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the quantity",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Quantity not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/quantity")
    public int getQuantityByIdTripAndDate(@RequestParam("idTrip") int idTrip, @RequestParam("date") String date) {
        Date sqldate = Date.valueOf(date);
        return billService.getQuantityByIdTripAndDate(idTrip, sqldate);
    }

    @Operation(summary = "Get bill by ID", description = "Retrieve a bill by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the bill",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Bill.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Bill not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/get")
    public Bill getById(@RequestParam("id") int id) {
        return billService.getById(id);
    }

    @Operation(summary = "Save a bill", description = "Save a bill into the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved the bill",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Bill.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid bill supplied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/save")
    public Bill save(@RequestBody Bill bill) {
        return billService.save(bill);
    }
}
