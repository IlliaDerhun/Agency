package agency.illiaderhun.com.github.model.daoInterface;

import agency.illiaderhun.com.github.model.exeptions.IdInvalid;

public interface ReportDao<Report, Integer> extends Dao<Report, Integer> {

    Report readByOrderId(Integer orderId) throws IdInvalid;
}
