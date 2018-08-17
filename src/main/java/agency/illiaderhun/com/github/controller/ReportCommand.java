package agency.illiaderhun.com.github.controller;

import agency.illiaderhun.com.github.controller.service.impl.ReportControllerHelper;
import agency.illiaderhun.com.github.model.dao.ReportJdbcDao;
import agency.illiaderhun.com.github.model.entities.Report;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import org.apache.log4j.Logger;

/**
 * UserCommand work with {@link Report} through {@link ReportControllerHelper}
 * Send to {@link ReportJdbcDao} readByEmail(email) and return it
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class ReportCommand {

    private static final Logger LOGGER =  Logger.getLogger(ReportCommand.class.getSimpleName());

    private ReportControllerHelper reportControllerHelper = new ReportControllerHelper();

    public Report getReportByOrderId(Integer orderId) throws IdInvalid {
        LOGGER.info("getReportByOrderId start with order " + orderId);
        Report theReport = reportControllerHelper.getReportByOrderId(orderId);
        LOGGER.info("getReportByOrderId return " + theReport);
        return theReport;
    }

    public void createNewReport(String breakingDescription, Integer orderId){
        LOGGER.info("createNewReport start with" + breakingDescription + " " + orderId);
        reportControllerHelper.createNewReport(breakingDescription, orderId);
        LOGGER.info("createNewReport done");
    }
}