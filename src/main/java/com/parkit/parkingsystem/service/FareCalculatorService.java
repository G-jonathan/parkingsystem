package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().isBefore(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        LocalDateTime inHour = ticket.getInTime();
        LocalDateTime outHour = ticket.getOutTime();
        Duration duration = Duration.between(inHour, outHour);
        double timeToPayInMinutes = (Math.abs(duration.toMinutes()));
        double timeToPayInHours = timeToPayInMinutes/60;
        DecimalFormat decimalFormat = new DecimalFormat("####.##");
        double timeToPayInHoursInDecimalFormat = Double.parseDouble(decimalFormat.format(timeToPayInHours)); //rounded

        //double finalResult = FreeParkingCalculator.CalculateFreePortion(timeToPayInHoursInDecimalFormat);
        //double finalResult = FreeParkingCalculator.CalculateFreePortion(Double.parseDouble(decimalFormat.format((Math.abs(Duration.between(inHour, outHour).toMinutes()))/60)));

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(timeToPayInHoursInDecimalFormat * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(timeToPayInHoursInDecimalFormat * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unknown Parking Type");
        }
    }
}