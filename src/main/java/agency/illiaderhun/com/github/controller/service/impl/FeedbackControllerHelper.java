package agency.illiaderhun.com.github.controller.service.impl;

import agency.illiaderhun.com.github.controller.FeedbackCommand;
import agency.illiaderhun.com.github.controller.ReportCommand;
import agency.illiaderhun.com.github.controller.service.FeedbackService;
import agency.illiaderhun.com.github.model.daoFactory.FeedbackDaoFactory;
import agency.illiaderhun.com.github.model.daoInterface.FeedbackDao;
import agency.illiaderhun.com.github.model.entities.Feedback;
import agency.illiaderhun.com.github.model.exeptions.IdInvalidExcepiton;
import org.apache.log4j.Logger;

/**
 * FeedbackControllerHelper it is helper for {@link FeedbackCommand}
 * Works with {@link FeedbackDao}
 *
 * @author Illia Derhun
 * @version 1.0
 */
public class FeedbackControllerHelper implements FeedbackService {

    private static final Logger LOGGER = Logger.getLogger(FeedbackControllerHelper.class.getSimpleName());
    private FeedbackDao<Feedback, Integer> feedbackDao = FeedbackDaoFactory.getFeedback("mysql");

    /**
     * Get feedback from DB through {@link FeedbackDao} by reportId
     *
     * @param reportId reportId for needed feedback
     * @return the valid Feedback if it exist
     */
    @Override
    public Feedback getFeedbackByReportId(Integer reportId) {
        LOGGER.info("getFeedbackByReportId start with reportId: " + reportId);
        Feedback theFeedback = null;

        try {
            theFeedback = feedbackDao.readByReportId(reportId);
        } catch (IdInvalidExcepiton idInvalidExcepiton) {
            LOGGER.error("feedback by reportId not found");
            idInvalidExcepiton.printStackTrace();
        }

        LOGGER.info("return " + theFeedback);
        return theFeedback;
    }

    /**
     * Create feedback in DB through {@link FeedbackDao} with two
     * required fields
     *
     * @param comment required fields for feedback
     * @param orderId required fields for feedback
     */
    @Override
    public void createFeedback(String comment, Integer orderId) {
        LOGGER.info("createFeedback start comment: " + comment + " orderId: " + orderId);
        try {
            Integer reportId = new ReportCommand().getReportByOrderId(orderId).getReportId();
            Feedback theFeedback = new Feedback(comment, reportId);
            feedbackDao.create(theFeedback);
        } catch (IdInvalidExcepiton idInvalidExcepiton) {
            LOGGER.error("caught IdInvalidExcepiton exception");
            idInvalidExcepiton.printStackTrace();
        }
    }
}
