package org.example.booking_service.service;

import org.example.booking_service.model.Bill;
import org.example.booking_service.model.Client;
import org.example.booking_service.model.Location;
import org.example.booking_service.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToDoubleFunction;

@Service
public class BookingService {


    public List<Trip> getTripByDateAndDonAndTra(Date date, String don, String tra){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String urlTrip = "http://localhost:8765/trip-service/trip/list?don="+ don +"&tra="+tra;
        Trip[] tripsArray = restTemplate.getForObject(urlTrip, Trip[].class);
        List<Trip> trips = Arrays.asList(tripsArray);
        System.out.println(trips);
        for(Trip trip : trips){
            int quantiy ;
            String urlBill = "http://localhost:8765/bill-service/bill/quantity?idTrip={idTrip}&date={date}";
            quantiy = restTemplate.getForObject(urlBill, Integer.class,trip.getId(),date);
            System.out.println(quantiy);
            trip.setQuantity(trip.getQuantity() - quantiy);
        }
        return trips;
    }

    public List<List<Location>> getRouteByIdRouteAndType(int id_trip) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String urlTrip = "http://localhost:8765/trip-service/trip/get?id="+ id_trip;

        Trip trip =  restTemplate.getForObject(urlTrip, Trip.class);

        List<Location> list_location_di = new ArrayList<>();
        List<Location> list_location_ve = new ArrayList<>();
        if(trip.getDirection().equals("Đi")){
            String urlLocation = "http://localhost:8765/location-service/location?idRoute="+trip.getIdRoute()+"&type=don";
            Location[] locationArrayDi = restTemplate.getForObject(urlLocation, Location[].class);
            list_location_di = Arrays.asList(locationArrayDi);

            String urlLocation2 = "http://localhost:8765/location-service/location?idRoute="+trip.getIdRoute()+"&type=tra";
            Location[] locationArrayVe = restTemplate.getForObject(urlLocation2, Location[].class);
            list_location_ve = Arrays.asList(locationArrayVe);
        }
        else {
            String urlLocation = "http://localhost:8765/location-service/location?idRoute="+trip.getIdRoute()+"&type=tra";
            Location[] locationArrayDi = restTemplate.getForObject(urlLocation, Location[].class);
            list_location_di = Arrays.asList(locationArrayDi);

            String urlLocation2 = "http://localhost:8765/location-service/location?idRoute="+trip.getIdRoute()+"&type=don";
            Location[] locationArrayVe = restTemplate.getForObject(urlLocation2, Location[].class);
            list_location_ve = Arrays.asList(locationArrayVe);
        }
        List<List<Location>> lists = new ArrayList<>();
        lists.add(list_location_di);
        lists.add(list_location_ve);
        return lists;
    }

    public Bill saveBill(Bill bill){
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            String url = "http://localhost:8765/bill-service/bill/save";
            return restTemplate.postForObject(url, bill, Bill.class);
        } catch (Exception e) {
            e.printStackTrace();
            // Return 500 Internal Server Error if there's an issue with saving client
            throw new RuntimeException("Internal server error");
        }
    }

    public Client saveClient(Client client){
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            String url = "http://localhost:8765/client-service/client/save";
            return restTemplate.postForObject(url, client, Client.class);
        } catch (Exception e) {
            e.printStackTrace();
            // Return 500 Internal Server Error if there's an issue with saving client
            throw new RuntimeException("Internal server error");
        }
    }

    public boolean sendMail(Integer billId){
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

            String url = "http://localhost:8765/mailsender-service/mailsender?billId=" + billId;
            ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.POST, entity, Boolean.class);
            return response.getBody() != null && response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            // Return 500 Internal Server Error if there's an issue with saving client
            throw new RuntimeException("Internal server error");
        }
    }
}