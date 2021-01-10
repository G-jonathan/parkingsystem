package com.parkit.parkingsystem.service;
import com.parkit.parkingsystem.model.Ticket;

public class AdvantagesCalculator {

    public static void CalculateFreeTime(Ticket ticket){
        double price = ticket.getPrice();

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                if(price <= 0.75){
                    ticket.setPrice(0);
                    System.out.println("reduction appliquée");
                }
                else {
                    ticket.setPrice(price - 0.75);
                }
                break;
            }
            case BIKE: {
                if(price <= 0.50){
                    ticket.setPrice(0);
                }
                else {
                    ticket.setPrice(price - 0.50);
                }
                break;
            }
            default: throw new IllegalArgumentException("Unknown Parking Type");
        }
    }

    public static void CalculateRecurringAdvantage(){

    }
}