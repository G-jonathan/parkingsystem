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
    public void calculateFreeTimeForCarEqualToReduction(){
        ticket.setPrice(0.75);
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setParkingSpot(parkingSpot);
        advantagesCalculator.CalculateFreeTime(ticket);
        assertEquals(ticket.getPrice(), 0.00);
    }
}
