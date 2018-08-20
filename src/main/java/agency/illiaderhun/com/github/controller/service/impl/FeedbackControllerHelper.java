package agency.illiaderhun.com.github.controller.service.impl;

import agency.illiaderhun.com.github.controller.ReportCommand;
import agency.illiaderhun.com.github.controller.service.FeedbackService;
import agency.illiaderhun.com.github.model.daoFactory.FeedbackDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.FeedbackDao;
import agency.illiaderhun.com.github.model.entities.Feedback;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;
import org.apache.log4j.Logger;

public class FeedbackControllerHelper implements FeedbackService {

    private static final Logger LOGGER = Logger.getLogger(FeedbackControllerHelper.class.getSimpleName());
    private FeedbackDao<Feedback, Integer> feedbackDao = FeedbackDaoFactory.getFeedback("mysql");

    @Override
    public Feedback getFeedbackByReportId(Integer reportId) {
        LOGGER.info("getFeedbackByReportId start with reportId: " + reportId);
        Feedback theFeedback = null;

        try {
            theFeedback = feedbackDao.readByReportId(reportId);
        } catch (IdInvalid idInvalid) {
            LOGGER.error("feedback by reportId not found");
            idInvalid.printStackTrace();
        }

        LOGGER.info("return " + theFeedback);
        return theFeedback;
    }

    @Override
    public void createFeedback(String comment, Integer orderId) {
        LOGGER.info("createFeedback start comment: " + comment + " orderId: " + orderId);
        try {
            Integer reportId = new ReportCommand().getReportByOrderId(orderId).getReportId();
            Feedback theFeedback = new Feedback(comment, reportId);
            feedbackDao.create(theFeedback);
        } catch (IdInvalid idInvalid) {
            LOGGER.error("caught IdInvalid exception");
            idInvalid.printStackTrace();
        }
    }
}
