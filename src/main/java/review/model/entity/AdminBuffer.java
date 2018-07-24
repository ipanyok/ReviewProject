package review.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "ADMINBUFFER")
@NamedQueries({
        @NamedQuery(name = "AdminBuffer.findAll", query = "select ab from AdminBuffer ab"),
        @NamedQuery(name = "AdminBuffer.findById", query = "select ab from AdminBuffer ab where ab.id = :id"),
        @NamedQuery(name = "AdminBuffer.findByUserId", query = "select ab from AdminBuffer ab where ab.idUser = :idUser")}
)
public class AdminBuffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IDUSER")
    private Integer idUser;

    @Column(name = "CATEGORYNAME")
    private String categoryName;

    @Column(name = "SUBCATEGORYNAME")
    private String subCategoryName;

    @Column(name = "TITLENAME")
    private String titleName;

    @Column(name = "TITLECITY")
    private String titleCity;

    @Column(name = "REVIEW")
    private String review;

    @Column(name = "MARK")
    private int mark;

    @Column(name = "ISADD")
    private boolean isAdd;

    @Column(name = "ISUSERWAIT")
    private boolean isUserWait;

    @Column(name = "ISUSERMODIFY")
    private boolean isUserModify;

    @Column(name = "CANCEL")
    private boolean cancel;

    public AdminBuffer() {
    }

    public AdminBuffer(Integer idUser, String categoryName, String subCategoryName, String titleName, String titleCity, String review, int mark, boolean isAdd, boolean isUserWait, boolean isUserModify, boolean cancel) {
        this.idUser = idUser;
        this.categoryName = categoryName;
        this.subCategoryName = subCategoryName;
        this.titleName = titleName;
        this.titleCity = titleCity;
        this.review = review;
        this.mark = mark;
        this.isAdd = isAdd;
        this.isUserWait = isUserWait;
        this.isUserModify = isUserModify;
        this.cancel = cancel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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

    public String getTitleCity() {
        return titleCity;
    }

    public void setTitleCity(String titleCity) {
        this.titleCity = titleCity;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
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

    public boolean isUserWait() {
        return isUserWait;
    }

    public void setUserWait(boolean userWait) {
        isUserWait = userWait;
    }

    public boolean isUserModify() {
        return isUserModify;
    }

    public void setUserModify(boolean userModify) {
        isUserModify = userModify;
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

        if (idUser != that.idUser) return false;
        if (mark != that.mark) return false;
        if (isAdd != that.isAdd) return false;
        if (isUserWait != that.isUserWait) return false;
        if (isUserModify != that.isUserModify) return false;
        if (cancel != that.cancel) return false;
        if (categoryName != null ? !categoryName.equals(that.categoryName) : that.categoryName != null) return false;
        if (subCategoryName != null ? !subCategoryName.equals(that.subCategoryName) : that.subCategoryName != null)
            return false;
        if (titleName != null ? !titleName.equals(that.titleName) : that.titleName != null) return false;
        if (titleCity != null ? !titleCity.equals(that.titleCity) : that.titleCity != null) return false;
        return review != null ? review.equals(that.review) : that.review == null;
    }

    @Override
    public int hashCode() {
        int result = idUser;
        result = 31 * result + (categoryName != null ? categoryName.hashCode() : 0);
        result = 31 * result + (subCategoryName != null ? subCategoryName.hashCode() : 0);
        result = 31 * result + (titleName != null ? titleName.hashCode() : 0);
        result = 31 * result + (titleCity != null ? titleCity.hashCode() : 0);
        result = 31 * result + (review != null ? review.hashCode() : 0);
        result = 31 * result + mark;
        result = 31 * result + (isAdd ? 1 : 0);
        result = 31 * result + (isUserWait ? 1 : 0);
        result = 31 * result + (isUserModify ? 1 : 0);
        result = 31 * result + (cancel ? 1 : 0);
        return result;
    }
}
