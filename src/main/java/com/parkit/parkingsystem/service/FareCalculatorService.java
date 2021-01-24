package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket, TicketDAO ticketDAO) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().isBefore(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }

        ParkingType parkingType = ticket.getParkingSpot().getParkingType();

        LocalDateTime inHour = ticket.getInTime();
        LocalDateTime outHour = ticket.getOutTime();

        Duration duration = Duration.between(inHour, outHour);
        double timeToPayInMinutes = (Math.abs(duration.toMinutes()));
        double timeToPayInHours = timeToPayInMinutes / 60;
        DecimalFormat decimalFormat = new DecimalFormat("####.##");
        double timeToPayInHoursInDecimalFormat = Double.parseDouble(decimalFormat.format(timeToPayInHours));

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
//             advantagesCalculator.CalculateFreeTime(ticket);
//            advantagesCalculator.CalculateDiscountForRecurringUsers(ticket);