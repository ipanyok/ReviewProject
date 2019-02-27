package review.model.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TITLE")
@NamedQueries({
        @NamedQuery(name = "Title.getAll", query = "select t from Title t"),
        @NamedQuery(name = "Title.getById", query = "select t from Title t where t.id = :id"),
        @NamedQuery(name = "Title.getByName", query = "select t from Title t where t.title = :title"),
        @NamedQuery(name = "Title.getBySubCategoryId", query = "select t from Title t where t.idSubCategory = :idSubCategory")
})
public class Title implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IDSUBCATEGORY")
    private Integer idSubCategory;

    @Column(name = "IDCITY")
    private Integer idCity;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PICTURE")
    @Lob
    private byte[] picture;

    public Title() {
    }

    public Title(Integer idSubCategory, String title, String description, Integer idCity) {
        this.idSubCategory = idSubCategory;
        this.title = title;
        this.description = description;
        this.idCity = idCity;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSubCategory() {
        return idSubCategory;
    }

    public void setIdSubCategory(Integer idSubCategory) {
        this.idSubCategory = idSubCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Title title1 = (Title) o;

        if (idSubCategory != title1.idSubCategory) return false;
        if (idCity != title1.idCity) return false;
        if (title != null ? !title.equals(title1.title) : title1.title != null) return false;
        return description != null ? description.equals(title1.description) : title1.description == null;
    }

    @Override
    public int hashCode() {
        int result = idSubCategory;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + idCity;
        return result;
    }
}
