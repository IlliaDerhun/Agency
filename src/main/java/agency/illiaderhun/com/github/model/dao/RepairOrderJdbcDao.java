package agency.illiaderhun.com.github.model.dao;

import agency.illiaderhun.com.github.model.daoInterface.RepairOrderDao;
import agency.illiaderhun.com.github.model.entities.RepairOrder;
import agency.illiaderhun.com.github.model.exeptions.IdInvalidExcepiton;
import com.sun.istack.internal.NotNull;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * RepairOrderJdbcDao works with Repair's entities
 * can do all CRUD operations and readBy - Customer/Manager/Master - Id
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class RepairOrderJdbcDao implements RepairOrderDao<RepairOrder, Integer> {

    private static final Logger LOGGER = Logger.getLogger(RepairOrderJdbcDao.class.getSimpleName());

    @NotNull
    private DataSource dataSource;

    @NotNull
    private Properties properties;

    public RepairOrderJdbcDao(DataSource dataSource, Properties properties) {
        this.dataSource = dataSource;
        this.properties = properties;
    }

    /**
     * Select RepairOrder by customerId
     *
     * @param customerId for select all customer's orders.
     * @return return all valid orders if it exist.
     * @exception IdInvalidExcepiton in case method couldn't find anything by customerId
     */
    @Override
    public ArrayList<RepairOrder> readByCustomerId(Integer customerId) throws IdInvalidExcepiton {
        LOGGER.info("method readByCustomerId start with customerId: " + customerId);
        ArrayList<RepairOrder> theRepairOrders = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("selectByCustomerId"))){
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null && resultSet.next()){
                theRepairOrders.add(madeOrder(resultSet));
                while (resultSet.next()){
                    theRepairOrders.add(madeOrder(resultSet));
                }
            } else {
                LOGGER.error("method readByCustomerId throw IdInvalidExcepiton Exception \"Invalid customer id: \"" + customerId);
                throw new IdInvalidExcepiton("Invalid customer id: " + customerId);
            }
        } catch (SQLException e) {
            LOGGER.error("method readByCustomerId caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("method readByCustomerId return all customer's orders" + theRepairOrders);
        return theRepairOrders;
    }

    /**
     * Select RepairOrder by managerId
     *
     * @param managerId for select all managers's orders.
     * @return return all valid orders if it exist.
     * @exception IdInvalidExcepiton in case method couldn't find anything by managerId
     */
    @Override
    public ArrayList<RepairOrder> readByManagerId(Integer managerId) throws IdInvalidExcepiton {
        LOGGER.info("method readByManagerId start with managerId: " + managerId);
        ArrayList<RepairOrder> theRepairOrders = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("selectByManagerId"))){
            statement.setInt(1, managerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null && resultSet.next()){
                theRepairOrders.add(madeOrder(resultSet));
                while (resultSet.next()){
                    theRepairOrders.add(madeOrder(resultSet));
                }
            } else {
                LOGGER.error("method readByManagerId throw IdInvalidExcepiton Exception \"Invalid manager id: \"" + managerId);
                throw new IdInvalidExcepiton("Invalid manager id: " + managerId);
            }
        } catch (SQLException e) {
            LOGGER.error("method readByManagerId caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("method readByManagerId return all manager's orders" + theRepairOrders);
        return theRepairOrders;
    }

    /**
     * Select RepairOrder by masterId
     *
     * @param masterId for select all master's orders.
     * @return return all valid orders if it exist.
     * @exception IdInvalidExcepiton in case method couldn't find anything by masterId
     */
    @Override
    public ArrayList<RepairOrder> readByMasterId(Integer masterId) throws IdInvalidExcepiton {
        LOGGER.info("method readByMasterId start with masterId: " + masterId);
        ArrayList<RepairOrder> theRepairOrders = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("selectByMasterId"))){
            statement.setInt(1, masterId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null && resultSet.next()){
                theRepairOrders.add(madeOrder(resultSet));
                while (resultSet.next()){
                    theRepairOrders.add(madeOrder(resultSet));
                }
            } else {
                LOGGER.error("method readByMasterId throw IdInvalidExcepiton Exception \"Invalid manager id: \"" + masterId);
                throw new IdInvalidExcepiton("Invalid master id: " + masterId);
            }
        } catch (SQLException e) {
            LOGGER.error("method readByMasterId caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("method readByMasterId return all master's orders" + theRepairOrders);
        return theRepairOrders;
    }

    @Override
    public Integer findFreeManager() {
        LOGGER.info("findFreeManager start");
        Integer freeManagerId = 2;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("freeManager"))){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                freeManagerId = resultSet.getInt("manager");
            }
        } catch (SQLException e) {
            LOGGER.error("caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("findFreeManager return freeManagerId " + freeManagerId);
        return freeManagerId;
    }

    @Override
    public Integer findFreeMaster() {
        LOGGER.info("findFreeMaster start");
        Integer freeMasterId = 3;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("freeMaster"))){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                freeMasterId = resultSet.getInt("master");
            }
        } catch (SQLException e) {
            LOGGER.error("caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("findFreeMaster return " + freeMasterId);
        return freeMasterId;
    }

    /**
     * Create Repair order in database.
     *
     * @param repairOrder for creating.
     * @return false if Order already exist. True if creation is success.
     */
    @Override
    public boolean create(RepairOrder repairOrder) {
        LOGGER.info("method create start with order: " + repairOrder);
        boolean result = false;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("insert"))){

            setStatement(statement, repairOrder);
            statement.setInt(4, findFreeManager());
            statement.setInt(5, findFreeMaster());
            statement.executeUpdate();

            repairOrder.setOrderId(setInsertedId());
            repairOrder.setDate(read(repairOrder.getOrderId()).getDate());

            result = true;
        } catch (SQLException e) {
            LOGGER.error("method create caught SQLException " + e);
            e.printStackTrace();
        } catch (IdInvalidExcepiton idInvalidExcepiton) {
            LOGGER.error("method create caught IdInvalidExcepiton Exception");
            idInvalidExcepiton.printStackTrace();
        }

        LOGGER.info("method create return result: " + result);
        return result;
    }

    private int setInsertedId() throws SQLException {
        LOGGER.info("method setInsertedId start with no parameters");
        int orderId = 0;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("readInsertedId"))){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet != null && resultSet.next()){
                orderId = resultSet.getInt("order_id");
            }
        }

        LOGGER.info("method setInsertedId return orderId: " + orderId);
        return orderId;
    }

    private void setStatement(PreparedStatement statement, RepairOrder repairOrder) throws SQLException {
        LOGGER.info("method setStatement start with statement: " + statement + " and repairOrder " + repairOrder);
        statement.setString(1, repairOrder.getDeviceName());
        statement.setString(2, repairOrder.getDescription());
        statement.setInt(3, repairOrder.getCustomerId());
        statement.setInt(4, repairOrder.getManagerId());
        statement.setInt(5, repairOrder.getMasterId());
        statement.setBigDecimal(6, repairOrder.getPrice());
        LOGGER.info("method setStatement new statement: " + statement);
        LOGGER.info("method setStatement done");
    }

    /**
     * Select Repair order by orderId
     *
     * @param repairOrderId for select all user's orders.
     * @return return valid order if it exist.
     * @exception IdInvalidExcepiton in case method couldn't find anything by repairOrderId
     */
    @Override
    public RepairOrder read(Integer repairOrderId) throws IdInvalidExcepiton {
        LOGGER.info("method read start with repairOrderId: " + repairOrderId);
        RepairOrder theOrder = null;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("select"))){
            statement.setInt(1, repairOrderId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet != null && resultSet.next()){
                theOrder = madeOrder(resultSet);
            } else {
                LOGGER.error("method read throw IdInvalidExcepiton Exception with message: \"Invalid repairOrderId\"" + repairOrderId);
                throw new IdInvalidExcepiton("Invalid repairOrderId");
            }
        } catch (SQLException e) {
            LOGGER.error("method read caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("method read return: " + theOrder);
        return theOrder;
    }

    /**
     * Create new {@link RepairOrder} with all parameters from DB
     *
     * @param resultSet with parameters for creating from DB
     * @return valid {@link RepairOrder}
     * @throws SQLException in case some problem with resultSet
     */
    private RepairOrder madeOrder(ResultSet resultSet) throws SQLException {
        LOGGER.info("method madeOrder start with resultSet: " + resultSet);
        Integer orderId = resultSet.getInt("order_id");
        String deviceName = resultSet.getString("device_name");
        String description = resultSet.getString("description");
        Integer customerId = resultSet.getInt("customer_id");
        Integer managerId = resultSet.getInt("manager_id");
        Integer masterId = resultSet.getInt("master_id");
        Date date = resultSet.getDate("date");
        BigDecimal price = resultSet.getBigDecimal("order_price");

        return new RepairOrder.Builder(deviceName, customerId)
                .orderId(orderId)
                .description(description)
                .managerId(managerId)
                .masterId(masterId)
                .date(date)
                .price(price)
                .build();
    }

    /**
     * Update all order's fields by repairOrderId
     * except date and orderId
     *
     * @param repairOrder for updating
     * @return true if order has updated
     */
    @Override
    public boolean update(RepairOrder repairOrder) {
        LOGGER.info("method update start with order: " + repairOrder);
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("update"))){

            setStatement(statement, repairOrder);

            statement.setInt(7, repairOrder.getOrderId());

            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("method update caught SQLException");
            e.printStackTrace();
        }

        LOGGER.info("method update return result " + result);
        return result;
    }

    @Override
    public boolean delete(Integer repairOrderId) {
        LOGGER.info("method delete start with repairOrderId: " + repairOrderId);
        boolean result = false;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(properties.getProperty("delete"))){
            statement.setInt(1, repairOrderId);

            result = statement.executeUpdate() == 1;
        } catch (SQLException e) {
            LOGGER.error("method delete caught SQLException " + e);
            e.printStackTrace();
        }

        LOGGER.info("method delete return result: " + result);
        return result;
    }
}
