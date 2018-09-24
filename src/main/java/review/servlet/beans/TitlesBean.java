package review.servlet.beans;

public class TitlesBean {

    private Integer idTitle;
    private String title;
    private String description;
    private String city;
    private String category;
    private String subCategory;

    public TitlesBean(Integer idTitle, String title, String description, String city, String category, String subCategory) {
        this.idTitle = idTitle;
        this.title = title;
        this.description = description;
        this.city = city;
        this.category = category;
        this.subCategory = subCategory;
    }

    public Integer getIdTitle() {
        return idTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }
}
