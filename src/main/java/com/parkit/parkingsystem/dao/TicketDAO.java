package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;

public final class TicketDAO {
    private static final Logger logger = LogManager.getLogger("TicketDAO");
    public DataBaseConfig dataBaseConfig = new DataBaseConfig();

    public boolean saveTicket(Ticket ticket) {
        Connection connection = null;
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DBConstants.SAVE_TICKET);
            statement.setInt(1, ticket.getParkingSpot().getId());
            statement.setString(2, ticket.getVehicleRegNumber());
            statement.setDouble(3, ticket.getPrice());
            statement.setTimestamp(4, Timestamp.valueOf(ticket.getInTime()));
            statement.setTimestamp(5, (ticket.getOutTime() == null) ? null : Timestamp.valueOf(ticket.getOutTime()));
            return statement.execute();
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeConnection(connection);
            return false;
        }
    }

    public Ticket getTicket(String vehicleRegNumber) {
        Connection connection = null;
        Ticket ticket = null;
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DBConstants.GET_TICKET);
            statement.setString(1, vehicleRegNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ticket = new Ticket();
                ParkingSpot parkingSpot = new ParkingSpot(resultSet.getInt(1), ParkingType.valueOf(resultSet.getString(6)), false);
                ticket.setParkingSpot(parkingSpot);
                ticket.setId(resultSet.getInt(2));
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(resultSet.getDouble(3));
                ticket.setInTime(resultSet.getTimestamp(4).toLocalDateTime());
                ticket.setOutTime(resultSet.getTimestamp(5).toLocalDateTime());
            }
            dataBaseConfig.closeResultSet(resultSet);
            dataBaseConfig.closePreparedStatement(statement);
        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeConnection(connection);
            return ticket;
        }
    }

    public boolean updateTicket(Ticket ticket) {
        Connection connection = null;
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DBConstants.UPDATE_TICKET);
            statement.setDouble(1, ticket.getPrice());
            statement.setTimestamp(2, Timestamp.valueOf(ticket.getOutTime()));
            statement.setInt(3, ticket.getId());
            statement.execute();
            return true;
        } catch (Exception ex) {
            logger.error("Error saving ticket info", ex);
        } finally {
            dataBaseConfig.closeConnection(connection);
        }
        return false;
    }

    public ArrayList<String> getAllVehicleRegNumber() {
        Connection connection = null;
        ArrayList<String> list = new ArrayList<>();
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DBConstants.GET_ALL_VEHICLE_REG_NUMBER);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (Exception ex) {
            logger.error("Error getting all vehicle reg number", ex);
        } finally {
            dataBaseConfig.closeConnection(connection);
        }
        return list;
    }
}
