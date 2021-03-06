package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class ParkingService {
    private static final Logger logger = LogManager.getLogger("ParkingService");
    private final FareCalculatorService fareCalculatorService = new FareCalculatorService();
    private final InputReaderUtil inputReaderUtil;
    private final ParkingSpotDAO parkingSpotDAO;
    private final TicketDAO ticketDAO;

    public ParkingService(InputReaderUtil inputReaderUtil, ParkingSpotDAO parkingSpotDAO, TicketDAO ticketDAO) {
        this.inputReaderUtil = inputReaderUtil;
        this.parkingSpotDAO = parkingSpotDAO;
        this.ticketDAO = ticketDAO;
    }

    public void processIncomingVehicle() throws Exception {
        ParkingSpot parkingSpot = getNextParkingNumberIfAvailable();
        if (parkingSpot != null && parkingSpot.getId() > 0) {
            String vehicleRegNumber = getVehicleRegNumber();
            parkingSpot.setAvailable(false);
            parkingSpotDAO.updateParking(parkingSpot);
            LocalDateTime inTime = LocalDateTime.now();
            Ticket ticket = new Ticket();
            ticket.setParkingSpot(parkingSpot);
            ticket.setVehicleRegNumber(vehicleRegNumber);
            ticket.setPrice(0);
            ticket.setInTime(inTime);
            ticket.setOutTime(null);
            ticketDAO.saveTicket(ticket);
            System.out.println("Generated Ticket and saved in DB");
            System.out.println("Please park your vehicle in spot number:" + parkingSpot.getId());
            System.out.println("Recorded in-time for vehicle number:" + vehicleRegNumber + " is:" + inTime);
        }
    }

    private String getVehicleRegNumber() throws Exception {
        System.out.println("Please type the vehicle registration number and press enter key");
        return inputReaderUtil.readVehicleRegistrationNumber();
    }

    public ParkingSpot getNextParkingNumberIfAvailable() {
        int parkingNumber = 0;
        ParkingSpot parkingSpot = null;
        try {
            ParkingType parkingType = getVehicleType();
            parkingNumber = parkingSpotDAO.getNextAvailableSlot(parkingType);
            if (parkingNumber > 0) {
                parkingSpot = new ParkingSpot(parkingNumber, parkingType, true);
            } else {
                throw new Exception("Error fetching parking number from DB. Parking slots might be full");
            }
        } catch (IllegalArgumentException ie) {
            logger.error("Error parsing user input for type of vehicle", ie);
        } catch (Exception e) {
            logger.error("Error fetching next available parking slot", e);
        }
        return parkingSpot;
    }

    private ParkingType getVehicleType() {
        System.out.println("Please select vehicle type from menu");
        System.out.println("1 CAR");
        System.out.println("2 BIKE");
        int input = inputReaderUtil.readSelection();
        switch (input) {
            case 1: {
                return ParkingType.CAR;
            }
            case 2: {
                return ParkingType.BIKE;
            }
            default: {
                System.out.println("Incorrect input provided");
                throw new IllegalArgumentException("Entered input is invalid");
            }
        }
    }

    public void processExitingVehicle() throws Exception {
        String vehicleRegNumber = getVehicleRegNumber();
        Optional<Ticket> ticket = ticketDAO.getTicket(vehicleRegNumber);
        if (!ticket.isPresent()) {
            System.out.println("Unable to retrieve information from vehicle " + vehicleRegNumber);
            return;
        }
        LocalDateTime outTime = LocalDateTime.now();
        ticket.get().setOutTime(outTime);
        ArrayList<String> vehiclesAlreadyRegisteredList = ticketDAO.getAllVehicleRegNumber();
        double finalFare = fareCalculatorService.calculateFare(ticket.get(), vehiclesAlreadyRegisteredList);
        ticket.get().setPrice(finalFare);
        if (ticketDAO.updateTicket(ticket.get())) {
            ParkingSpot parkingSpot = ticket.get().getParkingSpot();
            parkingSpot.setAvailable(true);
            parkingSpotDAO.updateParking(parkingSpot);
            System.out.println("Please pay the parking fare:" + finalFare);
            System.out.println("Recorded out-time for vehicle number:" + vehicleRegNumber + " is:" + outTime);
        } else {
            System.out.println("Unable to update ticket information. Error occurred");
        }
    }
}
