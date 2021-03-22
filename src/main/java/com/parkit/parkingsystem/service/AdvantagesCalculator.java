package com.parkit.parkingsystem.service;

import java.util.ArrayList;

public final class AdvantagesCalculator {

    public long subtractFreeTime(long time) {
        if (time > 30) {
            return time - 30;
        }
        return 0;
    }

    public boolean isEligibleForDiscountForRecurringUsers(ArrayList<String> list, String vehicleRegNumber) {
        int number = 0;
        for (String index : list) {
            if (index.equals(vehicleRegNumber)) {
                number++;
            }
        }
        if (number >= 1) {
            return true;
        }
        return false;
    }

    public double applyFivePercentReduction(double numberWithoutReduction) {
        if (numberWithoutReduction > 0) {
            return numberWithoutReduction - (numberWithoutReduction * 5 / 100);
        }
        return 0;
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