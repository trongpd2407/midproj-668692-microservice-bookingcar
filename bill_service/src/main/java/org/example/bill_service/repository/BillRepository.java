package org.example.bill_service.repository;
import org.example.bill_service.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Date;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    Bill getBillByDateAndTimeAndIdClient(Date date, Time time, Integer idClient);

    @Query(value = "SELECT SUM(quantity) FROM bill t WHERE t.id_trip = :id_trip AND t.date = :date", nativeQuery = true)
    Integer getQuantityByIdTripAndDate(@Param("id_trip") Integer id_trip, @Param("date") Date date);


}