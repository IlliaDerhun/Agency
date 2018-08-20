package agency.illiaderhun.com.github.controller.service.impl;

import agency.illiaderhun.com.github.controller.FeedbackCommand;
import agency.illiaderhun.com.github.controller.RepairOrderCommand;
import agency.illiaderhun.com.github.controller.ReportCommand;
import agency.illiaderhun.com.github.controller.UserCommand;
import agency.illiaderhun.com.github.controller.service.RepairOrderService;
import agency.illiaderhun.com.github.model.daoFactory.RepairOrderDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.RepairOrderDao;
import agency.illiaderhun.com.github.model.entities.Feedback;
import agency.illiaderhun.com.github.model.entities.RepairOrder;
import agency.illiaderhun.com.github.model.entities.Report;
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

    /**
     * Delete order by order's id through {@link RepairOrderDao}
     *
     * @param orderId order's id for deleting
     */
    @Override
    public void deleteOrder(Integer orderId){
        LOGGER.info("deleteOrder start with orderId: " + orderId);
        repairOrderDao.delete(orderId);
    }

    /**
     * Create order in DB with all required fields
     *
     * @param newRepairOrder already checked and validated repair order for creating
     */
    @Override
    public void createNewOrder(RepairOrder newRepairOrder) {
        LOGGER.info("createNewOrder start");
        repairOrderDao.create(newRepairOrder);
        LOGGER.info("createNewOrder done");
    }

    /**
     * Searches all customer's orders by userId through {@link RepairOrderDao}
     *
     * @param userId for searching
     * @return all customer's orders
     */
    @Override
    public ArrayList<RepairOrder> getCustomerOrders(Integer userId) {
        LOGGER.info("getCustomerOrders start with userId " + userId);
        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByCustomerId(userId);
            setHelpInfoIntoOrder(repairOrders);
            LOGGER.info("getCustomerOrders return order " + repairOrders);
            return repairOrders;
        } catch (IdInvalid idInvalid) {
            return null;
        }
    }

    /**
     * Searches all manager's orders by userId through {@link RepairOrderDao}
     *
     * @param userId for searching
     * @return all manager's orders
     */
    @Override
    public ArrayList<RepairOrder> getManagerOrders(Integer userId) {
        LOGGER.info("getManagerOrders start with userId " + userId);

        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByManagerId(userId);
            setHelpInfoIntoOrder(repairOrders);
            return repairOrders;
        } catch (IdInvalid idInvalid) {
            return null;
        }
    }

    /**
     * Searches all master's orders by userId through {@link RepairOrderDao}
     *
     * @param userId for searching
     * @return all master's orders
     */
    @Override
    public ArrayList<RepairOrder> getMasterOrders(Integer userId) {
        LOGGER.info("getMasterOrders start with userId " + userId);
        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByMasterId(userId);
            setHelpInfoIntoOrder(repairOrders);
            return repairOrders;
        } catch (IdInvalid idInvalid) {
            return null;
        }
    }

    /**
     * For all get/Users/Orders needs this help method
     * to avoid duplicate code
     *
     * @param repairOrders ready repair order without help info (master/customer/manager name or comment)
     */
    private void setHelpInfoIntoOrder(ArrayList<RepairOrder> repairOrders) {
        if (repairOrders != null) {
            for (RepairOrder tempOrder : repairOrders) {
                tempOrder.setManagerName(new UserCommand().readNameByUserId(tempOrder.getManagerId()));
                tempOrder.setMasterName(new UserCommand().readNameByUserId(tempOrder.getMasterId()));
                tempOrder.setCustomerName(new UserCommand().readNameByUserId(tempOrder.getCustomerId()));

                try {
                    Report theReport = new ReportCommand().getReportByOrderId(tempOrder.getOrderId());
                    if (theReport != null){
                        Feedback theFeedback = new FeedbackCommand().findFeedbackByReportId(theReport.getReportId());
                        if (theFeedback != null){
                            tempOrder.setFeedback(theFeedback.getComment());
                        }
                    }
                } catch (IdInvalid idInvalid) {
                    LOGGER.error("for this order the feedback doesn't exist");
                    idInvalid.printStackTrace();
                }
                try {
                    tempOrder.setReport(new ReportCommand().getReportByOrderId(tempOrder.getOrderId()).getBreakingDescription());
                } catch (IdInvalid e){

                }
            }
        }


    }

    /**
     * Find and read one order from DB through {@link RepairOrderDao}
     *
     * @param orderId for searching
     * @return valid RepairOrder if it exist
     * @throws IdInvalid if orderId not valid and nothing exist
     */
    @Override
    public RepairOrder readOrderById(Integer orderId) throws IdInvalid {
        RepairOrder repairOrder = repairOrderDao.read(orderId);
        repairOrder.setCustomerName(new UserCommand().readNameByUserId(repairOrder.getCustomerId()));
        repairOrder.setMasterName(new UserCommand().readNameByUserId(repairOrder.getMasterId()));

        return repairOrder;
    }

    /**
     * Send to {@link RepairOrderDao} repairOrder for updating
     *
     * @param repairOrder with already updated fields
     */
    @Override
    public void updateOrder(RepairOrder repairOrder){
        LOGGER.info("updateOrder start");

        repairOrderDao.update(repairOrder);

        LOGGER.info("update order done");
    }
}
