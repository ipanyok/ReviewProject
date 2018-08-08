package review.servlet.beans;

import java.util.Date;

public class ReviewsBean {

    private String reviewName;
    private int mark;
    private String text;
    private Date date;
    private String userLogin;

    public ReviewsBean(String reviewName, int mark, String text, Date date, String userLogin) {
        this.reviewName = reviewName;
        this.mark = mark;
        this.text = text;
        this.date = date;
        this.userLogin = userLogin;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
