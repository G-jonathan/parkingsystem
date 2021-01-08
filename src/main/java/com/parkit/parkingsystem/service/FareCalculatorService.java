package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;
import java.time.LocalDateTime;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().isBefore(ticket.getInTime())) ){

            System.out.println("0000000001");
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        LocalDateTime inHour = ticket.getInTime();
        LocalDateTime outHour = ticket.getOutTime();
        Duration duration = Duration.between(inHour, outHour);
        Duration duration2 = Duration.between(outHour, inHour);
        long timeToPay = (Math.abs(duration.toMinutes()));
        double timeToPay2 = (double)timeToPay/60;


        //TODO: Some tests are failing here. Need to check if this logic ist correct

        System.out.println("########## entrée   "+ inHour);
        System.out.println("##########  sortie   "+ outHour);
        System.out.println("##########  durée1  "+ duration);
        System.out.println("##########  durée2  "+ duration2);
        System.out.println("##########  timeToPay  "+ timeToPay);
        System.out.println("##########  timeToPay2  "+ timeToPay2);

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                System.out.println("0000000002");
                ticket.setPrice(timeToPay2 * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                System.out.println("0000000003");
                ticket.setPrice(timeToPay2 * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}