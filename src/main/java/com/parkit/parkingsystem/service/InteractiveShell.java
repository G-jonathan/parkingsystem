package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class InteractiveShell {
    private static final Logger logger = LogManager.getLogger("InteractiveShell");


    public static void loadInterface() {
        DataBaseConfig dataBaseConfig = new DataBaseConfig();
        logger.info("App initialized!!!");
        System.out.println("Welcome to Parking System!");
        boolean continueApp = true;
        InputReaderUtil inputReaderUtil = new InputReaderUtil();
        ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO(dataBaseConfig);
        TicketDAO ticketDAO = new TicketDAO();
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);

        while (continueApp) {
            loadMenu();
            int option = inputReaderUtil.readSelection();
            switch (option) {
                case 1: {
                    try {
                        parkingService.processIncomingVehicle();
                    } catch (Exception e) {
                        logger.error("Unable to process incoming vehicle", e);
                    }
                    break;
                }
                case 2: {
                    try {
                        parkingService.processExitingVehicle();
                    } catch (Exception e) {
                        logger.error("Unable to process exiting vehicle", e);
                    }
                    break;
                }
                case 3: {
                    System.out.println("Exiting from the system!");
                    continueApp = false;
                    break;
                }
                default:
                    System.out.println("Unsupported option. Please enter a number corresponding to the provided menu");
            }
        }
    }

    private static void loadMenu() {
        System.out.println("Please select an option. Simply enter the number to choose an action");
        System.out.println("1 New Vehicle Entering - Allocate Parking Space");
        System.out.println("2 Vehicle Exiting - Generate Ticket Price");
        System.out.println("3 Shutdown System");
    }
}
