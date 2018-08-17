package agency.illiaderhun.com.github.controller.service.impl;

import agency.illiaderhun.com.github.controller.ReportCommand;
import agency.illiaderhun.com.github.controller.service.ReportService;
import agency.illiaderhun.com.github.model.daoFactory.ReportDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.ReportDao;
import agency.illiaderhun.com.github.model.entities.Report;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import org.apache.log4j.Logger;

/**
 * Help in work with UserDao for {@link ReportCommand}
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class ReportControllerHelper implements ReportService {

    private static final Logger LOGGER =  Logger.getLogger(ReportControllerHelper.class.getSimpleName());
    private ReportDao<Report, Integer> reportDao = ReportDaoFactory.getReport("mysql");

    @Override
    public Report getReportByOrderId(Integer orderId) throws IdInvalid {
        LOGGER.info("getReportByOrderId start with order " + orderId);
        Report theReport = reportDao.readByOrderId(orderId);
        LOGGER.info("getReportByOrderId return " + theReport);
        return theReport;
    }

    @Override
    public void createNewReport(String breakingDescription, Integer orderId){
        LOGGER.info("createNewReport start with" + breakingDescription + " " + orderId);
        Report report = new Report.Builder(breakingDescription, orderId).build();
        reportDao.create(report);
        LOGGER.info("createNewReport done");
    }
}
