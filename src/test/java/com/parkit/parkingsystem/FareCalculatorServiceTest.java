package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FareCalculatorServiceTest {
    private static FareCalculatorService fareCalculatorService;
    private static ArrayList<String> noVehiclesAlreadyRegistered;
    private static DecimalFormat decimalFormat;
    private Ticket ticket;

    @BeforeAll
    private static void setUp() {
        fareCalculatorService = new FareCalculatorService();
        noVehiclesAlreadyRegistered = new ArrayList<>();
        decimalFormat = new DecimalFormat("####.##");
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    @Test
    public void calculateFareCarForOneHour(){
        ticket.setInTime(LocalDateTime.now().minusHours(1));
        ticket.setOutTime(LocalDateTime.now());
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setParkingSpot(parkingSpot);
        double returnedAmount = fareCalculatorService.calculateFare(ticket, noVehiclesAlreadyRegistered);
        assertEquals(Fare.CAR_RATE_PER_HOUR / 2, returnedAmount);
    }

    @Test
    public void calculateFareBikeForOneHour(){
        ticket.setInTime(LocalDateTime.now().minusHours(1));
        ticket.setOutTime(LocalDateTime.now());
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setParkingSpot(parkingSpot);
        double returnedAmount = fareCalculatorService.calculateFare(ticket, noVehiclesAlreadyRegistered);
        assertEquals(Fare.BIKE_RATE_PER_HOUR / 2, returnedAmount);
    }

    @Test
    public void calculateFareUnknownType(){
        ticket.setInTime(LocalDateTime.now().minusHours(1));
        ticket.setOutTime(LocalDateTime.now());
        ParkingSpot parkingSpot = new ParkingSpot(1, null,false);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket, noVehiclesAlreadyRegistered));
    }

    @Test
    public void calculateFareBikeWithFutureInTime(){
        ticket.setInTime(LocalDateTime.now().plusHours(1));
        ticket.setOutTime(LocalDateTime.now());
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket, noVehiclesAlreadyRegistered));
    }

    @Test
    public void calculateFareCarWithFutureInTime(){
        ticket.setInTime(LocalDateTime.now().plusHours(1));
        ticket.setOutTime(LocalDateTime.now());
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket, noVehiclesAlreadyRegistered));
    }

    @Test
    public void calculateFareBikeWithLessThanOneHourParkingTime(){
        ticket.setInTime(LocalDateTime.now().minusMinutes(45));
        ticket.setOutTime(LocalDateTime.now());
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setParkingSpot(parkingSpot);
        double returnedAmount = fareCalculatorService.calculateFare(ticket, noVehiclesAlreadyRegistered);
        assertEquals(0.25 * Fare.BIKE_RATE_PER_HOUR, returnedAmount);
    }

    @Test
    public void calculateFareCarWithLessThanOneHourParkingTime(){
        ticket.setInTime(LocalDateTime.now().minusMinutes(45));
        ticket.setOutTime(LocalDateTime.now());
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setParkingSpot(parkingSpot);
        double returnedAmount = fareCalculatorService.calculateFare(ticket, noVehiclesAlreadyRegistered);
        assertEquals(Double.parseDouble(decimalFormat.format(0.25 * Fare.CAR_RATE_PER_HOUR)), returnedAmount);
    }

    @Test
    public void calculateFareCarWithMoreThanADayParkingTime(){
        ticket.setInTime(LocalDateTime.now().minusHours(25));
        ticket.setOutTime(LocalDateTime.now());
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setParkingSpot(parkingSpot);
        double returnedAmount = fareCalculatorService.calculateFare(ticket, noVehiclesAlreadyRegistered);
        assertEquals(24.5 * Fare.CAR_RATE_PER_HOUR, returnedAmount);
    }

    @Test
    public void calculateFareBikeWithMoreThanADayParkingTime(){
        ticket.setInTime(LocalDateTime.now().minusHours(25));
        ticket.setOutTime(LocalDateTime.now());
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setParkingSpot(parkingSpot);
        double returnedAmount = fareCalculatorService.calculateFare(ticket, noVehiclesAlreadyRegistered);
        assertEquals(24.5 * Fare.BIKE_RATE_PER_HOUR, returnedAmount);
    }
}