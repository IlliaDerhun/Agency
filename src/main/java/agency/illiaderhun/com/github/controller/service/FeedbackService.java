package agency.illiaderhun.com.github.controller.service;

import agency.illiaderhun.com.github.model.entities.Feedback;
import agency.illiaderhun.com.github.model.exeptions.IdInvalid;

public interface FeedbackService {

    Feedback getFeedbackByReportId(Integer reportId);
    void createFeedback(String comment, Integer orderId);
}
