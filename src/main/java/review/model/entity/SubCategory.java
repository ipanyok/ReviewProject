package review.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "SUBCATEGORY")
@NamedQueries({
        @NamedQuery(name = "SubCategory.getAll", query = "select s from SubCategory s"),
        @NamedQuery(name = "SubCategory.getById", query = "select s from SubCategory s where s.id = :id"),
        @NamedQuery(name = "SubCategory.getByCategoryId", query = "select s from SubCategory s where s.idCategory = :idCategory")
})
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "IDCATEGORY")
    private Integer idCategory;

    @Column(name = "NAME")
    private String name;

    public SubCategory() {
    }

    public SubCategory(Integer idCategory, String name) {
        this.idCategory = idCategory;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SubCategory that = (SubCategory) o;

        if (idCategory != that.idCategory) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = idCategory;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
