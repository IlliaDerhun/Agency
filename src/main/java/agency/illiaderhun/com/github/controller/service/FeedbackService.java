package agency.illiaderhun.com.github.controller.service;

import agency.illiaderhun.com.github.model.entities.Feedback;

public interface FeedbackService {

    Feedback getFeedbackByReportId(Integer reportId);
    void createFeedback(String comment, Integer orderId);
}
