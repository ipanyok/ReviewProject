package review.servlet.beans;

public class TitlesBean {

    private Integer idTitle;
    private String title;
    private String description;
    private String city;

    public TitlesBean(Integer idTitle, String title, String description, String city) {
        this.idTitle = idTitle;
        this.title = title;
        this.description = description;
        this.city = city;
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
}
