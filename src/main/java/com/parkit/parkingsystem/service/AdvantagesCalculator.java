package com.parkit.parkingsystem.service;

import java.util.ArrayList;

public class AdvantagesCalculator {

    public long subtractFreeTime(long time) {
        if (time > 30) {
            return time - 30;
        }
        return 0;
    }

    public boolean isEligibleForDiscountForRecurringUsers(ArrayList<String> list, String vehicleRegNumber) {
        int number = 0;
        for (String i : list) {
            if (i.equals(vehicleRegNumber)) {
                number++;
            }
        }
        if (number>=1){
            return true;
        }
        return false;
    }

    public double applyFivePercentReduction(double numberWithoutReduction) {
        return numberWithoutReduction - (numberWithoutReduction * 5 / 100);
    }
}