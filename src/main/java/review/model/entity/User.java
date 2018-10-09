package review.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "USER")
@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "select u from User u"),
        @NamedQuery(name = "User.getById", query = "select u from User u where u.id = :id"),
        @NamedQuery(name = "User.getByLogin", query = "select u from User u where u.login = :login")
})

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "FIRSTNAME")
    @Pattern(regexp = "\\+[a-zA-Zа-яА-Яё]+|[a-zA-Zа-яА-Яё]+")
    private String firstName;

    @Column(name = "LASTNAME")
    @NotNull
    @Pattern(regexp = "\\+[a-zA-Zа-яА-Яё]+|[a-zA-Zа-яА-Яё]+")
    private String lastName;

    @Column(name = "LOGIN")
    @Size(min = 3, max = 20)
    @Pattern(regexp = "\\+[a-zA-Zа-яА-Я0-9_\\-]+|[a-zA-Zа-яА-Я0-9_\\-]+")
    private String login;

    @Column(name = "EMAIL")
    @Email
    private String email;

    @Column(name = "PASSWORD")
    @NotNull
    private String password;

    @Column(name = "CITY")
    private String city;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATEDATE")
    private Date createDate;

    public User() {
    }

    public User(String firstName, String lastName, String login, String email, String password, String city, Date date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
        this.city = city;
        this.createDate = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        return city != null ? city.equals(user.city) : user.city == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
