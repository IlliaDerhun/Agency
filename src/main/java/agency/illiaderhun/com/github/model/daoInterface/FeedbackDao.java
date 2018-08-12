package agency.illiaderhun.com.github.model.daoInterface;

import agency.illiaderhun.com.github.model.exeptions.IdInvalid;

public interface FeedbackDao<Feedback, Integer> extends Dao<Feedback, Integer> {

    Feedback readByReportId(Integer reportId) throws IdInvalid;
}
