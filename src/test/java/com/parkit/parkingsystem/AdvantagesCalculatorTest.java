package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.AdvantagesCalculator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class AdvantagesCalculatorTest {
    private static AdvantagesCalculator advantagesCalculator;

    @BeforeEach
    private void setUpPerTest() {
    }

    @BeforeAll
    private static void setUp() {
        advantagesCalculator = new AdvantagesCalculator();
    }

    @AfterAll
    private static void tearDown() {
    }

    @Test
    public void subtractFreeTimeFromThirtyMinutesDuration() {
        long durationWithoutFreeTime = 30;
        long durationMinusFreeTime = advantagesCalculator.subtractFreeTime(durationWithoutFreeTime);
        assertEquals(0, durationMinusFreeTime);
    }

    @Test
    public void subtractFreeTimeFromZeroMinutesDuration() {
        long durationWithoutFreeTime = 0;
        long durationMinusFreeTime = advantagesCalculator.subtractFreeTime(durationWithoutFreeTime);
        assertEquals(0, durationMinusFreeTime);
    }

    @Test
    public void subtractFreeTimeFromNegativeDuration() {
        long durationWithoutFreeTime = -100;
        long durationMinusFreeTime = advantagesCalculator.subtractFreeTime(durationWithoutFreeTime);
        assertEquals(0, durationMinusFreeTime);
    }

    @Test
    public void subtractFreeTimeFromSixtyMinutesDuration() {
        long durationWithoutFreeTime = 60;
        long durationMinusFreeTime = advantagesCalculator.subtractFreeTime(durationWithoutFreeTime);
        assertEquals(30, durationMinusFreeTime);
    }

    @Test
    public void applyFivePercentReductionOnAnAmountEqualToZero() {
        double amountEqualToZero = 0;
        double amountAfterApplyFivePercentReduction = advantagesCalculator.applyFivePercentReduction(amountEqualToZero);
        assertEquals(0, amountAfterApplyFivePercentReduction);
    }

    @Test
    public void applyFivePercentReductionOnAPositiveAmount() {
        double positiveAmount = 100;
        double amountAfterApplyFivePercentReduction = advantagesCalculator.applyFivePercentReduction(positiveAmount);
        assertEquals(95, amountAfterApplyFivePercentReduction);
    }

    @Test
    public void applyFivePercentReductionOnANegativeAmount() {
        double negativeAmount = -100;
        double amountAfterApplyFivePercentReduction = advantagesCalculator.applyFivePercentReduction(negativeAmount);
        assertEquals(0, amountAfterApplyFivePercentReduction);
    }

    @Test
    public void isEligibleForDiscountForRecurringUsersIfExistOnDataBase() {
        ArrayList<String> vehiclesAlreadyRegisteredList = new ArrayList<>();
        String vehicleRegNumber = "TEST";
        vehiclesAlreadyRegisteredList.add(vehicleRegNumber);
        boolean isRecurringUser = advantagesCalculator.isEligibleForDiscountForRecurringUsers(vehiclesAlreadyRegisteredList, vehicleRegNumber);
        assertTrue(isRecurringUser);
    }

    @Test
    public void isEligibleForDiscountForRecurringUsersIfDontExistOnDataBase() {
        ArrayList<String> vehiclesAlreadyRegisteredList = new ArrayList<>();
        vehiclesAlreadyRegisteredList.add("TEST2");
        String vehicleRegNumber = "TEST";
        boolean isRecurringUser = advantagesCalculator.isEligibleForDiscountForRecurringUsers(vehiclesAlreadyRegisteredList, vehicleRegNumber);
        assertFalse(isRecurringUser);
    }
}