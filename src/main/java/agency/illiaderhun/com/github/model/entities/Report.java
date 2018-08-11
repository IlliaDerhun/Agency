package agency.illiaderhun.com.github.model.entities;

import agency.illiaderhun.com.github.annotations.*;

import java.sql.Date;
import java.util.Objects;

/**
 * Report represents report from {@link User} master
 * about {@link RepairOrder} from manager
 *
 * @author Illia Derhun
 * @version 1.0
 */

@Table(name = "report")
public class Report {

    @Column(name = "report_id")
    @AutoGeneration(TypeOfGeneration.AUTOINCREMENT)
    private int reportId;

    @Column(name = "replaced_part")
    private String replacedPart;

    @Column(name = "breaking_description")
    private String breakingDescription;

    @Column(name = "the_work_done")
    private String theWorkDone;

    @Column(name = "order_id")
    @JoinTable(name = "repair_order", joinsColums = @JoinColumn(name = "order_id"),
            inverseJoinColumn = @JoinColumn(name = "order_id"))
    private int orderId;

    @Column(name = "date")
    private Date date;

    public static class Builder{

        // Required fields
        private String breakingDescription;
        private int orderId;

        // Optional fields
        private int reportId;
        private String replacedPart = "default parts";
        private String theWorkDone = "some work has been done";
        private Date date;

        public Builder(String breakingDescription, int orderId){
            this.breakingDescription = breakingDescription;
            this.orderId = orderId;
        }

        public Builder reportId(int reportId){
            this.reportId = reportId;
            return this;
        }

        public Builder replacedPart(String replacedPart){
            this.replacedPart = replacedPart;
            return this;
        }

        public Builder theWorkDone(String theWorkDone){
            this.theWorkDone = theWorkDone;
            return this;
        }

        public Builder date(Date date){
            this.date = date;
            return this;
        }

        public Report build(){
            return new Report(this);
        }

    }

    public Report(Builder builder) {
        this.reportId = builder.reportId;
        this.replacedPart = builder.replacedPart;
        this.breakingDescription = builder.breakingDescription;
        this.theWorkDone = builder.theWorkDone;
        this.orderId = builder.orderId;
        this.date = builder.date;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReplacedPart() {
        return replacedPart;
    }

    public void setReplacedPart(String replacedPart) {
        this.replacedPart = replacedPart;
    }

    public String getBreakingDescription() {
        return breakingDescription;
    }

    public void setBreakingDescription(String breakingDescription) {
        this.breakingDescription = breakingDescription;
    }

    public String getTheWorkDone() {
        return theWorkDone;
    }

    public void setTheWorkDone(String theWorkDone) {
        this.theWorkDone = theWorkDone;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return reportId == report.reportId &&
                orderId == report.orderId &&
                Objects.equals(replacedPart, report.replacedPart) &&
                Objects.equals(breakingDescription, report.breakingDescription) &&
                Objects.equals(theWorkDone, report.theWorkDone) &&
                Objects.equals(date, report.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(reportId, replacedPart, breakingDescription, theWorkDone, orderId, date);
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", replacedPart='" + replacedPart + '\'' +
                ", breakingDescription='" + breakingDescription + '\'' +
                ", theWorkDone='" + theWorkDone + '\'' +
                ", orderId=" + orderId +
                ", date=" + date +
                '}';
    }
}
