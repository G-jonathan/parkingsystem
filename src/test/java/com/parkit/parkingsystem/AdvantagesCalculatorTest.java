package com.parkit.parkingsystem;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.AdvantagesCalculator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AdvantagesCalculatorTest {

    private Ticket ticket;
    private static AdvantagesCalculator advantagesCalculator;

    @BeforeEach
    private void setUpPerTest() { ticket = new Ticket(); }

    @BeforeAll
    private static void setUp(){ advantagesCalculator = new AdvantagesCalculator(); }

    @Test
    public void calculateFreeTimeForCarEqualToReduction(){ //01
        System.out.println("ENTREE DANS LE TEST 01");
        ticket.setPrice(0.75);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setParkingSpot(parkingSpot);
        advantagesCalculator.CalculateFreeTime(ticket);
        assertEquals(ticket.getPrice(), 0.00);
    }

    @Test
    public void calculateFreeTimeForCarSuperiorToReduction(){ //02
        System.out.println("ENTREE DANS LE TEST 02");
        ticket.setPrice(1.00);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setParkingSpot(parkingSpot);
        advantagesCalculator.CalculateFreeTime(ticket);
        assertEquals(ticket.getPrice(), 0.25);
    }

    @Test
    public void calculateFreeTimeForBikeEqualToReduction(){ //03
        System.out.println("ENTREE DANS LE TEST 03");
        ticket.setPrice(0.50);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setParkingSpot(parkingSpot);
        advantagesCalculator.CalculateFreeTime(ticket);
        assertEquals(ticket.getPrice(), 0.00);
    }

    @Test
    public void calculateFreeTimeForBikeSuperiorToReduction(){ //04
        System.out.println("ENTREE DANS LE TEST 04");
        ticket.setPrice(0.75);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE,false);
        ticket.setParkingSpot(parkingSpot);
        advantagesCalculator.CalculateFreeTime(ticket);
        assertEquals(ticket.getPrice(), 0.25);
    }
}
