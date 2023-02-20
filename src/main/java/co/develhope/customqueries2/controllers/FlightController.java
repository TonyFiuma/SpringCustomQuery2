package co.develhope.customqueries2.controllers;

import co.develhope.customqueries2.entities.Flight;
import co.develhope.customqueries2.entities.FlightStatusEnum;
import co.develhope.customqueries2.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/flights")
public class FlightController {

    String generateRandomString(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public FlightStatusEnum getRandomStatus(){
        return FlightStatusEnum.values()[new Random().nextInt(FlightStatusEnum.values().length)];
    }

    @Autowired
    private FlightRepository flightRepository;

    @GetMapping("/provisioning")
    public void provisionFlights(@RequestParam(required = false) Integer n){
        if(n == null) n=100;
        List<Flight> newFlights = new ArrayList<>();
        for(int i = 0; i < n; i++){
            Flight flight = new Flight();
            flight.setDescription(generateRandomString());
            flight.setFromAirport(generateRandomString());
            flight.setToAirport(generateRandomString());
            flight.setStatus(getRandomStatus());
            newFlights.add(flight);
        }
        flightRepository.saveAll(newFlights);
    }

    @GetMapping("")
    public Page<Flight> getAllFlights(@RequestParam int page, @RequestParam int size){
        return flightRepository.findAll(PageRequest.of(page, size, Sort.by("fromAirport").ascending()));
    }

    @GetMapping("/status/{status}")
    public List<Flight> getAllFlightsByStatus(@PathVariable FlightStatusEnum status){
        return flightRepository.findAllByStatus(status);
    }

    @GetMapping("/custom")
    public List<Flight> getCustomFlight(@RequestParam FlightStatusEnum p1,@RequestParam FlightStatusEnum p2){
        return flightRepository.getCustomFlight(p1, p2);
    }

}
