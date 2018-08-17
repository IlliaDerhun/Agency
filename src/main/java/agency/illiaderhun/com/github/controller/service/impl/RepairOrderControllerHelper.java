package agency.illiaderhun.com.github.controller.service.impl;

import agency.illiaderhun.com.github.controller.RepairOrderCommand;
import agency.illiaderhun.com.github.controller.ReportCommand;
import agency.illiaderhun.com.github.controller.UserCommand;
import agency.illiaderhun.com.github.controller.service.RepairOrderService;
import agency.illiaderhun.com.github.model.daoFactory.RepairOrderDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.RepairOrderDao;
import agency.illiaderhun.com.github.model.entities.RepairOrder;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Help in work with UserDao for {@link RepairOrderCommand}
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class RepairOrderControllerHelper implements RepairOrderService {

    private static final Logger LOGGER = Logger.getLogger(RepairOrderControllerHelper.class.getSimpleName());
    private RepairOrderDao<RepairOrder, Integer> repairOrderDao = RepairOrderDaoFactory.getReport("mysql");

    @Override
    public void deleteOrder(Integer orderId){
        repairOrderDao.delete(orderId);
    }

    @Override
    public void createNewOrder(RepairOrder newRepairOrder) {
        LOGGER.info("createNewOrder start");
        repairOrderDao.create(newRepairOrder);
        LOGGER.info("createNewOrder done");
    }

    @Override
    public ArrayList<RepairOrder> getCustomerOrders(Integer userId) {
        LOGGER.info("getCustomerOrders start with userId " + userId);
        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByCustomerId(userId);
            if (repairOrders != null) {
                LOGGER.info("getCustomerOrders found order");
                for (RepairOrder tempOrder : repairOrders) {
                    tempOrder.setManagerName(new UserCommand().readNameByUserId(tempOrder.getManagerId()));
                    tempOrder.setMasterName(new UserCommand().readNameByUserId(tempOrder.getMasterId()));
                    tempOrder.setCustomerName(new UserCommand().readNameByUserId(tempOrder.getCustomerId()));
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

    @Override
    public ArrayList<RepairOrder> getManagerOrders(Integer userId) {
        LOGGER.info("getManagerOrders start with userId " + userId);

        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByManagerId(userId);
            if (repairOrders != null) {
                for (RepairOrder tempOrder : repairOrders) {
                    tempOrder.setCustomerName(new UserCommand().readNameByUserId(tempOrder.getCustomerId()));
                    tempOrder.setMasterName(new UserCommand().readNameByUserId(tempOrder.getMasterId()));
                    tempOrder.setManagerName(new UserCommand().readNameByUserId(tempOrder.getManagerId()));
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

    @Override
    public ArrayList<RepairOrder> getMasterOrders(Integer userId) {
        LOGGER.info("getMasterOrders start with userId " + userId);
        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByMasterId(userId);
            if (repairOrders != null) {
                for (RepairOrder tempOrder : repairOrders) {
                    tempOrder.setManagerName(new UserCommand().readNameByUserId(tempOrder.getManagerId()));
                    tempOrder.setCustomerName(new UserCommand().readNameByUserId(tempOrder.getCustomerId()));
                    tempOrder.setMasterName(new UserCommand().readNameByUserId(tempOrder.getMasterId()));
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

    @Override
    public RepairOrder readOrderById(Integer orderId) throws IdInvalid {
        RepairOrder repairOrder = repairOrderDao.read(orderId);
        repairOrder.setCustomerName(new UserCommand().readNameByUserId(repairOrder.getCustomerId()));
        repairOrder.setMasterName(new UserCommand().readNameByUserId(repairOrder.getMasterId()));

        return repairOrder;
    }

    @Override
    public void updateOrder(RepairOrder repairOrder){
        LOGGER.info("updateOrder start");

        repairOrderDao.update(repairOrder);

        LOGGER.info("update order done");
    }
}
