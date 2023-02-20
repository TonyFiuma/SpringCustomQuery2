package co.develhope.customqueries2.repositories;

import co.develhope.customqueries2.entities.Flight;
import co.develhope.customqueries2.entities.FlightStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{

    List<Flight> findAllByStatus(FlightStatusEnum flightStatusEnum);

    @Query(value = "SELECT u FROM Flight u WHERE u.status = ?1 OR u.status = ?2")
    List<Flight> getCustomFlight(FlightStatusEnum p1,FlightStatusEnum p2);
}
