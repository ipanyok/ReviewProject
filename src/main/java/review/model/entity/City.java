package review.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "CITY")
@NamedQueries({
        @NamedQuery(name = "City.getAll", query = "select c from City c"),
        @NamedQuery(name = "City.getById", query = "select c from City c where c.id = :id"),
        @NamedQuery(name = "City.getByName", query = "select c from City c where c.name = :name")
})
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    public City() {
    }

    public City(String name) {
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

        City city = (City) o;

        return name != null ? name.equals(city.name) : city.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
