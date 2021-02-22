package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ParkingSpotDAO {
    private static final Logger logger = LogManager.getLogger("ParkingSpotDAO");
    public DataBaseConfig dataBaseConfig = new DataBaseConfig();

    public int getNextAvailableSlot(ParkingType parkingType) {
        Connection connection = null;
        int result = -1;
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
            statement.setString(1, parkingType.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            dataBaseConfig.closeResultSet(resultSet);
            dataBaseConfig.closePreparedStatement(statement);
        } catch (Exception ex) {
            logger.error("01 Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeConnection(connection);
        }
        return result;
    }

    public boolean updateParking(ParkingSpot parkingSpot) {
        Connection connection = null;
        try {
            connection = dataBaseConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DBConstants.UPDATE_PARKING_SPOT);
            statement.setBoolean(1, parkingSpot.isAvailable());
            statement.setInt(2, parkingSpot.getId());
            int updateRowCount = statement.executeUpdate();
            dataBaseConfig.closePreparedStatement(statement);
            return (updateRowCount == 1);
        } catch (Exception ex) {
            logger.error("Error updating parking info", ex);
            return false;
        } finally {
            dataBaseConfig.closeConnection(connection);
        }
    }
}
