package com.parkit.parkingsystem.service;

public class FreeParkingCalculator {

    public static double CalculateFreePortion(double number){
        if (number <= 0.30){
            return 0.00;
        }
        else {
            return number - 0.30;
        }
    }
}