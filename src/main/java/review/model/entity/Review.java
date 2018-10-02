package review.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "REVIEW")
@NamedQueries({
        @NamedQuery(name = "Review.getAll", query = "select r from Review r"),
        @NamedQuery(name = "Review.getById", query = "select r from Review r where r.id = :id"),
        @NamedQuery(name = "Review.getByTitleId", query = "select r from Review r where r.idTitle = :idTitle"),
        @NamedQuery(name = "Review.getLastAdded", query = "select r from Review r order by r.date desc")
})
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IDTITLE")
    private Integer idTitle;

    @Column(name = "IDUSER")
    private Integer idUser;

    @Column(name = "TEXT")
    @NotEmpty
    private String text;

    @Column(name = "MARK")
    private int mark;

    @Column(name = "REVIEWNAME")
    @NotEmpty
    private String reviewName;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE")
    private Date date;

    public Review() {
    }

    public Review(Integer idTitle, Integer idUser, String text, int mark, String reviewName) {
        this.idTitle = idTitle;
        this.idUser = idUser;
        this.text = text;
        this.mark = mark;
        this.reviewName = reviewName;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdTitle() {
        return idTitle;
    }

    public void setIdTitle(Integer idTitle) {
        this.idTitle = idTitle;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
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

        Review review = (Review) o;

        if (mark != review.mark) return false;
        if (text != null ? !text.equals(review.text) : review.text != null) return false;
        if (reviewName != null ? !reviewName.equals(review.reviewName) : review.reviewName != null) return false;
        return date != null ? date.equals(review.date) : review.date == null;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + mark;
        result = 31 * result + (reviewName != null ? reviewName.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
