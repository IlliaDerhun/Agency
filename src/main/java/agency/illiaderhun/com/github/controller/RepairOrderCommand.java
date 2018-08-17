package agency.illiaderhun.com.github.controller;

import agency.illiaderhun.com.github.controller.service.impl.RepairOrderControllerHelper;
import agency.illiaderhun.com.github.controller.service.impl.ReportControllerHelper;
import agency.illiaderhun.com.github.model.entities.RepairOrder;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * UserCommand work with {@link RepairOrder} through {@link ReportControllerHelper}
 * Send to {@link RepairOrder} readByEmail(email) and return it
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class RepairOrderCommand {

    private static final Logger LOGGER = Logger.getLogger(RepairOrderCommand.class.getSimpleName());

    private RepairOrderControllerHelper repairOrderControllerHelper = new RepairOrderControllerHelper();

    public void deleteOrder(Integer orderId){
        repairOrderControllerHelper.deleteOrder(orderId);
    }

    public void createNewOrder(String deviceName, String description, Integer customerId) {
        LOGGER.info("createNewOrder start with deviceName: " + deviceName + " description: " + description + " customerId: " + customerId);
        RepairOrder newRepairOrder = new RepairOrder.Builder(deviceName, customerId)
                .description(description)
                .build();
        repairOrderControllerHelper.createNewOrder(newRepairOrder);
        LOGGER.info("createNewOrder done");
    }

    public ArrayList<RepairOrder> getCustomerOrders(Integer userId) {
        LOGGER.info("getCustomerOrders start with userId " + userId);
        return repairOrderControllerHelper.getCustomerOrders(userId);
    }

    public ArrayList<RepairOrder> getManagerOrders(Integer userId) {
        LOGGER.info("getManagerOrders start with userId " + userId);

        return repairOrderControllerHelper.getManagerOrders(userId);
    }

    public ArrayList<RepairOrder> getMasterOrders(Integer userId) {
        LOGGER.info("getMasterOrders start with userId " + userId);

        return repairOrderControllerHelper.getMasterOrders(userId);
    }

    public RepairOrder readOrderById(Integer orderId) throws IdInvalid {
        RepairOrder repairOrder = repairOrderControllerHelper.readOrderById(orderId);

        return repairOrder;
    }

    public void updateOrder(String deviceName, String description, BigDecimal price, Integer orderId, Integer customerId) {
        LOGGER.info("updateOrder start");
        RepairOrder repairOrder = new RepairOrder.Builder(deviceName, customerId)
                .description(description)
                .price(price)
                .orderId(orderId)
                .build();
        repairOrderControllerHelper.updateOrder(repairOrder);
        LOGGER.info("update order done");
    }
}
