package agency.illiaderhun.com.github.controller;

import agency.illiaderhun.com.github.model.daoFactory.RepairOrderDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.RepairOrderDao;
import agency.illiaderhun.com.github.model.entities.RepairOrder;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;

public class RepairOrderCommand {

    private static final Logger LOGGER = Logger.getLogger(RepairOrderCommand.class.getSimpleName());
    private RepairOrderDao<RepairOrder, Integer> repairOrderDao = RepairOrderDaoFactory.getReport("mysql");

    public void deleteOrder(Integer orderId){
        repairOrderDao.delete(orderId);
    }

    public void createNewOrder(String deviceName, String description, Integer customerId){
        LOGGER.info("createNewOrder start with deviceName: " + deviceName + " description: " + description + " customerId: " + customerId);
        RepairOrder newRepairOrder = new RepairOrder.Builder(deviceName, customerId)
                .description(description)
                .build();

        repairOrderDao.create(newRepairOrder);
        LOGGER.info("createNewOrder done");
    }

    public ArrayList<RepairOrder> getCustomerOrders(Integer userId){
        LOGGER.info("getCustomerOrders start with userId " + userId);
        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByCustomerId(userId);
            if (repairOrders != null) {
                LOGGER.info("getCustomerOrders found order");
                for (RepairOrder tempOrder : repairOrders) {
                    tempOrder.setManagerName(new UserCommand().findUserNameById(tempOrder.getManagerId()));
                    try {
                        tempOrder.setReport(new ReportCommand().getReportByOrderId(tempOrder.getOrderId()).getBreakingDescription());
                    } catch (IdInvalid e){

                    }
                }
            }
            LOGGER.info("getCustomerOrders return order " + repairOrders);
            return repairOrders;
        } catch (IdInvalid idInvalid) {
            return null;
        }
    }

    public ArrayList<RepairOrder> getManagerOrders(Integer userId){
        LOGGER.info("getManagerOrders start with userId " + userId);

        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByManagerId(userId);
            if (repairOrders != null) {
                for (RepairOrder tempOrder : repairOrders) {
                    tempOrder.setCustomerName(new UserCommand().findUserNameById(tempOrder.getCustomerId()));
                    tempOrder.setMasterName(new UserCommand().findUserNameById(tempOrder.getMasterId()));
                    try {
                        tempOrder.setReport(new ReportCommand().getReportByOrderId(tempOrder.getOrderId()).getBreakingDescription());
                    } catch (IdInvalid e){

                    }
                }
            }
            return repairOrders;
        } catch (IdInvalid idInvalid) {
            return null;
        }
    }

    public ArrayList<RepairOrder> getMasterOrders(Integer userId){
        LOGGER.info("getMasterOrders start with userId " + userId);
        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByMasterId(userId);
            if (repairOrders != null) {
                for (RepairOrder tempOrder : repairOrders) {
                    tempOrder.setManagerName(new UserCommand().findUserNameById(tempOrder.getManagerId()));
                    try {
                        tempOrder.setReport(new ReportCommand().getReportByOrderId(tempOrder.getOrderId()).getBreakingDescription());
                    } catch (IdInvalid e){

                    }
                }
            }
            return repairOrders;
        } catch (IdInvalid idInvalid) {
            return null;
        }
    }

    public RepairOrder readOrderById(Integer orderId) throws IdInvalid {
        RepairOrder repairOrder = repairOrderDao.read(orderId);
        repairOrder.setCustomerName(new UserCommand().findUserNameById(repairOrder.getCustomerId()));
        repairOrder.setMasterName(new UserCommand().findUserNameById(repairOrder.getMasterId()));

        return repairOrder;
    }

    public void updateOrder(String deviceName, String description, BigDecimal price, Integer orderId, Integer customerId){
        LOGGER.info("updateOrder start");
        RepairOrder repairOrder = new RepairOrder.Builder(deviceName, customerId)
                .description(description)
                .price(price)
                .orderId(orderId)
                .build();
        repairOrderDao.update(repairOrder);
        LOGGER.info("update order done");
    }
}
