package agency.illiaderhun.com.github.controller;

import agency.illiaderhun.com.github.model.daoFactory.RepairOrderDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.RepairOrderDao;
import agency.illiaderhun.com.github.model.entities.RepairOrder;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class UserPageCommand {

    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class.getSimpleName());
    private RepairOrderDao<RepairOrder, Integer> repairOrderDao = RepairOrderDaoFactory.getReport("mysql");

    public ArrayList<RepairOrder> getCustomerOrders(Integer userId){
        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByCustomerId(userId);
            for (RepairOrder tempOrder : repairOrders) {
                tempOrder.setManagerName(new LoginCommand().findUserNameById(tempOrder.getManagerId()));
            }
            return repairOrders;
        } catch (IdInvalid idInvalid) {
            return null;
        }
    }

    public ArrayList<RepairOrder> getManagerOrders(Integer userId){
        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByManagerId(userId);
            for (RepairOrder tempOrder : repairOrders) {
                tempOrder.setCustomerName(new LoginCommand().findUserNameById(tempOrder.getCustomerId()));
            }
            return repairOrders;
        } catch (IdInvalid idInvalid) {
            return null;
        }
    }

    public ArrayList<RepairOrder> getMasterOrders(Integer userId){
        try {
            ArrayList<RepairOrder> repairOrders = repairOrderDao.readByMasterId(userId);
            for (RepairOrder tempOrder : repairOrders) {
                tempOrder.setManagerName(new LoginCommand().findUserNameById(tempOrder.getManagerId()));
            }
            return repairOrders;
        } catch (IdInvalid idInvalid) {
            return null;
        }
    }
}
