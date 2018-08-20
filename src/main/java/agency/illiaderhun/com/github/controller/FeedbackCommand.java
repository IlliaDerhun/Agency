package agency.illiaderhun.com.github.controller;

import agency.illiaderhun.com.github.controller.service.impl.FeedbackControllerHelper;
import agency.illiaderhun.com.github.model.entities.Feedback;
import org.apache.log4j.Logger;

public class FeedbackCommand {

    private static final Logger LOGGER = Logger.getLogger(FeedbackCommand.class.getSimpleName());
    private FeedbackControllerHelper feedbackControllerHelper = new FeedbackControllerHelper();

    public Feedback findFeedbackByReportId(Integer reportId){
        LOGGER.info("findFeedbackByReportId start with reportId: " + reportId);
        return feedbackControllerHelper.getFeedbackByReportId(reportId);
    }

    public void createFeedback(String comment, Integer orderId){
        LOGGER.info("createFeedback start with comment: " + comment + " orderId: " + orderId);
        feedbackControllerHelper.createFeedback(comment, orderId);
    }
}
