package review.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "ADMINBUFFER")
@NamedQueries({
        @NamedQuery(name = "AdminBuffer.findAll", query = "select ab from AdminBuffer ab"),
        @NamedQuery(name = "AdminBuffer.findById", query = "select ab from AdminBuffer ab where ab.id = :id"),
        @NamedQuery(name = "AdminBuffer.findByUserLogin", query = "select ab from AdminBuffer ab where ab.userName = :userName")}
)
public class AdminBuffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "CATEGORYNAME")
    private String categoryName;

    @Column(name = "SUBCATEGORYNAME")
    private String subCategoryName;

    @Column(name = "TITLENAME")
    private String titleName;

    @Column(name = "TITLEDESCRIPTION")
    private String titleDescription;

    @Column(name = "TITLECITY")
    private String titleCity;

    @Column(name = "REVIEWTEXT")
    private String reviewText;

    @Column(name = "REVIEWNAME")
    private String reviewName;

    @Column(name = "MARK")
    private int mark;

    @Column(name = "ISADD", columnDefinition = "default 'false'")
    private boolean isAdd;

    @Column(name = "CANCEL", columnDefinition = "default 'false'")
    private boolean cancel;

    public AdminBuffer() {
    }

    public AdminBuffer(String userName, String categoryName, String subCategoryName, String titleName, String titleDescription, String titleCity, String reviewText, String reviewName, int mark, boolean isAdd, boolean cancel) {
        this.userName = userName;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
        this.titleName = titleName;
        this.titleDescription = titleDescription;
        this.titleCity = titleCity;
        this.reviewText = reviewText;
        this.reviewName = reviewName;
        this.mark = mark;
        this.isAdd = isAdd;
        this.cancel = cancel;
    }

    public String getTitleCity() {
        return titleCity;
    }

    public void setTitleCity(String titleCity) {
        this.titleCity = titleCity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTitleDescription() {
        return titleDescription;
    }

    public void setTitleDescription(String titleDescription) {
        this.titleDescription = titleDescription;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
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

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminBuffer that = (AdminBuffer) o;

        if (mark != that.mark) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (categoryName != null ? !categoryName.equals(that.categoryName) : that.categoryName != null) return false;
        if (subCategoryName != null ? !subCategoryName.equals(that.subCategoryName) : that.subCategoryName != null)
            return false;
        if (titleName != null ? !titleName.equals(that.titleName) : that.titleName != null) return false;
        if (titleDescription != null ? !titleDescription.equals(that.titleDescription) : that.titleDescription != null)
            return false;
        if (reviewText != null ? !reviewText.equals(that.reviewText) : that.reviewText != null) return false;
        return reviewName != null ? reviewName.equals(that.reviewName) : that.reviewName == null;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (subCategoryName != null ? subCategoryName.hashCode() : 0);
        result = 31 * result + (titleName != null ? titleName.hashCode() : 0);
        result = 31 * result + (titleDescription != null ? titleDescription.hashCode() : 0);
        result = 31 * result + (reviewText != null ? reviewText.hashCode() : 0);
        result = 31 * result + (reviewName != null ? reviewName.hashCode() : 0);
        result = 31 * result + mark;
        return result;
    }
}
