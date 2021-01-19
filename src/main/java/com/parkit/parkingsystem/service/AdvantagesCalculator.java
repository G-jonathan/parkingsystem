package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

import java.util.ArrayList;

public class AdvantagesCalculator {

    public  void CalculateFreeTime(Ticket ticket){
        double price = ticket.getPrice();

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                if(price <= 0.75){
                    ticket.setPrice(0);
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

    public void CalculateDiscountForRecurringUsers(Ticket ticket){

        TicketDAO ticketDAO = new TicketDAO();
        String vehicleRegNumber = ticket.getVehicleRegNumber();
        double priceWithoutDiscount = ticket.getPrice();
        ArrayList<String> list = ticketDAO.getAllVehicleRegNumber();
        int number = 0;

        for (String i : list) {
            if (i.equals(vehicleRegNumber)) {
                number++;
            }
        }
        if (number>=3){
            double priceWithDiscount;
            priceWithDiscount = priceWithoutDiscount - (priceWithoutDiscount*5/100);
            ticket.setPrice(priceWithDiscount);
            System.out.println("UTILISATEUR RECURENT..." +"ANCIEN PRIX: " +priceWithoutDiscount +" ... NOUVEAU PRIX:..." + priceWithDiscount);
        }
        else {
            System.out.println("UTILISATEUR NON TROUVE, PAS DE REMISE ACCORDEE");
        }
        System.out.println("#####  VOICI LES VEHICULES ENREGISTRES:" + list + "#####");
    }
}