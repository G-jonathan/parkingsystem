package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class FareCalculatorService {
    private final AdvantagesCalculator advantagesCalculator = new AdvantagesCalculator();
    private double fareWithoutDiscountForRecurringUsers = 0;
    private final DecimalFormat decimalFormat = new DecimalFormat("####.##");

    public double calculateFare(LocalDateTime inTime, LocalDateTime outTime, ParkingType parkingType, String vehicleRegNumber, ArrayList<String> vehiclesAlreadyRegisteredList) {
        if ((outTime == null) || (outTime.isBefore(inTime))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + outTime.toString());
        }
        long durationBetweenInTimeAndOutTime = Duration.between(inTime, outTime).toMinutes();
        long durationAfterSubtractFreeTime = advantagesCalculator.subtractFreeTime(durationBetweenInTimeAndOutTime);

        if (durationAfterSubtractFreeTime <= 0) {
            return fareWithoutDiscountForRecurringUsers;
        } else {
            switch (parkingType) {
                case CAR: {
                    fareWithoutDiscountForRecurringUsers = durationAfterSubtractFreeTime * Fare.CAR_RATE_PER_MINUTE;
                    break;
                }
                case BIKE: {
                    fareWithoutDiscountForRecurringUsers = durationAfterSubtractFreeTime * Fare.BIKE_RATE_PER_MINUTE;
                    break;
                }
            }
        }
        boolean isRecurringUser = advantagesCalculator.isEligibleForDiscountForRecurringUsers(vehiclesAlreadyRegisteredList, vehicleRegNumber);
        double finalFare = isRecurringUser ? advantagesCalculator.applyFivePercentReduction(fareWithoutDiscountForRecurringUsers) : fareWithoutDiscountForRecurringUsers;
        return Double.parseDouble(decimalFormat.format(finalFare));
    }
}