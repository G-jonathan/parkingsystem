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
}