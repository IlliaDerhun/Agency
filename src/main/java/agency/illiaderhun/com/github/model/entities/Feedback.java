package agency.illiaderhun.com.github.model.entities;

import agency.illiaderhun.com.github.annotations.Column;
import agency.illiaderhun.com.github.annotations.JoinColumn;
import agency.illiaderhun.com.github.annotations.JoinTable;
import agency.illiaderhun.com.github.annotations.Table;

import java.sql.Date;
import java.util.Objects;

/**
 * Feedback represents comment from {@link User} customer
 * about {@link Report} from master
 *
 * @author Illia Derhun
 * @version 1.0
 */

@Table(name = "feedback")
public class Feedback {

    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "report_id")
    @JoinTable(name = "report", joinsColums = @JoinColumn(name = "report_di"),
            inverseJoinColumn = @JoinColumn(name = "report_di"))
    private int reportId;

    @Column(name = "date")
    private Date date;

    public Feedback(String comment, int reportId){
        this.comment = comment;
        this.reportId = reportId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
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
        Feedback feedback = (Feedback) o;
        return commentId == feedback.commentId &&
                reportId == feedback.reportId &&
                Objects.equals(comment, feedback.comment) &&
                Objects.equals(date, feedback.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(commentId, comment, reportId, date);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", reportId=" + reportId +
                ", date=" + date +
                '}';
    }
}
