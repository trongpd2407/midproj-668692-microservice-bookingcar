package org.example.bill_service.service;

import org.example.bill_service.model.Bill;
import org.example.bill_service.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;
    public Bill save(Bill bill){
        bill.setCreateDate(Date.valueOf(LocalDate.now()));
        bill.setTime(Time.valueOf(LocalTime.now()));
        return billRepository.save(bill);
    }

    public Integer getQuantityByIdTripAndDate(Integer id_trip, Date date){
        int quantity = 0;
        if (billRepository.getQuantityByIdTripAndDate(id_trip, date) != null)
            quantity = billRepository.getQuantityByIdTripAndDate(id_trip, date);
        return quantity;
    }

    public Bill getById(int id){
        return billRepository.findById(id).get();
    }




}