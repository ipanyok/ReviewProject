package review.model.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORY")
@NamedQueries({
        @NamedQuery(name = "Category.getAll", query = "select c from Category c"),
        @NamedQuery(name = "Category.getById", query = "select c from Category c where c.id = :id"),
        @NamedQuery(name = "Category.getByName", query = "select c from Category c where c.name = :name")
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @NotNull
    @Column(unique = true, name = "NAME")
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

        Category category = (Category) o;

        return name != null ? name.equals(category.name) : category.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
