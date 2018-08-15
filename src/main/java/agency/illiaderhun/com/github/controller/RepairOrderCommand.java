package agency.illiaderhun.com.github.controller;

import agency.illiaderhun.com.github.model.daoFactory.RepairOrderDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.RepairOrderDao;
import agency.illiaderhun.com.github.model.entities.RepairOrder;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import org.apache.log4j.Logger;

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
        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByCustomerId(userId);
            if (repairOrders != null) {
                for (RepairOrder tempOrder : repairOrders) {
                    tempOrder.setManagerName(new LoginCommand().findUserNameById(tempOrder.getManagerId()));
                }
            }
            return repairOrders;
        } catch (IdInvalid idInvalid) {
            return null;
        }
    }

    public ArrayList<RepairOrder> getManagerOrders(Integer userId){
        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByManagerId(userId);
            if (repairOrders != null) {
                for (RepairOrder tempOrder : repairOrders) {
                    tempOrder.setCustomerName(new LoginCommand().findUserNameById(tempOrder.getCustomerId()));
                    tempOrder.setMasterName(new LoginCommand().findUserNameById(tempOrder.getMasterId()));
                }
            }
            return repairOrders;
        } catch (IdInvalid idInvalid) {
            return null;
        }
    }

    public ArrayList<RepairOrder> getMasterOrders(Integer userId){
        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByMasterId(userId);
            if (repairOrders != null) {
                for (RepairOrder tempOrder : repairOrders) {
                    tempOrder.setManagerName(new LoginCommand().findUserNameById(tempOrder.getManagerId()));
                }
            }
            return repairOrders;
        } catch (IdInvalid idInvalid) {
            return null;
        }
    }

    public RepairOrder readOrderById(Integer orderId) throws IdInvalid {
        RepairOrder repairOrder = repairOrderDao.read(orderId);
        repairOrder.setCustomerName(new LoginCommand().findUserNameById(repairOrder.getCustomerId()));
        repairOrder.setMasterName(new LoginCommand().findUserNameById(repairOrder.getMasterId()));

        return repairOrder;
    }
}
